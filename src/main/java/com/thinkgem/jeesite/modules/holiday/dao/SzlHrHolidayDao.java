/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.holiday.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.holiday.entity.SzlHrHoliday;

/**
 * holidayDAO接口
 * @author yue.qiu
 * @version 2018-08-15
 */
@MyBatisDao
public interface SzlHrHolidayDao extends CrudDao<SzlHrHoliday> {
	public String findByNumber(String number);
	public String findShiftLeaveNumber(String number);
	public String findAnnualLeaveNumber(String number);
	public String updateShift(SzlHrHoliday szlHrHoliday);
	public String updateAnnual(SzlHrHoliday szlHrHoliday);
	public String getloginName(String number);
}