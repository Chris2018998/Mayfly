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
package cn.mayfly.element.parameter.collection;

import cn.mayfly.BeanParameterException;
import cn.mayfly.element.parameter.ContainerParameter;

/**
 * 容器类型的Value
 *
 * @author Chris Liao
 * @version 1.0
 */
public final class ArrayParameter extends ContainerParameter {
	
	/**
	 * 数组元素类型
	 */
	private Class arrayType;
	
  /**
   * Constructor with a IoC description object.
   */
  public ArrayParameter(Class arrayType,Object array)throws BeanParameterException {
  	super(array);
  	this.arrayType = arrayType;
  
    if(arrayType==null)
    	throw new BeanParameterException("Parameter array type can't be null");
    if(array==null)
    	throw new BeanParameterException("Parameter array can't be null");
    if(!array.getClass().isArray())
    	throw new BeanParameterException("Parameter is not an array");
  }
  
  /**
	 * 数组元素类型
	 */
  public Class getArrayType(){
  	return this.arrayType;
  }
}