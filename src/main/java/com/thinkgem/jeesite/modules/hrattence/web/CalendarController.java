package com.thinkgem.jeesite.modules.hrattence.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.hrattence.dao.SzlHrStaffDao;
import com.thinkgem.jeesite.modules.hrattence.entity.SzlHrAttence;
import com.thinkgem.jeesite.modules.hrattence.entity.SzlHrStaff;
import com.thinkgem.jeesite.modules.hrattence.service.SzlHrAttenceService;
import com.thinkgem.jeesite.modules.overtime.entity.SzlHrOvertime;
import com.thinkgem.jeesite.modules.overtime.service.SzlHrOvertimeService;

@Controller
@RequestMapping(value = "${adminPath}/hrattence/calendar")
public class CalendarController extends BaseController{

	@Autowired
	private SzlHrStaffDao staffdao;
	
	@Autowired
	private SzlHrAttenceService attenceService;
	
	@Autowired
	private SzlHrOvertimeService overtimeService;
	
	@RequestMapping(value = "list")
	public String list(SzlHrAttence szlHrAttence,HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
		Page<SzlHrAttence> result=  new Page<SzlHrAttence>(request, response) ;
		SzlHrStaff paramstaff = new SzlHrStaff();
//		if(!"0000".equals(szlHrAttence.getNumber())) {
		paramstaff.setNumber(szlHrAttence.getNumber());
//		} 
		
		List<SzlHrStaff> stafflist = staffdao.findstaff(paramstaff);
		result.setCount(stafflist.size());
		List<HashMap> list = attenceService.findMonthPage(new Page<SzlHrAttence>(request, response), szlHrAttence,paramstaff,request);
		Map calendarMap = new HashMap();
		
		StringBuffer html = new StringBuffer();
		html.append("<tr>\r\n");
		html.append("<th>姓名</th>");
		Calendar start = Calendar.getInstance();
		start.add(Calendar.MONTH, -3);
		start.set(Calendar.DAY_OF_MONTH, 26);
		SimpleDateFormat dfst = new SimpleDateFormat("yyyy-MM-dd");
		String begindate = dfst.format(start.getTime());
		int maxCols = start.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		Calendar end = Calendar.getInstance();
		end.add(Calendar.MONTH, -2);
		end.set(Calendar.DAY_OF_MONTH, 25);
		String enddate =  dfst.format(end.getTime());
		
		List<Calendar> calendar = this.getWeekend(begindate, enddate);
		for(int col = 26; col <= maxCols; col++) {
			html.append("<th>");
			
			for(Calendar element:calendar) {
				if(element.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY&&element.get(Calendar.DAY_OF_MONTH)==col){
					html.append("六</br>");
					break;
				}
				if(element.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY&&element.get(Calendar.DAY_OF_MONTH)==col){
					html.append("日</br>");
					break;
				}
			}
			html.append(col);
			html.append("</th>");
		}
		for(int col = 1; col <= 25; col++) {
			html.append("<th>");
			
			for(Calendar element:calendar) {
				if(element.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY&&element.get(Calendar.DAY_OF_MONTH)==col){
					html.append("六</br>");
					break;
				}
				if(element.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY&&element.get(Calendar.DAY_OF_MONTH)==col){
					html.append("日</br>");
					break;
				}
			}
			html.append(col);
			html.append("</th>");
		}
		html.append("<th>加班总数/</br>小时</th>");
		html.append("<th>倒休</br>(天)</th>");
		html.append("<th>扣薪假</br>(天)</th>");
		html.append("<th>实际出</br>勤天数</th>");
		html.append("<th>签字确认</th>");
		html.append("</tr>\r\n");
		for(HashMap map:list) {
			Iterator iter = map.entrySet().iterator();
			while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			new SzlHrStaff();
			SzlHrStaff tmpstaff   = staffdao.findByNumber(key.toString());
			if(tmpstaff!=null) {
				html.append("<tr><td>");
				html.append(tmpstaff.getName());
				html.append("</td>");
			}
			List<SzlHrAttence> val = (List<SzlHrAttence>)entry.getValue();
			result.setList(val);
			for(SzlHrAttence obj:val) {
				html.append("<td>");
				html.append(obj.getStatus());
				html.append("</td>");
		
			}
			SzlHrOvertime szlHrOvertime = new SzlHrOvertime();
			szlHrOvertime.setNumber(key.toString());
			List<SzlHrOvertime> timelist = overtimeService.findList(szlHrOvertime);
			long hours =0l;
			for(SzlHrOvertime element:timelist) {
				hours += Long.valueOf(element.getWorkHours());
			}
			html.append("<td>");
			html.append(hours);
			html.append("</td>");
			html.append("</tr>");
			}
			
		}
		
		szlHrAttence.setHtml(html.toString());
		model.addAttribute("page", result);
		return "modules/hrattence/calendarList";
	}

	//获得周六周日
	public List<Calendar> getWeekend(String startTime, String endTime) throws ParseException{
		    SimpleDateFormat dfst = new SimpleDateFormat("yyyy-MM-dd");  
			Date beginDate = null;
			Date endDate= null;
			Calendar start = Calendar.getInstance();
			if(startTime!=null &&!startTime.isEmpty())  {
				beginDate = dfst.parse(startTime);
				start.setTime(beginDate);
			}
			Calendar end = Calendar.getInstance();
			if(endTime!=null &&!endTime.isEmpty())  {
				endDate = dfst.parse(endTime);
				end.setTime(endDate);
			}
			List<Calendar> lDate = new ArrayList<Calendar>();
			List<Calendar> calendar = new ArrayList<Calendar>();
			lDate.add(start); 
			Calendar cal = Calendar.getInstance();
			cal.setTime(beginDate);
			while (true) {
				cal.add(Calendar.DAY_OF_MONTH, 1);
				Calendar tmp = Calendar.getInstance();
				tmp.setTime(cal.getTime());
				if (endDate.after(cal.getTime())) {
					lDate.add(tmp);
				} else {
					break;
				}
			}
			lDate.add(end); 
			for (Calendar element: lDate) {
				if(element.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
					calendar.add(element);
				}
				if(element.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
					calendar.add(element);
				}
			
			} 
			return calendar;
		}
}
