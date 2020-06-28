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
package cn.mayfly.element;

import cn.mayfly.BeanElement;
import cn.mayfly.BeanElementException;

/**
 * parent ID
 * 
 * 
 *@author Chris Liao
 */

public final class ParentReferenceId implements BeanElement {

	/**
	 * destroy Method
	 */
	private Object referenceId;

	/**
	 * destroyMethod
	 */
	public ParentReferenceId(Object referenceId)throws BeanElementException {
		this.referenceId = referenceId;
		if(referenceId==null)
			throw new BeanElementException("Parent reference id can't be null");
	}

	/**
	 * Returns parent ID
	 */
	public Object getReferenceId() {
		return referenceId;
	}
	
  /**
   * Return hash code for this interception
   */
  public int hashCode() {
  	return referenceId.hashCode();
  }
 
  /**
   * Override method
   */
  public boolean equals(Object obj) {
    if(obj instanceof ParentReferenceId) {
    	ParentReferenceId other = (ParentReferenceId) obj;
      return this.referenceId.equals(other.referenceId);
    } else {
      return false;
    }
  }
}