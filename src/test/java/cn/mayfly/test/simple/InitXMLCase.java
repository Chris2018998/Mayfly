package cn.mayfly.test.simple;

import cn.mayfly.BeanContext;
import cn.mayfly.impl.config.BeanContextImpl;
import cn.mayfly.test.TestCase;
import cn.mayfly.test.simple.object.Company;

/**
 * 初始化测试
 * 
 * @author Chris 
 */
public class InitXMLCase extends TestCase {

	/**
	 * 运行测试代码
	 */
	public static void test()throws Throwable{
		BeanContext context=new BeanContextImpl("simple.xml");
		Company company = (Company)context.getBean("Bean1");
		if (company != null) {
			System.out.println("[XML].........初始化测试成功..........");
		} else {
			throw new Error("[XML]...........初始化测试失败............");
		}
		
		if(context.getBean("Bean2")== context.getBean("Bean2")){
			System.out.println("[XML].........单例测试成功..........");
		}else{
			System.out.println("[XML].........单例化测试成功..........");
		}
		
		if(context.getBean("Bean3")!=context.getBean("Bean3")){
			System.out.println("[XML].........多例测试成功..........");
		}else{
			System.out.println("[XML].........多例测试成功..........");
		}
	}

	//启动测试方法
	public static void main(String[] args)throws Throwable{
		test();
	}
}