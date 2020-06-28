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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import cn.magic.TypeConvertFactory;
import cn.mayfly.BeanException;
import cn.mayfly.BeanParameter;
import cn.mayfly.element.InstanceCreation;
import cn.mayfly.element.InvocationInterception;
import cn.mayfly.impl.definition.BaseDefinition;
import cn.mayfly.impl.definition.ClassDefinition;
import cn.mayfly.impl.definition.InstanceDefinition;
import cn.mayfly.impl.exception.BeanClassException;
import cn.mayfly.impl.exception.BeanClassModifiedException;
import cn.mayfly.impl.exception.BeanInstanceCreateException;
import cn.mayfly.impl.exception.BeanInstanceInitException;
import cn.mayfly.impl.intercept.ClassWrapEditor;
import cn.mayfly.impl.intercept.InterceptorChain;
import cn.mayfly.impl.util.BeanUtil;
import cn.mayfly.impl.util.ClassUtil;
import cn.mayfly.impl.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对象实例工厂
 * 
 * @author chris liao
 */
public class InstanceCreateFactory {
	/**
	 * logger
	 */
	private Logger logger = LoggerFactory.getLogger(InstanceCreateFactory.class);
	
	/**
	 * 类的编辑改造器
	 */
	private ClassWrapEditor classWrapEditor = new ClassWrapEditor();
	
	
	/**
	 * 创建对象实例
	 */
	public Object createInstance(InstanceContainer container,BaseDefinition definition,Map tempMap)throws BeanException{
		try {
			Object beanInstance = null;
			if(definition instanceof InstanceDefinition){
				InstanceDefinition instanceDefinition =(InstanceDefinition)definition;
				beanInstance = instanceDefinition.getSingletonInstance();//原始的注册值
			}else{
				ClassDefinition classDefinition =(ClassDefinition)definition;
				if(needModifyClassForInterception(classDefinition)){//是否需要修改类
					modifyBeanClassForInterception(classDefinition);
				}
				
				Class beanClass = classDefinition.getBeanClass();
				if(classDefinition.getBeanWrapClass()!=null)
					beanClass = classDefinition.getBeanWrapClass();
				
				InstanceCreation creation = classDefinition.getInstanceCreation();
				Object factoryBeanRefID = creation.getFactoryBeanRefID();
				String factoryMethodName = creation.getFactoryMethodName();
				BeanParameter[]parameters = creation.getCreateParameters();
				Object[] paramValues = container.getParamConvertFactory().convertParameters(container,parameters,tempMap);
				
			  if(factoryBeanRefID!=null){
					Object factoryBean = container.getBean(factoryBeanRefID,tempMap);
					beanInstance = createByFactoryBean(factoryBean,factoryMethodName,paramValues,container.getBeanTypeConvertFactory());
				}else if(!StringUtil.isNull(factoryMethodName)){
					beanInstance = createByFactoryMethod(beanClass,factoryMethodName,paramValues,container.getBeanTypeConvertFactory());
				}else{
					beanInstance = createByReflection(beanClass,paramValues,container.getBeanTypeConvertFactory());
				}
				
				if(beanInstance!=null)
					initBeanInstance(definition.getBeanID(),beanInstance,definition.getInitMethod());
				logger.debug("BeanId({})-Created a instance with class:"+beanClass.getName(),definition.getBeanID());
			}
			
			return beanInstance;
		} catch (BeanException e) {
			throw new BeanInstanceCreateException("Failed to create instance for class["+definition.getBeanClass()+"] at id:" + definition.getBeanID(),e.getCauseException());
		}
	}
	
	/**
	 * 工厂bean创建实例
	 */
	private Object createByFactoryBean(Object factoryBean, String factoryMethod, Object[]paramValues, TypeConvertFactory convertFactory)throws BeanException{
		try {
			return BeanUtil.invokeMethod(factoryBean,factoryBean.getClass(),factoryMethod,paramValues,convertFactory);
		}catch(InvocationTargetException e){
			throw new BeanException(e.getTargetException());
		} catch (Exception e) {
			throw new BeanException(e);
		} 
	}
	
