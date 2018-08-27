/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.leave.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.leave.entity.SzlHrLeave;

/**
 * leaveDAO接口
 * @author yue.qiu
 * @version 2018-08-15
 */
@MyBatisDao
public interface SzlHrLeaveDao extends CrudDao<SzlHrLeave> {
	public List<SzlHrLeave> findAllMonthList(SzlHrLeave szlHrLeave);
	
}