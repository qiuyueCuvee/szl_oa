package com.thinkgem.jeesite.modules.hrattence.service;

import java.text.SimpleDateFormat;
<<<<<<< HEAD
=======

>>>>>>> 2a997e54d0e4c734c61d526815270946da4d8987
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
	
	@Autowired
	private SzlHrAttenceDao  attencedao;
	
	public SzlHrAttence get(String id) {
		return super.get(id);
	}
	
	public List<SzlHrAttence> findList(SzlHrAttence szlHrAttence) {
		return super.findList(szlHrAttence);
	}
	
	public List<SzlHrAttence> findAllMonthList(SzlHrAttence szlHrAttence) {
		return attencedao.findAllMonthList(szlHrAttence);
	}
	
	public Page<SzlHrAttence> findPage(Page<SzlHrAttence> page, SzlHrAttence szlHrAttence) {
		Page<SzlHrAttence> result= super.findPage(page, szlHrAttence);
//		List<SzlHrAttence> list = result.getList();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -3);
		c.set(Calendar.DAY_OF_MONTH, 26);
	
		SimpleDateFormat dfst = new SimpleDateFormat("yyyy-MM-dd");
		String begindate = dfst.format(c.getTime());
		Calendar d = Calendar.getInstance();
		d.add(Calendar.MONTH, -2);
		d.set(Calendar.DAY_OF_MONTH, 25);
		String enddate =  dfst.format(d.getTime());
		
		/*SzlHrAttence attence	= new SzlHrAttence();
		attence.setBegindate(begindate);
		attence.setEnddate(enddate);*/
//		List<SzlHrAttence> attencelist = attencedao.findAllMonthList(attence);
		
		szlHrAttence.setBegindate(begindate);
		szlHrAttence.setEnddate(enddate);
		List<SzlHrAttence> list = attencedao.findAllMonthList(szlHrAttence);
		List<SzlHrAttence> reslist = new ArrayList<SzlHrAttence>();
		for(SzlHrAttence entity:list) {
			SzlHrStaff hhrStaff = new SzlHrStaff();
			hhrStaff.setNumber(entity.getNumber());
			SzlHrStaff staff =  null;
			if(entity.getNumber()!=null) {
				staff = staffdao.findByNumber(entity.getNumber().toString());
			}
			
			if(staff!=null) {
				entity.setHrStaffName(staff.getName());
				entity.setHrStaffDept(staff.getDepartment());
			}
		
			SimpleDateFormat dfs = new SimpleDateFormat("HH:mm:ss");
			String start = entity.getStarttime();
			String end = entity.getEndtime();
			
		    try {
				  if(staff!=null&&end!=null) {
					  
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
						 if(!"0".equals(entity.getSum())) {
							reslist.add(entity);
						} 
						/* if("0".equals(entity.getSum())) {
							 attencelist.remove(entity);
							}*/
						 
				  }
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
		}
		result.setList(reslist);
//		result.setCount(attencelist.size());
		return result;
	}
	
	public List<HashMap> findMonthPage(Page<SzlHrAttence> page, SzlHrAttence szlHrAttence,SzlHrStaff szlHrStaff,HttpServletRequest request) throws ParseException {
//		Page<SzlHrAttence> result= super.findPage(page, szlHrAttence);
//		List<SzlHrAttence> list = result.getList();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -3);
		c.set(Calendar.DAY_OF_MONTH, 26);
	
		SimpleDateFormat dfst = new SimpleDateFormat("yyyy-MM-dd");
		String begindate = dfst.format(c.getTime());
		Calendar d = Calendar.getInstance();
		d.add(Calendar.MONTH, -2);
		d.set(Calendar.DAY_OF_MONTH, 25);
		String enddate =  dfst.format(d.getTime());
//		SzlHrStaff paramstaff = new SzlHrStaff();
//		paramstaff.setNumber(request.getParameter("number"));
		if("0000".equals(szlHrStaff.getNumber())) {
			szlHrStaff=null;
		}
		List<SzlHrStaff> stafflist = staffdao.findstaff(szlHrStaff);
		if(stafflist.size()>30) {
			if(page.getPageNo()*page.getPageSize()>stafflist.size()) {
				stafflist = stafflist.subList((page.getPageNo()-1)*page.getPageSize(),stafflist.size());
			}else {
				stafflist = stafflist.subList((page.getPageNo()-1)*page.getPageSize(), page.getPageNo()*page.getPageSize());
				}
			}
		
		List<HashMap> maplist = new ArrayList<HashMap>();
		for(SzlHrStaff element:stafflist) {
			
			szlHrAttence.setBegindate(begindate);
			szlHrAttence.setEnddate(enddate);
			szlHrAttence.setNumber(element.getNumber());
			
			List<SzlHrAttence> list = attencedao.findMonthList(szlHrAttence);
			HashMap calendarMap = new HashMap();
			calendarMap.put(element.getName(), list);
			maplist.add(calendarMap);
			for(SzlHrAttence entity:list) {
				
//				HashMap calendarMap = new HashMap();
				SzlHrStaff hhrStaff = new SzlHrStaff();
				hhrStaff.setNumber(entity.getNumber());
				SzlHrStaff staff =  null;
				/*if(entity.getDate().getTime()>c.getTimeInMillis()&&entity.getDate().getTime()<d.getTimeInMillis()) {
					calendarMap.put(entity.getDate(), "o");
				}*/
				
				if(entity.getNumber()!=null) {
					staff = staffdao.findByNumber(entity.getNumber().toString());
				}
				
				if(staff!=null) {
					entity.setHrStaffName(staff.getName());
					entity.setHrStaffDept(staff.getDepartment());
				}
			
				SimpleDateFormat dfs = new SimpleDateFormat("HH:mm:ss");
				String start = entity.getStarttime();
				String end = entity.getEndtime();
				
			    try {
					  if(start!=null&&end!=null) {
						  
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
							entity.setStatus(" ");
							if(Integer.parseInt(entity.getSum())==0) {
								entity.setStatus("√");
								/*if(entity.getDate().getTime()>c.getTimeInMillis()&&entity.getDate().getTime()<d.getTimeInMillis()) {
									calendarMap.put(entity.getDate(), "√");
								}*/
								
							}
							if(Integer.parseInt(entity.getLatetime())>0) {
								entity.setStatus("×");
								/*if(entity.getDate().getTime()>c.getTimeInMillis()&&entity.getDate().getTime()<d.getTimeInMillis()) {
									calendarMap.put(entity.getDate(), "×");
								}*/
								
							}
							if(Integer.parseInt(entity.getEarlytime())>0) {
								entity.setStatus("#");
								/*if(entity.getDate().getTime()>c.getTimeInMillis()&&entity.getDate().getTime()<d.getTimeInMillis()) {
									calendarMap.put(entity.getDate(), "#");
								}*/
								
							}
							
					  }
		
		
//		List<HashMap> calendarMapList = new ArrayList();
		
			
				 /* calendarMapList.add(calendarMap);
				  entity.setCalendarMapList(calendarMapList);*/
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
//		result.setList(list);
		return maplist;
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