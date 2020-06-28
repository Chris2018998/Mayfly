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

import java.util.List;
import java.util.Map;

/**
 * Bean Context
 *
 * @author Chris Liao
 * @version 1.0
 */

public abstract class BeanContext {

	/**
	 * refresh,reload xml files
	 */
	public abstract void refresh() throws BeanException;

	/**
	 * contains beanID
	 */
	public abstract boolean containsId(Object id) throws BeanException;

	/**
	 * Find a bean instance from container by a id. If not found, then return
	 * null.
	 */
	public abstract Object getBean(Object id) throws BeanException;

	/**
	 * Find a bean instance with a class. If not found, then return null.
	 */
	public abstract Object getBeanOfType(Class cls) throws BeanException;

	/**
	 * Find all bean instance map
	 */
	public abstract Map getBeansMapOfType(Class cls) throws BeanException;
	
	/**
	 * Find all bean instance map
	 */
	public abstract List getBeansListOfType(Class cls) throws BeanException;

}
