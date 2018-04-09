package com.kangyonggan.activiti;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.activiti.constants.AppConstants;
import com.kangyonggan.activiti.service.ActivitiService;
import lombok.extern.log4j.Log4j2;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author kangyonggan
 * @date 4/9/18
 */
@Log4j2
public class ActivitiServiceTest extends AbstractServiceTest {

    @Autowired
    private ActivitiService activitiService;

    /**
     * 保存流程定义
     */
    @Test
    public void testSaveProcessDefinition() {
        for (int i = 0; i < 25; i++) {
            boolean result = activitiService.saveProcessDefinition("/Users/kyg/code/kyg/acti-leave/src/main/resources/leave.zip");
            Assert.assertTrue(result);
        }
    }

    /**
     * 搜索流程定义
     */
    @Test
    public void testSearchProcessDefinition() {
        PageInfo<ProcessDefinition> page = activitiService.searchProcessDefinitions(2, AppConstants.PAGE_SIZE, null, "请假", null);
        log.info(page);
    }

    /**
     * 保存流程实例
     */
    @Test
    public void testSaveProcessInstance() {
        Assert.assertTrue(activitiService.saveProcessInstance("leave_process:15:2556"));
    }

    /**
     * 搜索任务
     */
    @Test
    public void testSearchTasks() {
        PageInfo<Task> page = activitiService.searchTasks(1, AppConstants.PAGE_SIZE, null);
        log.info(page);
    }

    /**
     * 办理任务
     */
    @Test
    public void testUpdateTask() {
        Assert.assertTrue(activitiService.updateTask("5005"));
    }

}
