/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hrattence.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * hrattenceEntity
 * @author fang
 * @version 2018-08-06
 */
public class SzlHrStaff extends DataEntity<SzlHrStaff> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// name
	private String number;		// number
	private String department;		// department
	
	public SzlHrStaff() {
		super();
	}

	public SzlHrStaff(String id){
		super(id);
	}

	@Length(min=0, max=10, message="name长度必须介于 0 和 10 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=11, message="number长度必须介于 0 和 11 之间")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@Length(min=0, max=10, message="department长度必须介于 0 和 10 之间")
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
}