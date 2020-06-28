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

/**
 * A description element about implemented Interface on a bean. If import some
 * interfaces,then build auto proxy for bean
 * 
 * @author Chris Liao
 * @version 1.0
 */

public final class ProxyInterfaces implements BeanElement {

	/**
	 * auto proxy itnerfaces
	 */
	private Class[] interfaces;

	/**
	 *构造函数
	 */
	public ProxyInterfaces(Class[] interfaces)throws BeanElementException {
		this.interfaces = interfaces;
		this.checkInterfaces(interfaces);
	}

	/**
	 * return proxy interface
	 */
	public Class[] getInterfaces() {
		return this.interfaces;
	}

	/**
	 * override method
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder();
		buff.append("Proxy interfaces:");
		for(int i=0,n=this.interfaces.length;i<n; i++) 
			buff.append(interfaces[i] + "\n");
		return buff.toString();
	}

	/**
	 * Overide method
	 */
	public int hashCode() {
		int code = 0;
		for(int i=0,n= this.interfaces.length;i<n; i++) 
			code ^= interfaces[i].hashCode();
		return code;
	}

	/**
	 * Overide method
	 */
	public boolean equals(Object obj) {
		if(obj instanceof ProxyInterfaces) {
			ProxyInterfaces other = (ProxyInterfaces) obj;
			return Arrays.equals(this.interfaces,other.interfaces);
		} else {
			return false;
		}
	}
	
	 
  /**
   * 检查参数
   */
  private void checkInterfaces(Class[] interfaces)throws BeanElementException{
  	if(this.interfaces==null)
			throw new BeanElementException("At least supply an implement interface");
		for(int i=0,n=interfaces.length;i<n;i++){
			if(interfaces[i]==null)
				throw new BeanElementException(new StringBuilder("There exist an null interface at index:").append((i+1)).toString());
			if (!interfaces[i].isInterface())
			  throw new BeanElementException(new StringBuilder("Class[").append(interfaces[i]).append("] is not an interface").toString());
		}
  }
}
