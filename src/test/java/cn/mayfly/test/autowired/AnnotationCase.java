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
package cn.mayfly.test.autowired;

import cn.mayfly.BeanContext;
import cn.mayfly.impl.config.BeanContextImpl;
import cn.mayfly.test.TestCase;

/**
 * Autowired Demo
 *
 * @author Chris
 */
public class AnnotationCase extends TestCase {
	
	/**
	 * test method
	 */
	public static void test()throws Throwable{
		BeanContext context = new BeanContextImpl("autowired2.xml");
		
		Boy boy = (Boy)context.getBean("Boy");
		if(boy!=null){
			if("Chris".equals(boy.getName())){
				System.out.println("[Annotation].........Autowired(byName) Success.........");
			}else{
				throw new Error("[Annotation]...........Autowired(byName) Failed............");
			}
		}
		
		Girl girl = (Girl)context.getBean("Girl");
		if(girl!=null){
			if((28== girl.getAge())){
				System.out.println("[Annotation]...........Autowired(byType) Success.............");
			}else{
				throw new Error("[Annotation]...........Autowired(byType) Failed...............");
			}
		}
	}
	
	//方法
	public static void main(String[] args)throws Throwable{
		test();
	}
}