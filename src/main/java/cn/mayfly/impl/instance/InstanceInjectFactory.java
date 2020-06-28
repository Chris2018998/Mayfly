/*
 * Copyright Chris2018998
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.mayfly.impl.instance;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import cn.magic.TypeConvertFactory;
import cn.mayfly.BeanException;
import cn.mayfly.BeanParameter;
import cn.mayfly.element.AutowiredType;
import cn.mayfly.element.InjectionField;
import cn.mayfly.element.InjectionInvocation;
import cn.mayfly.element.InjectionProperty;
import cn.mayfly.impl.definition.BaseDefinition;
import cn.mayfly.impl.exception.BeanInvocationInjectException;
import cn.mayfly.impl.exception.BeanParentClassException;
import cn.mayfly.impl.exception.BeanPropertyInjectException;
import cn.mayfly.impl.util.ArrayUtil;
import cn.mayfly.impl.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bean实例注入工厂
 * 1：字段注入（自动注入和参数注入）
 * 2：属性注入
 * 3：方法调用注入工厂
 * 
 * @author Chris Liao
 * @version 1.0
 */

public class InstanceInjectFactory {

	/**
	 * logger
	 */
	private Logger logger = LoggerFactory.getLogger(InstanceInjectFactory.class);

	/**
	 * 实例执行注入,自动装载应该放置在最前，属性注射部分值定义部分为最后的值
	 */
	public Object injectInstance(Object instance,BaseDefinition definition,InstanceContainer container,Map bijectionMap)throws BeanException{
		Object beanId = definition.getBeanID();
		List injectFieldList = new ArrayList();
		List injectPropertyList = new ArrayList();
		List injectionInvocationList = definition.getInjectionInvocationList();
		this.resolveParentInjectPropertyList(definition,container,injectFieldList,injectPropertyList);
		
	  Collections.reverse(injectFieldList);
	  Collections.reverse(injectPropertyList);
	
		for(int i=0,size=injectFieldList.size();i<size;i++)//字段
			injectField(beanId,instance,(InjectionField)injectFieldList.get(i),container,bijectionMap);//字段注入
		for(int i=0,size=injectPropertyList.size();i<size;i++)//属性set
			injectProperty(beanId,instance,(InjectionProperty)injectPropertyList.get(i),container,bijectionMap);//属性注入
		for(int i=0,size=injectionInvocationList.size();i<size;i++)//方法调用
			injectInvocation(beanId,instance,(InjectionInvocation)injectionInvocationList.get(i),container,bijectionMap);//方法注入
		
		return instance;
	}
	
  /**
   * 字段注入
   */
  private void injectField(Object beanId,Object instance,InjectionField injectField,InstanceContainer container,Map bijectionMap)throws BeanException{
		try {
			Field field =BeanUtil.findField(instance.getClass(),injectField.getFieldName());
			Object paramValue=null;
			if(injectField.isAutowired()){
				AutowiredType autowiredType = injectField.getAutowiredType();
				if(AutowiredType.BY_NAME.equals(autowiredType)){//通过字段名装载
					paramValue = container.getBean(field.getName(),bijectionMap);
				}else{//通过类型名装载
					paramValue = container.getBeanByClass(field.getType(),bijectionMap);
				}
			}else{//通过参数装载
				BeanParameter param = injectField.getParameter();
				paramValue=container.getParamConvertFactory().convertParameter(container,param,bijectionMap);
			}
			
			if(paramValue!=null){//不为空才注入
				if(field!=null){
			    field.setAccessible(true);
				  TypeConvertFactory convertFact=container.getBeanTypeConvertFactory();
				  Object fieldValue=BeanUtil.convertValue(paramValue,field.getType(),convertFact);
				
				  field.set(instance, fieldValue);
				  logger.debug("BeanId({})-Auto injected field["+field.getName()+"]with value:"+fieldValue,beanId);
				}else{
					if(Collection.class.isInstance(instance) && Collection.class.isInstance(paramValue)){//collection
						Collection souceBean =(Collection)instance;
						Collection collParam =(Collection)paramValue;
						souceBean.addAll(collParam);
					}else if(Map.class.isInstance(instance) && Map.class.isInstance(paramValue)){//Map
						Map souceBean =(Map)instance;
						Map mapParam =(Map)paramValue;
						souceBean.putAll(mapParam);
					}else if(ArrayUtil.isArray(instance)){//数组
					 if(ArrayUtil.isArray(paramValue) && ArrayUtil.getArrayType(instance).isAssignableFrom(ArrayUtil.getArrayType(paramValue))){
						 int len1 = ArrayUtil.getArraySize(instance); 
						 int len2 = ArrayUtil.getArraySize(paramValue); 
						 int len = Math.min(len1,len2);
						 for(int i=0;i<len;i++){
							 ArrayUtil.setObject(instance,i,ArrayUtil.getObject(paramValue,i));
						 }
					 }else{
						 throw new BeanException("Bean("+ beanId+") - Not found field["+injectField.getFieldName()+"] in bean["+beanId+"]");
					 }
					}else{
						 throw new BeanException("Bean("+ beanId+") - Not found field["+injectField.getFieldName()+"] in bean["+beanId+"]");
					}
				}
			}
	  }catch(BeanException e){
    	throw e;
    } catch(Exception e) {
			throw new BeanPropertyInjectException("Bean("+ beanId+") - Failed to inject field[" +injectField.getFieldName() +"]",e);
		}
  }

