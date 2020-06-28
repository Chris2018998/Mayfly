package cn.mayfly.test.classedit;

import java.lang.reflect.Method;

import cn.mayfly.BeanInterceptor;
import cn.mayfly.BeanInterceptor;

public class Interceptor2 implements BeanInterceptor {
	
	 /**
  * intercept method
  */
 public void before(final Object bean,Method method,Object[] params)throws Throwable{
 	System.out.println("Interceptor2 before");
 }
 
 /**
  * intercept method
  */
 public void after(final Object bean,Method method,Object[] params,final Object result)throws Throwable{
 	System.out.println("Interceptor2 after");
 }
 
 /**
  * intercept method
  */
 public void afterThrowing(final Object bean,Method method,Object[] params,Throwable throwable)throws Throwable{
 	System.out.println("Interceptor2 afterThrowing");
 }
 
 /**
  * intercept method
  */
 public void afterFinally(final Object bean,Method method,Object[] params,final Object result,Throwable throwable)throws Throwable{
 	System.out.println("Interceptor2 afterFinally");
 }
}
