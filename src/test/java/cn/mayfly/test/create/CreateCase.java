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
package cn.mayfly.test.create;

import cn.mayfly.BeanContainer;
import cn.mayfly.BeanElement;
import cn.mayfly.BeanElementFactory;
import cn.mayfly.BeanParameter;
import cn.mayfly.BeanParameterFactory;
import cn.mayfly.element.InstanceCreation;
import cn.mayfly.impl.MayflyBeanContainer;
import cn.mayfly.test.TestCase;
import cn.mayfly.test.create.object.Toy;
import cn.mayfly.test.create.object.ToyFactory;

/**
 * A IOC creation example
 *
 * @author Chris
 */
public class CreateCase extends TestCase {
	
	/**
	 * 运行测试代码
	 */
	public static void test()throws Throwable{
		BeanContainer container = new MayflyBeanContainer();
		BeanElementFactory beanElementFactory =container.getBeanElementFactory();
		BeanParameterFactory beanParameterFactory =container.getBeanParameterFactory();
		
		BeanParameter nameParmeter = beanParameterFactory.createStringParameter("Cat");
		
		InstanceCreation classCreation = beanElementFactory.createInstanceCreation(new BeanParameter[]{nameParmeter});
		container.registerClass("Bean1",Toy.class,new BeanElement[]{classCreation});
		
		InstanceCreation methodCreation = beanElementFactory.createInstanceCreation("create",new BeanParameter[]{nameParmeter});
		container.registerClass("Bean2",Toy.class,new BeanElement[]{methodCreation});
		
		InstanceCreation beanCreation = beanElementFactory.createInstanceCreation("factory","create",new BeanParameter[]{nameParmeter});
		container.registerClass("Bean3",Toy.class,new BeanElement[]{beanCreation});
		container.registerClass("factory",ToyFactory.class);
		
		Toy toy =(Toy)container.getBean("Bean1");
		if(toy!=null){
				System.out.println("[Container].........类反射创建测试成功..........");
		}else{
				throw new Error("[Container]...........类反射创建测试失败............");
		}
	
		Toy toy2 =(Toy)container.getBean("Bean2");
		if(toy2!=null){
			System.out.println("[Container].........工厂方法创建测试成功..........");
		}else{
			throw new Error("[Container]...........工厂方法创建测试失败............");
		}
		
		Toy toy3 =(Toy)container.getBean("Bean3");
		if(toy3!=null){
			System.out.println("[Container].........工厂Bean测试成功..........");
		}else{
			throw new Error("[Container]...........工厂Bean测试失败............");
		}
	}
	
	//启动测试方法
	public static void main(String[] args)throws Throwable{
		test();
	}
}
