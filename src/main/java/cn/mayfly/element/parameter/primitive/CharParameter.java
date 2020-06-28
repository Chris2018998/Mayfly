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
 * Char primitive Type
 *
 * @author Chris Liao
 * @version 1.0
 */

public final class CharParameter extends PrimitiveParameter {

  /**
   * Constructor
   */
  public CharParameter(char value)throws BeanParameterException {
    super(Character.valueOf(value));
  }

  /**
   * Constructor
   */
  public CharParameter(Character value)throws BeanParameterException {
    super(value);
  }

  /**
   * Return class for Primitive
   */
  public Class getTypeClass() {
    return Character.TYPE;
  }
}