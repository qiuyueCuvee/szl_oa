/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hrattence.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.hrattence.entity.SzlHrStaff;

/**
 * hrattenceDAO接口
 * @author fang
 * @version 2018-08-06
 */
@MyBatisDao
public interface SzlHrStaffDao extends CrudDao<SzlHrStaff> {
	
	public SzlHrStaff findByNumber(String number);
	
	public List<SzlHrStaff> findstaff(SzlHrStaff staff);
}