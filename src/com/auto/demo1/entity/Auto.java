package com.auto.demo1.entity;

/**
 * <ul>
 * <li>table name:  auto</li>
 * <li>table comment:  </li>
 * <li>author name: Generate</li>
 * <li>create time: 2021-03-18 17:53:39</li>
 * </ul>
 */ 
public class Auto{

	/*测试表主键标识*/
	private Integer id;
	/*名称*/
	private String name;
	private String self_data;

	public Auto() {
		super();
	}
	public Auto(Integer id,String name,String self_data) {
		this.id=id;
		this.name=name;
		this.self_data=self_data;
	}
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return id;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setSelf_data(String self_data){
		this.self_data=self_data;
	}
	public String getSelf_data(){
		return self_data;
	}
	@Override
	public String toString() {
		return "auto[" + 
			"id=" + id + 
			", name=" + name + 
			", self_data=" + self_data + 
			"]";
	}
}

