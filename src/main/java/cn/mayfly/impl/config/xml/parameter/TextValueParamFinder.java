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

import org.jdom.Element;
import cn.mayfly.BeanParameter;
import cn.mayfly.BeanParameterException;
import cn.mayfly.element.parameter.InstanceParameter;
import cn.mayfly.impl.config.xml.BeanXMLNodeUtil;
import cn.mayfly.impl.util.StringUtil;

/**
 * 文本参数
 *
 * @author Chris Liao
 * @version 1.0
 */
public class TextValueParamFinder implements BeanParameterXMLFinder{
	
	/**
	 * 获得参数
	 */
	public BeanParameter find(String spaceName,Element node,BeanParameterXMLFactory beanParameterXMLFactory,BeanParameterXMLTags paramXMLTags,BeanXMLNodeUtil xmlUtil)throws BeanParameterException{
		String text = xmlUtil.getValueByName(node,paramXMLTags.Value);
		if(text!=null && text.trim().length()>0){
			return new InstanceParameter(StringUtil.getFilterValue(text));//防止参数值里面带有系统的环境变量 ${XX},#{XX}
		}else if(text!=null){
			return new InstanceParameter(text);
		}else{
			throw new BeanParameterException("Null value at text parameter node:<"+node.getName()+">");
		}
	}
}
