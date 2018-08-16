/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.repairmgt.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 报修管理Entity
 * @author WHX
 * @version 2018-08-07
 */
public class Repairmgt extends DataEntity<Repairmgt> {
	
	private static final long serialVersionUID = 1L;
	private String applicant;		// 申请人
	private String department;		// 部门
	private String matter;		// 申请事项
	private String process;		// 处理进程
	private String receiver;		// 受理人
	private String solution;		// 解决方案
	private Date donedate;		// 解决日期
	private String status;		// 状态（0正常 1删除 2停用）
	
	public Repairmgt() {
		super();
	}

	public Repairmgt(String id){
		super(id);
	}

	@Length(min=0, max=64, message="申请人长度必须介于 0 和 64 之间")
	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	
	@Length(min=0, max=64, message="部门长度必须介于 0 和 64 之间")
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	@Length(min=0, max=64, message="申请事项长度必须介于 0 和 64 之间")
	public String getMatter() {
		return matter;
	}

	public void setMatter(String matter) {
		this.matter = matter;
	}
	
	@Length(min=0, max=64, message="处理进程长度必须介于 0 和 64 之间")
	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}
	
	@Length(min=0, max=64, message="受理人长度必须介于 0 和 64 之间")
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	@Length(min=0, max=128, message="解决方案长度必须介于 0 和 128 之间")
	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDonedate() {
		return donedate;
	}

	public void setDonedate(Date donedate) {
		this.donedate = donedate;
	}
	
	@Length(min=0, max=1, message="状态（0正常 1删除 2停用）长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}