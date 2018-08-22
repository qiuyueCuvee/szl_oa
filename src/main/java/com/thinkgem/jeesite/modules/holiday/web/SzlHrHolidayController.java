/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.holiday.web;

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
import com.thinkgem.jeesite.modules.holiday.entity.SzlHrHoliday;
import com.thinkgem.jeesite.modules.holiday.service.SzlHrHolidayService;

/**
 * holidayController
 * @author yue.qiu
 * @version 2018-08-15
 */
@Controller
@RequestMapping(value = "${adminPath}/holiday/szlHrHoliday")
public class SzlHrHolidayController extends BaseController {

	@Autowired
	private SzlHrHolidayService szlHrHolidayService;
	
	@ModelAttribute
	public SzlHrHoliday get(@RequestParam(required=false) String id) {
		SzlHrHoliday entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = szlHrHolidayService.get(id);
		}
		if (entity == null){
			entity = new SzlHrHoliday();
		}
		return entity;
	}
	
	@RequiresPermissions("holiday:szlHrHoliday:view")
	@RequestMapping(value = {"list", ""})
	public String list(SzlHrHoliday szlHrHoliday, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SzlHrHoliday> page = szlHrHolidayService.findPage(new Page<SzlHrHoliday>(request, response), szlHrHoliday); 
		model.addAttribute("page", page);
		return "modules/holiday/szlHrHolidayList";
	}

	/*@RequiresPermissions("holiday:szlHrHoliday:view")
	@RequestMapping(value = "form")
	public String form(SzlHrHoliday szlHrHoliday, Model model) {
		model.addAttribute("szlHrHoliday", szlHrHoliday);
		return "modules/holiday/szlHrHolidayForm";
	}

	@RequiresPermissions("holiday:szlHrHoliday:edit")
	@RequestMapping(value = "save")
	public String save(SzlHrHoliday szlHrHoliday, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, szlHrHoliday)){
			return form(szlHrHoliday, model);
		}
		szlHrHolidayService.save(szlHrHoliday);
		addMessage(redirectAttributes, "保存休假信息成功");
		return "redirect:"+Global.getAdminPath()+"/holiday/szlHrHoliday/?repage";
	}
	
	@RequiresPermissions("holiday:szlHrHoliday:edit")
	@RequestMapping(value = "delete")
	public String delete(SzlHrHoliday szlHrHoliday, RedirectAttributes redirectAttributes) {
		szlHrHolidayService.delete(szlHrHoliday);
		addMessage(redirectAttributes, "删除休假信息成功");
		return "redirect:"+Global.getAdminPath()+"/holiday/szlHrHoliday/?repage";
	}*/

}