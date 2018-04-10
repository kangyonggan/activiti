package com.kangyonggan.activiti.service;

import com.kangyonggan.activiti.model.DefinitionApply;

import java.util.List;

/**
 * @author kangyonggan
 * @date 4/10/18
 */
public interface DefinitionApplyService {

    /**
     * 搜索流程定义申请
     *
     * @param pageNum
     * @param pageSize
     * @param username
     * @param serialNo
     * @param remark
     * @param status
     * @return
     */
    List<DefinitionApply> searchDefinitionApplies(int pageNum, int pageSize, String username, String serialNo, String remark, String status);

    /**
     * 保存流程定义申请
     *
     * @param definitionApply
     */
    void saveDefinitionApply(DefinitionApply definitionApply);

    /**
     * 更新流程定义申请
     *
     * @param id
     * @param taskId
     * @param status
     * @param replyMsg
     */
    void updateDefinitionApply(Long id, String taskId, String status, String replyMsg);

    /**
     * 查找流程定义申请
     *
     * @param id
     * @return
     */
    DefinitionApply findDefinitionById(Long id);

    /**
     * 更新流程定义申请
     *
     * @param definitionApply
     */
    void updateDefinitionApply(DefinitionApply definitionApply);

}
