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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.mayfly.element.parameter.ClassParameter;
import cn.mayfly.element.parameter.InstanceParameter;
import cn.mayfly.element.parameter.ReferenceParameter;
import cn.mayfly.element.parameter.collection.ArrayParameter;
import cn.mayfly.element.parameter.collection.CollectionParameter;
import cn.mayfly.element.parameter.collection.ListParameter;
import cn.mayfly.element.parameter.collection.MapParameter;
import cn.mayfly.element.parameter.collection.SetParameter;
import cn.mayfly.element.parameter.primitive.BooleanParameter;
import cn.mayfly.element.parameter.primitive.ByteParameter;
import cn.mayfly.element.parameter.primitive.CharParameter;
import cn.mayfly.element.parameter.primitive.DoubleParameter;
import cn.mayfly.element.parameter.primitive.FloatParameter;
import cn.mayfly.element.parameter.primitive.IntegerParameter;
import cn.mayfly.element.parameter.primitive.LongParameter;
import cn.mayfly.element.parameter.primitive.ShortParameter;

/**
 * Bean parameter factory
 *
 * @author Chris Liao
 * @version 1.0
 */

public final class BeanParameterFactory {

  /**
   * Create a boolean type ioc value
   */
  public BeanParameter createBooleanParameter(boolean value)throws BeanParameterException {
    return new BooleanParameter(value);
  }

  /**
   * Create a boolean type ioc value
   */
  public BeanParameter createBooleanParameter(Boolean value)throws BeanParameterException {
    return new BooleanParameter(value);
  }

  /**
   * Create a byte type ioc value
   */
  public BeanParameter createByteParameter(byte value)throws BeanParameterException {
    return new ByteParameter(value);
  }
  /**
   * Create a byte type ioc value
   */
  public BeanParameter createByteParameter(Byte value)throws BeanParameterException {
    return new ByteParameter(value);
  }

  /**
   * Create a char type ioc value
   */
  public BeanParameter createCharParameter(char value)throws BeanParameterException {
    return new CharParameter(value);
  }

  /**
   * Create a char type ioc value
   */
  public BeanParameter createCharParameter(Character value)throws BeanParameterException {
    return new CharParameter(value);
  }

  /**
   * Create a short type ioc value
   */
  public BeanParameter createShortParameter(short value)throws BeanParameterException {
    return new ShortParameter(value);
  }

  /**
   * Create a short type ioc value
   */
  public BeanParameter createShortParameter(Short value) throws BeanParameterException{
    return new ShortParameter(value);
  }

  /**
   * Create a int type ioc value
   */
  public BeanParameter createIntegerParameter(int value)throws BeanParameterException {
    return new IntegerParameter(value);
  }

  /**
   * Create a int type ioc value
   */
  public BeanParameter createIntegerParameter(Integer value)throws BeanParameterException {
    return new IntegerParameter(value);
  }

  /**
   * Create a long type ioc value
   */
  public BeanParameter createLongParameter(long value) throws BeanParameterException{
    return new LongParameter(value);
  }

  /**
   * Create a long type ioc value
   */
  public BeanParameter createLongParameter(Long value)throws BeanParameterException {
    return new LongParameter(value);
  }

  /**
   * Create a float type ioc value
   */
  public BeanParameter createFloatParameter(float value)throws BeanParameterException {
    return new FloatParameter(value);
  }

  /**
   * Create a float type ioc value
   */
  public BeanParameter createFloatParameter(Float value)throws BeanParameterException {
    return new FloatParameter(value);
  }

  /**
   * Create a double type ioc value
   */
  public BeanParameter createDoubleParameter(double value)throws BeanParameterException {
    return new DoubleParameter(value);
  }

  /**
   * Create a double type ioc value
   */
  public BeanParameter createDoubleParameter(Double value)throws BeanParameterException {
    return new DoubleParameter(value);
  }

  /**
   * Create a String type ioc value
   */
  public BeanParameter createStringParameter(String value)throws BeanParameterException {
    return new InstanceParameter(value);
  }

  /**
   * Create a String type ioc value
   */
  public BeanParameter createInstanceParameter(Object value)throws BeanParameterException {
    return new InstanceParameter(value);
  }

  /**
   * Create a class class type ioc value
   */
  public BeanParameter createClassParameter(Class value)throws BeanParameterException {
    return new ClassParameter(value);
  }

  /**
   * Create a class class name ioc value
   */
  public BeanParameter createClassParameter(String value)throws BeanParameterException{
    return new ClassParameter(value);
  }

  /**
   * Create a reference name ioc value
   */
  public BeanParameter createReferenceParameter(Object value)throws BeanParameterException {
    return new ReferenceParameter(value);
  }
  
  /**
   * Create a collection ioc value
   */
  public BeanParameter createCollectionParameter(Collection value)throws BeanParameterException {
    return new CollectionParameter(value);
  }
  
  /**
   * Create a collection ioc value
   */
  public BeanParameter createListParameter(List value)throws BeanParameterException {
    return new ListParameter(value);
  }
 
  /**
   * Create a collection ioc value
   */
  public BeanParameter createSetParameter(Set value)throws BeanParameterException {
    return new SetParameter(value);
  }
 
  /**
   * Create a collection ioc value
   */
  public BeanParameter createMapParameter(Map value)throws BeanParameterException {
    return new MapParameter(value);
  }

  /**
   * Create a collection ioc value
   */
  public BeanParameter createArrayParameter(Class arrayType,Object array)throws BeanParameterException {
    return new ArrayParameter(arrayType,array);
  }
}