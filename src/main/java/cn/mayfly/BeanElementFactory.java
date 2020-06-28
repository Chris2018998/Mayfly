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

import cn.mayfly.element.AutowiredType;
import cn.mayfly.element.DestroyMethodName;
import cn.mayfly.element.InitializeMethodName;
import cn.mayfly.element.InjectionField;
import cn.mayfly.element.InjectionInvocation;
import cn.mayfly.element.InjectionProperty;
import cn.mayfly.element.InstanceCreation;
import cn.mayfly.element.InstanceSingleton;
import cn.mayfly.element.InvocationInterception;
import cn.mayfly.element.ProxyInterfaces;

/**
 * A factory to create some bean elements
 *
 * @author Chris Liao
 * @version 1.0
 */

public final class BeanElementFactory {

  /**
   * 创建是否单例属性
   */
  public InstanceSingleton createInstanceSingleton(boolean singleton)throws BeanElementException{
    return new InstanceSingleton(singleton);
  }
	
  /**
   * 创建破坏方法
   */
  public DestroyMethodName createDestroyMethod(String methodname)throws BeanElementException{
    return new DestroyMethodName(methodname);
  }

  /**
   * 创建初始化方法
   */
  public InitializeMethodName createInitializeMethod(String methodname)throws BeanElementException{
    return new InitializeMethodName(methodname);
  }
  
  /**
   *创建构造方法
   */
  public InstanceCreation createInstanceCreation(BeanParameter[] parameters)throws BeanElementException {
    return new InstanceCreation(parameters);
  }

  /**
   * 创建构造方法
   */
  public InstanceCreation createInstanceCreation(String factoryMethod,BeanParameter[] parameters)throws BeanElementException {
    return new InstanceCreation(factoryMethod,parameters);
  }

  /**
   * 创建构造方法
   */
  public InstanceCreation createInstanceCreation(String factoryBeanRefID,String factoryMethod,BeanParameter[] parameters)throws BeanElementException {
    return new InstanceCreation(factoryBeanRefID,factoryMethod,parameters);
  }

  
  /**
   * 创建自动自动注入字段
   */
  public InjectionField createInjectionField(String name)throws BeanElementException {
    return new InjectionField(name);
  }
  
  /**
   * 创建自动自动注入字段
   */
  public InjectionField createInjectionField(String name,AutowiredType type)throws BeanElementException {
    return new InjectionField(name,type);
  }
  
  /**
   * 创建注入字段
   */
  public InjectionField createInjectionField(String name,BeanParameter parameter)throws BeanElementException {
    return new InjectionField(name,parameter);
  }
 
  /**
   * 创建注入属性
   */
  public InjectionProperty createInjectionProperty(String name,BeanParameter parameter)throws BeanElementException {
    return new InjectionProperty(name, parameter);
  }
  
  /**
   * 创建注入方法
   */
  public InjectionInvocation createInjectionInvocation(String methodName,BeanParameter[] methodIocParameters)throws BeanElementException {
    return new InjectionInvocation(methodName, methodIocParameters);
  }
 
  /**
   * 创建实现接口
   */
  public ProxyInterfaces createProxyInterfaces(Class[] implInterfaces)throws BeanElementException{
    return new ProxyInterfaces(implInterfaces);
  }

  /**
   * 创建方法拦截
   */
  public InvocationInterception createInvocationInterception(String methodName,Class[] parameterTypes)throws BeanElementException {
    return new InvocationInterception(methodName,parameterTypes);
  }
}