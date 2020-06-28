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
package cn.mayfly.impl.intercept;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.mayfly.BeanInterceptor;
import cn.mayfly.impl.util.BeanUtil;

/**
 * AOP调用的拦截链
 *
 * @author chris liao
 */

public class InterceptorChain {
	
	/**
	 * 拦截链表，存放interceptor 到Node的映射
	 */
	private Map methodIntercepMap;

	/**
	 * 构造函数
	 */
	public InterceptorChain(){
		this.methodIntercepMap = new HashMap();
	}
	
	/**
	 * 增加一个拦截器
	 */
	public void addMethodInterceptor(String methodName,Class[] paramTypes,BeanInterceptor interceptor){
		int hashCode = methodName.hashCode();
		for(int i =0,n=paramTypes.length; i<n; i++)
			hashCode ^= paramTypes[i].hashCode();

		Integer hashCodeKey = Integer.valueOf(hashCode);
		List interceporList = (List) methodIntercepMap.get(hashCodeKey);
		if(interceporList == null) {
			interceporList = new ArrayList();
			methodIntercepMap.put(hashCodeKey, interceporList);
		}
		interceporList.add(new InteceptionNode(interceptor));
	}

	/**
	 * 增加一个拦截器
	 */
	public void removeMethodInterceptor(String methodName,Class[] paramTypes,BeanInterceptor interceptor){
		int hashCode = methodName.hashCode();
		for(int i = 0,n=paramTypes.length; i < n; i++)
			hashCode ^= paramTypes[i].hashCode();
		Integer hashCodeKey = Integer.valueOf(hashCode);
		List interceporList = (List) methodIntercepMap.get(hashCodeKey);
		if (interceporList != null) {
			interceporList.remove(interceptor);
		}
	}

  /**
   * intercept method
   */
  public Object intecept(Object bean,Method method,Object[] params,int hashCode)throws Throwable {
  	Integer hashCodeKey = Integer.valueOf(hashCode);
  	List interceporList = (List) methodIntercepMap.get(hashCodeKey);
  	try{
	  	if(interceporList==null || interceporList.isEmpty()){
	  	  return BeanUtil.invokeMethod(bean,method,params);
	  	}else{
	  		int index = 0;
	  		InteceptionNode node =(InteceptionNode)interceporList.get(index);
	  		return node.invoke(bean,method,params,interceporList,index);
	  	}
  	}catch(InvocationTargetException e){
  		throw e.getTargetException();
  	}
  }
}
