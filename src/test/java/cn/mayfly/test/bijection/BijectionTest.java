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
package cn.mayfly.test.bijection;

import cn.mayfly.BeanContainer;
import cn.mayfly.BeanElementFactory;
import cn.mayfly.BeanParameter;
import cn.mayfly.BeanParameterFactory;
import cn.mayfly.element.InjectionProperty;
import cn.mayfly.impl.MayflyBeanContainer;
import cn.mayfly.test.TestCase;

/**
 * Bijection Demo
 * 
 * @author Chris Liao
 */
public class BijectionTest extends TestCase {
	
	/**
	 * test method
	 */
	public static void test()throws Throwable{
		BeanContainer container = new MayflyBeanContainer();
		BeanElementFactory beanElementFactory =container.getBeanElementFactory();
		BeanParameterFactory beanParameterFactory =container.getBeanParameterFactory();
		
		BeanParameter WifNameParmeter =  beanParameterFactory.createStringParameter("Summer");
		InjectionProperty WifenameProperty = beanElementFactory.createInjectionProperty("name",WifNameParmeter);
		BeanParameter HusbandParmeter =  beanParameterFactory.createReferenceParameter("Husband");
		InjectionProperty HusbandProperty =  beanElementFactory.createInjectionProperty("husband",HusbandParmeter);
		container.registerClass("Wife",Wife.class,new InjectionProperty[] {WifenameProperty,HusbandProperty});
		
		BeanParameter HusNameParmeter =  beanParameterFactory.createStringParameter("Chris");
		InjectionProperty HusNameProperty = beanElementFactory.createInjectionProperty("name",HusNameParmeter);
		BeanParameter WifeParmeter =  beanParameterFactory.createReferenceParameter("Wife");
		InjectionProperty WifeProperty =  beanElementFactory.createInjectionProperty("wife",WifeParmeter);
		container.registerClass("Husband",Husband.class,new InjectionProperty[] {HusNameProperty,WifeProperty});
		
		Wife wife =(Wife)container.getBean("Wife");
		Husband husband =	(Husband)container.getBean("Husband");
		if(wife!=null && wife.getHusband()==husband){
			System.out.println("[Container].........Bijection success..........");
		}else{
			System.out.println("[Container].........Bijection failed ..........");
		}
	}

  //方法
	public static void main(String[] args)throws Throwable{
		test();
	}
}