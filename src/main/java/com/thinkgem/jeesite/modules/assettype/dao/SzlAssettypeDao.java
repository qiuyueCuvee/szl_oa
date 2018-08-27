/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.assettype.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.assettype.entity.SzlAssettype;

/**
 * assettypeDAO接口
 * @author fang
 * @version 2018-08-24
 */
@MyBatisDao
public interface SzlAssettypeDao extends CrudDao<SzlAssettype> {
	
}