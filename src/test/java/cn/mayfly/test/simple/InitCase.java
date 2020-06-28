package cn.mayfly.test.simple;

import cn.mayfly.BeanContainer;
import cn.mayfly.impl.MayflyBeanContainer;
import cn.mayfly.test.TestCase;
import cn.mayfly.test.simple.object.Company;

/**
 * 初始化测试
 * 
 * @author Chris 
 */
public class InitCase extends TestCase {

	/**
	 * 运行测试代码
	 */
	public static void test()throws Throwable{
		BeanContainer container = new MayflyBeanContainer();
	  container.registerClass("Bean1",Company.class);
		container.setInitMethodName("Bean1","init");
		container.setDestroyMethodName("Bean1","destroy");
		
		container.registerClass("Bean2",Company.class);
		container.registerClass("Bean3",Company.class);
		container.setInstanceSingleton("Bean2",true);
		container.setInstanceSingleton("Bean3",false);
		
		Company company = (Company)container.getBean("Bean1");
		if (company != null) {
			System.out.println("[Container].........初始化测试成功..........");
		} else {
			throw new Error("[Container]...........初始化测试失败............");
		}
		
		if(container.getBean("Bean2")== container.getBean("Bean2")){
			System.out.println("[Container].........单例测试成功..........");
		}else{
			System.out.println("[Container].........单例化测试成功..........");
		}
		
		if(container.getBean("Bean3")!=container.getBean("Bean3")){
			System.out.println("[Container].........多例测试成功..........");
		}else{
			System.out.println("[Container].........多例测试成功..........");
		}
	}

	//启动测试方法
	public static void main(String[] args)throws Throwable{
		test();
	}
}