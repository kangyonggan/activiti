package com.kangyonggan.activiti;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.activiti.constants.AppConstants;
import com.kangyonggan.activiti.constants.Status;
import com.kangyonggan.activiti.dto.TaskDto;
import com.kangyonggan.activiti.service.ActivitiService;
import lombok.extern.log4j.Log4j2;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kangyonggan
 * @date 4/9/18
 */
@Log4j2
public class ActivitiServiceTest extends AbstractServiceTest {

    @Autowired
    private ActivitiService activitiService;

    /**
     * 部署流程定义
     */
    @Test
    public void testDeployProcessDefinition() {
        activitiService.deployProcessDefinition("/Users/kyg/code/kyg/activiti/activiti-dao/src/main/resources/audit.zip");
    }

    /**
     * 搜索流程定义
     */
    @Test
    public void testSearchProcessDefinition() {
        PageInfo<ProcessDefinition> page = activitiService.searchProcessDefinitions(1, AppConstants.PAGE_SIZE, null, null, null, null);
        log.info(page);
    }

    /**
     * 启动流程实例
     */
    @Test
    public void testStartProcessInstance() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("username", "guest");
        // 启动实例
        ProcessInstance processInstance = activitiService.startProcessInstance("audit_process:1:4", variables);

        // 查找任务
        Task task = activitiService.findTaskByInstanceId(processInstance.getProcessInstanceId());

        // 执行任务
        variables = new HashMap<>();
        variables.put("zipFilePath", "D:\\code\\activiti\\activiti-dao\\src\\main\\resources\\audit.zip");
        activitiService.executeTask(task.getId(), variables);
    }

    /**
     * 搜索任务
     */
    @Test
    public void testSearchTasks() {
        PageInfo<Task> page = activitiService.searchTasks(1, AppConstants.PAGE_SIZE, "ROLE_AUDITOR");
        log.info(page);
    }

    /**
     * 搜索任务
     */
    @Test
    public void testSearchTasks2() {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_AUDITOR");
        roles.add("ROLE_XXX");
        PageInfo<TaskDto> page = activitiService.searchTasks(1, AppConstants.PAGE_SIZE, null, roles);
        log.info(page);
    }

    /**
     * 执行任务
     */
    @Test
    public void testExecuteTask() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("status", Status.BACK.getStatus());
        variables.put("msg", "没给小费");
        activitiService.executeTask("5010", variables);
    }

    /**
     * 搜索历史任务
     */
    @Test
    public void testSearchHistoricTaskInstances() {
        PageInfo<HistoricTaskInstance> page = activitiService.searchHistoricTaskInstances(1, AppConstants.PAGE_SIZE, null, true, null, null);
        log.info(page);
    }

}
