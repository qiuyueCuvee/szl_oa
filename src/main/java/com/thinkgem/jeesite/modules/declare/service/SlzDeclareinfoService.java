/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.declare.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.declare.entity.SlzDeclareinfo;
import com.thinkgem.jeesite.modules.declare.dao.SlzDeclareinfoDao;

/**
 * declareService
 * @author yue.qiu
 * @version 2018-08-06
 */
@Service
@Transactional(readOnly = true)
public class SlzDeclareinfoService extends CrudService<SlzDeclareinfoDao, SlzDeclareinfo> {

	public SlzDeclareinfo get(String id) {
		return super.get(id);
	}
	
	public List<SlzDeclareinfo> findList(SlzDeclareinfo slzDeclareinfo) {
		return super.findList(slzDeclareinfo);
	}
	
	public Page<SlzDeclareinfo> findPage(Page<SlzDeclareinfo> page, SlzDeclareinfo slzDeclareinfo) {
		return super.findPage(page, slzDeclareinfo);
	}
	
	@Transactional(readOnly = false)
	public void save(SlzDeclareinfo slzDeclareinfo) {
		super.save(slzDeclareinfo);
	}
	@Transactional(readOnly = false)
	public void update(SlzDeclareinfo slzDeclareinfo) {
		super.update(slzDeclareinfo);
	}
	@Transactional(readOnly = false)
	public void delete(SlzDeclareinfo slzDeclareinfo) {
		super.delete(slzDeclareinfo);
	}
	
}