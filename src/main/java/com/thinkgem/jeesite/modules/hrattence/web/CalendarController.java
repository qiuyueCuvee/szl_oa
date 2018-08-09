package com.thinkgem.jeesite.modules.hrattence.web;

import java.util.Calendar;
import java.util.HashMap;
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

@Controller
@RequestMapping(value = "${adminPath}/hrattence/calendar")
public class CalendarController extends BaseController{

	@Autowired
	private SzlHrStaffDao staffdao;
	
	@Autowired
	private SzlHrAttenceService attenceService;
	
	@RequestMapping(value = "list")
	public String list(SzlHrAttence szlHrAttence,HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SzlHrAttence> result= attenceService.findAllPage(new Page<SzlHrAttence>(request, response), szlHrAttence);
		List<SzlHrAttence> list = result.getList();
		Map calendarMap = new HashMap();
		for(SzlHrAttence entity:list) {
			SzlHrStaff hhrStaff = new SzlHrStaff();
			hhrStaff.setNumber(entity.getNumber());
			SzlHrStaff staff =  null;
			if(entity.getNumber()!=null) {
				staff = staffdao.findByNumber(entity.getNumber().toString());
			}
			
			if(staff!=null) {
				entity.setHrStaffName(staff.getName());
			}
		}
		
		StringBuffer html = new StringBuffer();
		html.append("<tr>\r\n");
//		html.append("<th>编号</th>\r\n");
		html.append("<th>姓名</th>");
		Calendar c = Calendar.getInstance();
//		int month = c.get(Calendar.MONTH);
		c.add(Calendar.MONTH, -1);
		
		int maxCols = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		
		for(int col = 26; col <= maxCols; col++) {
			html.append("<th>");
			html.append(col);
			html.append("</th>");
		}
		for(int col = 1; col <= 25; col++) {
			html.append("<th>");
			html.append(col);
			html.append("</th>");
		}
		html.append("<th>加班总数/小时</th>");
		html.append("<th>倒休(天)</th>");
		html.append("<th>扣薪假(天)</th>");
		html.append("<th>实际出勤天数</th>");
		html.append("<th>签字确认</th>");
		html.append("</tr>\r\n");
		
		
		szlHrAttence.setHtml(html.toString());
//		szlHrAttence.setCalendarMap(null);
//		model.addAttribute("szlHrAttence", szlHrAttence);
		model.addAttribute("page", result);
		return "modules/hrattence/calendarList";
		
	}
}
