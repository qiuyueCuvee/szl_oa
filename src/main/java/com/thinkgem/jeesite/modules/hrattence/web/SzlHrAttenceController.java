/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.hrattence.web;

import java.io.FileOutputStream;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.hrattence.entity.SzlHrAttence;
import com.thinkgem.jeesite.modules.hrattence.entity.SzlHrStaff;
import com.thinkgem.jeesite.modules.hrattence.service.SzlHrAttenceService;
import com.thinkgem.jeesite.modules.overtime.entity.SzlHrOvertime;

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
	
	@RequestMapping(value = "export", method=RequestMethod.POST)
	public String export(SzlHrAttence szlHrAttence,HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) throws ParseException {
		try {
            String fileName = "深之蓝考勤异常表"+DateUtils.getDate("yyyyMMddHHmmss")+".xls";
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("考勤异常");   
            HSSFRow row;
            HSSFCell cell;
            List<SzlHrAttence> list = szlHrAttenceService.findAttenceList(szlHrAttence);
            row = sheet.createRow(0); 
            String[] header = {"工号","姓名","所属部门","日期","上班","下班","迟到时间","早退时间","缺勤时间","合计","备注"};
            for(int j = 0; j < header.length; j++) {
        		cell = row.createCell(j);
    			cell.setCellValue(header[j]);
            }
            for(int i = 0; i < list.size(); i++) {
            	row = sheet.createRow(i+1); 
            	SzlHrAttence element = list.get(i);
        	    for(int j = 0; j < header.length; j++) {
        	    	 cell = row.createCell(j);
        	    	 if(j==0) {
        	    		 cell.setCellValue(element.getNumber());
        	    	 }
        	    	 if(j==1) {
        	    		 cell.setCellValue(element.getHrStaffName());
        	    	 }
        	    	 if(j==2) {
        	    		 cell.setCellValue(element.getHrStaffDept());
        	    	 }
        	    	 if(j==3) {
        	    		 cell.setCellValue(element.getDate());
        	    	 }
        	    	 if(j==4) {
        	    		 cell.setCellValue(element.getStarttime());
        	    	 }
        	    	 if(j==5) {
        	    		 cell.setCellValue(element.getEndtime());
        	    	 }
        	    	 if(j==6) {
        	    		 cell.setCellValue(element.getLatetime());
        	    	 }
        	    	 if(j==7) {
        	    		 cell.setCellValue(element.getEarlytime());
        	    	 }
        	    	 if(j==8) {
        	    		 cell.setCellValue(element.getAbsenttime());
        	    	 }
        	    	 if(j==9) {
        	    		 cell.setCellValue(element.getSum());
        	    	 }
        	    	 if(j==10) {
        	    		 cell.setCellValue(element.getRemark());
        	    	 }
        	    }
            }
            wb.write(new FileOutputStream("d:/"+fileName));
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出考勤异常信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/hrattence/szlHrAttence/list";
	} 

}