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

import java.lang.reflect.Method;
import java.util.List;

import cn.mayfly.BeanInterceptor;
import cn.mayfly.impl.util.BeanUtil;

/**
 * 拦截炼中的拦截节点
 * 
 * @author Chris
 */
public class InteceptionNode {
	
	/**
	 * 当前需要执行的拦截器
	 */
	private BeanInterceptor inteceptor;
	
	/**
	 * 构造方法
	 */
	public InteceptionNode(BeanInterceptor inteceptor){
		this.inteceptor = inteceptor;
	}
	
	/**
	 * 被拦截的方法名
	 */
	public BeanInterceptor getInteceptor() {
		return inteceptor;
	}
	
	/**
	 * intercept method
	 */
  public Object invoke(Object bean,Method method,Object[] params,List interceporList,int index)throws Throwable {
  	Object result = null;
  	Throwable throwable = null;
  	try{
  	 	inteceptor.before(bean,method,params);
  	 	result= intecept(bean,method,params,interceporList,++index);
  		inteceptor.after(bean,method,params,result);
  	}catch(Throwable e){
  		throwable = e;
  		inteceptor.afterThrowing(bean,method,params,e);
  		throw e;
  	}finally{
  		inteceptor.afterFinally(bean,method,params,result,throwable);
  	}
  	return result;
  }
  
  /**
   * intercept method
   */
  private Object intecept(Object bean,Method method,Object[] params,List interceporList,int index)throws Throwable {
  	 if(index > interceporList.size()-1){
  		 return BeanUtil.invokeMethod(bean,method,params);
  	 }else{
  		 InteceptionNode node =(InteceptionNode)interceporList.get(index);
  		 return node.invoke(bean,method,params,interceporList,index);
  	 }
  }
}
