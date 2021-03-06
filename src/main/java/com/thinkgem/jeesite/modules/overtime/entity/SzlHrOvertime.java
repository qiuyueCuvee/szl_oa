/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.overtime.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * overtimeEntity
 * @author yue.qiu
 * @version 2018-08-08
 */
public class SzlHrOvertime extends DataEntity<SzlHrOvertime> {
	
	private static final long serialVersionUID = 1L;
	private String number;		// 工号
	private String applyTime;		// 申请时间
	private String startTime;		// 加班开始时间
	private String endTime;		// 加班结束时间
	private String workHours;		// 工作时长
	private String workContent;		// 工作内容
	private String overtimeRemark;		// 备注
	private String overtimeStatus;		// 审批状态
	private String statusReason;		// 状态原因
	private  String  hrStaffName ;
	private  String  hrStaffDept ;	
	public SzlHrOvertime() {
		super();
	}

	public SzlHrOvertime(String id){
		super(id);
	}

	@Length(min=1, max=255, message="工号长度必须介于 1 和 255 之间")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@Length(min=1, max=255, message="申请时间长度必须介于 1 和 255 之间")
	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	
	@Length(min=1, max=255, message="加班开始时间长度必须介于 1 和 255 之间")
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	@Length(min=1, max=255, message="加班结束时间长度必须介于 1 和 255 之间")
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	@Length(min=1, max=255, message="工作时长长度必须介于 1 和 255 之间")
	public String getWorkHours() {
		return workHours;
	}

	public void setWorkHours(String workHours) {
		this.workHours = workHours;
	}
	
	@Length(min=1, max=255, message="工作内容长度必须介于 1 和 255 之间")
	public String getWorkContent() {
		return workContent;
	}

	public void setWorkContent(String workContent) {
		this.workContent = workContent;
	}
	
	@Length(min=0, max=255, message="备注长度必须介于 0 和 255 之间")
	public String getOvertimeRemark() {
		return overtimeRemark;
	}

	public void setOvertimeRemark(String overtimeRemark) {
		this.overtimeRemark = overtimeRemark;
	}
	
	@Length(min=1, max=255, message="审批状态长度必须介于 1 和 255 之间")
	public String getOvertimeStatus() {
		return overtimeStatus;
	}

	public void setOvertimeStatus(String overtimeStatus) {
		this.overtimeStatus = overtimeStatus;
	}
	
	@Length(min=0, max=255, message="状态原因长度必须介于 0 和 255 之间")
	public String getStatusReason() {
		return statusReason;
	}

	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
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