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
 * Bean实例创建定义
 *
 * @author Chris Liao
 * @version 1.0
 */

public final class InstanceCreation implements BeanElement {

  /**
   * 工厂Bean应用ID
   */
  private Object factoryBeanRefID;

  /**
   * 工厂方法
   */
  private String factoryMethodName;
	
  /**
   * 创建的参数定义
   */
  protected BeanParameter[] createParameters;
  
  /**
   * 构造函数
   */
  public InstanceCreation(BeanParameter[] createParameters)throws BeanElementException{
  	this.createParameters = createParameters;
  }
  
  /**
   * 构造函数
   */
  public InstanceCreation(String factoryMethodName,BeanParameter[] createParameters)throws BeanElementException{
  	this.factoryMethodName = factoryMethodName;
   	this.createParameters = createParameters;
   	this.checkFactoryMethodName(factoryMethodName);
  }
  
  /**
   * 构造函数
   */
  public InstanceCreation(Object factoryBeanRefID,String factoryMethodName,BeanParameter[] createParameters)throws BeanElementException{
  	this.factoryBeanRefID = factoryBeanRefID;
  	this.factoryMethodName = factoryMethodName;
   	this.createParameters = createParameters;
   	this.checkFactoryBeanRefID(factoryBeanRefID);
   	this.checkFactoryMethodName(factoryMethodName);
  }
  
  /**
   * 工厂应用Bean ID
   */
  public Object getFactoryBeanRefID() {
		return factoryBeanRefID;
	}
  
  /**
   * 工厂方法
   */
	public String getFactoryMethodName() {
		return factoryMethodName;
	}

	/**
   * 创建的参数定义
   */
  public BeanParameter[] getCreateParameters() {
    return this.createParameters;
  }
 
  /**
   * Override method
   */
  public boolean equals(Object obj) {
    if (obj instanceof InstanceCreation) {
    	InstanceCreation other = (InstanceCreation) obj;
    	boolean equalRefID =true,equalMethod =true;
    	if(this.factoryBeanRefID!=null)
    		equalRefID = this.factoryBeanRefID.equals(other.factoryBeanRefID);
    	if(!isNull(this.factoryMethodName))
    		equalMethod = this.factoryMethodName.equals(other.factoryMethodName);
    	
    	return equalRefID && equalMethod 
    					&& Arrays.equals(this.createParameters,other.createParameters);
    } else {
      return false;
    }
  }

  /**
   * Return hash code for this interception
   */
  public int hashCode() {
  	int hashCode = 1;
  	if(this.factoryBeanRefID!=null)
  		hashCode ^= this.factoryBeanRefID.hashCode();
  	if(!isNull(this.factoryMethodName))
  		hashCode ^= this.factoryMethodName.hashCode();
	  for(int i=0,n=createParameters.length; i<n; i++){
	  	if(createParameters[i]!=null)
	     hashCode = hashCode ^ createParameters[i].hashCode();
	   }
    return hashCode;
  }
  
  /**
   * Override method
   */
  public String toString() {
  	StringBuilder buff = new StringBuilder();
  	if(this.factoryBeanRefID!=null){
	    buff.append("Factory bean create method:");
			buff.append(factoryBeanRefID);
			buff.append(".");
			buff.append(factoryMethodName);
    }else if(!isNull(this.factoryMethodName)){
    	 buff.append("Factory create method:");
    }else{
    	buff.append("Class refelect method:");
    }
	
		buff.append("(");
		if(createParameters != null) {
			for(int i=0,n=createParameters.length;i<n;i++) {
				if(createParameters[i]!=null)
					buff.append(createParameters[i]);
				if (i < createParameters.length - 1)
					buff.append(",");
			}
		}
		buff.append(")");
		return buff.toString();
  }
  
  /**
   * 检查参数
   */
  private void checkFactoryBeanRefID(Object factoryBeanRefID)throws BeanElementException{
  	if(factoryBeanRefID == null)
   		throw new BeanElementException("Factory bean reference id can't be null");
  }
 
  /**
   * 检查参数
   */
  private void checkFactoryMethodName(String factoryMethodName)throws BeanElementException{
   	if(isNull(factoryMethodName))
   		throw new BeanElementException("Factory bean create method can't be null");
  }
  
	/**
	 * 判断字符是否为空
	 */
	private boolean isNull(String value) {
		return (value == null || value.trim().length()==0);
	}
}