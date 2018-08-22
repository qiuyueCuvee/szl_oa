/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.holiday.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * holidayEntity
 * @author yue.qiu
 * @version 2018-08-15
 */
public class SzlHrHoliday extends DataEntity<SzlHrHoliday> {
	
	private static final long serialVersionUID = 1L;
	private String number;		// 工号
	private String shiftLeave;		// 倒休剩余（小时）
	private String annualLeave;		// 年假剩余（小时）
	private  String  hrStaffName ;
	private  String  hrStaffDept ;	
	
	public SzlHrHoliday() {
		super();
	}

	public SzlHrHoliday(String id){
		super(id);
	}

	@Length(min=0, max=255, message="工号长度必须介于 0 和 255 之间")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@Length(min=0, max=255, message="倒休剩余（小时）长度必须介于 0 和 255 之间")
	public String getShiftLeave() {
		return shiftLeave;
	}

	public void setShiftLeave(String shiftLeave) {
		this.shiftLeave = shiftLeave;
	}
	
	@Length(min=0, max=255, message="年假剩余（小时）长度必须介于 0 和 255 之间")
	public String getAnnualLeave() {
		return annualLeave;
	}

	public void setAnnualLeave(String annualLeave) {
		this.annualLeave = annualLeave;
	}
	public String getHrStaffName() {
		return hrStaffName;
	}

	public void setHrStaffName(String hrStaffName) {
		this.hrStaffName = hrStaffName;
	}

	public String getHrStaffDept() {
		return hrStaffDept;
	}

	public void setHrStaffDept(String hrStaffDept) {
		this.hrStaffDept = hrStaffDept;
	}
	
}