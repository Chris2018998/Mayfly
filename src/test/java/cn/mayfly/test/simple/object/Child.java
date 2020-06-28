package cn.mayfly.test.simple.object;

public class Child extends Parent{
	
	private String addr;

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	public String toString(){
		return "Name: " + this.getName() +
		" Sex: " + this.getSex() + " Company: " + this.getCompany() + " Addr: " + this.getAddr() + "";
	}
	
}
