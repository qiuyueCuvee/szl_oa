/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.holiday.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.holiday.entity.SzlHrHoliday;
import com.thinkgem.jeesite.modules.hrattence.dao.SzlHrStaffDao;
import com.thinkgem.jeesite.modules.hrattence.entity.SzlHrStaff;
import com.thinkgem.jeesite.modules.holiday.dao.SzlHrHolidayDao;

/**
 * holidayService
 * @author yue.qiu
 * @version 2018-08-15
 */
@Service
@Transactional(readOnly = true)
public class SzlHrHolidayService extends CrudService<SzlHrHolidayDao, SzlHrHoliday> {
	@Autowired
	private SzlHrStaffDao staffdao;
	
	public SzlHrHoliday get(String id) {
		return super.get(id);
	}
	
	public List<SzlHrHoliday> findList(SzlHrHoliday szlHrHoliday) {
		return super.findList(szlHrHoliday);
	}
	
	
	public Page<SzlHrHoliday> findPage(Page<SzlHrHoliday> page, SzlHrHoliday szlHrHoliday) {
		//连表查询姓名、部门
		Page<SzlHrHoliday> result= super.findPage(page, szlHrHoliday);
		List<SzlHrHoliday> list = result.getList();
		for(SzlHrHoliday entity:list) {
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
	public void save(SzlHrHoliday szlHrHoliday) {
		super.save(szlHrHoliday);
	}
	
	@Transactional(readOnly = false)
	public void delete(SzlHrHoliday szlHrHoliday) {
		super.delete(szlHrHoliday);
	}
	
}