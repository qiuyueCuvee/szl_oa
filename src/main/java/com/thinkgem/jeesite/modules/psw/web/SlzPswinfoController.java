/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.psw.web;

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
import com.thinkgem.jeesite.modules.psw.entity.SlzPswinfo;
import com.thinkgem.jeesite.modules.psw.service.SlzPswinfoService;

/**
 * 密码管理Controller
 * @author yue.qiu
 * @version 2018-08-06
 */
@Controller
@RequestMapping(value = "${adminPath}/psw/slzPswinfo")
public class SlzPswinfoController extends BaseController {

	@Autowired
	private SlzPswinfoService slzPswinfoService;
	
	@ModelAttribute
	public SlzPswinfo get(@RequestParam(required=false) String id) {
		SlzPswinfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = slzPswinfoService.get(id);
		}
		if (entity == null){
			entity = new SlzPswinfo();
		}
		return entity;
	}
	
	@RequiresPermissions("psw:slzPswinfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(SlzPswinfo slzPswinfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SlzPswinfo> page = slzPswinfoService.findPage(new Page<SlzPswinfo>(request, response), slzPswinfo); 
		model.addAttribute("page", page);
		return "modules/psw/slzPswinfoList";
	}

	@RequiresPermissions("psw:slzPswinfo:view")
	@RequestMapping(value = "form")
	public String form(SlzPswinfo slzPswinfo, Model model) {
		model.addAttribute("slzPswinfo", slzPswinfo);
		return "modules/psw/slzPswinfoForm";
	}

	@RequiresPermissions("psw:slzPswinfo:edit")
	@RequestMapping(value = "save")
	public String save(SlzPswinfo slzPswinfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, slzPswinfo)){
			return form(slzPswinfo, model);
		}
		slzPswinfoService.save(slzPswinfo);
		addMessage(redirectAttributes, "保存密码成功");
		return "redirect:"+Global.getAdminPath()+"/psw/slzPswinfo/?repage";
	}
	
	@RequiresPermissions("psw:slzPswinfo:edit")
	@RequestMapping(value = "delete")
	public String delete(SlzPswinfo slzPswinfo, RedirectAttributes redirectAttributes) {
		slzPswinfoService.delete(slzPswinfo);
		addMessage(redirectAttributes, "删除密码成功");
		return "redirect:"+Global.getAdminPath()+"/psw/slzPswinfo/?repage";
	}

}