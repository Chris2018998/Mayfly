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
package cn.mayfly.element;

import cn.mayfly.BeanElement;
import cn.mayfly.BeanElementException;
import cn.mayfly.BeanParameter;

/**
 * IOC Field description in a bean.
 *
 * @author Chris Liao
 * @version 1.0
 */

public final class InjectionField implements BeanElement {

  /**
   * field name
   */
  private String fieldName;

  /**
   * field value
   */
  private BeanParameter parameter;
  
	/**
	 * 是否自动装配
	 */
	private boolean autowired;
  
	/**
	 * property Type
	 */
	private AutowiredType autowiredType;
	
  /**
   * 构造函数
   */
  public InjectionField(String fieldName)throws BeanElementException {
   this(fieldName,AutowiredType.BY_NAME);
  }
 
  /**
   * 构造函数
   */
  public InjectionField(String fieldName,AutowiredType autowiredType)throws BeanElementException {
    this.autowired=true;
    this.parameter=null;
    this.fieldName = fieldName;
    this.autowiredType = autowiredType;
    this.checkPropertyName(fieldName);
  }
  
  /**
   * 构造函数
   */
  public InjectionField(String fieldName,BeanParameter parameter)throws BeanElementException {
    this.autowired=false;
    this.autowiredType=null;
    this.fieldName = fieldName;
    this.parameter = parameter;
    this.checkPropertyName(fieldName);
    this.checkParameter(parameter);
  }

  /**
   * Return field name
   */
  public String getFieldName() {
    return fieldName;
  }

  /**
   * Return IOC parameter
   */
  public BeanParameter getParameter() {
    return parameter;
  }
  
  /**
   * is autowired Type
   */
	public boolean isAutowired() {
		return autowired;
	}

	/**
	 * autowire Type
	 */
	public AutowiredType getAutowiredType() {
		return autowiredType;
	}
  
  /**
   * Return hash code for this object
   */
  public int hashCode() {
  	return fieldName.trim().hashCode() ^ parameter.hashCode();
  }

  /**
   * Override method
   */
  public boolean equals(Object obj) {
    if (obj instanceof InjectionField) {
    	 InjectionField other = (InjectionField) obj;
       return this.fieldName.equals(other.fieldName);
    }else{
      return false;
    }
  }
  
  /**
   * 检查方法名
   */
  private void checkPropertyName(String methodName)throws BeanElementException{
  	if(methodName == null ||  methodName.trim().length()==0)
   		throw new BeanElementException("Field method name can't be null");
  }
 
  /**
   * 检查参数
   */
  private void checkParameter(BeanParameter parameter)throws BeanElementException{
  	if(parameter==null)
  		throw new BeanElementException("Field parameter can't be null");
  }
}