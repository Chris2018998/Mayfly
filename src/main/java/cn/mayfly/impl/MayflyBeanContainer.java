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
package cn.mayfly.impl;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.magic.TypeConvertException;
import cn.magic.TypeConvertFactory;
import cn.magic.TypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.mayfly.BeanContainer;
import cn.mayfly.BeanDefinition;
import cn.mayfly.BeanElement;
import cn.mayfly.BeanElementFactory;
import cn.mayfly.BeanException;
import cn.mayfly.BeanParameterFactory;
import cn.mayfly.element.DestroyMethodName;
import cn.mayfly.element.InitializeMethodName;
import cn.mayfly.element.InjectionField;
import cn.mayfly.element.InjectionInvocation;
import cn.mayfly.element.InjectionProperty;
import cn.mayfly.element.InstanceSingleton;
import cn.mayfly.element.InvocationInterception;
import cn.mayfly.element.ParentReferenceId;
import cn.mayfly.element.ProxyInterfaces;
import cn.mayfly.impl.definition.BaseDefinition;
import cn.mayfly.impl.definition.ClassDefinition;
import cn.mayfly.impl.definition.InstanceDefinition;
import cn.mayfly.impl.exception.BeanClassException;
import cn.mayfly.impl.exception.BeanDefinitionNotFoundException;
import cn.mayfly.impl.exception.BeanIdDuplicateRegisterException;
import cn.mayfly.impl.instance.InstanceContainer;
import cn.mayfly.impl.instance.ParamConvertFactory;
import cn.mayfly.impl.util.BeanUtil;

/**
 * Bean mico-container implement
 *
 * @author Chris Liao
 * @version 1.0
 */

public class MayflyBeanContainer implements BeanContainer {
	
	/**
	 * store ioc wrappers
	 */
	private Map definitionMap;

	/**
	 * Instance container
	 */
	private InstanceContainer instanceContainer = null;

	/**
	 * desctroy hook
	 */
	private ContainerDestroyHook instanceDestroyHook = null;

	/**
	 * element factory
	 */
	private BeanElementFactory beanElementFactory = null;

	/**
	 * parameter factory
	 */
	private BeanParameterFactory beanParameterFactory = null;

	/**
	 * type conversion factory
	 */
	private TypeConvertFactory beanTypeConvertFactory = null;

	/**
	 * Parameter Object converter
	 */
	private ParamConvertFactory paramConvertFactory = null;

	/**
	 * logger
	 */
	private Logger log = LoggerFactory.getLogger(MayflyBeanContainer.class);

	/**
	 * contaienr creation
	 */
	public MayflyBeanContainer() {
		this.definitionMap = new LinkedHashMap();
		this.instanceContainer = new InstanceContainer(this);
		this.beanElementFactory = new BeanElementFactory();
		this.beanParameterFactory = new BeanParameterFactory();
		this.paramConvertFactory = new ParamConvertFactory();
		this.beanTypeConvertFactory = new TypeConvertFactory();

		this.instanceDestroyHook = new ContainerDestroyHook(this);
		Runtime.getRuntime().addShutdownHook(this.instanceDestroyHook);
	}

	/**
	 * contains beanID
	 */
	public boolean containsId(Object id) {
		return definitionMap.containsKey(id);
	}

	/**
	 * Returns a instance for bean by ID
	 */
	public Object getBean(Object id) throws BeanException {
		return this.instanceContainer.getBean(id, null);
	}

	/**
	 * Returns a instance for bean by class
	 */
	public Object getBeanOfType(Class beanClass) throws BeanException {
		return this.instanceContainer.getBeanByClass(beanClass, null);
	}

	/**
	 * Find all bean instance map
	 */
	public Map getBeansMapOfType(Class beanClass) throws BeanException {
		return this.instanceContainer.getBeansMapByClass(beanClass, null);
	}

	/**
	 * Find all bean instance List
	 */
	public List getBeansListOfType(Class cls) throws BeanException {
		return this.instanceContainer.getBeansListByClass(cls, null);
	}

	/**
	 * register a object instance
	 */
	public void registerInstance(Object instance) throws BeanException {
		registerInstance(instance, instance);
	}

	/**
	 * Register a object instance with a ID
	 */
	public void registerInstance(Object id, Object instance) throws BeanException {
		this.checkIdDuplicateRegister(id);
		this.definitionMap.put(id, new InstanceDefinition(id, instance));
		log.info("Registered a bean instance:" + instance + " with id:" + id);
	}

