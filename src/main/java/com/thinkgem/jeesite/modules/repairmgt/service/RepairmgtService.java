/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.repairmgt.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.repairmgt.entity.Repairmgt;
import com.thinkgem.jeesite.modules.repairmgt.dao.RepairmgtDao;

/**
 * 报修管理Service
 * @author WHX
 * @version 2018-08-07
 */
@Service
@Transactional(readOnly = true)
public class RepairmgtService extends CrudService<RepairmgtDao, Repairmgt> {

	public Repairmgt get(String id) {
		return super.get(id);
	}
	
	public List<Repairmgt> findList(Repairmgt repairmgt) {
		return super.findList(repairmgt);
	}
	
	public Page<Repairmgt> findPage(Page<Repairmgt> page, Repairmgt repairmgt) {
		return super.findPage(page, repairmgt);
	}
	
	@Transactional(readOnly = false)
	public void save(Repairmgt repairmgt) {
		super.save(repairmgt);
	}
	
	@Transactional(readOnly = false)
	public void delete(Repairmgt repairmgt) {
		super.delete(repairmgt);
	}
	
}