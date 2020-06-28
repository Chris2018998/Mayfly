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
package cn.mayfly.element.parameter.primitive;

import cn.mayfly.BeanParameterException;
import cn.mayfly.element.parameter.PrimitiveParameter;

/**
 * boolean primitive Type
 *
 * @author Chris Liao
 * @version 1.0
 */

public final class LongParameter extends PrimitiveParameter {

  /**
   * constructor
   */
  public LongParameter(long value)throws BeanParameterException {
    super(Long.valueOf(value));
  }
  
  /**
   * constructor
   */
  public LongParameter(Long value)throws BeanParameterException {
  	super(value);
  }

  /**
   * Return class for Primitive
   */
  public Class getTypeClass() {
    return Long.TYPE;
  }
}