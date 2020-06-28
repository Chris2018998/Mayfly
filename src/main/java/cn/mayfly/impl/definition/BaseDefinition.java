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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.mayfly.BeanDefinition;
import cn.mayfly.BeanException;
import cn.mayfly.element.InjectionField;
import cn.mayfly.element.InjectionInvocation;
import cn.mayfly.element.InjectionProperty;
import cn.mayfly.element.InvocationInterception;
import cn.mayfly.impl.intercept.InterceptionHandler;
import cn.mayfly.impl.intercept.InterceptorChain;
import cn.mayfly.impl.util.BeanUtil;

/**
 * A bean wrapper store java beans
 *
 * @author Chris Liao
 * @version 1.0
 */

public abstract class BaseDefinition implements BeanDefinition {

	/**
	 * Bean registeration ID in Ioc container
	 */
	private Object beanID;

	/**
	 * singleton instance on bean.默认为单例
	 */
	private boolean singleton = true;

	/**
	 * bean init method
	 */
	private String initMethodName;

	/**
	 * bean destory method
	 */
	private String destroyMethodName;

	/**
	 * ref ID of super class
	 */
	private Object parentReferenceId;

	/**
	 * implements interface for bean
	 */
	private Class[] proxyInterfaces;

	/**
	 * field list
	 */
	protected List injectFieldList;

	/**
	 * proptery list
	 */
	protected List injectPropertyList;

	/**
	 * invocation list
	 */
	protected List injectInvocationList;

	/**
	 * inteception list
	 */
	protected List interceptionList;

	/**
	 * init method
	 */
	private Method initMethod = null;

	/**
	 * destroy Method
	 */
	private Method destroyMethod = null;

	/********************* 以下为非接口实现需要的变量 ******************/
	/**
	 * 类是否已经被修改过
	 */
	private boolean classModified;

	/**
	 * 是否已经创建过实例
	 */
	private boolean instaceCreated;

	/**
	 * 只保留单利模式下的一个实例
	 */
	private Object singletonInstance;

	/**
	 * 方法调用拦截链
	 */
	private InterceptorChain interceptorChain = null;

	/**
	 * Proxy下需要InvocationHandler实现
	 */
	private InterceptionHandler interceptionHandler;

	/**
	 * Class constructor with a ioc registeration ID
	 * 
	 * @param beanID
	 */
	public BaseDefinition(Object beanID) {
		this.beanID = beanID;
		this.injectFieldList = new ArrayList();
		this.injectPropertyList = new ArrayList();
		this.injectInvocationList = new ArrayList();
		this.interceptionList = new ArrayList();
	}

	/**
	 * Retrieve the key associated with the bean.
	 */
	public Object getBeanID() {
		return this.beanID;
	}

	/**
	 * return implimetation class
	 */
	public Class getBeanClass() {
		throw new RuntimeException("Need override in sub class");// support
																	// java1.3
	}

	/**
	 * return init method name
	 */
	public String getInitMethodName() throws BeanException {
		return this.initMethodName;
	}

	/**
	 * set destroy method
	 */
	public void setInitMethodName(String initMethodName) throws BeanException {
		try {
			if (!this.instaceCreated) {
				this.initMethod = BeanUtil.findMethod(this.getBeanClass(), initMethodName, new Class[0]);
				this.initMethodName = initMethodName;
			}
		} catch (Exception e) {
			throw new BeanException(e);
		}
	}

	/**
	 * return destroy Method Name
	 */
	public String getDestroyMethodName() throws BeanException {
		return this.destroyMethodName;
	}

	/**
	 * return destroy Method Name
	 */
	public void setDestroyMethodName(String destroyMethodName) throws BeanException {
		try {
			if (!this.instaceCreated) {
				this.destroyMethod = BeanUtil.findMethod(this.getBeanClass(), destroyMethodName, new Class[0]);
				this.destroyMethodName = destroyMethodName;
			}
		} catch (Exception e) {
			throw new BeanException(e);
		}
	}

	/**
	 * Return super ref ID
	 */
	public Object getParentReferenceId() throws BeanException {
		return parentReferenceId;
	}

	/**
	 * set super ref ID
	 */
	public void setParentReferenceId(Object parentID) throws BeanException {
		if (!this.instaceCreated) {
			this.parentReferenceId = parentID;
		}
	}

	/**
	 * is singleton instance
	 */
	public boolean isInstanceSingleton() throws BeanException {
		return singleton;
	}

	/**
	 * set singleton for bean
	 */
	public void setInstanceSingleton(boolean singleton) throws BeanException {
		if (!this.instaceCreated) {
			this.singleton = singleton;
		}
	}

