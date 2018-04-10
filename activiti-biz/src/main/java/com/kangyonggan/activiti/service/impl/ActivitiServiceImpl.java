package com.kangyonggan.activiti.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kangyonggan.activiti.dto.TaskDto;
import com.kangyonggan.activiti.mapper.TaskMapper;
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

    @Autowired
    private TaskMapper taskMapper;

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
    public ProcessInstance startProcessInstance(String processDefinitionId, String businessKey, Map<String, Object> variables) {
        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceById(processDefinitionId, businessKey, variables);
        log.info("启动流程实例成功, id={}", processInstance.getId());
        return processInstance;
    }

    @Override
    public Task findTaskByBusinessKey(String businessKey) {
        TaskQuery query = processEngine.getTaskService().createTaskQuery();
        query.processInstanceBusinessKey(businessKey);

        return query.singleResult();
    }

    @Override
    public Map<String, Object> findTaskVariables(String taskId) {
        return processEngine.getTaskService().getVariables(taskId);
    }

    @Override
    public PageInfo<TaskDto> searchTasks(int pageNum, int pageSize, String definitionKey, String serialNo, List<String> roles) {
        PageHelper.startPage(pageNum, pageSize);
        List<TaskDto> taskDtos = taskMapper.selectTasks(definitionKey, serialNo, roles);
        return new PageInfo<>(taskDtos);
    }

    @Override
    public PageInfo<TaskDto> searchHisTasks(int pageNum, int pageSize, String definitionKey, String serialNo, List<String> roles) {
        PageHelper.startPage(pageNum, pageSize);
        List<TaskDto> taskDtos = taskMapper.selectHisTasks(definitionKey, serialNo, roles);
        return new PageInfo<>(taskDtos);
    }

    @Override
    public void executeTask(String taskId, Map<String, Object> variables) {
        processEngine.getTaskService().complete(taskId, variables);
        log.info("执行任务成功,taskId={}", taskId);
    }

    @Override
    public ProcessDefinition findProcessDefinition(String definitionKey) {
        return processEngine.getRepositoryService().createProcessDefinitionQuery().processDefinitionKey(definitionKey).singleResult();
    }

    @Override
    public TaskDto findTaskBytaskId(String taskId) {
        return taskMapper.selectTaskByTaskId(taskId);
    }
}
