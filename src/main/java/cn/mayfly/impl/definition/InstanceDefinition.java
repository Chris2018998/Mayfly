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
package cn.mayfly.impl.definition;

import cn.mayfly.BeanException;

/**
 * A container store instance of a java beans
 *
 * @author Chris Liao
 * @version 1.0
 */

public class InstanceDefinition extends BaseDefinition {

	/**
	 * 代理实例
	 */
	private Object proxyInstance;

	/**
	 * 构造函数
	 */
	public InstanceDefinition(Object id, Object instance) {
		super(id);
		super.setSingletonInstance(instance);
	}

	/**
	 * Bean类
	 */
	public Class getBeanClass() {
		return super.getSingletonInstance().getClass();
	}

	/**
	 * is singleton instance
	 */
	public boolean isInstanceSingleton() throws BeanException {
		return true;
	}

	/**
	 * 代理实例
	 */
	public Object getBeanProxyInstance() {
		return proxyInstance;
	}

	/**
	 * 代理实例
	 */
	public void setProxyInstance(Object proxyInstance) {
		this.proxyInstance = proxyInstance;
	}
}