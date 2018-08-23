package com.thinkgem.jeesite.modules.hrattence.web;

import java.io.FileOutputStream;
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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.holiday.dao.SzlHrHolidayDao;
import com.thinkgem.jeesite.modules.hrattence.dao.SzlHrStaffDao;
import com.thinkgem.jeesite.modules.hrattence.entity.SzlHrAttence;
import com.thinkgem.jeesite.modules.hrattence.entity.SzlHrStaff;
import com.thinkgem.jeesite.modules.hrattence.service.SzlHrAttenceService;
import com.thinkgem.jeesite.modules.leave.entity.SzlHrLeave;
import com.thinkgem.jeesite.modules.leave.service.SzlHrLeaveService;
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
	
	@Autowired
	private SzlHrHolidayDao szlHrHolidayDaos;
	
	@Autowired
	private SzlHrLeaveService szlHrLeaveService;
	
	@RequestMapping(value = "list")
	public String list(SzlHrAttence szlHrAttence,HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
		Page<SzlHrAttence> result=  new Page<SzlHrAttence>(request, response) ;
		SzlHrStaff paramstaff = new SzlHrStaff();
		paramstaff.setNumber(szlHrAttence.getNumber());
		
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
			String shiftLeave = szlHrHolidayDaos.findShiftLeaveNumber(key.toString());
			if(shiftLeave==null) {
				shiftLeave = "0";
			}
			html.append("<td>");
			html.append(shiftLeave);
			html.append("</td>");
			
			SzlHrLeave szlHrLeave = new SzlHrLeave();
			szlHrLeave.setNumber(key.toString());
			szlHrLeave.setBegindate(begindate);
			szlHrLeave.setEnddate(enddate);
			List<SzlHrLeave> leavelist = szlHrLeaveService.findAllMonthList(szlHrLeave);
			long leavehour = 0l;
			for(SzlHrLeave element:leavelist) {
				leavehour +=Long.valueOf(element.getLeaveHours());
			}
			html.append("<td>");
			html.append(leavehour/8);
			html.append("</td>");
			long workdays = maxCols-calendar.size()-leavehour/8;
			html.append("<td>");
			html.append(workdays);
			html.append("</td>");
			html.append("<td>");
			html.append("");
			html.append("</td>");
			html.append("</tr>");
			}
			
			
		}
		
		szlHrAttence.setHtml(html.toString());
		model.addAttribute("page", result);
		return "modules/hrattence/calendarList";
	}
	
	@RequestMapping(value = "export", method=RequestMethod.POST)
	public String export(SzlHrAttence szlHrAttence,HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) throws ParseException {
		try {
            String fileName = "深之蓝考勤表"+DateUtils.getDate("yyyyMMddHHmmss")+".xls";
            Calendar start = Calendar.getInstance();
    		start.add(Calendar.MONTH, -3);
    		start.set(Calendar.DAY_OF_MONTH, 26);
    		int maxCols = start.getActualMaximum(Calendar.DAY_OF_MONTH);
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("考勤");   
            HSSFRow row;
            HSSFCell cell;
            SzlHrStaff paramstaff = new SzlHrStaff();
    		paramstaff.setNumber(szlHrAttence.getNumber());
            List<HashMap> list = attenceService.findMonth(new Page<SzlHrAttence>(request, response), szlHrAttence,paramstaff,request);
            row = sheet.createRow(0); 
            for(int j = 0; j < maxCols+6; j++) {
            		
            		cell = row.createCell(j);
            		 if(j==0) {
            			 cell.setCellValue("姓名");
            		 }
            		 else if(j==maxCols+1) {
            			 cell.setCellValue("加班总数/小时");
            		 }
            		 else  if(j==maxCols+2) {
            			 cell.setCellValue("倒休/(天)");
            		 }
            		 else  if(j==maxCols+3) {
            			 cell.setCellValue("扣薪假/(天)");
            		 }
            		 else  if(j==maxCols+4) {
            			 cell.setCellValue("实际出勤天数");
            		 }
            		 else  if(j==maxCols+5) {
            			 cell.setCellValue("签字确认");
            		 }else {
            			 if(j>0&&j<=maxCols-25) {
            				 cell.setCellValue(j+25);
            			 }
            			 if(j>maxCols-25) {
            				 cell.setCellValue(j-maxCols+25);
            			 }
            		 }
            }
            for(int i = 0; i < list.size(); i++) {
            		row = sheet.createRow(i+1); 
              
        			Iterator iter = list.get(i).entrySet().iterator();
        			while (iter.hasNext()) {
        			Map.Entry entry = (Map.Entry) iter.next();
        			Object key = entry.getKey();
        			SzlHrStaff tmpstaff   = staffdao.findByNumber(key.toString());
        			cell = row.createCell(0);
          			cell.setCellValue(tmpstaff.getName());
        			List<SzlHrAttence> val = (List<SzlHrAttence>)entry.getValue();
        			for(int j = 0; j < val.size(); j++) {
        				 cell = row.createCell(j+1);
        				 cell.setCellValue(val.get(j).getStatus());
        			}
        			SzlHrOvertime szlHrOvertime = new SzlHrOvertime();
        			szlHrOvertime.setNumber(key.toString());
        			List<SzlHrOvertime> timelist = overtimeService.findList(szlHrOvertime);
        			long hours =0l;
        			for(SzlHrOvertime element:timelist) {
        				hours += Long.valueOf(element.getWorkHours());
        			}
        			cell = row.createCell(maxCols+1);
          			cell.setCellValue(hours);
	    		}
	            
	        }
            wb.write(new FileOutputStream("d:/"+fileName));
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出考勤失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/hrattence/calendar/list";
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
