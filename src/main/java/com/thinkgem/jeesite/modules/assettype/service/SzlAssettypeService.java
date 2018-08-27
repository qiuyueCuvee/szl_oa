/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.assettype.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.assettype.entity.SzlAssettype;
import com.thinkgem.jeesite.modules.assettype.dao.SzlAssettypeDao;

/**
 * assettypeService
 * @author fang
 * @version 2018-08-24
 */
@Service
@Transactional(readOnly = true)
public class SzlAssettypeService extends CrudService<SzlAssettypeDao, SzlAssettype> {

	public SzlAssettype get(String id) {
		return super.get(id);
	}
	
	public List<SzlAssettype> findList(SzlAssettype szlAssettype) {
		return super.findList(szlAssettype);
	}
	
	public Page<SzlAssettype> findPage(Page<SzlAssettype> page, SzlAssettype szlAssettype) {
		return super.findPage(page, szlAssettype);
	}
	
	@Transactional(readOnly = false)
	public void save(SzlAssettype szlAssettype) {
		super.save(szlAssettype);
	}
	
	@Transactional(readOnly = false)
	public void delete(SzlAssettype szlAssettype) {
		super.delete(szlAssettype);
	}
	
}