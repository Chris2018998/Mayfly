package cn.mayfly.test.interception;

import cn.mayfly.BeanContainer;
import cn.mayfly.BeanElementFactory;
import cn.mayfly.element.InvocationInterception;
import cn.mayfly.impl.MayflyBeanContainer;
import cn.mayfly.test.TestCase;
import cn.mayfly.test.interception.object.Child;
import cn.mayfly.test.interception.object.ChildInterceptor;
import cn.mayfly.test.interception.object.ChildInterceptor2;
import cn.mayfly.test.interception.object.Young;

/**
 *  代理Aop测试
 *
 * @author Chris
 */

public class ProxyCase extends TestCase {

	/**
	 * 运行测试代码
	 */
	public static void test()throws Throwable{
		BeanContainer container = new MayflyBeanContainer();
		
		BeanElementFactory beanElementFactory =container.getBeanElementFactory();
		InvocationInterception interception = beanElementFactory.createInvocationInterception("sayHello",new Class[]{String.class});
		interception.addInterceptorClass(ChildInterceptor.class);
		interception.addInterceptorClass(ChildInterceptor2.class);
		container.registerClass("Bean1",Child.class);
		container.addInvocationInterception("Bean1", interception);
		container.setProxyInterfaces("Bean1",new Class[]{Young.class});
		
		Young child = (Young)container.getBean("Bean1");
		child.sayHello("Chris");
		System.out.println("[Container].........Proxy拦截测试成功..........");
	}
	
	//启动测试方法
	public static void main(String[] args)throws Throwable{
		test();
	}
}