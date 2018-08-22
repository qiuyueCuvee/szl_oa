/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.overtime.web;

import java.util.Date;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.holiday.dao.SzlHrHolidayDao;
import com.thinkgem.jeesite.modules.holiday.entity.SzlHrHoliday;
import com.thinkgem.jeesite.modules.holiday.service.SzlHrHolidayService;
import com.thinkgem.jeesite.modules.overtime.entity.SzlHrOvertime;
import com.thinkgem.jeesite.modules.overtime.service.SzlHrOvertimeService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * overtimeController
 * @author yue.qiu
 * @version 2018-08-08
 */
@Controller
@RequestMapping(value = "${adminPath}/overtime/szlHrOvertime")
public class SzlHrOvertimeController extends BaseController {

	@Autowired
	private SzlHrOvertimeService szlHrOvertimeService;
	@Autowired
	SzlHrHolidayDao szlHrHolidayDao;
	@Autowired
	SzlHrHolidayService szlHrHolidayService;
	
	
	@ModelAttribute
	public SzlHrOvertime get(@RequestParam(required=false) String id) {
		SzlHrOvertime entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = szlHrOvertimeService.get(id);
		}
		if (entity == null){
			entity = new SzlHrOvertime();
		}
		return entity;
	}
	
	@RequiresPermissions("overtime:szlHrOvertime:view")
	@RequestMapping(value = {"list", ""})
	public String list(SzlHrOvertime szlHrOvertime, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SzlHrOvertime> page = szlHrOvertimeService.findPage(new Page<SzlHrOvertime>(request, response), szlHrOvertime); 
		model.addAttribute("page", page);
		return "modules/overtime/szlHrOvertimeList";
	}
	
	@RequiresPermissions("overtime:szlHrOvertime:view")
	@RequestMapping(value = "check")
	public String check(SzlHrOvertime szlHrOvertime, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SzlHrOvertime> page = szlHrOvertimeService.findPage(new Page<SzlHrOvertime>(request, response), szlHrOvertime); 
		model.addAttribute("page", page);
		return "modules/overtime/szlHrOvertimeCheck";
	}

	@RequiresPermissions("overtime:szlHrOvertime:view")
	@RequestMapping(value = "form")
	public String form(SzlHrOvertime szlHrOvertime, Model model) {
		model.addAttribute("szlHrOvertime", szlHrOvertime);
		//加载用户工号
		String number = UserUtils.getUser().getNo();
		model.addAttribute("number", number);
		//今日日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String today=sdf.format(new Date());
		model.addAttribute("today", today);
		return "modules/overtime/szlHrOvertimeForm";
	}

	@RequiresPermissions("overtime:szlHrOvertime:edit")
	@RequestMapping(value = "save")
	public String save(SzlHrOvertime szlHrOvertime, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, szlHrOvertime)){
			return form(szlHrOvertime, model);
		}
		szlHrOvertime.setOvertimeStatus("1");		
		szlHrOvertime.setStatusReason("该加班单已经生成，等待领导审核！");	
		szlHrOvertimeService.save(szlHrOvertime);
		addMessage(redirectAttributes, "保存加班申请成功");
		return "redirect:"+Global.getAdminPath()+"/overtime/szlHrOvertime/?repage";
	}
	
	@RequiresPermissions("overtime:szlHrOvertime:check")
	@RequestMapping(value = "pass")
	public String pass(SzlHrOvertime szlHrOvertime, RedirectAttributes redirectAttributes) {
		szlHrOvertime.setOvertimeStatus("2");		
		szlHrOvertime.setStatusReason("该单据已经通过管理员审核！");		
		szlHrOvertimeService.update(szlHrOvertime);
		//添加信息到holiday表
		String number=szlHrOvertime.getNumber();
		String hours=szlHrOvertime.getWorkHours();
		String ifExist=szlHrHolidayDao.findByNumber(number);
		SzlHrHoliday szlHrHoliday=new SzlHrHoliday();
		szlHrHoliday.setNumber(number);
		//判断该员工信息是否存在
		if(ifExist==null || "".equals(ifExist)) {
			//不存在则添加
			//获取本人的userid
			String userid = szlHrHolidayDao.getUserId(number);
			User olduser=new User(userid);
			szlHrHoliday.setCreateBy(olduser);
			//默认年假1天
			szlHrHoliday.setAnnualLeave("1");
			szlHrHoliday.setShiftLeave(hours);
			szlHrHolidayService.save(szlHrHoliday);
		}else{
			//存在则更新
			String ShiftLeave=szlHrHolidayDao.findShiftLeaveNumber(number);
			int oldLeave=Integer.parseInt(ShiftLeave);
			int newLeave=Integer.parseInt(hours);
			int now=oldLeave+newLeave;
			String shiftLeave=now+"";
			szlHrHoliday.setShiftLeave(shiftLeave);
			szlHrHolidayDao.updateHoliday(szlHrHoliday);
		}
		addMessage(redirectAttributes, "通过加班单成功！");
		return "redirect:"+Global.getAdminPath()+"/overtime/szlHrOvertime/check/?repage";
	}
	
	@RequiresPermissions("overtime:szlHrOvertime:check")
	@RequestMapping(value = "goout")
	public String goout(SzlHrOvertime szlHrOvertime, Model model) {
		model.addAttribute("szlHrOvertime", szlHrOvertime);
		return "modules/overtime/szlHrOvertimeOut";
	}
	@RequiresPermissions("overtime:szlHrOvertime:check")
	@RequestMapping(value = "out")
	public String out(SzlHrOvertime szlHrOvertime, RedirectAttributes redirectAttributes) {
		szlHrOvertime.setOvertimeStatus("3");		
		szlHrOvertimeService.update(szlHrOvertime);
		addMessage(redirectAttributes, "驳回加班单成功！");
		return "redirect:"+Global.getAdminPath()+"/overtime/szlHrOvertime/check/?repage";
	}
	
	@RequiresPermissions("overtime:szlHrOvertime:edit")
	@RequestMapping(value = "delete")
	public String delete(SzlHrOvertime szlHrOvertime, RedirectAttributes redirectAttributes) {
		szlHrOvertimeService.delete(szlHrOvertime);
		addMessage(redirectAttributes, "删除加班申请成功");
		return "redirect:"+Global.getAdminPath()+"/overtime/szlHrOvertime/?repage";
	}

}