	/**
	 * Register a classes
	 */
	public void registerClass(Class beanClass) throws BeanException {
		registerClass(beanClass, beanClass, null);
	}

	/**
	 * Register a classes
	 */
	public void registerClass(Class beanClass, BeanElement[] beanElements) throws BeanException {
		registerClass(beanClass, beanClass, beanElements);
	}

	/**
	 * Register a classes
	 */
	public void registerClass(Object id, Class beanClass) throws BeanException {
		registerClass(id, beanClass, null);
	}

	/**
	 * Register a classes
	 */
	public void registerClass(Object id, Class beanClass, BeanElement[] beanElements) throws BeanException {
		this.checkIdDuplicateRegister(id);
		this.checkBeanClass(id, beanClass);
		this.definitionMap.put(id, new ClassDefinition(id, beanClass, beanElements));
	}

	/**
	 * deregister a bean definition
	 */
	public void deregister(Object id) throws BeanException {
		definitionMap.remove(id);
	}

	/**
	 * destroy all bean in container and clear all bean definition
	 */
	public void destroy() {
		this.destroy2();
		Runtime.getRuntime().removeShutdownHook(this.instanceDestroyHook);
	}

	/**
	 * destroy all bean in container and clear all bean definition
	 */
	private void destroy2() {
		BaseDefinition[] definition = getAllDefinition();
		for (int i = 0, n = definition.length; i < n; i++) {
			try {
				Object instance = definition[i].getSingletonInstance();
				Method destroyMethod = definition[i].getDestroyMethod();
				if (instance != null && destroyMethod != null)
					BeanUtil.invokeMethod(instance, destroyMethod, new Object[0]);
				instance = null;
			} catch (Exception e) {
				log.info("Bean(" + definition[i].getBeanID() + ") * Fail to destroy instance", e);
			}
		}
		definitionMap.clear();
	}

	/**
	 * Element factory
	 */
	public BeanElementFactory getBeanElementFactory() throws BeanException {
		return this.beanElementFactory;
	}

	/**
	 * Parameter factory
	 */
	public BeanParameterFactory getBeanParameterFactory() throws BeanException {
		return this.beanParameterFactory;
	}

	/**
	 * parameter Object converter
	 */
	public ParamConvertFactory getParamConvertFactory() throws BeanException {
		return this.paramConvertFactory;
	}

	/**
	 * type conversion factory
	 */
	public TypeConvertFactory getBeanTypeConvertFactory() {
		return this.beanTypeConvertFactory;
	}

	/**
	 * add a field
	 */
	public void addInjectionField(Object id, InjectionField field) throws BeanException {
		this.getBeanDefinition(id).addInjectionField(field);
	}

	/**
	 * remove a field
	 */
	public void removeInjectionField(Object id, InjectionField field) throws BeanException {
		this.getBeanDefinition(id).removeInjectionField(field);
	}

	/**
	 * Register properties
	 */
	public void addInjectionProperty(Object id, InjectionProperty property) throws BeanException {
		this.getBeanDefinition(id).addInjectionProperty(property);
	}

	/**
	 * Register properties
	 */
	public void removeInjectionProperty(Object id, InjectionProperty property) throws BeanException {
		this.getBeanDefinition(id).removeInjectionProperty(property);
	}

	/**
	 * Register a method invocation
	 */
	public void addInjectionInvocation(Object id, InjectionInvocation invocation) throws BeanException {
		this.getBeanDefinition(id).addInjectionInvocation(invocation);
	}

	/**
	 * Register a method invocation
	 */
	public void removeInjectionInvocation(Object id, InjectionInvocation invocation) throws BeanException {
		this.getBeanDefinition(id).removeInjectionInvocation(invocation);
	}

	/**
	 * Register interception
	 */
	public void addInvocationInterception(Object id, InvocationInterception interception) throws BeanException {
		this.getBeanDefinition(id).addInvocationInterception(interception);
	}

	/**
	 * Register interception
	 */
	public void removeInvocationInterception(Object id, InvocationInterception interception) throws BeanException {
		this.getBeanDefinition(id).removeInvocationInterception(interception);
	}

	/**
	 * Set a proxy interface on a object for
	 */
	public void setProxyInterfaces(Object id, ProxyInterfaces proxyInterfaces) throws BeanException {
		this.setProxyInterfaces(id, proxyInterfaces.getInterfaces());
	}

