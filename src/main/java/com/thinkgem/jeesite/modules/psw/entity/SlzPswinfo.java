/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.psw.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 密码管理Entity
 * @author yue.qiu
 * @version 2018-08-06
 */
public class SlzPswinfo extends DataEntity<SlzPswinfo> {
	
	private static final long serialVersionUID = 1L;
	private String password;		// 密码
	private String desc;		// 说明
	
	public SlzPswinfo() {
		super();
	}

	public SlzPswinfo(String id){
		super(id);
	}

	@Length(min=0, max=255, message="密码长度必须介于 0 和 255 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Length(min=0, max=255, message="说明长度必须介于 0 和 255 之间")
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}