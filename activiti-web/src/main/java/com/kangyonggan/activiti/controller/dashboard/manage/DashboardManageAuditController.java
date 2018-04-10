package com.kangyonggan.activiti.controller.dashboard.manage;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.activiti.constants.AppConstants;
import com.kangyonggan.activiti.controller.BaseController;
import com.kangyonggan.activiti.dto.TaskDto;
import com.kangyonggan.activiti.model.Role;
import com.kangyonggan.activiti.service.ActivitiService;
import com.kangyonggan.activiti.service.RoleService;
import com.kangyonggan.activiti.util.Collections3;
import com.kangyonggan.activiti.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author kangyonggan
 * @date 4/10/18
 */
@Controller
@RequestMapping("dashboard/manage/audit")
public class DashboardManageAuditController extends BaseController {

    @Autowired
    private ActivitiService activitiService;

    @Autowired
    private RoleService roleService;

    /**
     * 流程定义审核
     *
     * @param pageNum
     * @param definitionKey
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("MANAGE_AUDIT")
    public String list(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNum,
                       @RequestParam(value = "definitionKey", required = false, defaultValue = "") String definitionKey,
                       Model model) {
        List<Role> roles = roleService.findRolesByUsername(ShiroUtils.getShiroUser().getUsername());
        PageInfo<TaskDto> page = activitiService.searchTasks(pageNum, AppConstants.PAGE_SIZE, definitionKey, Collections3.extractToList(roles, "code"));

        model.addAttribute("page", page);
        return getPathList();
    }
}
