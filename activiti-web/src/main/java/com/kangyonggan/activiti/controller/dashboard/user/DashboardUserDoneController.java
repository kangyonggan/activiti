package com.kangyonggan.activiti.controller.dashboard.user;

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
import org.activiti.engine.history.HistoricVariableInstance;
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
@RequestMapping("dashboard/user/done")
public class DashboardUserDoneController extends BaseController {

    @Autowired
    private ActivitiService activitiService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private DefinitionApplyService definitionApplyService;

    /**
     * 我的已办
     *
     * @param pageNum
     * @param definitionKey
     * @param serialNo
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("USER_DONE")
    public String list(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNum,
                       @RequestParam(value = "definitionKey", required = false, defaultValue = "") String definitionKey,
                       @RequestParam(value = "serialNo", required = false, defaultValue = "") String serialNo,
                       Model model) {
        List<Role> roles = roleService.findRolesByUsername(ShiroUtils.getShiroUser().getUsername());
        PageInfo<TaskDto> page = activitiService.searchHisTasks(pageNum, AppConstants.PAGE_SIZE, definitionKey, serialNo, Collections3.extractToList(roles, "code"));

        model.addAttribute("page", page);
        return getPathList();
    }

    /**
     * 查看
     *
     * @param taskId
     * @param model
     * @return
     */
    @RequestMapping(value = "{taskId:[\\d]+}", method = RequestMethod.GET)
    @RequiresPermissions("USER_DONE")
    public String detail(@PathVariable("taskId") String taskId, Model model) {
        String username = (String) activitiService.findHisTaskVariable(taskId, "username").getValue();
        User applyUser = userService.findUserByUsername(username);
        DefinitionApply definitionApply = (DefinitionApply) activitiService.findHisTaskVariable(taskId, "definitionApply").getValue();
        TaskDto task = activitiService.findHisTaskByTaskId(taskId);
        String status = (String) activitiService.findHisTaskVariable(taskId, "status").getValue();
        String replyMsg = (String) activitiService.findHisTaskVariable(taskId, "replyMsg").getValue();

        model.addAttribute("task", task);
        model.addAttribute("status", status);
        model.addAttribute("replyMsg", replyMsg);
        model.addAttribute("applyUser", applyUser);
        model.addAttribute("definitionApply", definitionApply);
        return getPathDetail();
    }

}
