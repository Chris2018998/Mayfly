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
 * A description element for auto injection
 *
 * @author Chris Liao
 * @version 1.0
 */

public final class AutowiredType implements BeanElement {
	
	/**
	 * 根据属性名自动装配
	 */
	public static final AutowiredType BY_NAME = new AutowiredType("ByName");

	/**
	 * 依据属性类型进行自动装配,如果存在多个该类型的实例，将抛出异常，如果不存在任何实例则不装配
	 */
	public static final AutowiredType BY_TYPE = new AutowiredType("ByType");

	/**
	 * 自动装配值
	 */
	private String autowiredValue;

	/**
	 * 构造函数
	 */
	AutowiredType(String autowiredValue) {
		this.autowiredValue = autowiredValue;
	}

	/**
	 * toString
	 */
	public String toString() {
		return autowiredValue;
	}

	/**
	 * 通过名字找到一个匹配的Autowire
	 */
	public static AutowiredType toAutowiredType(String typeName)
			throws BeanElementException {
		if ("ByName".equalsIgnoreCase(typeName)) {
			return BY_NAME;
		} else if ("ByType".equalsIgnoreCase(typeName)) {
			return BY_TYPE;
		} else {
			throw new BeanElementException("Invalid autowired value,must be one of[ByName,ByType]");
		}
	}
	
	/**
	 * Override hashCode
	 */
	public int hashCode(){
		return this.autowiredValue.hashCode();
	}
  
	/**
	 * Override method
	 */
	public boolean equals(Object obj) {
		if (obj instanceof AutowiredType) {
			return this.autowiredValue.equalsIgnoreCase(((AutowiredType) obj).autowiredValue);
		} else {
			return false;
		}
	}
}
