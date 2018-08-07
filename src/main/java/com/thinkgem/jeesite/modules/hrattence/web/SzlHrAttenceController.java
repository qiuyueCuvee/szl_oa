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
import com.thinkgem.jeesite.modules.hrattence.entity.SzlHrAttence;
import com.thinkgem.jeesite.modules.hrattence.service.SzlHrAttenceService;

/**
 * hrattenceController
 * @author fang
 * @version 2018-08-06
 */
@Controller
@RequestMapping(value = "${adminPath}/hrattence/szlHrAttence")
public class SzlHrAttenceController extends BaseController {

	@Autowired
	private SzlHrAttenceService szlHrAttenceService;
	
	@ModelAttribute
	public SzlHrAttence get(@RequestParam(required=false) String id) {
		SzlHrAttence entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = szlHrAttenceService.get(id);
		}
		if (entity == null){
			entity = new SzlHrAttence();
		}
		return entity;
	}
	
	@RequiresPermissions("hrattence:szlHrAttence:view")
	@RequestMapping(value = {"list", ""})
	public String list(SzlHrAttence szlHrAttence, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SzlHrAttence> page = szlHrAttenceService.findPage(new Page<SzlHrAttence>(request, response), szlHrAttence); 
		model.addAttribute("page", page);
		return "modules/hrattence/szlHrAttenceList";
	}

	@RequiresPermissions("hrattence:szlHrAttence:view")
	@RequestMapping(value = "form")
	public String form(SzlHrAttence szlHrAttence, Model model) {
		model.addAttribute("szlHrAttence", szlHrAttence);
		return "modules/hrattence/szlHrAttenceForm";
	}

	@RequiresPermissions("hrattence:szlHrAttence:edit")
	@RequestMapping(value = "save")
	public String save(SzlHrAttence szlHrAttence, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, szlHrAttence)){
			return form(szlHrAttence, model);
		}
		szlHrAttenceService.save(szlHrAttence);
		addMessage(redirectAttributes, "保存hrattence成功");
		return "redirect:"+Global.getAdminPath()+"/hrattence/szlHrAttence/?repage";
	}
	
	@RequiresPermissions("hrattence:szlHrAttence:edit")
	@RequestMapping(value = "delete")
	public String delete(SzlHrAttence szlHrAttence, RedirectAttributes redirectAttributes) {
		szlHrAttenceService.delete(szlHrAttence);
		addMessage(redirectAttributes, "删除hrattence成功");
		return "redirect:"+Global.getAdminPath()+"/hrattence/szlHrAttence/?repage";
	}

}