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
package cn.mayfly.test.collection;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.mayfly.BeanContext;
import cn.mayfly.impl.config.BeanContextImpl;
import cn.mayfly.test.TestCase;

/**
 * A IOC Autowire example
 *
 * @author Chris
 */
public class ContainerXMLCase extends TestCase {
	
	/**
	 * 运行测试代码
	 */
	public static void test()throws Throwable{
		BeanContext context=new BeanContextImpl("collection.xml");
		School school = (School)context.getBean("school");
		List roomList = school.getRoomList();
		Set classSet = school.getClassSet();
		Map teacherMap= school.getTeacherMap();
		
		if(roomList!=null&&roomList.size()==3){
			System.out.println("[XML].........List测试成功..........");
		}else{
			throw new Error("[XML].........List测试失败..........");
		}
		
		if(classSet!=null&&classSet.size()==3){
			System.out.println("[XML].........Set测试成功..........");
		}else{
			throw new Error("[XML].........Set测试失败..........");
		}
		
		if(teacherMap!=null&&teacherMap.size()==3){
			System.out.println("[XML].........Map测试成功..........");
		}else{
			throw new Error("[XML].........Map测试失败..........");
		}
	}
	
	//启动测试方法
	public static void main(String[] args)throws Throwable{
		test();
	}
}