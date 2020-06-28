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
package cn.mayfly.test.interception.object;

import java.lang.reflect.Method;

import cn.mayfly.BeanInterceptor;
import cn.mayfly.BeanInterceptor;

/**
 * A interceptor class for IOC test
 *
 * @author Chris Liao
 */

public class ChildInterceptor implements BeanInterceptor {

	 /**
	  * intercept method
	  */
	 public void before(final Object bean,Method method,Object[] params)throws Throwable{
	 	System.out.println("Interceptor1 before");
	 }
	 
	 /**
	  * intercept method
	  */
	 public void after(final Object bean,Method method,Object[] params,final Object result)throws Throwable{
	 	System.out.println("Interceptor1 after");
	 }
	 
	 /**
	  * intercept method
	  */
	 public void afterThrowing(final Object bean,Method method,Object[] params,Throwable throwable)throws Throwable{
	 	System.out.println("Interceptor1 afterThrowing");
	 }
	 
	 /**
	  * intercept method
	  */
	 public void afterFinally(final Object bean,Method method,Object[] params,final Object result,Throwable throwable)throws Throwable{
	 	System.out.println("Interceptor1 afterFinally");
	 }
}