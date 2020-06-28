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
package cn.mayfly.impl.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import cn.magic.TypeConvertFactory;
import cn.mayfly.BeanException;

/**
 * 反射辅助类
 *
 * @author Chris Liao
 * @version 1.0
 */

public class BeanUtil{

  /**
   * instantiate a object by name
   */
  public static Object createInstance(Class beanClass, TypeConvertFactory convertFactory)throws NoSuchMethodException,SecurityException,
  					IllegalAccessException,InstantiationException,IllegalArgumentException,InvocationTargetException {
  	
  	return createInstance(beanClass,new Class[0],convertFactory);
  }
 
  /**
   * instantiate a object by name
   */
  public static Object createInstance(Class beanClass, Object[] paramValues, TypeConvertFactory convertFactory)throws NoSuchMethodException,SecurityException,
  				IllegalAccessException,InstantiationException,IllegalArgumentException,InvocationTargetException {
  	 
  	checkBeanClass(beanClass);
  	checkParameterValues(paramValues);
  	Constructor targetConstructor = null;
  	
  	try {
  		Class[]paramTypes = getValueTypes(paramValues);
			targetConstructor = beanClass.getDeclaredConstructor(paramTypes);
			return targetConstructor.newInstance(paramValues);
		}catch (Exception e){
	  	Object[]targetparamValues=null;
			Constructor[] allConstructors = beanClass.getDeclaredConstructors();
			for(int i=0,n=allConstructors.length;i<n;i++){
				Constructor constructor = allConstructors[i];
				Class[]parameterTypes=constructor.getParameterTypes();
				if(parameterTypes.length==paramValues.length){
					try {
						targetparamValues=convertValues(paramValues,parameterTypes,convertFactory);
						targetConstructor = allConstructors[i];
						targetConstructor.setAccessible(true);
						break;
					} catch (BeanException e1) {
						 
					}
				}
			}
			
	  	if(targetConstructor==null)
	  		throw new NoSuchMethodException(new StringBuilder("Not found matched constructor from class: ").append(beanClass).toString());
	  	else
	  	 return targetConstructor.newInstance(targetparamValues);
		}
  }

  /**
   * 通过反射调用某个方法
   */
  public static Object invokeMethod(Object bean, Class beanClass, String methodName, Object[] paramValues, TypeConvertFactory convertFactory)throws NoSuchMethodException,SecurityException,
                                                                                              IllegalAccessException,IllegalArgumentException,
                                                                                              InvocationTargetException{

  	Method targetMethod=null;
  	if(bean!=null)
  		beanClass=bean.getClass();
  	
  	checkMethodName(methodName);
  	checkParameterValues(paramValues);
  	checkBeanClass(beanClass);
  		
  	try{
  		Class[]paramTypes = getValueTypes(paramValues);
  		targetMethod = beanClass.getMethod(methodName,paramTypes);
  		return BeanUtil.invokeMethod(bean,targetMethod,paramValues);
		}catch (Exception e){
	  	Object[]targetParamValues=null;
			Method[] allMethods = beanClass.getMethods();
			for(int i = 0,n=allMethods.length; i<n; i++) {
				Method method=allMethods[i];
				Class[]parameterTypes=method.getParameterTypes();
				if(method.getName().equals(methodName)  && parameterTypes.length==paramValues.length) {
					try {
						targetParamValues=convertValues(paramValues,parameterTypes,convertFactory);
						targetMethod = method;
						break;
					} catch (BeanException e1) {
						 
					}
				}
			}
			
		 	if(targetMethod==null)
		 		throw new NoSuchMethodException(new StringBuilder("Not found matched method[").append(methodName).append("]from class: ").append(beanClass).toString());
	  	else
	  	  return BeanUtil.invokeMethod(bean,targetMethod,targetParamValues);
		}
  }
  
