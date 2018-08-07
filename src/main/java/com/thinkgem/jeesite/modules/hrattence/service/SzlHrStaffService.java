/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hrattence.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.hrattence.entity.SzlHrStaff;
import com.thinkgem.jeesite.modules.hrattence.dao.SzlHrStaffDao;

/**
 * hrattenceService
 * @author fang
 * @version 2018-08-06
 */
@Service
@Transactional(readOnly = true)
public class SzlHrStaffService extends CrudService<SzlHrStaffDao, SzlHrStaff> {

	public SzlHrStaff get(String id) {
		return super.get(id);
	}
	
	public List<SzlHrStaff> findList(SzlHrStaff szlHrStaff) {
		return super.findList(szlHrStaff);
	}
	
	public Page<SzlHrStaff> findPage(Page<SzlHrStaff> page, SzlHrStaff szlHrStaff) {
		return super.findPage(page, szlHrStaff);
	}
	
	@Transactional(readOnly = false)
	public void save(SzlHrStaff szlHrStaff) {
		super.save(szlHrStaff);
	}
	
	@Transactional(readOnly = false)
	public void delete(SzlHrStaff szlHrStaff) {
		super.delete(szlHrStaff);
	}
	
}