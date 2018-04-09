package com.kangyonggan.activiti.service.impl;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.activiti.constants.MonitorType;
import com.kangyonggan.activiti.service.ActivitiService;
import com.kangyonggan.activiti.util.MyPageInfo;
import com.kangyonggan.activiti.util.StringUtil;
import com.kangyonggan.extra.core.annotation.Log;
import com.kangyonggan.extra.core.annotation.Monitor;
import lombok.extern.log4j.Log4j2;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * @author kangyonggan
 * @date 4/9/18
 */
@Service
@Log4j2
public class ActivitiServiceImpl implements ActivitiService {

    @Autowired
    private ProcessEngine processEngine;

    @Override
    @Log
    @Monitor(type = MonitorType.INSERT, description = "保存流程定义${zipPath}")
    public boolean saveProcessDefinition(String zipPath) {
        DeploymentBuilder deploymentBuilder = processEngine.getRepositoryService().createDeployment();
        ZipInputStream zipInputStream = null;
        try {
            zipInputStream = new ZipInputStream(new FileInputStream(zipPath));
            deploymentBuilder.addZipInputStream(zipInputStream);

            // 部署，并返回一个部署对象
            Deployment deployment = deploymentBuilder.deploy();
            log.info("流程部署成功，id={}", deployment.getId());
            return true;
        } catch (FileNotFoundException e) {
            log.error("流程部署异常", e);
        } finally {
            if (zipInputStream != null) {
                try {
                    zipInputStream.close();
                } catch (IOException e) {
                    log.error("流程部署异常", e);
                }
            }
        }

        return false;
    }

    @Override
    public PageInfo<ProcessDefinition> searchProcessDefinitions(int pageNum, int pageSize, String id, String name, String key) {
        ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery();

        if (StringUtils.isNotEmpty(id)) {
            query.processDefinitionId(id);
        }
        if (StringUtils.isNotEmpty(name)) {
            query.processDefinitionNameLike(StringUtil.toLikeString(name));
        }
        if (StringUtils.isNotEmpty(key)) {
            query.processDefinitionKeyLike(StringUtil.toLikeString(key));
        }

        query.orderByDeploymentId().desc();
        List<ProcessDefinition> list = query.listPage((pageNum - 1) * pageSize, pageSize);

        return new MyPageInfo(list, pageNum, pageSize, (int) query.count());
    }

    @Override
    @Log
    @Monitor(type = MonitorType.INSERT, description = "保存流程实例${processDefinitionId}")
    public boolean saveProcessInstance(String processDefinitionId) {
        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceById(processDefinitionId);
        log.info("保存流程实例成功, id={}", processInstance.getId());
        return true;
    }

    @Override
    public PageInfo<Task> searchTasks(int pageNum, int pageSize, String assignee) {
        TaskQuery query = processEngine.getTaskService().createTaskQuery();

        if (StringUtils.isNotEmpty(assignee)) {
            query.taskAssignee(assignee);
        }

        query.orderByTaskId().desc();
        List<Task> list = query.listPage((pageNum - 1) * pageSize, pageSize);

        return new MyPageInfo(list, pageNum, pageSize, (int) query.count());
    }

    @Override
    @Log
    @Monitor(type = MonitorType.UPDATE, description = "办理任务${taskId}")
    public boolean updateTask(String taskId) {
        processEngine.getTaskService().complete(taskId);
        log.info("办理任务成功,taskId={}", taskId);
        return true;
    }
}
