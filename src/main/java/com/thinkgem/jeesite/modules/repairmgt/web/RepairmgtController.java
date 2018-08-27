/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.repairmgt.web;

import java.util.ArrayList;
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

import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.repairmgt.entity.Repairmgt;
import com.thinkgem.jeesite.modules.repairmgt.service.RepairmgtService;


/**
 * 报修管理Controller
 * @author WHX
 * @version 2018-08-07
 */
@Controller
@RequestMapping(value = "${adminPath}/repairmgt/repairmgt")
public class RepairmgtController extends BaseController {

	@Autowired
	private RepairmgtService repairmgtService;
	
	@ModelAttribute
	public Repairmgt get(@RequestParam(required=false) String id) {
		Repairmgt entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = repairmgtService.get(id);
		}
		if (entity == null){
			entity = new Repairmgt();
		}
		return entity;
	}
	
	@RequiresPermissions("repairmgt:repairmgt:view")
	@RequestMapping(value = {"list", ""})
	public String list(Repairmgt repairmgt, HttpServletRequest request, HttpServletResponse response, Model model) {
		User usr = repairmgt.getCurrentUser();
		Page<Repairmgt> page = repairmgtService.findPage(new Page<Repairmgt>(request, response), repairmgt); 
		
		String roleName = usr.getRoleNames();
		if (usr.getName().equals("系统管理员")){
			roleName = roleName.substring(roleName.indexOf(",") + 1);
		}
		if (roleName.equals("普通用户")){//普通用户只显示该用户创建的条目
			List<Repairmgt> list = page.getList();
			List<Repairmgt> reslist =new ArrayList<Repairmgt>();

			for(Repairmgt obj:list) {
				if(usr.getLoginName().equals(obj.getCreateBy().getId())) {
					reslist.add(obj);
				}	
			}
			page.setList(reslist);
		}

		model.addAttribute("page", page);
		model.addAttribute("whoami", roleName);
		
		return "modules/repairmgt/repairmgtList";
	}
	
	@RequiresPermissions("repairmgt:repairmgt:view")
	@RequestMapping(value = "form")
	public String form(Repairmgt repairmgt, Model model) {
		model.addAttribute("repairmgt", repairmgt);
		model.addAttribute("whoami", UserUtils.getUser().getRoleNames());
		return "modules/repairmgt/repairmgtForm";
	}

	@RequiresPermissions("repairmgt:repairmgt:edit")
	@RequestMapping(value = "save")
	public String save(Repairmgt repairmgt, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, repairmgt)){
			return form(repairmgt, model);
		}
		repairmgtService.save(repairmgt);
		addMessage(redirectAttributes, "保存报修信息成功");
		return "redirect:"+Global.getAdminPath()+"/repairmgt/repairmgt/?repage";
	}
	
	@RequiresPermissions("repairmgt:repairmgt:dlt")
	@RequestMapping(value = "delete")
	public String delete(Repairmgt repairmgt, RedirectAttributes redirectAttributes) {
		repairmgtService.delete(repairmgt);
		addMessage(redirectAttributes, "删除报修信息成功");
		return "redirect:"+Global.getAdminPath()+"/repairmgt/repairmgt/?repage";
	}

}