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
package cn.mayfly.impl.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符川辅助类
 * 
 * @author Chris
 */
public class StringUtil {

	/**
	 * 判断字符是否为空
	 */
	public static boolean isNull(String value) {
		return value == null || value.trim().length()==0;
	}
	
	/**
	 * 将字符串分割成多个子串
	 */
  public static final String[] EMPTY_STRING_ARRAY = new String[0];
	public static String[] split(String source,String sepString) {
	  if(isNull(source)) 
			return EMPTY_STRING_ARRAY;
		if(sepString == null || sepString.length() ==0) 
			sepString =Symbols.Space;
		
		int startPos =0,indexPos=0;
		int sourceLen = source.length();
		int sepStringLen = sepString.length();
		List list = new ArrayList(sourceLen/sepStringLen);
	
		while(startPos < sourceLen){
			indexPos = source.indexOf(sepString,startPos);
			if(indexPos==0){//从头开始的字符，只递增开始位置
				startPos =sepStringLen;
			}else if(indexPos >0){//中途截取
	    	list.add(source.substring(startPos,indexPos));
	    	startPos = indexPos+sepStringLen;
	    }else{//最后一次截取
	    	list.add(source.substring(startPos));
	    	startPos = sourceLen;
	    }
		}
		
		return (String[])list.toArray(new String[list.size()]);
	}
	
	/**
	 * 字符串替换
	 */
  public static String replace(String text, String oldStr,String newStr) {
    if(isNull(text) || isNull(oldStr) || newStr==null)
      return text;  
    
		int startPos = 0, indexPos = 0;
		int sourceLen = text.length();
		int oldStringLen = oldStr.length();
		StringBuilder buf = new StringBuilder(text.length());
		while (startPos < sourceLen) {
			indexPos = text.indexOf(oldStr, startPos);
			if (indexPos == 0) {// 从头开始的字符，只递增开始位置
				buf.append(newStr);
				startPos = oldStringLen;
			} else if (indexPos > 0) {
				buf.append(text.substring(startPos, indexPos));
				buf.append(newStr);
				startPos = indexPos + oldStringLen;
			} else {//最后一次截取
				buf.append(text.substring(startPos));
				startPos = sourceLen;
			}
		}
		return buf.toString(); 
  }
	private final static String SEP_CHAR1="#{";
	private final static String SEP_CHAR2="${";
	private final static String SEP_CHAR3="}";
	
	/**
	 * 获得过滤环境变量
	 */
	public static String getFilterValue(String value){
		int[] pos = getVariablePos(value);
		while(pos[1] >0 && pos[0] >=0 && pos[1] > pos[0]){
			String variableName = value.substring(pos[0]+2,pos[1]);// 参数变量名
			String variableBlock = value.substring(pos[0],pos[1]+1);// 参数变量块
			
			if(!StringUtil.isNull(variableName)){
				String variableValue = System.getProperty(variableName.trim());
				if(StringUtil.isNull(variableValue))
					throw new IllegalArgumentException("Not found system variable value with name:"+variableName);
					value = StringUtil.replace(value,variableBlock,variableValue);
			}
			pos = getVariablePos(value);
		}
		
		return value;
	}

	/**
	 * 获取环境变量位置
	 */
	private static int[]getVariablePos(String text){
		int index =text.indexOf(SEP_CHAR1);
		int index2 =text.indexOf(SEP_CHAR2);
		int endPos =text.indexOf(SEP_CHAR3);
		int startPos= -1;
		
		if(index>=0 && index2>=0){
			startPos=(index<=index2)?index:index2;
		}else if(index==-1 && index2>=0){
			startPos = index2;
		}else if(index>=0 && index2==-1){
			startPos = index;
		}else if(index==-1 && index2==-1){
			startPos = -1;
		}
		
		return new int[]{startPos,endPos};
	}
}

