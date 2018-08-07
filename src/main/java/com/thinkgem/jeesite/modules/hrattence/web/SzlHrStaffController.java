/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hrattence.web;

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
import com.thinkgem.jeesite.modules.hrattence.entity.SzlHrStaff;
import com.thinkgem.jeesite.modules.hrattence.service.SzlHrStaffService;

/**
 * hrattenceController
 * @author fang
 * @version 2018-08-06
 */
@Controller
@RequestMapping(value = "${adminPath}/hrattence/szlHrStaff")
public class SzlHrStaffController extends BaseController {

	@Autowired
	private SzlHrStaffService szlHrStaffService;
	
	@ModelAttribute
	public SzlHrStaff get(@RequestParam(required=false) String id) {
		SzlHrStaff entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = szlHrStaffService.get(id);
		}
		if (entity == null){
			entity = new SzlHrStaff();
		}
		return entity;
	}
	
	@RequiresPermissions("hrattence:szlHrStaff:view")
	@RequestMapping(value = {"list", ""})
	public String list(SzlHrStaff szlHrStaff, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SzlHrStaff> page = szlHrStaffService.findPage(new Page<SzlHrStaff>(request, response), szlHrStaff); 
		model.addAttribute("page", page);
		return "modules/hrattence/szlHrStaffList";
	}

	@RequiresPermissions("hrattence:szlHrStaff:view")
	@RequestMapping(value = "form")
	public String form(SzlHrStaff szlHrStaff, Model model) {
		model.addAttribute("szlHrStaff", szlHrStaff);
		return "modules/hrattence/szlHrStaffForm";
	}

	@RequiresPermissions("hrattence:szlHrStaff:edit")
	@RequestMapping(value = "save")
	public String save(SzlHrStaff szlHrStaff, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, szlHrStaff)){
			return form(szlHrStaff, model);
		}
		szlHrStaffService.save(szlHrStaff);
		addMessage(redirectAttributes, "保存hrattence成功");
		return "redirect:"+Global.getAdminPath()+"/hrattence/szlHrStaff/?repage";
	}
	
	@RequiresPermissions("hrattence:szlHrStaff:edit")
	@RequestMapping(value = "delete")
	public String delete(SzlHrStaff szlHrStaff, RedirectAttributes redirectAttributes) {
		szlHrStaffService.delete(szlHrStaff);
		addMessage(redirectAttributes, "删除hrattence成功");
		return "redirect:"+Global.getAdminPath()+"/hrattence/szlHrStaff/?repage";
	}

}