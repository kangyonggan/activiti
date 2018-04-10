package com.kangyonggan.activiti.service.impl;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.activiti.service.ActivitiService;
import com.kangyonggan.activiti.util.MyPageInfo;
import com.kangyonggan.activiti.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
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
    public Deployment deployProcessDefinition(String zipPath) {
        DeploymentBuilder deploymentBuilder = processEngine.getRepositoryService().createDeployment();
        ZipInputStream zipInputStream = null;
        try {
            zipInputStream = new ZipInputStream(new FileInputStream(zipPath));
            deploymentBuilder.addZipInputStream(zipInputStream);

            // 部署，并返回一个部署对象
            Deployment deployment = deploymentBuilder.deploy();
            log.info("流程定义部署成功，deployment={}", deployment);
            return deployment;
        } catch (FileNotFoundException e) {
            log.error("流程定义部署异常", e);
        } finally {
            if (zipInputStream != null) {
                try {
                    zipInputStream.close();
                } catch (IOException e) {
                    log.error("流程定义部署异常", e);
                }
            }
        }

        return null;
    }

    @Override
    public void deleteProcessDefinition(String deploymentId) {
        processEngine.getRepositoryService().deleteDeployment(deploymentId);
    }

    @Override
    public PageInfo<ProcessDefinition> searchProcessDefinitions(int pageNum, int pageSize, String deploymentId, String definitionId, String name, String key) {
        ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery();

        if (StringUtils.isNotEmpty(deploymentId)) {
            query.deploymentId(deploymentId);
        }
        if (StringUtils.isNotEmpty(definitionId)) {
            query.processDefinitionId(definitionId);
        }
        if (StringUtils.isNotEmpty(name)) {
            query.processDefinitionNameLike(StringUtil.toLikeString(name));
        }
        if (StringUtils.isNotEmpty(key)) {
            query.processDefinitionKeyLike(StringUtil.toLikeString(key));
        }

        query.orderByDeploymentId().desc();
        List<ProcessDefinition> list = query.listPage((pageNum - 1) * pageSize, pageSize);

        return new MyPageInfo<>(list, pageNum, pageSize, (int) query.count());
    }

    @Override
    public ProcessInstance startProcessInstance(String processDefinitionId) {
        return startProcessInstance(processDefinitionId, null);
    }

    @Override
    public ProcessInstance startProcessInstance(String processDefinitionId, Map<String, Object> variables) {
        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceById(processDefinitionId, variables);
        log.info("启动流程实例成功, id={}", processInstance.getId());
        return processInstance;
    }

    @Override
    public Task findTaskByInstanceId(String instanceId) {
        TaskQuery query = processEngine.getTaskService().createTaskQuery();
        query.processInstanceId(instanceId);

        return query.singleResult();
    }

    @Override
    public PageInfo<Task> searchTasks(int pageNum, int pageSize, String assignee) {
        TaskQuery query = processEngine.getTaskService().createTaskQuery();

        if (StringUtils.isNotEmpty(assignee)) {
            query.taskAssignee(assignee);
        }

        query.orderByTaskId().desc();
        List<Task> list = query.listPage((pageNum - 1) * pageSize, pageSize);

        return new MyPageInfo<>(list, pageNum, pageSize, (int) query.count());
    }

    @Override
    public void executeTask(String taskId) {
        executeTask(taskId, null);
    }

    @Override
    public void executeTask(String taskId, Map<String, Object> variables) {
        processEngine.getTaskService().complete(taskId, variables);
        log.info("执行任务成功,taskId={}", taskId);
    }

    @Override
    public PageInfo<HistoricTaskInstance> searchHistoricTaskInstances(int pageNum, int pageSize, String assignee, Boolean isFinished, Date beginTime, Date endTime) {

        HistoricTaskInstanceQuery query = processEngine.getHistoryService().createHistoricTaskInstanceQuery();

        if (StringUtils.isNotEmpty(assignee)) {
            query.taskAssignee(assignee);
        }

        if (beginTime != null) {
            query.taskCreatedAfter(beginTime);
        }
        if (endTime != null) {
            query.taskCreatedBefore(endTime);
        }

        if (isFinished != null) {
            if (isFinished) {
                query.finished();
            } else {
                query.unfinished();
            }
        }

        query.orderByTaskCreateTime().desc();
        List<HistoricTaskInstance> list = query.listPage((pageNum - 1) * pageSize, pageSize);

        return new MyPageInfo<>(list, pageNum, pageSize, (int) query.count());
    }

    @Override
    public ProcessDefinition findProcessDefinition(String definitionKey) {
        return processEngine.getRepositoryService().createProcessDefinitionQuery().processDefinitionKey(definitionKey).singleResult();
    }
}
