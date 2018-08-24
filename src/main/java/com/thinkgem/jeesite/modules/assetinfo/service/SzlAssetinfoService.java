/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.assetinfo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.assetinfo.entity.SzlAssetinfo;
import com.thinkgem.jeesite.modules.assetinfo.dao.SzlAssetinfoDao;

/**
 * assetinfoService
 * @author fang
 * @version 2018-08-24
 */
@Service
@Transactional(readOnly = true)
public class SzlAssetinfoService extends CrudService<SzlAssetinfoDao, SzlAssetinfo> {

	public SzlAssetinfo get(String id) {
		return super.get(id);
	}
	
	public List<SzlAssetinfo> findList(SzlAssetinfo szlAssetinfo) {
		return super.findList(szlAssetinfo);
	}
	
	public Page<SzlAssetinfo> findPage(Page<SzlAssetinfo> page, SzlAssetinfo szlAssetinfo) {
		return super.findPage(page, szlAssetinfo);
	}
	
	@Transactional(readOnly = false)
	public void save(SzlAssetinfo szlAssetinfo) {
		super.save(szlAssetinfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(SzlAssetinfo szlAssetinfo) {
		super.delete(szlAssetinfo);
	}
	
}