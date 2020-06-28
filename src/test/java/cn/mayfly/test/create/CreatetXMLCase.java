package cn.mayfly.test.create;

import cn.mayfly.BeanContext;
import cn.mayfly.impl.config.BeanContextImpl;
import cn.mayfly.test.TestCase;
import cn.mayfly.test.create.object.Toy;

/**
 * A IOC creation example
 *
 * @author Chris
 */
public class CreatetXMLCase extends TestCase {
	
	/**
	 * 运行测试代码
	 */
	public static void test()throws Throwable{
		BeanContext context = new BeanContextImpl("create.xml");
		
		Toy toy1 =(Toy)context.getBean("Bean1");
		if(toy1!=null){
			System.out.println("[XML].........类反射创建测试成功..........");
		}else{
			throw new Error("[XML]...........类反射创建测试失败............");
	  }
		
		Toy toy2 =(Toy)context.getBean("Bean2");
		if(toy2!=null){
			System.out.println("[XML].........工厂方法创建测试成功..........");
		}else{
			throw new Error("[XML]...........工厂方法创建测试失败............");
		}
		
		Toy toy3 =(Toy)context.getBean("Bean3");
		if(toy3!=null){
			System.out.println("[XML].........工厂Bean测试成功..........");
		}else{
			throw new Error("[XML]...........工厂Bean测试失败............");
		}
	}

	//启动测试方法
	public static void main(String[] args)throws Throwable{
		test();
	}
}