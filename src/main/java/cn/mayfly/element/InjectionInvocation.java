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

import java.util.Arrays;

import cn.mayfly.BeanElement;
import cn.mayfly.BeanElementException;
import cn.mayfly.BeanParameter;

/**
 * Bean element description object.After bean instantiated, container will invoke
 * the method described in the object and inject some parameter value to it.
 *
 * @author Chris Liao
 * @version 1.0
 */

public final class InjectionInvocation implements BeanElement {

  /**
   * 方法名
   */
  private String methodName;

  /**
   * 方法参数
   */
  private BeanParameter[] parameters;
  
  /**
   * 构造函数
   */
  public InjectionInvocation(String methodName,BeanParameter[] parameters)throws BeanElementException {
    this.methodName = methodName;
    this.parameters = parameters;
    this.checkMethodName(methodName);
    this.checkParameters(parameters);
  }

  /**
   * 方法名
   */
  public String getMethodName() {
    return methodName;
  }

  /**
   * 方法参数
   */
  public BeanParameter[] getParameters() {
    return parameters;
  }
  
  /**
   * Override method
   */
  public boolean equals(Object obj) {
    if (obj instanceof InjectionInvocation) {
    	InjectionInvocation other = (InjectionInvocation) obj;
      return this.methodName.equals(other.methodName) 
      	&& Arrays.equals(this.parameters,other.parameters);
    } else {
      return false;
    }
  }

  /**
   * Return hash code for this interception
   */
  public int hashCode() {
    int hashCode = methodName.trim().hashCode();
    if(parameters!=null){
	    for(int i=0,n=parameters.length; i<n; i++) 
	    	if(parameters[i]!=null)
	    		hashCode = hashCode ^ parameters[i].hashCode();
  	}
    return hashCode;
  }
  
  /**
   * Override method
   */
  public String toString() {
	  	StringBuilder buff = new StringBuilder();
		buff.append("Inject method:").append(methodName);
		buff.append("(");
		if (parameters != null) {
			for(int i=0,n=parameters.length;i<n;i++) {
				buff.append(parameters[i]);
				if (i < parameters.length - 1)
					buff.append(",");
			}
		}
		buff.append(")");
		return buff.toString();
  }
  
  /**
   * 检查方法名
   */
  private void checkMethodName(String methodName)throws BeanElementException{
  	if(methodName == null || methodName.trim().length()==0)
   		throw new BeanElementException("Invocation method name can't be null");
  }
 
  /**
   * 检查参数
   */
  private void checkParameters(BeanParameter[] createParameters)throws BeanElementException{
  	if(createParameters==null)
  		throw new BeanElementException("Invocation parameters can't be null");
  	for(int i=0,n=createParameters.length;i<n;i++){
  		if(createParameters[i]==null)
  		throw new BeanElementException(new StringBuilder("Found null parameter at index:").append(i).toString());
  	}
  }
}