/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.repairmgt.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.repairmgt.entity.Repairmgt;

/**
 * 报修管理DAO接口
 * @author WHX
 * @version 2018-08-07
 */
@MyBatisDao
public interface RepairmgtDao extends CrudDao<Repairmgt> {
	
}