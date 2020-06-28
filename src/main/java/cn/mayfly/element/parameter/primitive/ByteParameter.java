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
 * byte primitive Type
 *
 * @author Chris Liao
 * @version 1.0
 */

public final class ByteParameter extends PrimitiveParameter {

  /**
   * Constructor
   */
  public ByteParameter(byte value)throws BeanParameterException {
    super(Byte.valueOf(value));
  }

  /**
   * Constructor
   */
  public ByteParameter(Byte value)throws BeanParameterException {
    super(value);
  }

  /**
   * Return class for Primitive
   */
  public Class getTypeClass() {
    return Byte.TYPE;
  }
}