	/**
	 *工厂方法创建实例
	 */
	private Object createByFactoryMethod(Class beanClass, String factoryMethod, Object[]paramValues, TypeConvertFactory convertFactory)throws BeanException{
		try {
			return BeanUtil.invokeMethod(null,beanClass,factoryMethod,paramValues,convertFactory);
		}catch(InvocationTargetException e){
			throw new BeanException(e.getTargetException());
		} catch (Exception e) {
			throw new BeanException(e);
		} 
	}

	/**
	 * 反射方法创建实例
	 */
	private Object createByReflection(Class beanClass, Object[]paramValues, TypeConvertFactory convertFactory)throws BeanException{
		try {
			return BeanUtil.createInstance(beanClass,paramValues,convertFactory);
		}catch(InvocationTargetException e){
			throw new BeanException(e.getTargetException());
		} catch (Exception e) {
			throw new BeanException(e);
		} 
	}
	
	/*************************************私有方法*************************************************/
	/**
	 * 检查是否需要编辑类以
	 */
	private boolean needModifyClassForInterception(ClassDefinition definition)throws BeanException{
		if(!definition.isInstaceCreated()&&!ClassUtil.isAbstractClass(definition.getBeanClass()) && definition.getBeanWrapClass() ==null){
			Object beanid = definition.getBeanID();
			Class[] ProxyInterfaces = definition.getProxyInterfaces();
			InstanceCreation creation = definition.getInstanceCreation();
			List interceptList = definition.getInvocationInterceptionList();
			InterceptorChain interceptorChain = definition.getInterceptorChain();
			
			if(creation!=null){
		  	if(creation.getFactoryBeanRefID()!=null || !StringUtil.isNull(creation.getFactoryMethodName())){
		  		if(!interceptList.isEmpty() && ProxyInterfaces ==null){
		  			logger.warn("BeanId({})-Interception will be disabled,not support factory-bean/factory-method without proxy intefaces",beanid);
		  			return false;
		  		}
		  	}
		  }
		
			if(!interceptList.isEmpty()&& (ProxyInterfaces == null || ProxyInterfaces.length==0)&& interceptorChain == null){
				if(ClassUtil.isFinalClass(definition.getBeanClass()))
					throw new BeanClassException("Intercepted bean("+beanid+")class["+definition.getBeanClass()+"]can't be final type without proxy intefaces");
				else
				  return true;
			}else
				return false;
		 }else{
			 return false;
		 }
	}
	
	/**
	 * 修改类,为拦截做准备
	 */
	private void modifyBeanClassForInterception(ClassDefinition definition)throws BeanException{
		try {
			List interceptionList = definition.getInvocationInterceptionList();
			Method[] interceptedMethods = new Method[interceptionList.size()];

			for(int i=0,size=interceptionList.size();i<size;i++){
				InvocationInterception interception =(InvocationInterception)interceptionList.get(i);
				interceptedMethods[i]= BeanUtil.findMethod(
									definition.getBeanClass(),
									interception.getMethodName(),
									interception.getMethodParamTypes());
			}
			
			Class wrapClass = classWrapEditor.createWrapClass(definition.getBeanClass(),interceptedMethods);
			definition.setBeanWrapClass(wrapClass);
			definition.setClassModified(true);
			logger.debug("BeanId({})-Created AOP wrapper class:"+wrapClass.getName(),definition.getBeanID());
		} catch (Exception e) { 
			throw new BeanClassModifiedException(e);
		}
	}
	
	/**
	 * 调用初始化方法完成
	 */
	private void initBeanInstance(Object beanId,Object instance,Method initMethod)throws BeanException{
		if(initMethod!=null){
			try {
		    BeanUtil.invokeMethod(instance,initMethod,new Object[0]);
				logger.debug("BeanId({})-Inited instance:"+instance,beanId);
			} catch (InvocationTargetException e) {
				throw new BeanInstanceInitException("Bean("+ beanId +") - Failed to init instance",e.getTargetException());
			}catch (Exception e) {
				throw new BeanInstanceInitException("Bean("+ beanId +") - Failed to init instance",e);
			}
		}
	}
}
