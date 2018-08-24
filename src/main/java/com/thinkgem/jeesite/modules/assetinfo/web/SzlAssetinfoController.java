/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.assetinfo.web;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.assetinfo.entity.SzlAssetinfo;
import com.thinkgem.jeesite.modules.assetinfo.service.SzlAssetinfoService;

/**
 * assetinfoController
 * @author fang
 * @version 2018-08-24
 */
@Controller
@RequestMapping(value = "${adminPath}/assetinfo/szlAssetinfo")
public class SzlAssetinfoController extends BaseController {

	@Autowired
	private SzlAssetinfoService szlAssetinfoService;
	
	@ModelAttribute
	public SzlAssetinfo get(@RequestParam(required=false) String id) {
		SzlAssetinfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = szlAssetinfoService.get(id);
		}
		if (entity == null){
			entity = new SzlAssetinfo();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(SzlAssetinfo szlAssetinfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SzlAssetinfo> page = szlAssetinfoService.findPage(new Page<SzlAssetinfo>(request, response), szlAssetinfo); 
		model.addAttribute("page", page);
		return "modules/assetinfo/szlAssetinfoList";
	}

	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SzlAssetinfo> listData(SzlAssetinfo assetinfo, HttpServletRequest request, HttpServletResponse response) {
		Page<SzlAssetinfo> page = szlAssetinfoService.findPage(new Page<SzlAssetinfo>(request, response), assetinfo); 
		List<SzlAssetinfo> list= page.getList();
		for(SzlAssetinfo  obj: list) {
			
		String json = obj.getInfo();
		StringBuffer str = new StringBuffer();
		 ObjectMapper objectMapper = new ObjectMapper();
		 if(json!=null&&json.length()>0) {
			json = json.substring(1, json.length()-1);
			try {
				Map<String, Map<String, Object>> maps = objectMapper.readValue(json, Map.class);
				Set<String> key = maps.keySet();
				Iterator<String> iter = key.iterator();
				while (iter.hasNext()) {
					String field = iter.next();
					str.append("●");
					str.append(field+":");
					str.append(maps.get(field));
					str.append(" ");
					str.append("<br/>");	
					System.out.println(field + ":" + maps.get(field));
				}
				obj.setInfo(str.toString());
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
		}
		return page;
	}
	
	@RequiresPermissions("assetinfo:szlAssetinfo:view")
	@RequestMapping(value = "form")
	public String form(SzlAssetinfo szlAssetinfo, Model model) {
		model.addAttribute("szlAssetinfo", szlAssetinfo);
		return "modules/assetinfo/szlAssetinfoForm";
	}

	@RequiresPermissions("assetinfo:szlAssetinfo:edit")
	@RequestMapping(value = "save")
	public String save(SzlAssetinfo szlAssetinfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, szlAssetinfo)){
			return form(szlAssetinfo, model);
		}
		szlAssetinfoService.save(szlAssetinfo);
		addMessage(redirectAttributes, "保存assetinfo成功");
		return "redirect:"+Global.getAdminPath()+"/assetinfo/szlAssetinfo/?repage";
	}
	
	@RequiresPermissions("assetinfo:szlAssetinfo:edit")
	@RequestMapping(value = "delete")
	public String delete(SzlAssetinfo szlAssetinfo, RedirectAttributes redirectAttributes) {
		szlAssetinfoService.delete(szlAssetinfo);
		addMessage(redirectAttributes, "删除assetinfo成功");
		return "redirect:"+Global.getAdminPath()+"/assetinfo/szlAssetinfo/?repage";
	}

}