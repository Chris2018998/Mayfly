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
package cn.mayfly.test.field;

/**
 * 字段参数注入
 * 
 * @author chris
 */
public class Boy {
	
	/**
	 * 名字
	 */
	private String name;

	/**
	 * 年龄
	 */
	private int age;
	
	/**
	 * 名字
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 名字
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 年龄
	 */
	public int getAge() {
		return age;
	}
	
	/**
	 * 年龄
	 */
	public void setAge(int age) {
		this.age = age;
	}
}
