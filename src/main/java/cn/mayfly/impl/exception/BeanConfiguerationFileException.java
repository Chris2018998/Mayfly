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
package cn.mayfly.impl.exception;


import cn.mayfly.BeanException;

/**
 * A exception for bean Definition
 *
 * @author Chris Liao
 * @version 1.0
 */

public class BeanConfiguerationFileException extends BeanException {

	/**
	 * 构造函数
	 */
	public BeanConfiguerationFileException(String message) {
		super(message);
	}
	
	/**
	 * 构造函数
	 */
	public BeanConfiguerationFileException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * 构造函数
	 */
	public BeanConfiguerationFileException(String message,Throwable cause) {
		super(message,cause);
	}

}