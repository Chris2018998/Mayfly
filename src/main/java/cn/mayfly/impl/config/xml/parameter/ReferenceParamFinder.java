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

import org.jdom.Attribute;
import org.jdom.Element;
import cn.mayfly.BeanParameter;
import cn.mayfly.BeanParameterException;
import cn.mayfly.element.parameter.ReferenceParameter;
import cn.mayfly.impl.config.xml.BeanXMLNodeUtil;
import cn.mayfly.impl.util.StringUtil;

/**
 * 引用参数
 *
 * @author Chris Liao
 * @version 1.0
 */

public class ReferenceParamFinder implements BeanParameterXMLFinder{
	
	/**
	 * 查找引用参数
	 */
	public BeanParameter find(String spaceName,Element node,BeanParameterXMLFactory beanParameterXMLFactory,BeanParameterXMLTags paramXMLTags,BeanXMLNodeUtil xmlUtil)throws BeanParameterException{
    String refid = null;
    if(paramXMLTags.Ref.equalsIgnoreCase(node.getName())){
    	//有可能出现三种编写方式
    	Attribute beanRefAttr = node.getAttribute(paramXMLTags.ATTR_ID_Ref_Bean);
    	Attribute localRefAttr = node.getAttribute(paramXMLTags.ATTR_ID_Ref_Local);
    	Attribute valueRefAttr = node.getAttribute(paramXMLTags.ATTR_ID_Ref_Value);
    	
    	if(beanRefAttr!=null)
    		refid = beanRefAttr.getValue();
    	else if(localRefAttr!=null)
    		refid = localRefAttr.getValue();
    	else if(valueRefAttr!=null)
    		refid = valueRefAttr.getValue();
    }else if(node.getAttribute(paramXMLTags.Ref)!=null){
    	refid = node.getAttributeValue(paramXMLTags.Ref);
    } 
   
  	if(StringUtil.isNull(refid))
  		throw new BeanParameterException("Null value at reference-id parameter node:<"+node.getName()+">");
  	else if(!StringUtil.isNull(spaceName))
  		refid = new StringBuilder(spaceName).append(".").append(refid.trim()).toString() ;
   
		return new ReferenceParameter(refid);
	}
}