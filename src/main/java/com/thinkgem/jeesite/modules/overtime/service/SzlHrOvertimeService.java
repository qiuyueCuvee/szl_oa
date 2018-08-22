/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.overtime.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.overtime.entity.SzlHrOvertime;
import com.thinkgem.jeesite.modules.hrattence.dao.SzlHrStaffDao;
import com.thinkgem.jeesite.modules.hrattence.entity.SzlHrStaff;
import com.thinkgem.jeesite.modules.overtime.dao.SzlHrOvertimeDao;

/**
 * overtimeService
 * @author yue.qiu
 * @version 2018-08-08
 */
@Service
@Transactional(readOnly = true)
public class SzlHrOvertimeService extends CrudService<SzlHrOvertimeDao, SzlHrOvertime> {
	@Autowired
	private SzlHrStaffDao staffdao;
	
	public SzlHrOvertime get(String id) {
		return super.get(id);
	}
	
	public List<SzlHrOvertime> findList(SzlHrOvertime szlHrOvertime) {
		return super.findList(szlHrOvertime);
	}
	
	public Page<SzlHrOvertime> findPage(Page<SzlHrOvertime> page, SzlHrOvertime szlHrOvertime) {
		//连表查询姓名、部门
		Page<SzlHrOvertime> result= super.findPage(page, szlHrOvertime);
		List<SzlHrOvertime> list = result.getList();
		for(SzlHrOvertime entity:list) {
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
	
	@Transactional(readOnly = false)
	public void save(SzlHrOvertime szlHrOvertime) {
		super.save(szlHrOvertime);
	}
	
	@Transactional(readOnly = false)
	public void delete(SzlHrOvertime szlHrOvertime) {
		super.delete(szlHrOvertime);
	}
	
}