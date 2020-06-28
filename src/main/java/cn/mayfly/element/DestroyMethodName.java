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
 * 事例对象释放的时候,将调用破坏方法来做一些结束事情
 * 
 * <destroy-method>xxx.xxx</destroy-method>
 *
 * @author Chris Liao
 * @version 1.0
 */
public final class DestroyMethodName implements BeanElement {

  /**
   * 破坏方法名
   */
  private String methodName;
  
  /**
   * 构造函数
   */
  public DestroyMethodName(String methodName)throws BeanElementException {
  	this.checkMethodName(methodName);
  	this.methodName = methodName;
  }
  
  /**
   * 破坏方法名
   */
  public String getMethodName() {
    return methodName;
  }
  
  /**
   * Return hash code for this interception
   */
  public int hashCode() {
    return methodName.trim().hashCode();
  }
  
  /**
   * Override method
   */
  public boolean equals(Object obj) {
    if (obj instanceof DestroyMethodName) {
    	DestroyMethodName other = (DestroyMethodName) obj;
      return this.methodName.equals(other.methodName);
    } else {
      return false;
    }
  }
 
  /**
   * 检查方法名
   */
  private void checkMethodName(String methodName)throws BeanElementException{
  	if(methodName == null || methodName.trim().length()==0)
   		throw new BeanElementException("Destroy method name can't be null");
  }
}