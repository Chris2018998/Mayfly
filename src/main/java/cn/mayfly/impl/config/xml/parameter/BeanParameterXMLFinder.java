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
import cn.mayfly.impl.config.xml.BeanXMLNodeUtil;
 
/**
 * 参数查找
 * 
 * @author Chris Liao 
 */
public interface BeanParameterXMLFinder {

	/**
	 * 获取bean参数
	 */
	public BeanParameter find(String spaceName, Element node, BeanParameterXMLFactory beanParameterXMLFactory,
                              BeanParameterXMLTags paramXMLTags, BeanXMLNodeUtil xmlUtil)throws BeanParameterException;

}
