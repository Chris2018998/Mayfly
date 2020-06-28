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

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import cn.magic.TypeConvertFactory;
import cn.mayfly.BeanException;
import cn.mayfly.BeanParameter;
import cn.mayfly.BeanParameterException;
import cn.mayfly.element.parameter.ClassParameter;
import cn.mayfly.element.parameter.ContainerParameter;
import cn.mayfly.element.parameter.InstanceParameter;
import cn.mayfly.element.parameter.PrimitiveParameter;
import cn.mayfly.element.parameter.ReferenceParameter;
import cn.mayfly.impl.util.ArrayUtil;
import cn.mayfly.impl.util.BeanUtil;
import cn.mayfly.impl.util.ClassUtil;

/**
 * 将参数定义转化为真实的对象值
 * 
 * @author Chris Liao
 * @version 1.0
 */

public class ParamConvertFactory {

	/**
	 * 将参数定义转化为真实的对象值
	 */
	public Object convertParameter(InstanceContainer container,BeanParameter parameter,Map tempMap) throws BeanException {
		return convertParamToValue(container, parameter, tempMap);
	}

	/**
	 * 将参数定义转化为真实的对象值
	 */
	public Object[] convertParameters(InstanceContainer container,BeanParameter[] parameters, Map tempMap) throws BeanException {
		Object[] paramValues = new Object[parameters == null ? 0: parameters.length];
		for(int i=0,n=paramValues.length; i<n;i++)
			paramValues[i] = convertParamToValue(container, parameters[i], tempMap);
		
		return paramValues;
	}

	/**
	 * 真实执行转换的内部方法
	 */
	private Object convertParamToValue(InstanceContainer container,BeanParameter param, Map tempMap) throws BeanException {
		Object paramValue = null;
		if (param instanceof PrimitiveParameter) {
			paramValue = param.getParameterContent();
		} else if (param instanceof InstanceParameter) {
			paramValue = param.getParameterContent();
		} else if (param instanceof ContainerParameter) {
			paramValue = param.getParameterContent();
		} else if (param instanceof ClassParameter) {
			Object desc = param.getParameterContent();
			paramValue = createClassParamValue(desc,container.getBeanTypeConvertFactory());
		} else if (param instanceof ReferenceParameter) {
			Object refKey = param.getParameterContent();
			if(tempMap != null && tempMap.containsKey(refKey)) 
				paramValue = tempMap.get(refKey);
			else
			 paramValue = container.getBean(refKey,tempMap);
		}

		/**
		 * if instance is a collection
		 */
		if (paramValue instanceof Collection) {
			paramValue = digCollectionParameter(container,(Collection)paramValue,tempMap);
		} else if (paramValue instanceof Map) {
			paramValue = digMapParameter(container, (Map)paramValue,tempMap);
		} else if (paramValue!=null && ArrayUtil.isArray(paramValue)){
			paramValue = digArrayParameter(container,paramValue,tempMap);
		}

		return paramValue;
	}

	/**
	 * search all value in a collection
	 */
	public Collection digCollectionParameter(InstanceContainer container,Collection col,Map tempMap) throws BeanException {
		Collection valueCollection =(Collection)createContainerInstance(col.getClass());
		Iterator itor = col.iterator();
		while (itor.hasNext()) {
			Object value = itor.next();
			if(value instanceof BeanParameter) 
				value = convertParamToValue(container,(BeanParameter)value,tempMap);
			valueCollection.add(value);
		}
		return valueCollection;
	}

	/**
	 * search all value in a map
	 */
	public Map digMapParameter(InstanceContainer container,Map map,Map tempMap) throws BeanException {
		Map valueMap =(Map)createContainerInstance(map.getClass());
		Iterator itor = map.entrySet().iterator();
		while (itor.hasNext()) {
			Map.Entry entry = (Map.Entry) itor.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			if (key instanceof BeanParameter) 
				key = convertParamToValue(container,(BeanParameter)key,tempMap);
			if (value instanceof BeanParameter) 
				value = convertParamToValue(container,(BeanParameter) value,tempMap);

			valueMap.put(key, value);
		}
		return valueMap;
	}

	/**
	 * search all value in a array
	 */
	public Object digArrayParameter(InstanceContainer container,Object arry,Map tempMap) throws BeanException {
		int size = ArrayUtil.getArraySize(arry);
		Class type = ArrayUtil.getArrayType(arry);
		Object valueArray =ArrayUtil.createArray(type,size);
		TypeConvertFactory convertFact=container.getBeanTypeConvertFactory();
	
		for(int i = 0; i < size; i++) {
			Object value = ArrayUtil.getObject(arry,i);
			if(value instanceof BeanParameter) {
				value = convertParamToValue(container, (BeanParameter) value,tempMap);
				ArrayUtil.setObject(valueArray,i,value);
			}else{
				ArrayUtil.setObject(valueArray,i,BeanUtil.convertValue(value,type,convertFact));
			}
		}
		
		return valueArray;
	}

	/**
	 * 创建容器类型的参数
	 */
	private Object createContainerInstance(Class containerClass) throws BeanException {
		try {
			return containerClass.newInstance();
		} catch (Exception e) {
			throw new BeanParameterException(e);
		}
	}
	
	/**
	 * 创建类参数值
	 */
	private Object createClassParamValue(Object description, TypeConvertFactory convertFactory)throws BeanException {
		try {
			if (description instanceof String) {
				return BeanUtil.createInstance(ClassUtil.loadClass((String) description),convertFactory);
			} else{
				return BeanUtil.createInstance((Class)description,convertFactory);
			} 
		} catch (Exception e) {
			throw new BeanParameterException(e);
		}
	}
}