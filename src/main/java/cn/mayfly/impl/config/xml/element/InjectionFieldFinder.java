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

import org.jdom.Element;

import cn.mayfly.BeanElement;
import cn.mayfly.BeanElementException;
import cn.mayfly.BeanParameter;
import cn.mayfly.BeanParameterException;
import cn.mayfly.element.AutowiredType;
import cn.mayfly.element.InjectionField;
import cn.mayfly.impl.config.xml.BeanXMLNodeUtil;
import cn.mayfly.impl.config.xml.parameter.BeanParameterXMLFactory;
import cn.mayfly.impl.config.xml.parameter.BeanParameterXMLTags;
import cn.mayfly.impl.util.StringUtil;

/**
 * Bean Element Finder
 *
 * @author Chris Liao
 * @version 1.0
 */

public class InjectionFieldFinder implements BeanElementXMLFinder{
	/**
	 * 查找注入属性
	 */
	public BeanElement find(Element beanNode,String beanid,String spaceName,String file,BeanParameterXMLFactory beanParameterXMLFactory,BeanEelementXMLTags xmlTags,BeanXMLNodeUtil xmlUtil) throws BeanElementException{
		BeanParameterXMLTags paramTags = beanParameterXMLFactory.getBeanParameterXMLTags();
		String fieldName = xmlUtil.getValueByName(beanNode,paramTags.Constants_FIELD_NAME);
		try {
			if (!StringUtil.isNull(fieldName)){
				String autowiredType = xmlUtil.getValueByName(beanNode,paramTags.TAG_ID_Autowired_Type);
				 
				if(!StringUtil.isNull(autowiredType)){
				 if(!autowiredType.equalsIgnoreCase(paramTags.Constants_Autowired_By_Name) && !autowiredType.equalsIgnoreCase(paramTags.Constants_Autowired_By_Type))
		  		 throw new BeanElementException("Invalid autowired field["+fieldName+"]of bean:"+beanid +" at file:"+file+",it must be one of[ByName,ByType]");
		
					return new InjectionField(fieldName,AutowiredType.toAutowiredType(autowiredType));
				}else{
					BeanParameter beanParameter = beanParameterXMLFactory.find(spaceName,beanNode,beanParameterXMLFactory,xmlUtil);
					if(beanParameter==null){
						return new InjectionField(fieldName,AutowiredType.BY_NAME);
					}else{
						return new InjectionField(fieldName,beanParameter);
					}
				}
			}else
				throw new BeanElementException("Null field name of bean:"+beanid +" at file:"+file);
		} catch (BeanParameterException e) {
			 throw new BeanElementException("Failed to find parameter for field["+fieldName+"]of bean:"+beanid +" at file:"+file,e);
		}
	}
}