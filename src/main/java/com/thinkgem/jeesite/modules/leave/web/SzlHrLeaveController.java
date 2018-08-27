/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.leave.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.thinkgem.jeesite.modules.leave.entity.SzlHrLeave;
import com.thinkgem.jeesite.modules.leave.service.SzlHrLeaveService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * leaveController
 * @author yue.qiu
 * @version 2018-08-15
 */
@Controller
@RequestMapping(value = "${adminPath}/leave/szlHrLeave")
public class SzlHrLeaveController extends BaseController {

	@Autowired
	private SzlHrLeaveService szlHrLeaveService;
	@Autowired
	SzlHrHolidayDao szlHrHolidayDao;
	@Autowired
	SzlHrHolidayService szlHrHolidayService;
	
	@ModelAttribute
	public SzlHrLeave get(@RequestParam(required=false) String id) {
		SzlHrLeave entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = szlHrLeaveService.get(id);
		}
		if (entity == null){
			entity = new SzlHrLeave();
		}
		return entity;
	}
	
	@RequiresPermissions("leave:szlHrLeave:view")
	@RequestMapping(value = {"list", ""})
	public String list(SzlHrLeave szlHrLeave, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SzlHrLeave> page = szlHrLeaveService.findPage(new Page<SzlHrLeave>(request, response), szlHrLeave); 
		//普通用户只显示该用户创建的条目
		User usr = szlHrLeave.getCurrentUser();
		String roleName = usr.getRoleNames();
		if (roleName.equals("普通用户")){//普通用户只显示该用户创建的条目
			List<SzlHrLeave> list = page.getList();
			List<SzlHrLeave> reslist =new ArrayList<SzlHrLeave>();
			for(SzlHrLeave obj:list) {
				if(usr.getLoginName().equals(obj.getCreateBy().getId())) {
					reslist.add(obj);
				}	
			}
			page.setList(reslist);
		}
		model.addAttribute("page", page);
		return "modules/leave/szlHrLeaveList";
	}
	
	@RequiresPermissions("leave:szlHrLeave:view")
	@RequestMapping(value = "check")
	public String check(SzlHrLeave szlHrLeave, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SzlHrLeave> page = szlHrLeaveService.findPage(new Page<SzlHrLeave>(request, response), szlHrLeave); 
		model.addAttribute("page", page);
		return "modules/leave/szlHrLeaveCheck";
	}
	
	@RequiresPermissions("leave:szlHrLeave:view")
	@RequestMapping(value = "form")
	public String form(SzlHrLeave szlHrLeave, Model model) {
		model.addAttribute("szlHrLeave", szlHrLeave);
		//加载用户工号
		String number = UserUtils.getUser().getNo();
		model.addAttribute("number", number);
		//今日日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String today=sdf.format(new Date());
		model.addAttribute("today", today);
		return "modules/leave/szlHrLeaveForm";
	}
	
	@RequiresPermissions("leave:szlHrLeave:edit")
	@RequestMapping(value = "save")
	public String save(SzlHrLeave szlHrLeave, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, szlHrLeave)){
			return form(szlHrLeave, model);
		}
		String number=szlHrLeave.getNumber();
		String leaveHours1=szlHrLeave.getLeaveHours();
		String type=szlHrLeave.getLeaveType();
		String h1=szlHrHolidayDao.findShiftLeaveNumber(number);
		String h2=szlHrHolidayDao.findAnnualLeaveNumber(number);
		int shiftHours=0;
		int yearHours=0;
		int leaveHours=0;
		if(!"".equals(h1) && h1!=null) {
			shiftHours=Integer.parseInt(h1);//剩余倒休时长
		}
		if(!"".equals(h2) && h2!=null) {
			yearHours=Integer.parseInt(h2);//剩余年假时长
		}
		if(!"".equals(leaveHours1) && leaveHours1!=null) {
			leaveHours=Integer.parseInt(leaveHours1);//请假时长
		}
		if("1".equals(type)){
			//倒休
			if(leaveHours<=shiftHours) {
				szlHrLeave.setLeaveStatus("1");	
				szlHrLeave.setStatusReason("该请假单已经生成，等待领导审核！");
				szlHrLeaveService.save(szlHrLeave);
				addMessage(redirectAttributes, "保存请假信息(倒休)成功");
			}else if(leaveHours>shiftHours){
				addMessage(redirectAttributes, "保存请假信息(倒休)失败,剩余倒休时长不足，剩余时长为："+shiftHours+"小时！");
			}else {
				addMessage(redirectAttributes, "保存请假信息(倒休)失败！");
			}
		}else if("2".equals(type)) {
			//年假
			if(leaveHours<=yearHours) {
				szlHrLeave.setLeaveStatus("1");	
				szlHrLeave.setStatusReason("该请假单已经生成，等待领导审核！");
				szlHrLeaveService.save(szlHrLeave);
				addMessage(redirectAttributes, "保存请假信息(年假)成功");
			}else if(leaveHours>yearHours){
				addMessage(redirectAttributes, "保存请假信息(年假)失败,剩余年假时长不足，剩余时长为："+yearHours+"小时！");
			}else {
				addMessage(redirectAttributes, "保存请假信息(年假)失败！");
			}
		}else {
			szlHrLeave.setLeaveStatus("1");	
			szlHrLeave.setStatusReason("该请假单已经生成，等待领导审核！");
			szlHrLeaveService.save(szlHrLeave);
			addMessage(redirectAttributes, "保存请假信息成功");
		}
		return "redirect:"+Global.getAdminPath()+"/leave/szlHrLeave/?repage";
	}
	
	@RequiresPermissions("leave:szlHrLeave:check")
	@RequestMapping(value = "pass")
	public String pass(SzlHrLeave szlHrLeave, RedirectAttributes redirectAttributes) {
		String number=szlHrLeave.getNumber();
		String leaveHours1=szlHrLeave.getLeaveHours();
		String type=szlHrLeave.getLeaveType();
		String h1=szlHrHolidayDao.findShiftLeaveNumber(number);
		String h2=szlHrHolidayDao.findAnnualLeaveNumber(number);
		int shiftHours=0;
		int yearHours=0;
		int leaveHours=0;
		if(!"".equals(h1) && h1!=null) {
			shiftHours=Integer.parseInt(h1);//剩余倒休时长
		}
		if(!"".equals(h2) && h2!=null) {
			yearHours=Integer.parseInt(h2);//剩余年假时长
		}
		if(!"".equals(leaveHours1) && leaveHours1!=null) {
			leaveHours=Integer.parseInt(leaveHours1);//请假时长
		}
		//holiday扣去相应的小时
		SzlHrHoliday szlHrHoliday=new SzlHrHoliday();
		szlHrHoliday.setNumber(number);
		if("1".equals(type)){
			if(leaveHours<=shiftHours) {
				int now=shiftHours-leaveHours;
				String s = now+"";
				szlHrHoliday.setShiftLeave(s);
				szlHrHolidayDao.updateShift(szlHrHoliday);
				szlHrLeave.setLeaveStatus("2");		
				szlHrLeave.setStatusReason("该单据已经通过管理员审核！");		
				szlHrLeaveService.update(szlHrLeave);
				addMessage(redirectAttributes, "通过请假单(倒休)成功！");
			}else {
				addMessage(redirectAttributes, "该员工的剩余倒休时长不足，剩余："+shiftHours+"小时！");
			}
		}else if("2".equals(type)){
			if(leaveHours<=yearHours) {
				int now=yearHours-leaveHours;
				String a = now+"";
				szlHrHoliday.setAnnualLeave(a);
				szlHrHolidayDao.updateAnnual(szlHrHoliday);
				szlHrLeave.setLeaveStatus("2");		
				szlHrLeave.setStatusReason("该单据已经通过管理员审核！");		
				szlHrLeaveService.update(szlHrLeave);
				addMessage(redirectAttributes, "通过请假单(年假)成功！");
			}else {
				addMessage(redirectAttributes, "该员工的剩余年假时长不足，剩余："+yearHours+"小时！");
			}
		}else {
			szlHrLeave.setLeaveStatus("2");		
			szlHrLeave.setStatusReason("该单据已经通过管理员审核！");		
			szlHrLeaveService.update(szlHrLeave);
			addMessage(redirectAttributes, "通过请假单成功！");
		}
		return "redirect:"+Global.getAdminPath()+"/leave/szlHrLeave/check/?repage";
	}
	
	@RequiresPermissions("leave:szlHrLeave:check")
	@RequestMapping(value = "goout")
	public String goout(SzlHrLeave szlHrLeave, Model model) {
		model.addAttribute("szlHrLeave", szlHrLeave);
		return "modules/leave/szlHrLeaveOut";
	}
	@RequiresPermissions("leave:szlHrLeave:check")
	@RequestMapping(value = "out")
	public String out(SzlHrLeave szlHrLeave, RedirectAttributes redirectAttributes) {
		szlHrLeave.setLeaveStatus("3");		
		szlHrLeaveService.update(szlHrLeave);
		addMessage(redirectAttributes, "驳回请假单成功！");
		return "redirect:"+Global.getAdminPath()+"/leave/szlHrLeave/check/?repage";
	}
	
	@RequiresPermissions("leave:szlHrLeave:edit")
	@RequestMapping(value = "delete")
	public String delete(SzlHrLeave szlHrLeave, RedirectAttributes redirectAttributes) {
		szlHrLeaveService.delete(szlHrLeave);
		addMessage(redirectAttributes, "删除请假信息成功");
		return "redirect:"+Global.getAdminPath()+"/leave/szlHrLeave/?repage";
	}


}