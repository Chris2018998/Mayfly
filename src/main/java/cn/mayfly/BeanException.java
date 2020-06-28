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
package cn.mayfly;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Ioc异常
 *
 * @author Chris Liao
 * @version 1.0
 */

public class BeanException extends Exception {

	/**
	 * 异常触发原因
	 */
	private Throwable cause;
	
	/**
	 * 构造函数
	 */
	public BeanException(String message){
		this(message,null);
	}
	
	/**
	 * 构造函数
	 */
	public BeanException(Throwable cause) {
		this(null,cause);
	}
	
	/**
	 * 构造函数
	 */
	public BeanException(String message,Throwable cause) {
		super(message);
		this.cause = cause;
	}
	

	/**
	 * 获得触发原因
	 */
	public Throwable getCauseException() {
		return cause;
	}

  /**
	 * 打印堆栈，重写方法
	 */
	public void printStackTrace() {
		this.printStackTrace(System.err);
	}
	
	/**
	 * 打印堆栈，重写方法
	 */
	public void printStackTrace(PrintStream s) {
		super.printStackTrace(s);
		if(cause!=null){
			s.print("Caused by: ");
			cause.printStackTrace(s);
		}
	}
	
	/**
	 * 打印堆栈，重写方法
	 */
	public void printStackTrace(PrintWriter w) {
		super.printStackTrace(w);
		if(cause!=null){
			w.print("Caused by: ");
			cause.printStackTrace(w);
		}
	}
}