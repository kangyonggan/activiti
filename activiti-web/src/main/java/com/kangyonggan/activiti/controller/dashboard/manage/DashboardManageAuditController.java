package com.kangyonggan.activiti.controller.dashboard.manage;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.activiti.constants.AppConstants;
import com.kangyonggan.activiti.controller.BaseController;
import com.kangyonggan.activiti.dto.TaskDto;
import com.kangyonggan.activiti.model.DefinitionApply;
import com.kangyonggan.activiti.model.Role;
import com.kangyonggan.activiti.model.User;
import com.kangyonggan.activiti.service.ActivitiService;
import com.kangyonggan.activiti.service.DefinitionApplyService;
import com.kangyonggan.activiti.service.RoleService;
import com.kangyonggan.activiti.service.UserService;
import com.kangyonggan.activiti.util.Collections3;
import com.kangyonggan.activiti.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @Autowired
    private UserService userService;

    @Autowired
    private DefinitionApplyService definitionApplyService;

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

    /**
     * 详情
     *
     * @param taskId
     * @param model
     * @return
     */
    @RequestMapping(value = "{taskId:[\\d]+}", method = RequestMethod.GET)
    @RequiresPermissions("MANAGE_AUDIT")
    public String detail(@PathVariable("taskId") String taskId, Model model) {
        Map<String, Object> variables = activitiService.findTaskVariables(taskId);
        User applyUser = userService.findUserByUsername((String) variables.get("username"));
        DefinitionApply definitionApply = (DefinitionApply) variables.get("definitionApply");
        TaskDto task = activitiService.findTaskBytaskId(taskId);

        model.addAttribute("task", task);
        model.addAttribute("applyUser", applyUser);
        model.addAttribute("definitionApply", definitionApply);
        return getPathDetail();
    }

    /**
     * 审批提交
     *
     * @param taskId
     * @param status
     * @param replyMsg
     * @param applyId
     * @return
     */
    @RequestMapping(value = "{taskId:[\\d]+}", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("MANAGE_AUDIT")
    public Map<String, Object> submit(@PathVariable("taskId") String taskId, @RequestParam("status") String status,
                                      @RequestParam("replyMsg") String replyMsg, @RequestParam("applyId") Long applyId) {
        definitionApplyService.updateDefinitionApply(applyId, taskId, status, replyMsg);
        return getResultMap();
    }
}
