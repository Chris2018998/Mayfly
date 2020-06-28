package cn.mayfly.test.collection;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class School {
	
	/**
	 * 房间列表
	 */
	private List roomList;
	
	/**
	 * 课
	 */
	private Set classSet;
	
	/**
	 * 老师编号映射
	 */
	private Map teacherMap;
	
	/**
	 * 课
	 */
	public Set getClassSet() {
		return classSet;
	}
	
	/**
	 * 课
	 */
	public void setClassSet(Set classSet) {
		this.classSet = classSet;
	}
	
	/**
	 * 房间列表
	 */
	public List getRoomList() {
		return roomList;
	}
	
	/**
	 * 房间列表
	 */
	public void setRoomList(List roomList) {
		this.roomList = roomList;
	}
	
	/**
	 * 老师编号映射
	 */
	public Map getTeacherMap() {
		return teacherMap;
	}
	
	/**
	 * 老师编号映射
	 */
	public void setTeacherMap(Map teacherMap) {
		this.teacherMap = teacherMap;
	}
}
