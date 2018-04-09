package com.kangyonggan.activiti.service;

import com.github.pagehelper.PageInfo;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;

/**
 * @author kangyonggan
 * @date 4/9/18
 */
public interface ActivitiService {

    /**
     * 保存流程定义
     *
     * @param zipPath
     * @return
     */
    boolean saveProcessDefinition(String zipPath);

    /**
     * 搜索流程定义
     *
     * @param pageNum
     * @param pageSize
     * @param id
     * @param name
     * @param key
     * @return
     */
    PageInfo<ProcessDefinition> searchProcessDefinitions(int pageNum, int pageSize, String id, String name, String key);

    /**
     * 保存流程实例
     *
     * @param processDefinitionId
     * @return
     */
    boolean saveProcessInstance(String processDefinitionId);

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
     * 办理任务
     *
     * @param taskId
     * @return
     */
    boolean updateTask(String taskId);

}
