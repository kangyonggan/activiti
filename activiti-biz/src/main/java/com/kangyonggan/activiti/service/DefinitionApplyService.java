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
     * @param dirPath
     */
    void saveDefinitionApply(DefinitionApply definitionApply, String dirPath);

}