	/**
	 * Set a proxy interface on a object for
	 */
	public void setProxyInterfaces(Object id, Class[] proxyInterfaces) throws BeanException {
		this.getBeanDefinition(id).setProxyInterfaces(proxyInterfaces);
	}

	/**
	 * set Parent
	 */
	public void setParentReferenceId(Object id, ParentReferenceId parent) throws BeanException {
		this.getBeanDefinition(id).setParentReferenceId(parent.getReferenceId());
	}

	/**
	 * set Parent
	 */
	public void setParentReferenceId(Object id, Object referenceId) throws BeanException {
		this.getBeanDefinition(id).setParentReferenceId(referenceId);
	}

	/**
	 * Register singleton for bean
	 */
	public void setInstanceSingleton(Object id, boolean singleton) throws BeanException {
		this.getBeanDefinition(id).setInstanceSingleton(singleton);
	}

	/**
	 * Register singleton for bean
	 */
	public void setInstanceSingleton(Object id, InstanceSingleton singleton) throws BeanException {
		this.setInstanceSingleton(id, singleton.isSingleton());
	}

	/**
	 * Register init method
	 */
	public void setInitMethodName(Object id, String initMethod) throws BeanException {
		this.getBeanDefinition(id).setInitMethodName(initMethod);
	}

	/**
	 * Register init method
	 */
	public void setInitMethodName(Object id, InitializeMethodName initMethod) throws BeanException {
		this.setInitMethodName(id, initMethod.getMethodName());
	}

	/**
	 * Register destroy method
	 */
	public void setDestroyMethodName(Object id, String destroyMethod) throws BeanException {
		BeanDefinition wrapper = this.getBeanDefinition(id);
		wrapper.setDestroyMethodName(destroyMethod);
	}

	/**
	 * Register init method
	 */
	public void setDestroyMethodName(Object id, DestroyMethodName destroyMethod) throws BeanException {
		this.setDestroyMethodName(id, destroyMethod.getMethodName());
	}

	/**
	 * check contains converter by type
	 */
	public boolean containsTypeConverter(Class type){
		return this.getBeanTypeConvertFactory().containsTypeConverter(type);
	}

	/**
	 * remove type converter
	 */
	public void removeTypeConverter(Class type){
		this.getBeanTypeConvertFactory().removeTypeConverter(type);
	}

	/**
	 * get type converter
	 */
	public TypeConverter getTypeConverter(Class type) {
		return (TypeConverter) this.getBeanTypeConvertFactory().getTypeConverter(type);
	}

	/**
	 * add type converter
	 */
	public void addTypeConverter(Class type, TypeConverter converter){
		this.getBeanTypeConvertFactory().addTypeConverter(type, converter);
	}

	/**
	 * convert object
	 */
	public Object convertObject(Object obj, Class type)throws TypeConvertException {
		return this.getBeanTypeConvertFactory().convert(obj, type);
	}

	
	/********************************以下方法非接口的实现,只供内部使用*******************************/

	/**
	 * 获得所有Bean的定义信息
	 */
	public BaseDefinition[] getAllDefinition() {
		return (BaseDefinition[]) definitionMap.values().toArray(new BaseDefinition[definitionMap.size()]);
	}

	/**
	 * 查找Bean定义
	 */
	public BaseDefinition getBeanDefinition(Object id) throws BeanException {
		if (definitionMap.containsKey(id))
			return (BaseDefinition) definitionMap.get(id);
		else
			throw new BeanDefinitionNotFoundException("Not found bean definition with id:" + id);
	}

	/**
	 * 检查注册见键是否已存在
	 */
	private void checkIdDuplicateRegister(Object id) throws BeanException {
		if (this.definitionMap.containsKey(id))
			throw new BeanIdDuplicateRegisterException("Duplicate register bean definition with id:" + id);
	}

	/**
	 * 检查注册见键是否已存在
	 */
	private void checkBeanClass(Object id, Class beanClass) throws BeanException {
		if (beanClass == null)
			throw new BeanClassException("Found null registered bean class with id:" + id);
	}
	

	
	
	private class ContainerDestroyHook extends Thread{

	  /**
	   * conatain a ioc container
	   */
	  private MayflyBeanContainer container;

	  /**
	   * constructor
	   */
	  public ContainerDestroyHook(MayflyBeanContainer container){
	    this.container = container;
	  }

	  /**
	   * main method of hook thread
	   */
	  public void run(){
	    this.container.destroy2();
	  }
 }
}

	