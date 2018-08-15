/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.leave.web;

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
import com.thinkgem.jeesite.modules.leave.entity.SzlHrLeave;
import com.thinkgem.jeesite.modules.leave.service.SzlHrLeaveService;
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
		szlHrLeave.setLeaveStatus("1");		
		szlHrLeave.setStatusReason("该请假单已经生成，等待领导审核！");	
		szlHrLeaveService.save(szlHrLeave);
		addMessage(redirectAttributes, "保存请假信息成功");
		return "redirect:"+Global.getAdminPath()+"/leave/szlHrLeave/?repage";
	}
	
	@RequiresPermissions("leave:szlHrLeave:check")
	@RequestMapping(value = "pass")
	public String pass(SzlHrLeave szlHrLeave, RedirectAttributes redirectAttributes) {
		szlHrLeave.setLeaveStatus("2");		
		szlHrLeave.setStatusReason("该单据已经通过管理员审核！");		
		szlHrLeaveService.update(szlHrLeave);
		addMessage(redirectAttributes, "通过请假单成功！");
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