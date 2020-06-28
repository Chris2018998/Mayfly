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

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.WeakHashMap;

import org.jdom.Element;
import cn.mayfly.BeanParameter;
import cn.mayfly.BeanParameterException;
import cn.mayfly.element.parameter.InstanceParameter;
import cn.mayfly.element.parameter.ReferenceParameter;
import cn.mayfly.element.parameter.collection.MapParameter;
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
public class MapParamFinder implements BeanParameterXMLFinder{

	/**
	 * 解析获得Map参类
	 */
	public BeanParameter find(String spaceName,Element node,BeanParameterXMLFactory beanParameterXMLFactory,
			BeanParameterXMLTags paramXMLTags,BeanXMLNodeUtil xmlUtil)throws BeanParameterException{
			Map map = this.createMap(node.getName(),paramXMLTags);
			List childList = node.getChildren(paramXMLTags.Constants_Map_Entry);
		  for(int i=0,size=childList.size();i<size;i++){
			  Element mapEntry = (Element)childList.get(i); 
			  String key = mapEntry.getAttributeValue(paramXMLTags.Constants_Map_Key);
			  BeanParameter keyMeta= null;
			  if(key== null){
			  	key = mapEntry.getAttributeValue(paramXMLTags.Ref);
			  	if(key!=null)
			  		keyMeta = new ReferenceParameter(key);
			  }else{ 
			  	keyMeta = new InstanceParameter(key);
			  }
			  
			 if(keyMeta == null)
			  throw new BeanParameterException("Can't find valid Key for map node " + node.getName());
			 
			  map.put(keyMeta,beanParameterXMLFactory.find(spaceName,mapEntry,beanParameterXMLFactory,xmlUtil));
			}
			return new MapParameter(map);
  }
	
	/**
	 * 创建Map子类
	 */
	private Map createMap(String nodeName,BeanParameterXMLTags paramXMLTags){
		if(paramXMLTags.HashMap.equalsIgnoreCase(nodeName)){
			return new HashMap();
		}else if(paramXMLTags.Hashtable.equalsIgnoreCase(nodeName)){
			return new Hashtable();
		}else if(paramXMLTags.Props.equalsIgnoreCase(nodeName)){
			return new Properties();
		}else if(paramXMLTags.TreeMap.equalsIgnoreCase(nodeName)){
			return new TreeMap();
		}else if(paramXMLTags.WeakHashMap.equalsIgnoreCase(nodeName)){
			return new WeakHashMap();
		}else{
			return new HashMap();
		}
	}
}