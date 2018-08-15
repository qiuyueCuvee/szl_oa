/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.leave.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * leaveEntity
 * @author yue.qiu
 * @version 2018-08-15
 */
public class SzlHrLeave extends DataEntity<SzlHrLeave> {
	
	private static final long serialVersionUID = 1L;
	private String number;		// 工号
	private String applyTime;		// 申请时间
	private String leaveType;		// 请假类别
	private String leaveReason;		// 请假事由
	private String startTime;		// 开始时间
	private String endTime;		// 结束时间
	private String leaveHours;		// 请假时长
	private String leaveRemark;		// 备注
	private String leaveStatus;		// 审批状态
	private String statusReason;		// 状态原因
	private  String  hrStaffName ;
	private  String  hrStaffDept ;	
	
	public SzlHrLeave() {
		super();
	}

	public SzlHrLeave(String id){
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
	
	@Length(min=1, max=255, message="请假类别长度必须介于 1 和 255 之间")
	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	
	@Length(min=1, max=255, message="请假事由长度必须介于 1 和 255 之间")
	public String getLeaveReason() {
		return leaveReason;
	}

	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}
	
	@Length(min=1, max=255, message="开始时间长度必须介于 1 和 255 之间")
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	@Length(min=1, max=255, message="结束时间长度必须介于 1 和 255 之间")
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	@Length(min=1, max=255, message="请假时长长度必须介于 1 和 255 之间")
	public String getLeaveHours() {
		return leaveHours;
	}

	public void setLeaveHours(String leaveHours) {
		this.leaveHours = leaveHours;
	}
	
	@Length(min=0, max=255, message="备注长度必须介于 0 和 255 之间")
	public String getLeaveRemark() {
		return leaveRemark;
	}

	public void setLeaveRemark(String leaveRemark) {
		this.leaveRemark = leaveRemark;
	}
	
	@Length(min=0, max=255, message="审批状态长度必须介于 0 和 255 之间")
	public String getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(String leaveStatus) {
		this.leaveStatus = leaveStatus;
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