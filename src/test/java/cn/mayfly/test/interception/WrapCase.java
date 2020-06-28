package cn.mayfly.test.interception;

import cn.mayfly.BeanContainer;
import cn.mayfly.BeanElementFactory;
import cn.mayfly.element.InvocationInterception;
import cn.mayfly.impl.MayflyBeanContainer;
import cn.mayfly.test.TestCase;
import cn.mayfly.test.interception.object.Child;
import cn.mayfly.test.interception.object.ChildInterceptor;
import cn.mayfly.test.interception.object.ChildInterceptor2;

/**
 * 方法拦截测试
 * 
 * @author chris
 *
 */
public class WrapCase extends TestCase {
	
	/**
	 * 运行测试代码
	 */
	public static void test()throws Throwable{
		BeanContainer container = new MayflyBeanContainer();
		
		BeanElementFactory beanElementFactory =container.getBeanElementFactory();
		InvocationInterception interception = beanElementFactory.createInvocationInterception("sayHello", new Class[] {java.lang.String.class });
		interception.addInterceptorClass(ChildInterceptor.class);
		interception.addInterceptorClass(ChildInterceptor2.class);
		container.registerClass("Bean2", Child.class);
		container.addInvocationInterception("Bean2",interception);
		
		Child child = (Child)container.getBean("Bean2");
		child.sayHello("Chris");
		System.out.println("[Container].........AOP类修改拦截测试成功..........");
	}
	
	//启动测试方法
	public static void main(String[] args)throws Throwable{
		test();
	}
}