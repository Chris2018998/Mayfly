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

import cn.mayfly.element.annotation.Component;
import cn.mayfly.element.annotation.Parameter;

/**
 * 双向注入类
 * 
 * @author Chris liao
 */
@Component
public class Husband {
	
	/**
	 *名字
	 */
	@Parameter("Chris")
	private String name;
	
	/**
	 * 妻子
	 */
	@Parameter("ref:Wife")
	private Wife wife;

	/**
	 *名字
	 */
	public String getName() {
		return name;
	}
	
	/**
	 *名字
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 妻子
	 */
	public Wife getWife() {
		return wife;
	}
	
	/**
	 * 妻子
	 */
	public void setWife(Wife wife) {
		this.wife = wife;
	}
}
