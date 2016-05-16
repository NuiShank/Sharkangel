package com.example.user.sharkangel.tools;

public class DataItem {
	
	private String id,icon,ctno;

	public DataItem(){}

	public DataItem(String id, String icon,String ctno){
		this.id = id;
		this.icon = icon;
		this.ctno=ctno;
	}
	

	
	public String getId(){
		return this.id;
	}
	
	public String getIcon(){
		return this.icon;
	}
	
	public String getCtno(){
		return this.ctno;
	}


}
