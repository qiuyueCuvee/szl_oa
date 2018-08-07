/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.declare.web;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.thinkgem.jeesite.modules.declare.entity.SlzDeclareinfo;
import com.thinkgem.jeesite.modules.declare.service.SlzDeclareinfoService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * declareController
 * @author yue.qiu
 * @version 2018-08-06
 */
@Controller
@RequestMapping(value = "${adminPath}/declare/slzDeclareinfo")
public class SlzDeclareinfoController extends BaseController {

	@Autowired
	private SlzDeclareinfoService slzDeclareinfoService;
	
	@ModelAttribute
	public SlzDeclareinfo get(@RequestParam(required=false) String id) {
		SlzDeclareinfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = slzDeclareinfoService.get(id);
		}
		if (entity == null){
			entity = new SlzDeclareinfo();
		}
		return entity;
	}
	
	@RequiresPermissions("declare:slzDeclareinfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(SlzDeclareinfo slzDeclareinfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SlzDeclareinfo> page = slzDeclareinfoService.findPage(new Page<SlzDeclareinfo>(request, response), slzDeclareinfo); 
		model.addAttribute("page", page);
		return "modules/declare/slzDeclareinfoList";
	}
	
	@RequiresPermissions("declare:slzDeclareinfo:view")
	@RequestMapping(value = {"check", ""})
	public String check(SlzDeclareinfo slzDeclareinfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SlzDeclareinfo> page = slzDeclareinfoService.findPage(new Page<SlzDeclareinfo>(request, response), slzDeclareinfo); 
		model.addAttribute("page", page);
		return "modules/declare/slzDeclareinfoCheck";
	}
	
	@RequiresPermissions("declare:slzDeclareinfo:view")
	@RequestMapping(value = "form")
	public String form(SlzDeclareinfo slzDeclareinfo, Model model) {
		model.addAttribute("slzDeclareinfo", slzDeclareinfo);
		return "modules/declare/slzDeclareinfoForm";
	}
	/**
	 * 保存申报单
	 */
	@RequiresPermissions("declare:slzDeclareinfo:edit")
	@RequestMapping(value = "save")
	public String save(SlzDeclareinfo slzDeclareinfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, slzDeclareinfo)){
			return form(slzDeclareinfo, model);
		}
		
		User user=UserUtils.getUser();
		slzDeclareinfo.setUser(user);		
		slzDeclareinfo.setDeclareStatus("1");		
		slzDeclareinfo.setStatusReason("该单据已经生成，等待管理员审核！");	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		String declareTime=sdf.format(new Date());
		slzDeclareinfo.setDeclareTime(declareTime);
		slzDeclareinfoService.save(slzDeclareinfo);
		addMessage(redirectAttributes, "保存申报信息成功");
		return "redirect:"+Global.getAdminPath()+"/declare/slzDeclareinfo/?repage";
	}
	
	@RequiresPermissions("declare:slzDeclareinfo:check")
	@RequestMapping(value = "pass")
	public String pass(SlzDeclareinfo slzDeclareinfo, RedirectAttributes redirectAttributes) {
		slzDeclareinfo.setDeclareStatus("2");		
		slzDeclareinfo.setStatusReason("该单据已经通过管理员审核！");		
		slzDeclareinfoService.update(slzDeclareinfo);
		addMessage(redirectAttributes, "通过申报信息成功！");
		return "redirect:"+Global.getAdminPath()+"/declare/slzDeclareinfo/?repage";
	}
	
	@RequiresPermissions("declare:slzDeclareinfo:check")
	@RequestMapping(value = "goout")
	public String goout(SlzDeclareinfo slzDeclareinfo, Model model) {
		model.addAttribute("slzDeclareinfo", slzDeclareinfo);
		return "modules/declare/slzDeclareinfoOut";
	}
	@RequiresPermissions("declare:slzDeclareinfo:check")
	@RequestMapping(value = "out")
	public String out(SlzDeclareinfo slzDeclareinfo, RedirectAttributes redirectAttributes) {
		slzDeclareinfo.setDeclareStatus("3");		
		slzDeclareinfoService.update(slzDeclareinfo);
		addMessage(redirectAttributes, "驳回申报信息成功！");
		return "redirect:"+Global.getAdminPath()+"/declare/slzDeclareinfo/?repage";
	}
	
	@RequiresPermissions("declare:slzDeclareinfo:edit")
	@RequestMapping(value = "delete")
	public String delete(SlzDeclareinfo slzDeclareinfo, RedirectAttributes redirectAttributes) {
		slzDeclareinfoService.delete(slzDeclareinfo);
		addMessage(redirectAttributes, "删除申报信息成功");
		return "redirect:"+Global.getAdminPath()+"/declare/slzDeclareinfo/?repage";
	}

}