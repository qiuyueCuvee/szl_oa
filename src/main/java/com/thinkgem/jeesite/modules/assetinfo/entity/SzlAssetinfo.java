/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.assetinfo.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * assetinfoEntity
 * @author fang
 * @version 2018-08-24
 */
public class SzlAssetinfo extends DataEntity<SzlAssetinfo> {
	
	private static final long serialVersionUID = 1L;
	private String state;		// state
	private String uid;		// uid
	private String info;		// info
	private String tid;		// tid
	
	public SzlAssetinfo() {
		super();
	}

	public SzlAssetinfo(String id){
		super(id);
	}

	@Length(min=0, max=255, message="state长度必须介于 0 和 255 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Length(min=0, max=255, message="uid长度必须介于 0 和 255 之间")
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	@Length(min=0, max=255, message="info长度必须介于 0 和 255 之间")
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	@Length(min=0, max=255, message="tid长度必须介于 0 和 255 之间")
	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}
	
}