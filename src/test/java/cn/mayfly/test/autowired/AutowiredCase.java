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
package  cn.mayfly.test.autowired;

import cn.mayfly.BeanContainer;
import cn.mayfly.BeanElement;
import cn.mayfly.BeanElementFactory;
import cn.mayfly.element.AutowiredType;
import cn.mayfly.element.InjectionField;
import cn.mayfly.impl.MayflyBeanContainer;
import cn.mayfly.test.TestCase;

/**
 * Autowired Demo
 *
 * @author Chris
 */
public class AutowiredCase extends TestCase {
	
	/**
	 *test method
	 */
	public static void test()throws Throwable{
		BeanContainer container = new MayflyBeanContainer();
		BeanElementFactory beanElementFactory =container.getBeanElementFactory();
		
		container.registerInstance("name", "Chris");
		container.registerInstance("age",Integer.valueOf(28));
		
        InjectionField nameField=beanElementFactory.createInjectionField("name", AutowiredType.BY_NAME);
		container.registerClass("Boy", Boy.class,new BeanElement[]{nameField});
   
		InjectionField ageField=beanElementFactory.createInjectionField("age",AutowiredType.BY_TYPE);
		container.registerClass("Girl", Girl.class,new BeanElement[]{ageField});
		
		Boy boy = (Boy)container.getBean("Boy");
		if(boy!=null){
			if("Chris".equals(boy.getName())){
				System.out.println("[Container].........Autowired(byName) Success.........");
			}else{
				throw new Error("[Container]...........Autowired(byName) Failed............");
			}
		}
		
		Girl girl = (Girl)container.getBean("Girl");
		if(girl!=null){
			if((28== girl.getAge())){
				System.out.println("[Container]...........Autowired(byType) Success.............");
			}else{
				throw new Error("[Container]...........Autowired(byType) Failed...............");
			}
		}
	}
 
	//方法
	public static void main(String[] args)throws Throwable{
		test();
	}
}
