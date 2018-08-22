/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.psw.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.psw.entity.SlzPswinfo;

/**
 * 密码管理DAO接口
 * @author yue.qiu
 * @version 2018-08-06
 */
@MyBatisDao
public interface SlzPswinfoDao extends CrudDao<SlzPswinfo> {
	
}