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

import java.util.List;

import org.jdom.Element;

import cn.mayfly.BeanElement;
import cn.mayfly.BeanElementException;
import cn.mayfly.BeanParameter;
import cn.mayfly.BeanParameterException;
import cn.mayfly.element.InstanceCreation;
import cn.mayfly.impl.config.xml.BeanXMLNodeUtil;
import cn.mayfly.impl.config.xml.parameter.BeanParameterXMLFactory;
import cn.mayfly.impl.util.StringUtil;

/**
 * XML Template is below
 * 
 *<constructor-arg value="xxxxx"/>
 *<constructor-arg class="xxxxx"/>
 *<constructor-arg ref="xxxxx"/>
 *
 *<constructor-arg>
 *  <value>xxxx</value>
 *</constructor-arg>
 *<constructor-arg>
 * xxxxxxx
 *</constructor-arg>
 *
 *<constructor-arg>
 *  <ref>xxxx</ref>
 *</constructor-arg>
 *<constructor-arg>
 *  <ref value="xxxx"/>
 *</constructor-arg>
 *
 *<constructor-arg>
 *  <class>xxxx</class>
 *</constructor-arg>
 *<constructor-arg>
 *  <class value="xxxx"/>
 *</constructor-arg>
 *
 *
 * <parameter>
 * <map><entry key ="xxxx" value="xxxx"/><entry key ="xxxx"><value>
 * xxxxx</value></entry>
 *
 * <entry key ="xxxx" ref="xxxx"/><entry key ="xxxx"><ref value="xxxx"/>
 * </entry><entry key ="xxxx"><ref>xxxxx</ref></entry>
 *
 * <entry key ="xxxx" class="xxxx"/><entry key ="xxxx">
 * <class value="xxxx"/></entry><entry key ="xxxx"><class>xxxxx
 * </class></entry>
 *
 * <entry key ="xxxx"  value ="xxx"/><entry ref ="xxxx"  value="xxx"/>
 * <entry class="xxxx" value ="xxxx"/></map></parameter>
 *
 * <parameter>
 * <props>
 * <prop key ="xxxx"  value ="xxx"/>
 * <prop key ="xxxx"/>
 * <value="xxxxx"/></prop></props></parameter>
 *
 * <parameter>
 * <list><item value="xxx"/><item><value>xxxx</value></item>
 *
 * <item ref="xxx"/><item><ref value="xxxx"/></item><item><ref>xxx
 * </ref></item>
 *
 * <item class="xxxx"/><item><class value="xxx"/></item><item><class>
 * xxxx</class></item></list></parameter>
 * </constructor>
 */

public class InstanceCreationFinder implements BeanElementXMLFinder{

	/**
	 * 查找Bean定义元素
	 */
	public BeanElement find(Element beanNode,String beanid,String spaceName,String file,BeanParameterXMLFactory beanParameterXMLFactory,BeanEelementXMLTags xmlTags,BeanXMLNodeUtil xmlUtil) throws BeanElementException{
		Element constructorNode = beanNode.getChild(xmlTags.Constructor);
		if(constructorNode != null){//<constructor>  </constructor>
	    String factoryRefID = getFactoryRefID(beanNode,xmlTags,beanParameterXMLFactory);
	    String factoryMethod = getFactoryMethod(beanNode,xmlTags);
	    
	    List childList = constructorNode.getChildren(xmlTags.Constructor_arg);
			BeanParameter[] parameters = new BeanParameter[childList.size()];
	    try {
	    		for(int i=0,n=parameters.length;i<n;i++)
	    		 parameters[i] = beanParameterXMLFactory.find(spaceName,(Element)childList.get(i),beanParameterXMLFactory,xmlUtil);
			} catch (BeanParameterException e) {
				throw new BeanElementException("Failed to find parameters for constructer of bean:"+beanid +" at file:"+file,e);
			}
	   
	    if(!StringUtil.isNull(factoryRefID) && !StringUtil.isNull(factoryMethod)){
	    	return new InstanceCreation(factoryRefID,factoryMethod,parameters);
	    }else if(!StringUtil.isNull(factoryMethod)){
	    	return new InstanceCreation(factoryMethod,parameters);
	    }else {
	    	return new InstanceCreation(parameters);
	    }
		}else{
			return null;
		}
  }

	/**
	 * find factory bean refID
	 */
	private String getFactoryRefID(Element node,BeanEelementXMLTags xmlTags,BeanParameterXMLFactory beanParameterXMLFactory){
	  Element parent = (Element)node.getParent();
	  String factoryBeanId = parent.getAttributeValue(xmlTags.Factory_Bean);
	  
	  if(StringUtil.isNull(factoryBeanId))
	   factoryBeanId = node.getAttributeValue(beanParameterXMLFactory.getBeanParameterXMLTags().Ref);
	  
	  if(StringUtil.isNull(factoryBeanId))
	    factoryBeanId = parent.getChildText(xmlTags.Factory_Bean);
	
	  return factoryBeanId;
	}
	
	/**
	 * find factory method
	 */
	private String getFactoryMethod(Element node,BeanEelementXMLTags xmlTags){
		Element parent = (Element)node.getParent();
	  String factoryMethod = parent.getAttributeValue(xmlTags.Factory_Method);
	  
	  if(StringUtil.isNull(factoryMethod))
	    factoryMethod = parent.getChildText(xmlTags.Factory_Method);
	 
	  return factoryMethod;
	}
}