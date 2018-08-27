package com.thinkgem.jeesite.modules.hrattence.service;

import java.text.SimpleDateFormat;

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
import com.thinkgem.jeesite.modules.leave.entity.SzlHrLeave;
import com.thinkgem.jeesite.modules.leave.service.SzlHrLeaveService;
import com.thinkgem.jeesite.modules.sys.entity.User;

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
	
	@Autowired
	private SzlHrLeaveService szlHrLeaveService;
	
	public SzlHrAttence get(String id) {
		return super.get(id);
	}
	
	public List<SzlHrAttence> findList(SzlHrAttence szlHrAttence) {
		return super.findList(szlHrAttence);
	}
	
	public List<SzlHrAttence> findAllMonthList(SzlHrAttence szlHrAttence) {
		return attencedao.findAllMonthList(szlHrAttence);
	}
	
	public List<SzlHrAttence> findAttenceList(SzlHrAttence szlHrAttence){
		
	/*	User user = szlHrAttence.getCurrentUser();
		String deptname = user.getOffice().getName();
		SzlHrStaff szlHrStaff = new SzlHrStaff();
		szlHrStaff.setDepartment(deptname);
		List<SzlHrStaff> list = szlHrStaffService.findList(szlHrStaff);
		for(SzlHrStaff staff:list) {
			staff.getNumber();
		}*/
		
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -3);
		c.set(Calendar.DAY_OF_MONTH, 26);
	
		SimpleDateFormat dfst = new SimpleDateFormat("yyyy-MM-dd");
		
		SimpleDateFormat tfst = new SimpleDateFormat("hh:mm:ss");
		String begindate = dfst.format(c.getTime());
		String starttime = tfst.format(c.getTime());
		Calendar d = Calendar.getInstance();
		d.add(Calendar.MONTH, -2);
		d.set(Calendar.DAY_OF_MONTH, 25);
		String enddate =  dfst.format(d.getTime());
		String endtime = tfst.format(d.getTime());
		
		szlHrAttence.setBegindate(begindate);
		szlHrAttence.setEnddate(enddate);
		szlHrAttence.setStarttime(starttime);
		szlHrAttence.setEndtime(endtime);
		List<SzlHrAttence> list = attencedao.findAllMonthList(szlHrAttence);
		
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
			String start = entity.getStarttime();
			String end = entity.getEndtime();
			
		    try {
				  if(start!=null&&end!=null) {
					    this.parseDate(entity, start, end);
				  }
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
		}
		return list;
	}
	//考勤异常
	public Page<SzlHrAttence> findPage(Page<SzlHrAttence> page, SzlHrAttence szlHrAttence) {
		Page<SzlHrAttence> result= super.findPage(page, szlHrAttence);
//		List<SzlHrAttence> list = result.getList();
		result.setList(findAttenceList(szlHrAttence));
		return result;
	}
	
	//考勤
	public List<HashMap> findMonth(Page<SzlHrAttence> page, SzlHrAttence szlHrAttence,SzlHrStaff szlHrStaff,HttpServletRequest request) throws ParseException {
		
		List<HashMap> maplist = new ArrayList<HashMap>();
		List<SzlHrStaff> stafflist = staffdao.findstaff(szlHrStaff);
		this.refactorStaff(stafflist, szlHrAttence, maplist);
		return maplist;
	}
	
	//分页考勤
	public List<HashMap> findMonthPage(Page<SzlHrAttence> page, SzlHrAttence szlHrAttence,SzlHrStaff szlHrStaff,HttpServletRequest request) throws ParseException {
		
		List<HashMap> maplist = new ArrayList<HashMap>();
		List<SzlHrStaff> stafflist = staffdao.findstaff(szlHrStaff);
		
		if(stafflist.size()>page.getPageSize()) {
			if(page.getPageNo()*page.getPageSize()>stafflist.size()) {
				stafflist = stafflist.subList((page.getPageNo()-1)*page.getPageSize(),stafflist.size());
			}else {
				stafflist = stafflist.subList((page.getPageNo()-1)*page.getPageSize(), page.getPageNo()*page.getPageSize());
			}
		}
		
		this.refactorStaff(stafflist, szlHrAttence, maplist);
		return maplist;
	}
	
	public  void refactorStaff(List<SzlHrStaff> stafflist,SzlHrAttence szlHrAttence,List<HashMap> maplist){
		
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -3);
		c.set(Calendar.DAY_OF_MONTH, 26);
	
		SimpleDateFormat dfst = new SimpleDateFormat("yyyy-MM-dd");
		String begindate = dfst.format(c.getTime());
		Calendar d = Calendar.getInstance();
		d.add(Calendar.MONTH, -2);
		d.set(Calendar.DAY_OF_MONTH, 25);
		String enddate =  dfst.format(d.getTime());
		for(SzlHrStaff element:stafflist) {
			
			szlHrAttence.setBegindate(begindate);
			szlHrAttence.setEnddate(enddate);
			szlHrAttence.setNumber(element.getNumber());
			
			List<SzlHrAttence> list = attencedao.findMonthList(szlHrAttence);
			HashMap calendarMap = new HashMap();
			calendarMap.put(element.getNumber(), list);
			maplist.add(calendarMap);
			
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
			 
				String start = entity.getStarttime();
				String end = entity.getEndtime();
				
			    try {
					  if(start!=null&&end!=null) {
						    this.parseDate(entity, start, end);
						    entity.setStatus(" ");
							if(Integer.parseInt(entity.getSum())==0) {
								entity.setStatus("√");
								
							}
							if(Integer.parseInt(entity.getLatetime())>0) {
								entity.setStatus("×");
								
							}
							if(Integer.parseInt(entity.getEarlytime())>0) {
								entity.setStatus("#");
							}
					  }
					  SzlHrLeave szlHrLeave = new SzlHrLeave();
					  szlHrLeave.setNumber(entity.getNumber());
					  szlHrLeave.setBegindate(begindate);
					  szlHrLeave.setEnddate(enddate);
					  szlHrLeave.setLeaveType("1");
					  parseHoliday(entity,szlHrLeave);
					  szlHrLeave.setLeaveType("2");
					  parseHoliday(entity,szlHrLeave);
					  szlHrLeave.setLeaveType("3");
					  parseHoliday(entity,szlHrLeave);
					  szlHrLeave.setLeaveType("4");
					  parseHoliday(entity,szlHrLeave);
					  szlHrLeave.setLeaveType("5");
					  parseHoliday(entity,szlHrLeave);
					  szlHrLeave.setLeaveType("6");
					  parseHoliday(entity,szlHrLeave);
					  
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}
	}
	
	public void parseHoliday(SzlHrAttence entity,SzlHrLeave szlHrLeave) throws ParseException {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");
		List<SzlHrLeave>  list = szlHrLeaveService.findAllMonthList(szlHrLeave);
		List<Calendar> lDate = new ArrayList<Calendar>();
	    for(SzlHrLeave element:list) {
	    	    String stardate = element.getStartTime();
		        String enddate = element.getEndTime();
		        Date begin=dfs.parse(stardate);
			    Date after=dfs.parse(enddate);
				Calendar start = Calendar.getInstance();
				start.setTime(begin);
				Calendar end = Calendar.getInstance();
				end.setTime(after);
				lDate.add(start); 
				Calendar cal = Calendar.getInstance();
				cal.setTime(begin);
				while (true) {
					cal.add(Calendar.DAY_OF_MONTH, 1);
					Calendar tmp = Calendar.getInstance();
					tmp.setTime(cal.getTime());
					if (after.after(cal.getTime())) {
						lDate.add(tmp);
					} else {
						break;
					}
				}
				lDate.add(end); 
	    }
	    for(Calendar date:lDate) {
	    	if(entity.getDate().equals(date.get(Calendar.DAY_OF_MONTH))) {
	    		if(szlHrLeave.equals("1")) {
	    			entity.setStatus("◻");
	    		}
	    		if(szlHrLeave.equals("2")) {
	    			entity.setStatus("年假");
	    		}
	    		if(szlHrLeave.equals("3")) {
	    			entity.setStatus("△");
	    		}
	    		if(szlHrLeave.equals("4")) {
	    			entity.setStatus("*");
	    		}
	    		if(szlHrLeave.equals("5")) {
	    			entity.setStatus("婚假");
	    		}
	    		if(szlHrLeave.equals("6")) {
	    			entity.setStatus("丧假");
	    		}
	    		
	    	}
	    }
		
		
	}
	public void parseDate(SzlHrAttence entity,String start,String end) throws ParseException {
		SimpleDateFormat dfs = new SimpleDateFormat("HH:mm:ss");
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