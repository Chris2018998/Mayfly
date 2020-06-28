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
package cn.mayfly.element.parameter;

import cn.mayfly.BeanParameter;
import cn.mayfly.BeanParameterException;

/**
 * This object indicates that a property value is anohter java bean.
 *
 * @author Chris Liao
 * @version 1.0
 */

public final class ReferenceParameter extends BeanParameter{

  /**
   * Constructor with a class name
   */
  public ReferenceParameter(Object referenceID) throws BeanParameterException{
    super(referenceID);
  }
  
  /**
   * 获得引用ID
   */
  public Object getReferenceId(){
  	return this.getParameterContent();
  }
  
}