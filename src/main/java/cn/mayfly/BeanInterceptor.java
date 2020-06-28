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
package cn.mayfly;

import java.lang.reflect.Method;

/**
 * Bean Interceptor
 *
 * @author Chris Liao
 * @version 1.0
 */

public interface BeanInterceptor {

  /**
   * intercept method
   */
  public void before(final Object bean, Method method, Object[] params)throws Throwable;
  
  /**
   * intercept method
   */
  public void after(final Object bean, Method method, Object[] params, final Object result)throws Throwable;
  
  /**
   * intercept method
   */
  public void afterThrowing(final Object bean, Method method, Object[] params, Throwable throwable)throws Throwable;
  
  /**
   * intercept method
   */
  public void afterFinally(final Object bean, Method method, Object[] params, final Object result, Throwable throwable)throws Throwable;
}