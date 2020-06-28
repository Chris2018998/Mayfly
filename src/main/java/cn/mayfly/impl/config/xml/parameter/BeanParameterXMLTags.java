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
package cn.mayfly.impl.config.xml.parameter;

/**
 *  ParameterType
 *
 * @author Chris Liao
 * @version 1.0
 */

public class BeanParameterXMLTags {
	
	/**
	 * value type parameter
	 */
	public final String Value = "value";

	/**
	 * class type parameter
	 */
	public final String Class = "class";

	/**
	 * ref type parameter
	 */
	public final String Ref = "ref";
	
	/**
	 * list type parameter
	 */
	public final String List = "list";

	/**
	 * list type parameter
	 */
	public final String ArrayList = "arrayList";

	/**
	 * list type parameter
	 */
	public  final String LinkedList = "linkedList";

	/**
	 * list type parameter
	 */
	public final String Stack = "stack";

	/**
	 * list type parameter
	 */
	public final String Vector = "vector";
	

	/**
	 * collection type parameter
	 */
	public final String Set = "set";

	/**
	 * collection type parameter
	 */
	public final String HashSet = "hashSet";

	/**
	 * collection type parameter
	 */
	public final String TreeSet = "treeSet";

	/**
	 * map type parameter
	 */
	public final String Map = "map";
	
	/**
	 * props type parameter
	 */
	public final String HashMap = "hashMap";
	
	/**
	 * props type parameter
	 */
	public final String Hashtable = "hashtable";
	
	/**
	 * props type parameter
	 */
	public final String TreeMap = "treeMap";
	
	/**
	 * props type parameter
	 */
	public final String Props = "properties";
	
	/**
	 * props type parameter
	 */
	public final String WeakHashMap = "weakHashMap";
	
	/**
	 * array type parameter
	 */
	public final String Array = "array";
	
	
	
	/**
	 * ref  bean type
	 */
	public final String ATTR_ID_Ref_Bean = "bean";
	
	/**
	 * ref local type
	 */
	public final String ATTR_ID_Ref_Local = "local";
	
	/**
	 * ref value type
	 */
	public final String ATTR_ID_Ref_Value = "value";
	
	
	
	/**
	 * array type 
	 */
	public final String Constants_Array_Type = "type";
	
	/**
	 * list item
	 */
	public final String Constants_Item = "item";
	
	/**
	 * map.key
	 */
	public final String Constants_Map_Key = "key";
 
	/**
	 * map.entry
	 */
	public final String Constants_Map_Entry = "entry";
	
	/**
	 *字段名
	 */
	public final String Constants_FIELD_NAME = "name"; 
	
	/**
	 * 元素名
	 */
	public final String TAG_ID_Autowired_Type = "autowiredType"; 
	
	/**
	 *通过名字装配
	 */
	public final String Constants_Autowired_By_Name = "ByName"; 
	
	/**
	 * 通过类型装配
	 */
	public final String Constants_Autowired_By_Type = "ByType"; 
	
}