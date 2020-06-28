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
package cn.mayfly.impl.config.xml;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import cn.mayfly.BeanContainer;
import cn.mayfly.BeanElement;
import cn.mayfly.BeanException;
import cn.mayfly.impl.config.xml.element.BeanEelementXMLTags;
import cn.mayfly.impl.config.xml.element.BeanElementXMLFactory;
import cn.mayfly.impl.config.xml.parameter.BeanParameterXMLTags;
import cn.mayfly.impl.exception.BeanClassNotFoundException;
import cn.mayfly.impl.exception.BeanConfiguerationFileException;
import cn.mayfly.impl.exception.BeanDefinitionException;
import cn.mayfly.impl.exception.BeanIdDuplicateRegisterException;
import cn.mayfly.impl.util.ClassUtil;
import cn.mayfly.impl.util.StringUtil;

/**
 * 部署文件Loader
 * 
 * @author chris
 */

public class BeanXMLFileLoader {

	/**
	 * 类的扫描
	 */
	private BeanClassScanner beanClassScanner = new BeanClassScanner();
	
	/**
	 * bean xml node util
	 */
	private BeanXMLNodeUtil elementXMLUtil = new BeanXMLNodeUtil();

	/**
	 * Eelement XML Tags
	 */
	private BeanEelementXMLTags elementXMLTags = new BeanEelementXMLTags();
	
	/**
	 * parameter XML Tags
	 */
	private BeanParameterXMLTags parameterXMLTags = new BeanParameterXMLTags();
	
  /**
   * Bean XML工厂类
   */
	private BeanElementXMLFactory beanElementXMLFactory = new BeanElementXMLFactory(elementXMLUtil,elementXMLTags,parameterXMLTags);
	
	/**
	 * xml解析
	 */
	private SAXBuilder saxBuilder = new SAXBuilder();

	
	/**
	 * 装载默认文件
	 */
	public void loadIocFiles(BeanContainer container, URL[] fileURLs)throws BeanException {
		if(fileURLs ==null)
			 throw new BeanConfiguerationFileException("File url array can't be null");
		for(int i=0,n=fileURLs.length;i<n;i++){
			if(fileURLs[i]!=null)
			loadIocFile(container,fileURLs[i]);
		}
	}
	
	/**
	 * 装载文件
	 */
	public void loadIocFile(BeanContainer iocContainer,URL url)throws BeanException{
		try {
			BeanXMLFileFinder.validateXMLFile(url);
			Document document = saxBuilder.build(url);
			Element rootElement = document.getRootElement();
			BeanXMLFileFinder.validateXMLRoot(rootElement,BeanXMLConstants.TAG_BEANS,url.getPath());
			String spacename = rootElement.getAttributeValue(BeanXMLConstants.ATTR_ID_NAMESPACE);
	
			List annotationList=rootElement.getChildren(BeanXMLConstants.TAG_ANNOTATION);
			List includeList = rootElement.getChildren(BeanXMLConstants.TAG_INCLUDE);
			
			String fileFolder = BeanXMLFileFinder.getFilePath(url.getPath());
			resolveAnnotation(fileFolder,annotationList,iocContainer);
			resolveIncludeFile(fileFolder,includeList,iocContainer);
			List beanElementList = rootElement.getChildren(BeanXMLConstants.TAG_BEAN);
			resolveBeanList(url.getFile(),spacename,beanElementList,iocContainer);
		} catch (IOException e) {
			throw new BeanConfiguerationFileException(e);
		} catch (JDOMException e) {
			throw new BeanConfiguerationFileException(e);
		} catch (BeanException e) {
			throw e;
	  }
	}

	/**
	 * 解析annotation
	 */
	private void resolveAnnotation(String parentFolder,List annotationList,BeanContainer iocContainer)throws BeanException{
		for(int i=0,n=annotationList.size();i<n;i++){
			Element element =(Element)annotationList.get(i);
			String basePackage = elementXMLUtil.getValueByName(element,BeanXMLConstants.ATTR_ID_BASE_PACKAGE);
			String[]folders = StringUtil.split(basePackage,BeanXMLConstants.Symbols_COMMA);
		    for(int j=0,m=folders.length;j<m;j++){
		    	beanClassScanner.scan(folders[j],iocContainer);
		    }
		}
	}
	
	/**
	 * 解析Include文件
	 */
	private void resolveIncludeFile(String parentFolder, List includeList, BeanContainer iocContainer)throws BeanException {
		for (int i = 0, n = includeList.size(); i < n; i++) {
			Element element = (Element) includeList.get(i);
			String includeFile = elementXMLUtil.getValueByName(element, BeanXMLConstants.ATTR_ID_INCLUDE_FILE);
			loadIocFile(iocContainer, BeanXMLFileFinder.findFileURL(includeFile));
		}
	}
	
	/**
	 * 解析Bean列表
	 */
	private void resolveBeanList(String filename,String spacename,List elementList,BeanContainer iocContainer)throws BeanException{
		for(int i=0,n=elementList.size();i<n;i++){
			Element element =(Element)elementList.get(i);
			String beanid = elementXMLUtil.getValueByName(element,elementXMLTags.Id);
			String className=elementXMLUtil.getValueByName(element,elementXMLTags.Class);
			if(StringUtil.isNull(beanid))
				throw new BeanDefinitionException("Missed bean id at file:"+ filename);
			if(iocContainer.containsId(beanid))
				throw new BeanIdDuplicateRegisterException("Duplicate Registered with bean id:" +beanid +" at file:"+ filename);
			if(StringUtil.isNull(className))
				throw new BeanDefinitionException("Missed bean class with bean id:"+beanid+" at file:"+ filename);
			if(!StringUtil.isNull(spacename))
				beanid = spacename.trim()+ BeanXMLConstants.Symbols_DOT +beanid;
		
			try {
				BeanElement[] beanElements = beanElementXMLFactory.find(element,beanid,spacename,filename);
				iocContainer.registerClass(beanid,ClassUtil.loadClass(className),beanElements);
			} catch (ClassNotFoundException e) {
				throw new BeanClassNotFoundException("Not found bean class:"+ className +" with id: "+beanid+" at file:"+filename);
			}
		}
	}
}
