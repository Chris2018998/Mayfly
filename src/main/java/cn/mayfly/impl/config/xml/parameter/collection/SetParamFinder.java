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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.jdom.Element;
import cn.mayfly.BeanParameter;
import cn.mayfly.BeanParameterException;
import cn.mayfly.element.parameter.collection.SetParameter;
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
public class SetParamFinder implements BeanParameterXMLFinder{

	/**
	 * 解析获得Set参类
	 */
	public BeanParameter find(String spaceName,Element node,BeanParameterXMLFactory beanParameterXMLFactory,
			BeanParameterXMLTags paramXMLTags,BeanXMLNodeUtil xmlUtil)throws BeanParameterException{
		Set set = this.createSet(node.getName(),paramXMLTags);
		List itemList = node.getChildren(paramXMLTags.Constants_Item);
		for(int i = 0,n=itemList.size(); i<n; i++) {
			Element item = (Element) itemList.get(i);
			set.add(beanParameterXMLFactory.find(spaceName,item,beanParameterXMLFactory,xmlUtil));
		}
		
		return new SetParameter(set);
	}
	
	/**
	 * 创建Set子类
	 */
	private Set createSet(String nodename,BeanParameterXMLTags paramXMLTags){
		if(paramXMLTags.HashSet.equalsIgnoreCase(nodename)){
			return new HashSet();
		}else if(paramXMLTags.TreeSet.equalsIgnoreCase(nodename)){
			return new TreeSet();
		}else{
			return new HashSet();
		}
	}
}