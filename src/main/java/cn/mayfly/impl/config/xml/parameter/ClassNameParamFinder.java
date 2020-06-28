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
import cn.mayfly.element.parameter.ClassParameter;
import cn.mayfly.impl.config.xml.BeanXMLNodeUtil;
import cn.mayfly.impl.util.ClassUtil;
import cn.mayfly.impl.util.StringUtil;

/**
 *
 * @author Chris Liao
 * @version 1.0
 */

public class ClassNameParamFinder implements BeanParameterXMLFinder{

	/**
	 * 获得参数
	 */
	public BeanParameter find(String spaceName,Element node,BeanParameterXMLFactory beanParameterXMLFactory,BeanParameterXMLTags paramXMLTags,BeanXMLNodeUtil xmlUtil)throws BeanParameterException{
		try {
			String className = xmlUtil.getValueByName(node,paramXMLTags.Class);
			if(!StringUtil.isNull(className))
				return new ClassParameter(ClassUtil.loadClass(className));
			else
				throw new BeanParameterException("Null value at class parameter node:<"+node.getName()+">");
		} catch (ClassNotFoundException e) {
			throw new BeanParameterException(e);
		}
	}
}