package com.kangyonggan.activiti.service;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.activiti.dto.TaskDto;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author kangyonggan
 * @date 4/9/18
 */
public interface ActivitiService {

    /**
     * 部署流程定义
     *
     * @param zipPath
     * @return
     */
    Deployment deployProcessDefinition(String zipPath);

    /**
     * 删除流程定义
     *
     * @param deploymentId
     */
    void deleteProcessDefinition(String deploymentId);

    /**
     * 搜索流程定义
     *
     * @param pageNum
     * @param pageSize
     * @param deploymentId
     * @param definitionId
     * @param name
     * @param key
     * @return
     */
    PageInfo<ProcessDefinition> searchProcessDefinitions(int pageNum, int pageSize, String deploymentId, String definitionId, String name, String key);

    /**
     * 启动流程实例
     *
     * @param processDefinitionId
     * @return
     */
    ProcessInstance startProcessInstance(String processDefinitionId);

    /**
     * 启动流程实例, 带有参数
     *
     * @param processDefinitionId
     * @param variables
     * @return
     */
    ProcessInstance startProcessInstance(String processDefinitionId, Map<String, Object> variables);

    /**
     * 根据实例ID查找任务
     *
     * @param instanceId
     * @return
     */
    Task findTaskByInstanceId(String instanceId);

    /**
     * 搜索任务
     *
     * @param pageNum
     * @param pageSize
     * @param assignee
     * @return
     */
    PageInfo<Task> searchTasks(int pageNum, int pageSize, String assignee);

    /**
     * 搜索任务
     *
     * @param pageNum
     * @param pageSize
     * @param definitionKey
     * @param roles
     * @return
     */
    PageInfo<TaskDto> searchTasks(int pageNum, int pageSize, String definitionKey, List<String> roles);

    /**
     * 执行任务
     *
     * @param taskId
     */
    void executeTask(String taskId);

    /**
     * 执行任务
     *
     * @param taskId
     * @param variables
     */
    void executeTask(String taskId, Map<String, Object> variables);

    /**
     * 搜索历史任务
     *
     * @param pageNum
     * @param pageSize
     * @param assignee
     * @param isFinished
     * @param beginTime
     * @param endTime
     * @return
     */
    PageInfo<HistoricTaskInstance> searchHistoricTaskInstances(int pageNum, int pageSize, String assignee, Boolean isFinished, Date beginTime, Date endTime);

    /**
     * 查找流程定义
     *
     * @param definitionKey
     * @return
     */
    ProcessDefinition findProcessDefinition(String definitionKey);
}