  /**
   * 通过反射调用某个方法
   */
  public static Object invokeMethod(Object bean,Method method,Object[] paramValues)throws IllegalAccessException,IllegalArgumentException,InvocationTargetException{
  	if(method!=null){
  		method.setAccessible(true);
  		return method.invoke(bean,paramValues);
  	}else{
  		return null;
  	}
  }
  
  /**
   * 依照字段名查找
   * 
   */
  public static Field findField(Class beanClass,String fieldName)throws NoSuchFieldException, SecurityException{
  	Field field = beanClass.getDeclaredField(fieldName);
    if(field==null)
  		field = beanClass.getField(fieldName);
  	if(field==null && beanClass.getSuperclass()!=null){
  		field = findField(beanClass.getSuperclass(),fieldName);
  	}
  	return field;
  }
  
  
  /**
   * 依照参数类型组查找方法
   * fuzzy
   * 1:false: 准确匹配 
   * 2:true:  模糊匹配
   * 
   */
  public static Method findMethod(Class beanClass,String methodName,Class[] paramTypes)throws NoSuchMethodException, SecurityException{
  	checkBeanClass(beanClass);
  	checkMethodName(methodName);
  	Method targetMethod = null; 
  
  	try {
  		targetMethod = beanClass.getMethod(methodName,paramTypes);
		}catch (Exception e){
			Method[] allMethods = beanClass.getMethods();
			for(int i = 0,n=allMethods.length; i < n; i++) {
				Class[] methodParameterTypes = allMethods[i].getParameterTypes();
				if(allMethods[i].getName().equals(methodName) &&  ClassUtil.isMatchedClasses(methodParameterTypes,paramTypes)) {
					targetMethod = allMethods[i];
					break;
				}
			}
		}

	 	if(targetMethod!=null)
  		return targetMethod;
  	else
  		throw new NoSuchMethodException("Not found matched method["+methodName+"]from class: " + beanClass);
  }
  
  /**
   * 检查类不能为空
   */
  private static void checkBeanClass(Class beanclass)throws IllegalArgumentException{
  	if(beanclass==null)
  		throw new IllegalArgumentException("Target class can't be null");
  }
  
  /**
   * 检查方法名不能为空
   */
  private static void checkMethodName(String methodName)throws IllegalArgumentException{
  	if(methodName==null || methodName.trim().length()==0)
  		throw new IllegalArgumentException("Target method name can't be null");
  }
 
  /**
   * 检查参数不能为空
   */
  private static void checkParameterValues(Object[] paramValues)throws IllegalArgumentException{
  	if(paramValues==null)
  		throw new IllegalArgumentException("Parameter values can't be null");
  }
  
  /**
   * 获得值的类型
   */
  private static Class[] getValueTypes(Object[] values){
  	Class[]valueTypes = new Class[values==null?0:values.length];
		for(int i=0,n=valueTypes.length;i<n;i++){
			if(values[i]!=null)
				valueTypes[i] =values[i].getClass();
		}
		return valueTypes;
  }
  
	/**
	 * 获得方法的签名串
	 */
  public static String getMethodSignature(Method method) {
  	Class[] paramTypes = method.getParameterTypes();
  	Class returnType = method.getReturnType();
  	StringBuilder SignalBuff = new StringBuilder();
  	SignalBuff.append(Symbols.Left_Parentheses);
  	for(int i=0,n=paramTypes.length;i<n;i++){
  		SignalBuff.append(getClassSignature(paramTypes[i]));
  	}
  	
  	SignalBuff.append(Symbols.Right_Parentheses);
  	SignalBuff.append(getClassSignature(returnType));
    return SignalBuff.toString();
	}
  
