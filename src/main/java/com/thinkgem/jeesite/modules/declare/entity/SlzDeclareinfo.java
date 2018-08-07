/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.declare.entity;

import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * declareEntity
 * @author yue.qiu
 * @version 2018-08-06
 */
public class SlzDeclareinfo extends DataEntity<SlzDeclareinfo> {
	
	private static final long serialVersionUID = 1L;
	private String declareDept;		// 申报人部门
	private String declareName;		// 姓名
	private String declarePhone;		// 联系电话
	private String declareTime;		// 申报时间
	private String declareGoods;		// 申报物品
	private String goodsUse;		// 用途
	private String referenceUrl;		// 参考地址
	private String declareRemark;		// 备注
	private String declareStatus;		// 申报单状态
	private String statusReason;		// 原因
	
	public SlzDeclareinfo() {
		super();
	}

	public SlzDeclareinfo(String id){
		super(id);
	}

	@Length(min=1, max=255, message="申报人部门长度必须介于 1 和 255 之间")
	public String getDeclareDept() {
		return declareDept;
	}

	public void setDeclareDept(String declareDept) {
		this.declareDept = declareDept;
	}
	
	@Length(min=1, max=255, message="姓名长度必须介于 1 和 255 之间")
	public String getDeclareName() {
		return declareName;
	}

	public void setDeclareName(String declareName) {
		this.declareName = declareName;
	}
	
	@Length(min=0, max=255, message="联系电话长度必须介于 0 和 255 之间")
	public String getDeclarePhone() {
		return declarePhone;
	}

	public void setDeclarePhone(String declarePhone) {
		this.declarePhone = declarePhone;
	}
	
	@Length(min=1, max=255, message="申报时间长度必须介于 1 和 255 之间")
	public String getDeclareTime() {
		return declareTime;
	}

	public void setDeclareTime(String declareTime) {
		this.declareTime = declareTime;
	}
	
	@Length(min=1, max=255, message="申报物品长度必须介于 1 和 255 之间")
	public String getDeclareGoods() {
		return declareGoods;
	}

	public void setDeclareGoods(String declareGoods) {
		this.declareGoods = declareGoods;
	}
	
	@Length(min=0, max=255, message="用途长度必须介于 0 和 255 之间")
	public String getGoodsUse() {
		return goodsUse;
	}

	public void setGoodsUse(String goodsUse) {
		this.goodsUse = goodsUse;
	}
	
	@Length(min=0, max=255, message="参考地址长度必须介于 0 和 255 之间")
	public String getReferenceUrl() {
		return referenceUrl;
	}

	public void setReferenceUrl(String referenceUrl) {
		this.referenceUrl = referenceUrl;
	}
	
	@Length(min=0, max=255, message="备注长度必须介于 0 和 255 之间")
	public String getDeclareRemark() {
		return declareRemark;
	}

	public void setDeclareRemark(String declareRemark) {
		this.declareRemark = declareRemark;
	}
	
	@Length(min=1, max=255, message="申报单状态长度必须介于 1 和 255 之间")
	public String getDeclareStatus() {
		return declareStatus;
	}

	public void setDeclareStatus(String declareStatus) {
		this.declareStatus = declareStatus;
	}
	
	@Length(min=0, max=255, message="原因长度必须介于 0 和 255 之间")
	public String getStatusReason() {
		return statusReason;
	}

	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}
	
	
}