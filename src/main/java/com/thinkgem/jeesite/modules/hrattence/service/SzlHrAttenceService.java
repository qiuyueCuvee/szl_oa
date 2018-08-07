/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hrattence.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.hrattence.dao.SzlHrAttenceDao;
import com.thinkgem.jeesite.modules.hrattence.dao.SzlHrStaffDao;
import com.thinkgem.jeesite.modules.hrattence.entity.SzlHrAttence;
import com.thinkgem.jeesite.modules.hrattence.entity.SzlHrStaff;

/**
 * hrattenceService
 * @author fang
 * @version 2018-08-06
 */
@Service
@Transactional(readOnly = true)
public class SzlHrAttenceService extends CrudService<SzlHrAttenceDao, SzlHrAttence> {

	@Autowired
	private SzlHrStaffDao staffdao;
	
	public SzlHrAttence get(String id) {
		return super.get(id);
	}
	
	public List<SzlHrAttence> findList(SzlHrAttence szlHrAttence) {
		return super.findList(szlHrAttence);
	}
	
	public Page<SzlHrAttence> findPage(Page<SzlHrAttence> page, SzlHrAttence szlHrAttence) {
		Page<SzlHrAttence> result= super.findPage(page, szlHrAttence);
		List<SzlHrAttence> list = result.getList();
//		List<SzlHrAttence> reslist = new ArrayList<SzlHrAttence>();
		for(SzlHrAttence entity:list) {
			SzlHrStaff hhrStaff = new SzlHrStaff();
			hhrStaff.setNumber(entity.getNumber());
			SzlHrStaff staff =  staffdao.findByNumber(entity.getNumber().toString());
			if(staff!=null) {
				entity.setHrStaffName(staff.getName());
				entity.setHrStaffDept(staff.getDepartment());
			}
		
			SimpleDateFormat dfs = new SimpleDateFormat("HH:mm:ss");
			String start = entity.getStarttime();
			String end = entity.getEndtime();
		   try {
			Date begin=dfs.parse(start);
			Date after=dfs.parse(end);
		    Date tmp = dfs.parse("09:00:00");
		    Date tmp1 = dfs.parse("18:00:00");
		    long between=(begin.getTime()-tmp.getTime())/1000;
		    long min=between/60;
		    String minute = String.valueOf(min);
		    long between1=(tmp1.getTime()-after.getTime())/1000;
		    long min1=between1/60;
		    String minute1 = String.valueOf(min1);
		    long absenttime=0;
		    
		    //latetime
			if(!"00:00:00".equals(entity.getStarttime())) {
				entity.setLatetime(minute);
			}
			if("00:00:00".equals(entity.getStarttime())||min<0) {
				min =0;
				entity.setLatetime("0");
			}
			//earlytime
			if(!"00:00:00".equals(entity.getEndtime())) {
				entity.setEarlytime(minute1);
			}
			if("00:00:00".equals(entity.getEndtime())||min1<0) {
				min1=0;
				entity.setEarlytime("0");
			}
			//absenttime
			absenttime = 540-min-min1;
			entity.setAbsenttime(String.valueOf(absenttime));
			//sum
			entity.setSum(String.valueOf(min+min1+absenttime));
			if(!"00:00:00".equals(entity.getEndtime())&&!"00:00:00".equals(entity.getStarttime())) {
				absenttime = min+min1;
				entity.setAbsenttime(String.valueOf(absenttime));
				//sum
				entity.setSum(String.valueOf(absenttime));
			}
//			if(!"0".equals(entity.getSum())) {
//			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
		}
//		result.setList(reslist);
		return result;
	}
	
	@Transactional(readOnly = false)
	public void save(SzlHrAttence szlHrAttence) {
		super.save(szlHrAttence);
	}
	
	@Transactional(readOnly = false)
	public void delete(SzlHrAttence szlHrAttence) {
		super.delete(szlHrAttence);
	}
	
}