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
 * IOC property description object in a bean,setter method will be called to inject parameter value
 *
 * @author Chris Liao
 * @version 1.0
 */

public final class InjectionProperty implements BeanElement {

  /**
   * Property name
   */
  private String propertyName;

  /**
   * Property value
   */
  private BeanParameter parameter;
  
  /**
   * 构造函数
   */
  public InjectionProperty(String propertyName,BeanParameter parameter)throws BeanElementException {
    this.propertyName = propertyName;
    this.parameter = parameter;
    this.checkPropertyName(propertyName);
    this.checkParameter(parameter);
  }

  /**
   * Return property name
   */
  public String getPropertyName() {
    return propertyName;
  }

  /**
   * Return IOC parameter
   */
  public BeanParameter getParameter() {
    return parameter;
  }
  
  /**
   * Return hash code for this object
   */
  public int hashCode() {
  	return propertyName.trim().hashCode() ^ parameter.hashCode();
  }

  /**
   * Override method
   */
  public boolean equals(Object obj) {
    if (obj instanceof InjectionProperty) {
       InjectionProperty other = (InjectionProperty) obj;
       return this.propertyName.equals(other.propertyName);
    }else{
      return false;
    }
  }
  
  /**
   * 检查方法名
   */
  private void checkPropertyName(String methodName)throws BeanElementException{
  	if(methodName == null ||  methodName.trim().length()==0)
   		throw new BeanElementException("Property name can't be null");
  }
 
  /**
   * 检查参数
   */
  private void checkParameter(BeanParameter parameter)throws BeanElementException{
  	if(parameter==null)
  		throw new BeanElementException("Property parameter can't be null");
  }
}