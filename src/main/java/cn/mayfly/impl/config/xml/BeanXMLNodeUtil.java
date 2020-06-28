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

import java.util.List;

import org.jdom.Element;

/**
 * XML节点辅助类
 * 
 * @author Chris Liao
 */
public class BeanXMLNodeUtil {

	/**
	 * 是否为顶节点
	 */
	public boolean isRootNode(Element node) {
		return node.isRootElement();
	}

	/**
	 * 是否为叶子节点
	 */
	public boolean isLeafNode(Element node) {
		return node.getChildren().isEmpty() ? true : false;
	}

	/**
	 * 是否存在子节点
	 */
	public boolean exitChild(Element node, String childName) {
		return !node.getChildren(childName).isEmpty();
	}

	/**
	 * 是否存在某个属性
	 */
	public boolean exitAttribute(Element node, String attributeName) {
		return node.getAttribute(attributeName) != null;
	}

	/**
	 * 获得节点文本内容
	 */
	public String getNodeText(Element node) {
		return node.getTextTrim();
	}

	/**
	 * 获得子节点文本内容
	 */
	public String getChildext(Element node, String childName) {
		return node.getChildTextTrim(childName);
	}

	/**
	 * 获得节点属性值
	 */
	public String getAttributeValue(Element node, String attributeName) {
		return node.getAttributeValue(attributeName);
	}

	/**
	 * 获得节点属性值
	 */
	public String getValueByName(Element node, String name) {
		String value = this.getAttributeValue(node, name);
		if (value == null) {
			List childList = node.getChildren(name);
			if (childList != null && !childList.isEmpty()) {
				Element child = (Element) childList.get(0);
				if (isLeafNode(child))
					value = getNodeText(child);
			} else if (isLeafNode(node)) {
				value = getNodeText(node);
			}
		}

		return value;
	}
}
