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
package cn.mayfly;

import java.util.List;
import java.util.Map;

import cn.magic.TypeConvertException;
import cn.magic.TypeConverter;
import cn.mayfly.element.DestroyMethodName;
import cn.mayfly.element.InitializeMethodName;
import cn.mayfly.element.InjectionField;
import cn.mayfly.element.InjectionInvocation;
import cn.mayfly.element.InjectionProperty;
import cn.mayfly.element.InstanceSingleton;
import cn.mayfly.element.InvocationInterception;
import cn.mayfly.element.ParentReferenceId;
import cn.mayfly.element.ProxyInterfaces;
/**
 * A micro-container registeres bean part definition,and which is called as elements,
 * the container will control bean instances lifecycle by their elements definition.
 * After elements registeration,bean instances can be gotten with registered keys from
 * the container.Some registeration methods are supplied to import elements to container.
 *
 * @author Chris Liao
 * @version 1.0
 */

public interface BeanContainer  {

  /**
   * contains beanID
   */
  public boolean containsId(Object id);

  /**
   * Find a bean instance from container by a id.
   * If not found, then return null.
   */
  public Object getBean(Object id)throws BeanException;

  /**
   * Find a bean instance with a class. If not found, then return null.
   */
  public Object getBeanOfType(Class cls)throws BeanException;

  /**
   * Find all bean instance map
   */
  public Map getBeansMapOfType(Class cls)throws BeanException;
  
  /**
   * Find all bean instance List
   */
  public List getBeansListOfType(Class cls)throws BeanException;
  
  /**
   * register a object instance in container
   */
  public void registerInstance(Object instance)throws BeanException;

  /**
   * register a object instance with a id
   */
  public void registerInstance(Object id, Object instance)throws BeanException;

  /**
   * register a classes
   */
  public void registerClass(Class beanClass)throws BeanException;
  
  /**
   * register a classes
   */
  public void registerClass(Class beanClass, BeanElement[] beanElements)throws BeanException;

  /**
   * register a classes
   */
  public void registerClass(Object id, Class beanClass)throws BeanException;

  /**
   * register a classes
   */
  public void registerClass(Object id, Class beanClass, BeanElement[] beanElements)throws BeanException;

  /**
   * deregister a bean by id
   */
  public void deregister(Object id)throws BeanException;
  
  /**
   * Destroy operation in ioc container
   */
  public void destroy();
  
  
  
  
  /**
   * Element factory
   */
  public BeanElementFactory getBeanElementFactory()throws BeanException;
  
  /**
   *Parameter factory
   */
  public BeanParameterFactory getBeanParameterFactory()throws BeanException;
  
  /**
   * set singleton for bean
   */
  public void setInstanceSingleton(Object id, boolean singleton)throws BeanException;

  /**
   * set singleton for bean
   */
  public void setInstanceSingleton(Object id, InstanceSingleton singleton)throws BeanException;
 
  /**
   * set singleton for bean
   */
  public void setParentReferenceId(Object id, Object parentID)throws BeanException;
  
  /**
   * set singleton for bean
   */
  public void setParentReferenceId(Object id, ParentReferenceId parent)throws BeanException;

  /**
   * set init Invocation
   */
  public void setInitMethodName(Object id, String methodName)throws BeanException;
  
  /**
   * set init Invocation
   */
  public void setInitMethodName(Object id, InitializeMethodName methodName)throws BeanException;
  
  /**
   * set destroy Invocation
   */
  public void setDestroyMethodName(Object id, String methodName)throws BeanException;

  /**
   * set destroy Invocation
   */
  public void setDestroyMethodName(Object id, DestroyMethodName methodName)throws BeanException;
  
  

  
 
  
  /**
   * add a field
   */
  public void addInjectionField(Object id, InjectionField field)throws BeanException;

  /**
   * remove a field
   */
  public void removeInjectionField(Object id, InjectionField field)throws BeanException;
  
  /**
   * add a property
   */
  public void addInjectionProperty(Object id, InjectionProperty property)throws BeanException;

  /**
   * remove a property
   */
  public void removeInjectionProperty(Object id, InjectionProperty property)throws BeanException;

  /**
   * add a Invocation invocation
   */
  public void addInjectionInvocation(Object id, InjectionInvocation invocation)throws BeanException;

  /**
   * remove a Invocation invocation
   */
  public void removeInjectionInvocation(Object id, InjectionInvocation invocation)throws BeanException;

  /**
   * add a interception
   */
  public void addInvocationInterception(Object id, InvocationInterception interceptions)throws BeanException;

  /**
   * remove a  interception
   */
  public void removeInvocationInterception(Object id, InvocationInterception interceptions)throws BeanException;

  /**
   * Set proxy interfaces on a object for
   */
  public void setProxyInterfaces(Object id, Class[] proxyInterfaces)throws BeanException;
  
  /**
   * Set proxy interfaces on a object for
   */
  public void setProxyInterfaces(Object id, ProxyInterfaces proxyInterfaces)throws BeanException;
  

  
  
  
	/**
	 * check contains converter by type
	 */
	public boolean containsTypeConverter(Class type);
	 
	/**
	 * remove type converter
	 */
	public void removeTypeConverter(Class type);
	
	/**
	 * get type converter
	 */
	public TypeConverter getTypeConverter(Class type);

	/**
	 * add type converter
	 */
	public void addTypeConverter(Class type, TypeConverter converter)throws TypeConvertException;
	
	/**
	 * convert object
	 */
	public Object convertObject(Object obj, Class type)throws BeanException, TypeConvertException;

}