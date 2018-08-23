/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.leave.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.leave.entity.SzlHrLeave;
import com.thinkgem.jeesite.modules.hrattence.dao.SzlHrStaffDao;
import com.thinkgem.jeesite.modules.hrattence.entity.SzlHrStaff;
import com.thinkgem.jeesite.modules.leave.dao.SzlHrLeaveDao;

/**
 * leaveService
 * @author yue.qiu
 * @version 2018-08-15
 */
@Service
@Transactional(readOnly = true)
public class SzlHrLeaveService extends CrudService<SzlHrLeaveDao, SzlHrLeave> {
	@Autowired
	private SzlHrStaffDao staffdao;
	
	@Autowired
	private SzlHrLeaveDao szlHrLeaveDao;
	
	public SzlHrLeave get(String id) {
		return super.get(id);
	}
	
	public List<SzlHrLeave> findList(SzlHrLeave szlHrLeave) {
		return super.findList(szlHrLeave);
	}
	
	public Page<SzlHrLeave> findPage(Page<SzlHrLeave> page, SzlHrLeave szlHrLeave) {
		//连表查询姓名、部门
		Page<SzlHrLeave> result= super.findPage(page, szlHrLeave);
		List<SzlHrLeave> list = result.getList();
		for(SzlHrLeave entity:list) {
			SzlHrStaff hrStaff = new SzlHrStaff();
			hrStaff.setNumber(entity.getNumber());
			SzlHrStaff staff =  staffdao.findByNumber(entity.getNumber().toString());
			if(staff!=null) {
				entity.setHrStaffName(staff.getName());
				entity.setHrStaffDept(staff.getDepartment());
			}
		}
		return result;
	}
	
	public List<SzlHrLeave> findAllMonthList(SzlHrLeave szlHrLeave){
		return szlHrLeaveDao.findAllMonthList(szlHrLeave);
	}
	
	@Transactional(readOnly = false)
	public void save(SzlHrLeave szlHrLeave) {
		super.save(szlHrLeave);
	}
	
	@Transactional(readOnly = false)
	public void delete(SzlHrLeave szlHrLeave) {
		super.delete(szlHrLeave);
	}
	
}