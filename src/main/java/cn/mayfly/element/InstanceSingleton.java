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

/**
 * 是否单例模式
 *
 * <singleton>true</singleton>
 * <singleton>false</singleton>
 *
 * @author Chris Liao
 * @version 1.0
 */

public final class InstanceSingleton implements BeanElement {

  /**
   * 单利标记
   */
  private boolean singleton =true;

  /**
   *构造函数
   */
  public InstanceSingleton(boolean singleton)throws BeanElementException {
    this.singleton = singleton;
  }

  /**
   * 是否单利
   */
  public boolean isSingleton() {
    return singleton;
  }
  
  /**
   * Return hash code for this interception
   */
  public int hashCode() {
  	return singleton?1:0;
  }

  /**
   * Override method
   */
  public boolean equals(Object obj) {
    if(obj instanceof InstanceSingleton) {
    	InstanceSingleton other = (InstanceSingleton) obj;
      return this.singleton == other.singleton;
    } else {
      return false;
    }
  }
}