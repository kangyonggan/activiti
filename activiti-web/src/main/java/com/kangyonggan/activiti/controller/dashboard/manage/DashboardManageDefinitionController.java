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
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
     * @param deploymentId
     * @param definitionId
     * @param name
     * @param key
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("MANAGE_DEFINITION")
    public String list(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNum,
                       @RequestParam(value = "deploymentId", required = false, defaultValue = "") String deploymentId,
                       @RequestParam(value = "definitionId", required = false, defaultValue = "") String definitionId,
                       @RequestParam(value = "name", required = false, defaultValue = "") String name,
                       @RequestParam(value = "key", required = false, defaultValue = "") String key,
                       Model model) {
        PageInfo<ProcessDefinition> page = activitiService.searchProcessDefinitions(pageNum, AppConstants.PAGE_SIZE, deploymentId, definitionId, name, key);
        model.addAttribute("page", page);
        return getPathList();
    }

    /**
     * 删除流程定义
     *
     * @param deploymentId
     * @return
     */
    @RequestMapping(value = "{deploymentId:[\\d]+}/delete", method = RequestMethod.GET)
    @RequiresPermissions("MANAGE_DEFINITION")
    @ResponseBody
    public Map<String, Object> delete(@PathVariable("deploymentId") String deploymentId) {
        activitiService.deleteProcessDefinition(deploymentId);
        return super.getResultMap();
    }
}