  /**
   * 属性注入
   */
  private void injectProperty(Object beanId,Object instance,InjectionProperty property,InstanceContainer container,Map bijectionMap)throws BeanException {
  	try {
			BeanParameter param = property.getParameter();
			String methodName=getPropertySetMethodName(property.getPropertyName());
			Object paramValue=container.getParamConvertFactory().convertParameter(container,param,bijectionMap);
	
			if(paramValue!=null){//不为空才注入
				try {
					BeanUtil.invokeMethod(instance,instance.getClass(),methodName,new Object[]{paramValue},container.getBeanTypeConvertFactory());
					logger.debug("BeanId({})-Injected property["+property.getPropertyName()+"]with value:"+paramValue,beanId);
				}catch (NoSuchMethodException e) {
					if(Collection.class.isInstance(instance) && Collection.class.isInstance(paramValue)){//collection
						Collection souceBean =(Collection)instance;
						Collection collParam =(Collection)paramValue;
						souceBean.addAll(collParam);
					}else if(Map.class.isInstance(instance) && Map.class.isInstance(paramValue)){//Map
						Map souceBean =(Map)instance;
						Map mapParam =(Map)paramValue;
						souceBean.putAll(mapParam);
					}else if(ArrayUtil.isArray(instance)){//数组
					 if(ArrayUtil.isArray(paramValue) && ArrayUtil.getArrayType(instance).isAssignableFrom(ArrayUtil.getArrayType(paramValue))){
						 int len1 = ArrayUtil.getArraySize(instance); 
						 int len2 = ArrayUtil.getArraySize(paramValue); 
						 int len = Math.min(len1,len2);
						 for(int i=0;i<len;i++){
							 ArrayUtil.setObject(instance,i,ArrayUtil.getObject(paramValue,i));
						 }
					 }else{
						 throw e;
					 }
					}else{
						throw e;
					}
				}
			}
    }catch(BeanException e){
    	throw e;
    }catch(InvocationTargetException e){
    	throw new BeanPropertyInjectException("Bean("+ beanId+") - Failed to inject property[" + property.getPropertyName() +"]",e.getTargetException());
    } catch(Exception e) {
			throw new BeanPropertyInjectException("Bean("+ beanId+") - Failed to inject property[" + property.getPropertyName() +"]",e);
		}
  }
 
  /**
   * 方法调用注入
   */
  private void injectInvocation(Object beanId,Object instance,InjectionInvocation invocation,InstanceContainer container,Map tempMap)throws BeanException {
    try {
    	String methodName = invocation.getMethodName();
      BeanParameter[] parameters = invocation.getParameters();
      Object[] paramValues = container.getParamConvertFactory().convertParameters(container,parameters,tempMap);
			BeanUtil.invokeMethod(instance,instance.getClass(),methodName,paramValues,container.getBeanTypeConvertFactory());
 
      //logger.debug(beanId,"Injected invocation["+invocation.getMethodName()+"]with values:"+paramValues);
    }catch(BeanException e){
    	throw e;
    }catch(InvocationTargetException e){
    	throw new BeanInvocationInjectException("Bean("+ beanId+") - Failed to inject method[" + invocation.getMethodName() +"]",e.getTargetException());
    }catch(Exception e){
    	throw new BeanInvocationInjectException("Bean("+ beanId+") - Failed to inject method[" + invocation.getMethodName() +"]",e);
    }
  }
  
  /**
   * return property set method name
   */
  private String getPropertySetMethodName(String name) {
    String firstChar = name.substring(0,1);
    String lastString = name.substring(1,name.length());
    return new StringBuilder("set").append(firstChar.toUpperCase()).append(lastString).toString();
  }
  
  /**
   * 义查找父类定义的自动装载属性和注射属性
   */
  private void resolveParentInjectPropertyList(BaseDefinition definition,InstanceContainer container,List fieldList,List propertyList) throws BeanException {
		Object beanID = definition.getBeanID();
		Class beanClass = definition.getBeanClass();
		List autowireFieldList = definition.getInjectionFieldList();
		List injectionPropertyList = definition.getInjectionPropertyList();
		
		for(int i=0,size=autowireFieldList.size();i<size;i++){
			Object element = autowireFieldList.get(i);
			if(!fieldList.contains(element))
				fieldList.add(element);
		}
		
		for(int i=0,size=injectionPropertyList.size();i<size;i++){
			Object element = injectionPropertyList.get(i);
			if(!propertyList.contains(element))
				propertyList.add(element);
		}
	
		Object parentID = definition.getParentReferenceId();
		if (parentID != null) {
			BaseDefinition parentDefinition = (BaseDefinition)container.getBeanDefinition(parentID);
			Class parentClass = parentDefinition.getBeanClass();
			if (!parentClass.isAssignableFrom(beanClass)) {
				throw new BeanParentClassException("Bean id("+parentDefinition.getBeanID()+")class["+parentDefinition.getBeanClass() + "]is not a parent for bean class["+beanClass+ "] at id:" +beanID);
			}
			resolveParentInjectPropertyList(parentDefinition, container,fieldList,propertyList);
		}
	}
}
