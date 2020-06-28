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

import cn.mayfly.BeanElement;
import cn.mayfly.BeanException;
import cn.mayfly.BeanParameter;
import cn.mayfly.element.DestroyMethodName;
import cn.mayfly.element.InitializeMethodName;
import cn.mayfly.element.InjectionField;
import cn.mayfly.element.InjectionInvocation;
import cn.mayfly.element.InjectionProperty;
import cn.mayfly.element.InstanceCreation;
import cn.mayfly.element.InstanceSingleton;
import cn.mayfly.element.InvocationInterception;
import cn.mayfly.element.ParentReferenceId;
import cn.mayfly.element.ProxyInterfaces;

/**
 * A bean wrapper store java beans
 *
 * @author Chris Liao
 * @version 1.0
 */

public class ClassDefinition extends BaseDefinition {

	/**
	 * bean class
	 */
	private Class beanClass;

	/**
	 * 包装类，主要为AOP准备
	 */
	private Class beanWrapClass;

	/**
	 * creation constructor
	 */
	private InstanceCreation creation;

	/**
	 * constructor
	 */
	public ClassDefinition(Object beanID, Class beanClass, BeanElement[] elements) throws BeanException {
		super(beanID);
		this.beanClass = beanClass;
		this.creation = new InstanceCreation(new BeanParameter[0]);// 默认给一个无参的构造
		this.divideElements(elements);
	}

	/**
	 * return implimetation class
	 */
	public Class getBeanClass() {
		return beanClass;
	}

	/**
	 * 包装类，主要为AOP准备
	 */
	public Class getBeanWrapClass() {
		return beanWrapClass;
	}

	/**
	 * 包装类，主要为AOP准备
	 */
	public void setBeanWrapClass(Class beanWrapClass) {
		this.beanWrapClass = beanWrapClass;
	}

	/**
	 * 获得构造方法
	 */
	public InstanceCreation getInstanceCreation() {
		return this.creation;
	}

	/**
	 * @param constructor
	 */
	private void setConstruction(InstanceCreation construction) {
		this.creation = construction;
	}

	/**
	 * Separate registered info.
	 */
	private void divideElements(BeanElement[] elements) throws BeanException {
		if (elements != null && elements.length > 0) {
			for (int i = 0, n = elements.length; i < n; i++) {
				if (elements[i] instanceof InstanceCreation) {
					this.setConstruction((InstanceCreation) elements[i]);
				} else if (elements[i] instanceof InjectionField) {
					this.addInjectionField(((InjectionField) elements[i]));
				} else if (elements[i] instanceof InjectionProperty) {
					this.addInjectionProperty((InjectionProperty) elements[i]);
				} else if (elements[i] instanceof InjectionInvocation) {
					this.addInjectionInvocation((InjectionInvocation) elements[i]);
				} else if (elements[i] instanceof InvocationInterception) {
					this.addInvocationInterception((InvocationInterception) elements[i]);

				} else if ((elements[i] instanceof InstanceSingleton)) {
					this.setInstanceSingleton(((InstanceSingleton) elements[i]).isSingleton());
				} else if ((elements[i] instanceof ParentReferenceId)) {
					this.setParentReferenceId(((ParentReferenceId) elements[i]).getReferenceId());
				} else if (elements[i] instanceof InitializeMethodName) {
					this.setInitMethodName(((InitializeMethodName) elements[i]).getMethodName());
				} else if (elements[i] instanceof DestroyMethodName) {
					this.setDestroyMethodName(((DestroyMethodName) elements[i]).getMethodName());
				} else if (elements[i] instanceof ProxyInterfaces) {
					this.setProxyInterfaces(((ProxyInterfaces) elements[i]).getInterfaces());
				}
			}
		}
	}
}