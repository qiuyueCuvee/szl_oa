/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.assettype.web;

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
import com.thinkgem.jeesite.modules.assetinfo.entity.SzlAssetinfo;
import com.thinkgem.jeesite.modules.assetinfo.service.SzlAssetinfoService;
import com.thinkgem.jeesite.modules.assettype.entity.SzlAssettype;
import com.thinkgem.jeesite.modules.assettype.service.SzlAssettypeService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * assettypeController
 * @author fang
 * @version 2018-08-24
 */
@Controller
@RequestMapping(value = "${adminPath}/assettype/szlAssettype")
public class SzlAssettypeController extends BaseController {

	@Autowired
	private SzlAssettypeService szlAssettypeService;
	
	@Autowired
	private SzlAssetinfoService assetinfoService;
	
	@ModelAttribute
	public SzlAssettype get(@RequestParam(required=false) String id) {
		SzlAssettype entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = szlAssettypeService.get(id);
		}
		if (entity == null){
			entity = new SzlAssettype();
		}
		return entity;
	}
	
	@RequiresPermissions("assettype:szlAssettype:view")
	@RequestMapping(value = {"list", ""})
	public String list(SzlAssettype szlAssettype, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SzlAssettype> page = szlAssettypeService.findPage(new Page<SzlAssettype>(request, response), szlAssettype); 
		model.addAttribute("page", page);
		return "modules/assettype/szlAssettypeList";
	}

	@RequiresPermissions("assettype:szlAssettype:view")
	@RequestMapping(value = "form")
	public String form(SzlAssettype szlAssettype, Model model) {
		model.addAttribute("szlAssettype", szlAssettype);
		return "modules/assettype/szlAssettypeForm";
	}

	/**
	 * 保存assettype
	 */
	@RequestMapping(value = "save")
	public String save(SzlAssettype assettype,HttpServletRequest request, SzlAssetinfo assetinfo,RedirectAttributes redirectAttributes) {
		String json = request.getParameter("jsonstring");
		assetinfo.setInfo(json);
		assetinfo.setUid(UserUtils.getUser().getName());
		assetinfo.setState("在用");
		assetinfoService.save(assetinfo);
		addMessage(redirectAttributes, "保存资产管理信息成功");
		return "redirect:"+Global.getAdminPath()+"/assetinfo/szlAssetinfo/?repage";
	}
	
	@RequiresPermissions("assettype:szlAssettype:edit")
	@RequestMapping(value = "delete")
	public String delete(SzlAssettype szlAssettype, RedirectAttributes redirectAttributes) {
		szlAssettypeService.delete(szlAssettype);
		addMessage(redirectAttributes, "删除assettype成功");
		return "redirect:"+Global.getAdminPath()+"/assettype/szlAssettype/?repage";
	}

}