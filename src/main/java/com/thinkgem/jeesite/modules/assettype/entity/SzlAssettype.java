/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.assettype.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * assettypeEntity
 * @author fang
 * @version 2018-08-24
 */
public class SzlAssettype extends DataEntity<SzlAssettype> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// title
	private String template;		// template
	
	public SzlAssettype() {
		super();
	}

	public SzlAssettype(String id){
		super(id);
	}

	@Length(min=0, max=255, message="title长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=255, message="template长度必须介于 0 和 255 之间")
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
}