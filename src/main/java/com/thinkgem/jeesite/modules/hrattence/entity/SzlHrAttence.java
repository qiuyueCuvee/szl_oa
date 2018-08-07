/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hrattence.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * hrattenceEntity
 * @author fang
 * @version 2018-08-06
 */
public class SzlHrAttence extends DataEntity<SzlHrAttence> {
	
	private static final long serialVersionUID = 1L;
	private String number;		// number
	private String starttime;		// starttime
	private String endtime;		// endtime
	private Date date;		// date
	private String memo;		// memo
	private String remark;		// remark
	
	private String latetime;
	private String earlytime;
	private String absenttime;
	private String sum;
	
	private  String  hrStaffName ;
	private  String  hrStaffDept ;
	
	public SzlHrAttence() {
		super();
	}

	public SzlHrAttence(String id){
		super(id);
	}

	@Length(min=0, max=11, message="number长度必须介于 0 和 11 之间")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	
	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@Length(min=0, max=50, message="memo长度必须介于 0 和 50 之间")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Length(min=0, max=255, message="remark长度必须介于 0 和 255 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLatetime() {
		return latetime;
	}

	public void setLatetime(String latetime) {
		this.latetime = latetime;
	}

	public String getEarlytime() {
		return earlytime;
	}

	public void setEarlytime(String earlytime) {
		this.earlytime = earlytime;
	}

	public String getAbsenttime() {
		return absenttime;
	}

	public void setAbsenttime(String absenttime) {
		this.absenttime = absenttime;
	}

	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
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