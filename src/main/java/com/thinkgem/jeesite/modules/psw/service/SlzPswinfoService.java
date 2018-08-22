/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.psw.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.psw.entity.SlzPswinfo;
import com.thinkgem.jeesite.modules.psw.dao.SlzPswinfoDao;

/**
 * 密码管理Service
 * @author yue.qiu
 * @version 2018-08-06
 */
@Service
@Transactional(readOnly = true)
public class SlzPswinfoService extends CrudService<SlzPswinfoDao, SlzPswinfo> {

	public SlzPswinfo get(String id) {
		return super.get(id);
	}
	
	public List<SlzPswinfo> findList(SlzPswinfo slzPswinfo) {
		return super.findList(slzPswinfo);
	}
	
	public Page<SlzPswinfo> findPage(Page<SlzPswinfo> page, SlzPswinfo slzPswinfo) {
		return super.findPage(page, slzPswinfo);
	}
	
	@Transactional(readOnly = false)
	public void save(SlzPswinfo slzPswinfo) {
		super.save(slzPswinfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(SlzPswinfo slzPswinfo) {
		super.delete(slzPswinfo);
	}
	
}