  /**
   * 获得类的签名
   */
  private static String getClassSignature(Class cl) {
    StringBuilder sbuf = new StringBuilder();
      while (cl.isArray()) {
         sbuf.append('[');
         cl = cl.getComponentType();
      }
      
      if (cl.isPrimitive()) {
          if (cl == Integer.TYPE) {
             sbuf.append('I');
          } else if (cl == Byte.TYPE) {
              sbuf.append('B');
          } else if (cl == Long.TYPE) {
              sbuf.append('J');
          } else if (cl == Float.TYPE) {
              sbuf.append('F');
          } else if (cl == Double.TYPE) {
              sbuf.append('D');
          } else if (cl == Short.TYPE) {
              sbuf.append('S');
          } else if (cl == Character.TYPE) {
              sbuf.append('C');
          } else if (cl == Boolean.TYPE) {
              sbuf.append('Z');
          } else if (cl == Void.TYPE) {
              sbuf.append('V');
          } else {
              throw new InternalError();
          }
      } else {
          sbuf.append('L' + cl.getName().replace('.', '/') + ';');
      }
      return sbuf.toString();
  }
  
	/**
	 * Convert a value to type
	 */
	public static Object[] convertValues(Object[] values, Class[] types, TypeConvertFactory convertFactory)throws BeanException{
		Object[]newValues=new Object[types.length];
		for(int i=0,n=types.length;i<n;i++){
			newValues[i]=convertValue(values[i],types[i],convertFactory);
		}
		return newValues;
	}
	
	/**
	 * Convert a value to type
	 */
	public static Object convertValue(Object value, Class type, TypeConvertFactory convertFactory) throws BeanException{
		try {
			if(value==null){
				return value;
			}else if(type.isInstance(value)){
				return value;
			}else if(convertFactory.containsTypeConverter(type)){
				return convertFactory.convert(value,type);
			}else if(Map.class.isAssignableFrom(type) && Map.class.isInstance(value) && !type.isInstance(value)){//应对特殊Map子类
				Map oldMap =(Map)value;  
				Map newMap =(Map)type.newInstance();
				newMap.putAll(oldMap);
				return newMap;
			}else if(Collection.class.isAssignableFrom(type) && Collection.class.isInstance(value) && !type.isInstance(value)){//应对特殊Collection子类
				Collection oldCol =(Collection)value;
				Collection newCol =(Collection)type.newInstance();
				newCol.addAll(oldCol);
			  return newCol;
			}else if(type.isArray()){//目标类型为数组
				int arraySize;
				Object array=null;
				Class arrayType=type.getComponentType();

				if(value instanceof String){//被转换的对象为字符串
				  String text =(String)value;
				  String[]stringArray=new String[]{text};
				  if(text.indexOf(Symbols.Comma)!=-1)
				   stringArray=StringUtil.split(text,Symbols.Comma);
				  else if(text.indexOf(Symbols.Vertical_Line)!=-1)
				   stringArray=StringUtil.split(text,Symbols.Vertical_Line);
				  else if(text.indexOf(Symbols.Space)!=-1)
				   stringArray=StringUtil.split(text,Symbols.Space);
				  
				  arraySize=stringArray.length;
				  array=ArrayUtil.createArray(arrayType,stringArray.length);
					for(int i=0;i<arraySize;i++)
						ArrayUtil.setObject(array,i,convertFactory.convert(stringArray[i],arrayType));
				}else if(ArrayUtil.isArray(value)){//被转换的对象也是数组
					arraySize = ArrayUtil.getArraySize(value);
				  array=ArrayUtil.createArray(arrayType,arraySize);
					for(int i=0;i<arraySize;i++)
						ArrayUtil.setObject(array,i,convertFactory.convert(ArrayUtil.getObject(value,i),arrayType));
				}
				if(array==null)
				 throw new BeanException(new StringBuilder("Cann't convert object[").append(value).append("]to type[").append(type).append("]").toString());
				else
				 return array;
			}else{
				throw new BeanException(new StringBuilder("Cann't convert object[").append(value).append("]to type[").append(type).append("]").toString());
			}
		}catch(BeanException e){
			throw e;
		} catch (Throwable e) {
			throw new BeanException(new StringBuilder("Cann't convert object[").append(value).append("]to type[").append(type).append("]").toString(),e);
		}
	}
}
