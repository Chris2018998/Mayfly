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
package cn.mayfly.impl.config.xml.element;

import java.util.List;

import org.jdom.Element;
import cn.mayfly.BeanElement;
import cn.mayfly.BeanElementException;
import cn.mayfly.BeanParameter;
import cn.mayfly.BeanParameterException;
import cn.mayfly.element.InvocationInterception;
import cn.mayfly.element.parameter.ClassParameter;
import cn.mayfly.element.parameter.ReferenceParameter;
import cn.mayfly.impl.config.xml.BeanXMLNodeUtil;
import cn.mayfly.impl.config.xml.parameter.BeanParameterXMLFactory;
import cn.mayfly.impl.util.ClassUtil;
import cn.mayfly.impl.util.StringUtil;

/**
 * XML Template is below
 *
 *
 * <interception>
 *   <method-name>hello</method-name>
 *   <method-param-types>
 *    <method-param-type>int</method-param-type>
 *   </method-param-types>
 *   <interceptor class=""/>  // <interceptor ref=""/>
 * </interception>
 */

public class InvocationInterceptionFinder implements BeanElementXMLFinder{
	
	/**
	 * 查找Bean定义元素
	 */
	public BeanElement find(Element beanNode,String beanid,String spaceName,String file,BeanParameterXMLFactory beanParameterXMLFactory,BeanEelementXMLTags xmlTags,BeanXMLNodeUtil xmlUtil) throws BeanElementException{
		String methodName = xmlUtil.getChildext(beanNode,xmlTags.Method_Name);
		try {
			if(StringUtil.isNull(methodName))
				throw new BeanElementException("Null intercepted method name of bean:" + beanid + " at file:" +file);

			Class[] parameters = getMethodParameterTypes(beanNode,methodName,beanid,file,xmlTags,xmlUtil);
			InvocationInterception interception = new InvocationInterception(methodName,parameters);

			BeanParameter[] interceptorValues = getInterceptorValueMetas(beanNode,methodName,beanid,file,xmlTags,xmlUtil);
			for(int i=0,n=interceptorValues.length;i<n;i++){
				if(interceptorValues[i] instanceof ClassParameter){
			    Class className =(Class)((ClassParameter)interceptorValues[i]).getParameterContent();
			    interception.addInterceptorClass(className);
			  }else if (interceptorValues[i]  instanceof ReferenceParameter){
			    Object refID = ((ReferenceParameter)interceptorValues[i]).getReferenceId();
			    interception.addInterceptorReference(refID);
			  }
			}
			return interception;
		} catch (BeanParameterException e) {
			throw new BeanElementException("Failed to find parameters for intercepted method:"+methodName + " of bean:" +beanid + " at file:" +file,e);
		}
	}
	
 
	/**
	 * 获取拦截器列表
	 */
  private BeanParameter[] getInterceptorValueMetas(Element node,String methodname,String beanid,String file,BeanEelementXMLTags xmlTags,BeanXMLNodeUtil xmlUtil)throws BeanElementException{
    List interceptorMetaList = node.getChildren(xmlTags.Interceptor);
    BeanParameter[] metas = new BeanParameter[interceptorMetaList.size()];
    for(int i=0,n=interceptorMetaList.size();i<n;i++){
      Element interceptorNode = (Element)interceptorMetaList.get(i);
      metas[i] = getInterceptorValueMeta(interceptorNode,methodname,beanid,file,xmlUtil);
    }
    return metas;
  }
	
  /**
   * Find all parameter types from interception node
   */
  private Class[] getMethodParameterTypes(Element node,String methodname,String beanid,String file,BeanEelementXMLTags xmlTags,BeanXMLNodeUtil xmlUtil)throws BeanElementException{
  	try {
			Element paramsNode = node.getChild(xmlTags.Method_Param_Types);
			if(paramsNode != null){
			  List paramList = paramsNode.getChildren(xmlTags.Method_Param_Type);
			  Class[] types = new Class[paramList.size()];
			  for(int i=0,n=paramList.size();i<n;i++){
			     Element paramNode = (Element)paramList.get(i);
			     types[i] = ClassUtil.loadClass(xmlUtil.getNodeText(paramNode));
			  }
			  return types;
			}else{
				return new Class[0];
			}
		} catch (ClassNotFoundException e) {
			 throw new BeanElementException("A method parameter type was not found for intercepted method: "+methodname + " of bean:" +beanid + " at file:" +file,e);
		}
  }
	
  /**
   * return Interceptor valueMeta
   */
  private BeanParameter getInterceptorValueMeta(Element node,String methodname,String beanid,String file,BeanXMLNodeUtil xmlUtil)throws BeanElementException{
    try {
			String clsname = xmlUtil.getValueByName(node,"class");
			String clsRef = xmlUtil.getValueByName(node,"ref");
			if(!StringUtil.isNull(clsname)) {
				return new ClassParameter(ClassUtil.loadClass(clsname));
			} else if (!StringUtil.isNull(clsRef)) {
				return new ReferenceParameter(clsRef);
			}else {
			  throw new BeanElementException("Missed interceptor class/refereceid for intercepted method: "+methodname + " of bean:" +beanid + " at file:" +file);
			}
		} catch (ClassNotFoundException e) {
			 throw new BeanElementException("Not found interceptor class for intercepted method: "+methodname + " of bean:" +beanid + " at file:" +file,e);
		}catch(BeanParameterException e){
			 throw new BeanElementException("Failded to find interceptor for intercepted method: "+methodname + " of bean:" +beanid + " at file:" +file,e);
		}
  }
}