	/**
	 * set proxy interface class on
	 */
	public void setProxyInterfaces(Class[] proxyInterfaces) throws BeanException {
		if (!this.instaceCreated) {
			for (int i = 0, n = proxyInterfaces.length; i < n; i++) {
				if (!proxyInterfaces[i].isAssignableFrom(this.getBeanClass()))
					throw new BeanException(new StringBuilder("Class[").append(proxyInterfaces[i]).append("] is not a super interface for bean ID[").append(this.getBeanID()).append("]").toString());
			}
			this.proxyInterfaces = proxyInterfaces;
		}
	}

	/**
	 * return implemented interfaces
	 */
	public Class[] getProxyInterfaces() throws BeanException {
		return proxyInterfaces;
	}

	/**
	 * Add a Field description for the bean class
	 */
	public void addInjectionField(InjectionField field) throws BeanException {
		if (!this.instaceCreated && !injectFieldList.contains(field))
			injectFieldList.add(field);
	}

	/**
	 * Remove a Field description for the bean class
	 */
	public void removeInjectionField(InjectionField field) throws BeanException {
		if (!this.instaceCreated)
			injectFieldList.remove(field);
	}

	/**
	 * Add a property description for the bean class
	 */
	public void addInjectionProperty(InjectionProperty property) throws BeanException {
		if (!this.instaceCreated && !injectPropertyList.contains(property))
			injectPropertyList.add(property);
	}

	/**
	 * Remove a property description for the bean class
	 */
	public void removeInjectionProperty(InjectionProperty property) throws BeanException {
		if (!this.instaceCreated)
			injectPropertyList.remove(property);
	}

	/**
	 * Add a method invocation for the bean class
	 */
	public void addInjectionInvocation(InjectionInvocation invocation) throws BeanException {
		if (!this.instaceCreated && !injectInvocationList.contains(invocation))
			injectInvocationList.add(invocation);
	}

	/**
	 * Remove a method invocation for the bean class
	 */
	public void removeInjectionInvocation(InjectionInvocation invocation) throws BeanException {
		if (!this.instaceCreated)
			injectInvocationList.remove(invocation);

	}

	/**
	 * Add a Interception for bean class
	 */
	public void addInvocationInterception(InvocationInterception interception) throws BeanException {
		if (!this.isInstaceCreated())
			interceptionList.add(interception);
	}

	/**
	 * Remove a Interception for bean class
	 */
	public void removeInvocationInterception(InvocationInterception interception) throws BeanException {
		if (!this.isInstaceCreated() && interceptorChain != null && interceptionList.contains(interception))
			interceptionList.remove(interception);
	}

	/**************************************** 以下方法为非接口实现方法 *********************************************/

	/**
	 * 类是否已经被修改过
	 */
	public boolean isClassModified() {
		return classModified;
	}

	/**
	 * 类是否已经被修改过
	 */
	public void setClassModified(boolean classModified) {
		this.classModified = classModified;
	}

	/**
	 * 是否已经创建过实例
	 */
	public boolean isInstaceCreated() {
		return instaceCreated;
	}

	/**
	 * 是否已经创建过实例
	 */
	public void setInstaceCreated(boolean inited) {
		this.instaceCreated = inited;
	}

	/**
	 * 获得初始化方法
	 */
	public Method getInitMethod() {
		return initMethod;
	}

	/**
	 * 获得初破坏方法
	 */
	public Method getDestroyMethod() {
		return this.destroyMethod;
	}

	/**
	 * 单实例
	 */
	public Object getSingletonInstance() {
		return this.singletonInstance;
	}

	/**
	 * 单实例
	 */
	public void setSingletonInstance(Object beanInstance) {
		this.singletonInstance = beanInstance;
	}

	/**
	 * 获得注入属性列表
	 */
	public List getInjectionFieldList() {
		return injectFieldList;
	}

	/**
	 * 获得注入属性列表
	 */
	public List getInjectionPropertyList() {
		return injectPropertyList;
	}

	/**
	 * 获得注入方法列表
	 */
	public List getInjectionInvocationList() {
		return injectInvocationList;
	}

	/**
	 * 获得调用拦截列表
	 */
	public List getInvocationInterceptionList() {
		return interceptionList;
	}

	/**
	 * 方法调用拦截链
	 */
	public InterceptorChain getInterceptorChain() {
		return interceptorChain;
	}

	/**
	 * 方法调用拦截链
	 */
	public void setInterceptorChain(InterceptorChain interceptorChain) {
		this.interceptorChain = interceptorChain;
	}

	/**
	 * Proxy下需要InvocationHandler实现
	 */
	public InterceptionHandler getInterceptionHandler() {
		return interceptionHandler;
	}

	/**
	 * Proxy下需要InvocationHandler实现
	 */
	public void setInterceptionHandler(InterceptionHandler interceptionHandler) {
		this.interceptionHandler = interceptionHandler;
	}
}