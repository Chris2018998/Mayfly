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
package cn.mayfly;

import java.io.Serializable;

/**
 * IOC Parameter.
 *
 * @author Chris Liao
 * @version 1.0
 */

public abstract class BeanParameter implements Serializable{

  /**
   * parameter Content
   */
  private Object parameterContent = null;

  /**
   * Constructor with a IoC description object.
   */
  public BeanParameter(Object parameterContent)throws BeanParameterException {
  	this.checkParameterObject(parameterContent);
    this.parameterContent = parameterContent;
  }

  /**
   * Return description object for ioc value
   */
  public Object getParameterContent() {
    return this.parameterContent;
  }

  /**
   * Override method
   */
  public String toString() {
     return String.valueOf(parameterContent);
  }

  /**
   * Return hash code for parameter
   */
  public int hashCode() {
    return parameterContent.hashCode();
  }

  /**
   * Override method
   */
  public boolean equals(Object obj) {
    if (obj instanceof BeanParameter) {
    	BeanParameter other = (BeanParameter) obj;
      return this.parameterContent.equals(other.parameterContent);
    } else {
      return false;
    }
  }
  
 /**
  * Parameter check
  */
 private void checkParameterObject(Object parameterContent)throws BeanParameterException{
	 if(parameterContent == null)
     throw new BeanParameterException("Parameter content can't be null");
 }
}