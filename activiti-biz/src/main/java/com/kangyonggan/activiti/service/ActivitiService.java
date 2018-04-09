package com.kangyonggan.activiti.service;

import com.github.pagehelper.PageInfo;
import org.activiti.engine.repository.ProcessDefinition;

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
    PageInfo<ProcessDefinition> searchProcessDefinition(int pageNum, int pageSize, String id, String name, String key);

}
