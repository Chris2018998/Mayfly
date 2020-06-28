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
package cn.mayfly.impl.config.xml.element;

import java.util.ArrayList;
import java.util.List;

import cn.mayfly.impl.MayflyBeanContainer;
import org.jdom.Element;

import cn.mayfly.BeanElement;
import cn.mayfly.BeanException;
import cn.mayfly.element.InstanceCreation;
import cn.mayfly.impl.config.xml.BeanXMLNodeUtil;
import cn.mayfly.impl.config.xml.parameter.BeanParameterXMLFactory;
import cn.mayfly.impl.config.xml.parameter.BeanParameterXMLTags;
import cn.mayfly.impl.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bean Element Finder
 *
 * @author Chris Liao
 * @version 1.0
 */

public class BeanElementXMLFactory {

	/**
	 * logger
	 */
	private Logger log = LoggerFactory.getLogger(MayflyBeanContainer.class);
	
	/**
	 * 单例摸是查找
	 */
	private InstanceSingletonFinder singletonFinder = new InstanceSingletonFinder();
	
	/**
	 * 父亲ID摸查找
	 */
	private ParentReferenceIdFinder parentIdFinder = new ParentReferenceIdFinder();
	
	/**
	 * 破坏方法查找
	 */
	private DestroyMethodNameFinder destroyFinder = new DestroyMethodNameFinder();
	
	/**
	 * 初始化方法法查找
	 */
	private InitializeMethodNameFinder initFinder = new InitializeMethodNameFinder();

	/**
	 * 构造方式法查找
	 */
	private InstanceCreationFinder creationFinder = new InstanceCreationFinder();
	
	/**
	 * 代理接口查找
	 */
	private ProxyInterfaceFinder proxyInterfaceFinder  = new ProxyInterfaceFinder();
	
	
	/**
	 * 注入字段查找
	 */
	private InjectionFieldFinder fieldFinder  = new InjectionFieldFinder();

	/**
	 * 注入属性查找
	 */
	private InjectionPropertyFinder propertyFinder = new InjectionPropertyFinder();
	
	/**
	 * 方法注入查找
	 */
	private InjectionInvocationFinder invocationFinder = new InjectionInvocationFinder();
	
	/**
	 * 拦截查找
	 */
	private InvocationInterceptionFinder interceptionFinder = new InvocationInterceptionFinder();
	
	/**
	 * bean xml node util
	 */
	private BeanXMLNodeUtil elementXMLUtil;
	
	/**
	 * Eelement XML Tags
	 */
	private BeanEelementXMLTags elementXMLTags;
	
	/**
	 * parameter XML Tags
	 */
	private BeanParameterXMLTags parameterXMLTags;
	
	/**
	 * 参数查找工厂
	 */
	private BeanParameterXMLFactory beanParameterXMLFactory;
	
	/**
	 * 构造函数
	 */
	public BeanElementXMLFactory(BeanXMLNodeUtil elementXMLUtil,BeanEelementXMLTags elementXMLTags,BeanParameterXMLTags parameterXMLTags){
		this.elementXMLUtil = elementXMLUtil;
		this.elementXMLTags = elementXMLTags;
		this.parameterXMLTags=parameterXMLTags; 
		this.beanParameterXMLFactory=new BeanParameterXMLFactory(this.parameterXMLTags);
	}
	
	
	
	/**
	 * 查找Bean XML节点,找出其元素
	 */
	public BeanElement[] find(Element beanNode,String beanid,String spaceName,String file)throws BeanException{
		List tempElementList = new ArrayList();
		BeanElement parentid = parentIdFinder.find(beanNode,beanid,spaceName,file,beanParameterXMLFactory,elementXMLTags,elementXMLUtil);
		if (parentid != null)
			tempElementList.add(parentid);
	
		BeanElement singleton = singletonFinder.find(beanNode,beanid,spaceName,file,beanParameterXMLFactory,elementXMLTags,elementXMLUtil);
		if (singleton != null)
			tempElementList.add(singleton);

		BeanElement InitMethod = initFinder.find(beanNode,beanid,spaceName,file,beanParameterXMLFactory,elementXMLTags,elementXMLUtil);
		if (InitMethod != null)
			tempElementList.add(InitMethod);
			
		BeanElement DestroyMethod = destroyFinder.find(beanNode,beanid,spaceName,file,beanParameterXMLFactory,elementXMLTags,elementXMLUtil);
		if (DestroyMethod != null)
			tempElementList.add(DestroyMethod);
	
		BeanElement Constructor = creationFinder.find(beanNode,beanid,spaceName,file,beanParameterXMLFactory,elementXMLTags,elementXMLUtil);
		if (Constructor != null)
			tempElementList.add(Constructor);
		
		BeanElement ProxyInterfaces = proxyInterfaceFinder.find(beanNode,beanid,spaceName,file,beanParameterXMLFactory,elementXMLTags,elementXMLUtil);
		if (ProxyInterfaces != null)
			tempElementList.add(ProxyInterfaces);
		
		List childList = beanNode.getChildren(elementXMLTags.Field);
		for(int i=0,n=childList.size();i<n;i++){
	  	Element autowireNode = (Element)childList.get(i);
	  	tempElementList.add(fieldFinder.find(autowireNode,beanid,spaceName,file,beanParameterXMLFactory,elementXMLTags,elementXMLUtil));
	  }
		
		childList = beanNode.getChildren(elementXMLTags.Property);
		for(int i=0,n=childList.size();i<n;i++){
	  	Element propNode = (Element)childList.get(i);
	  	tempElementList.add(propertyFinder.find(propNode,beanid,spaceName,file,beanParameterXMLFactory,elementXMLTags,elementXMLUtil));
	  }
	
		childList = beanNode.getChildren(elementXMLTags.Invocation);
		for(int i=0,n=childList.size();i<n;i++){
	    Element invocationNode = (Element)childList.get(i);
	    tempElementList.add(invocationFinder.find(invocationNode,beanid,spaceName,file,beanParameterXMLFactory,elementXMLTags,elementXMLUtil));
	  }
	
  	List inteceptorList = beanNode.getChildren(elementXMLTags.Interception);
  	for(int i=0,n=inteceptorList.size();i<n;i++){
	    Element inteceptorNode = (Element)inteceptorList.get(i);
	    tempElementList.add(interceptionFinder.find(inteceptorNode,beanid,spaceName,file,beanParameterXMLFactory,elementXMLTags,elementXMLUtil));
	  }
	  
	  if(Constructor!=null){
	  	InstanceCreation creation =(InstanceCreation)Constructor;
	  	if(creation.getFactoryBeanRefID()!=null || !StringUtil.isNull(creation.getFactoryMethodName())){
	  		if(!inteceptorList.isEmpty() && ProxyInterfaces ==null){
	  			log.warn(beanid,"Interception will be disabled,not support factory-bean/factory-method with zero proxy inteface at file:"+file);
	  		}
	  	}
	  }
	 
		return (BeanElement[])tempElementList.toArray(new BeanElement[tempElementList.size()]);
	}
}
