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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 拦截处理器
 * 
 * @author chris
 */
public class InterceptionHandler implements InvocationHandler {

	/**
	 * proxy object
	 */
	private Object bean;
	
	/**
	 * 拦截链
	 */
	private InterceptorChain chain;

	/**
	 * 构造函数
	 */
	public InterceptionHandler(Object bean) {
		this.bean = bean;
	}
	
	/**
	 * 拦截链
	 */
	public void setInterceptorChain(InterceptorChain chain){
		this.chain =chain;
	}

	/**
	 * 调用
	 */
	public Object invoke(Object proxy, Method method, Object[] args)throws Throwable {
		int hashCode = method.getName().hashCode();
		Class[] paramTypes =method.getParameterTypes();
		for(int i=0,n=paramTypes.length; i<n; i++)
			hashCode ^= paramTypes[i].hashCode();
	
		return chain.intecept(bean,method,args,hashCode);
	}
}
