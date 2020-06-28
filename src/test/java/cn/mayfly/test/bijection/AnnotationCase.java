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

import cn.mayfly.BeanContext;
import cn.mayfly.impl.config.BeanContextImpl;
import cn.mayfly.test.TestCase;

/**
 * A IOC Bijection demo
 *
 * @author Chris
 */
public class AnnotationCase extends TestCase {
	
	/**
	 * test method
	 */
	public static void test()throws Throwable{
		BeanContext context=new BeanContextImpl("bijection2.xml");
		Wife wife =(Wife)context.getBean("Wife");
		Husband husband =(Husband)context.getBean("Husband");
		if(wife!=null && wife.getHusband()==husband){
			System.out.println("[Annotation].........Bijection success..........");
		}else{
			System.out.println("[Annotation].........Bijection failed..........");
		}
	}
	
	//启动测试方法
	public static void main(String[] args)throws Throwable{
		test();
	}
}