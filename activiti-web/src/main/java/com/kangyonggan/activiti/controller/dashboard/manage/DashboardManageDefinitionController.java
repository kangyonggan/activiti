package com.kangyonggan.activiti.controller.dashboard.manage;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.activiti.constants.AppConstants;
import com.kangyonggan.activiti.controller.BaseController;
import com.kangyonggan.activiti.service.ActivitiService;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author kangyonggan
 * @date 4/10/18
 */
@Controller
@RequestMapping("dashboard/manage/definition")
public class DashboardManageDefinitionController extends BaseController {

    @Autowired
    private ActivitiService activitiService;

    /**
     * 流程定义
     *
     * @param pageNum
     * @param id
     * @param name
     * @param key
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("MANAGE_DEFINITION")
    public String list(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNum,
                       @RequestParam(value = "id", required = false, defaultValue = "") String id,
                       @RequestParam(value = "name", required = false, defaultValue = "") String name,
                       @RequestParam(value = "key", required = false, defaultValue = "") String key,
                       Model model) {
        PageInfo<ProcessDefinition> page = activitiService.searchProcessDefinitions(pageNum, AppConstants.PAGE_SIZE, id, name, key);
        model.addAttribute("page", page);
        return getPathList();
    }

}
