/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.overtime.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.overtime.entity.SzlHrOvertime;

/**
 * overtimeDAO接口
 * @author yue.qiu
 * @version 2018-08-08
 */
@MyBatisDao
public interface SzlHrOvertimeDao extends CrudDao<SzlHrOvertime> {
	
}