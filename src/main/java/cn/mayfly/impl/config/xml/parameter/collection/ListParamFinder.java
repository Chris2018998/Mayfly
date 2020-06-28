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
package cn.mayfly.impl.config.xml.parameter.collection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import org.jdom.Element;
import cn.mayfly.BeanParameter;
import cn.mayfly.BeanParameterException;
import cn.mayfly.element.parameter.collection.ListParameter;
import cn.mayfly.impl.config.xml.BeanXMLNodeUtil;
import cn.mayfly.impl.config.xml.parameter.BeanParameterXMLFactory;
import cn.mayfly.impl.config.xml.parameter.BeanParameterXMLFinder;
import cn.mayfly.impl.config.xml.parameter.BeanParameterXMLTags;

/**
 * 容器类型的Value
 *
 * @author Chris Liao
 * @version 1.0
 */
public class ListParamFinder implements BeanParameterXMLFinder{

	/**
	 * 解析获得List参类
	 */
	public BeanParameter find(String spaceName,Element node,BeanParameterXMLFactory beanParameterXMLFactory,
			BeanParameterXMLTags paramXMLTags,BeanXMLNodeUtil xmlUtil)throws BeanParameterException{
		List list = this.createList(node.getName(),paramXMLTags);
		List childList = node.getChildren(paramXMLTags.Constants_Item);
		for(int i=0,n=childList.size();i<n;i++){
			Element item = (Element)childList.get(i);
			list.add(beanParameterXMLFactory.find(spaceName,item,beanParameterXMLFactory,xmlUtil));
		}
		return new ListParameter(list);
	}
	
	/**
	 * 创建List子类
	 */
	private List createList(String nodename,BeanParameterXMLTags paramXMLTags){
		if(paramXMLTags.ArrayList.equalsIgnoreCase(nodename)){
			return new ArrayList();
		}else if(paramXMLTags.LinkedList.equalsIgnoreCase(nodename)){
			return new LinkedList();
		}else if(paramXMLTags.Stack.equalsIgnoreCase(nodename)){
			return new Stack();
		}else if(paramXMLTags.Vector.equalsIgnoreCase(nodename)){
			return new Vector();
		}else{
			return new ArrayList();
		}
	}
}