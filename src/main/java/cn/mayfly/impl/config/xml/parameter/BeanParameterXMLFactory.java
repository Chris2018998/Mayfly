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
package cn.mayfly.impl.config.xml.parameter;

import java.util.HashMap;
import java.util.Map;

import org.jdom.Element;
import cn.mayfly.BeanParameter;
import cn.mayfly.BeanParameterException;
import cn.mayfly.impl.config.xml.BeanXMLNodeUtil;
import cn.mayfly.impl.config.xml.parameter.collection.ListParamFinder;
import cn.mayfly.impl.config.xml.parameter.collection.MapParamFinder;
import cn.mayfly.impl.config.xml.parameter.collection.SetParamFinder;
import cn.mayfly.impl.util.StringUtil;

/**
 * 参数查找工厂
 * 
 * @author Chris Liao
 */

public class BeanParameterXMLFactory {
	
	/**
	 * 查找器列表
	 */
  private Map finderMap = new HashMap();
  
	/**
	 * 参数标记
	 */
  private BeanParameterXMLTags parmXMLTags;
 
  /**
   * 构造函数
   */
  public BeanParameterXMLFactory(BeanParameterXMLTags parmXMLTags) {
  	 this.parmXMLTags =parmXMLTags;
     MapParamFinder mapFinder = new MapParamFinder();
     finderMap.put(this.parmXMLTags.Map,mapFinder);
     finderMap.put(this.parmXMLTags.HashMap,mapFinder);
     finderMap.put(this.parmXMLTags.Hashtable,mapFinder);
     finderMap.put(this.parmXMLTags.TreeMap,mapFinder);
     finderMap.put(this.parmXMLTags.WeakHashMap,mapFinder);
     finderMap.put(this.parmXMLTags.Props,mapFinder);
     
     ListParamFinder listFinder = new ListParamFinder();
     finderMap.put(this.parmXMLTags.List,listFinder);
     finderMap.put(this.parmXMLTags.ArrayList,listFinder);
     finderMap.put(this.parmXMLTags.LinkedList,listFinder);
     finderMap.put(this.parmXMLTags.Stack,listFinder);
     finderMap.put(this.parmXMLTags.Vector,listFinder);
     
     SetParamFinder setFinder = new SetParamFinder();
     finderMap.put(this.parmXMLTags.Set,setFinder);
     finderMap.put(this.parmXMLTags.HashSet,setFinder);
     finderMap.put(this.parmXMLTags.TreeSet,setFinder);
     finderMap.put(this.parmXMLTags.Array,setFinder);
     
 	   finderMap.put(this.parmXMLTags.Value,new TextValueParamFinder());
     finderMap.put(this.parmXMLTags.Class,new ClassNameParamFinder());
     finderMap.put(this.parmXMLTags.Ref,new ReferenceParamFinder());
  }
  
  /**
   *  参数标记
   */
  public BeanParameterXMLTags getBeanParameterXMLTags(){
  	return this.parmXMLTags;
  }
  
	/**
	 * 从一个节点中查找出其XML参数
	 */
	public BeanParameter find(String spaceName,Element element,BeanParameterXMLFactory beanParameterXMLFactory,
			BeanXMLNodeUtil xmlUtil)throws BeanParameterException {
		
  	Element targetElement = element;
  	BeanParameterXMLFinder finder =(BeanParameterXMLFinder)finderMap.get(element.getName());
  	 if(finder == null){
  		 	Element subElement = getChildConainerNode(element);
  		 	if(subElement != null){
  		 		finder = (BeanParameterXMLFinder)finderMap.get(subElement.getName());
  		 		 targetElement = subElement;
  		 	}
  	 }
  	
  	if(finder == null){
    	 if (isValueNode(element)) {
    		 finder = (BeanParameterXMLFinder)finderMap.get(this.parmXMLTags.Value);
       } else if (isClassNode(element)) {
      	 finder = (BeanParameterXMLFinder)finderMap.get(this.parmXMLTags.Class);
       } else if (isRefNode(element)) {
      	 finder = (BeanParameterXMLFinder)finderMap.get(this.parmXMLTags.Ref);
       }
    }
    
    if (finder != null)
      return finder.find(spaceName,targetElement,beanParameterXMLFactory,this.parmXMLTags,xmlUtil);
    else
      throw new BeanParameterException("Not found a machted parmeter finder for unknown parameter node:<"+ element.getName()+">");
	}

	
	/**
	 * 是否为文本参数类型节点
	 */
	private boolean isValueNode(Element paraElement) {
		return (isNodeType(paraElement,this.parmXMLTags.Value) ||!StringUtil.isNull(paraElement.getTextTrim()));
	}
	
	/**
	 * 是否为类参数节点
	 */
	private boolean isClassNode(Element paraElement) {
		return isNodeType(paraElement,this.parmXMLTags.Class);
	}

	/**
	 * 是否为引用节点
	 */
	private boolean isRefNode(Element paraElement) {
		return isNodeType(paraElement,this.parmXMLTags.Ref);
	}

	/**
	 * 将节点是否为某种类型
	 */
	private boolean isNodeType(Element paraElement,String type) {
		return (paraElement.getName().equals(type)|| paraElement.getAttribute(type) != null || paraElement.getChild(type) != null)?true: false;
	}

	/**
	 * 查找子节点，且为容器型节点
	 */
	private Element getChildConainerNode(Element paraElement) {
		Element element = paraElement.getChild(this.parmXMLTags.Array);
		if (element == null)
			element = paraElement.getChild(this.parmXMLTags.List);
		if (element == null)
			element = paraElement.getChild(this.parmXMLTags.ArrayList);
		if (element == null)
			element = paraElement.getChild(this.parmXMLTags.LinkedList);
		if (element == null)
			element = paraElement.getChild(this.parmXMLTags.Stack);
		if (element == null)
			element = paraElement.getChild(this.parmXMLTags.Vector);

		if (element == null)
			element = paraElement.getChild(this.parmXMLTags.Set);
		if (element == null)
			element = paraElement.getChild(this.parmXMLTags.HashSet);
		if (element == null)
			element = paraElement.getChild(this.parmXMLTags.TreeSet);

		if (element == null)
			element = paraElement.getChild(this.parmXMLTags.Map);
		if (element == null)
			element = paraElement.getChild(this.parmXMLTags.HashMap);
		if (element == null)
			element = paraElement.getChild(this.parmXMLTags.Hashtable);
		if (element == null)
			element = paraElement.getChild(this.parmXMLTags.TreeMap);
		if (element == null)
			element = paraElement.getChild(this.parmXMLTags.Props);
		if (element == null)
			element = paraElement.getChild(this.parmXMLTags.WeakHashMap);
		return element;
	}
}
