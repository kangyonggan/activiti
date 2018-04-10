package com.kangyonggan.activiti.service.impl;

import com.github.pagehelper.PageHelper;
import com.kangyonggan.activiti.constants.Status;
import com.kangyonggan.activiti.dto.ReplyDto;
import com.kangyonggan.activiti.mapper.DefinitionApplyMapper;
import com.kangyonggan.activiti.model.DefinitionApply;
import com.kangyonggan.activiti.service.ActivitiService;
import com.kangyonggan.activiti.service.DefinitionApplyService;
import com.kangyonggan.activiti.util.ShiroUtils;
import com.kangyonggan.activiti.util.StringUtil;
import com.kangyonggan.extra.core.annotation.Log;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kangyonggan
 * @date 4/10/18
 */
@Service
public class DefinitionApplyServiceImpl extends BaseService<DefinitionApply> implements DefinitionApplyService {

    /**
     * 流程定义申请流程的KEY
     */
    private static final String DEFINITION_KEY = "audit_process";

    @Autowired
    private ActivitiService activitiService;

    @Autowired
    private DefinitionApplyMapper definitionApplyMapper;

    @Override
    public List<DefinitionApply> searchDefinitionApplies(int pageNum, int pageSize, String username, String serialNo, String remark, String status) {
        Example example = new Example(DefinitionApply.class);

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);

        if (StringUtils.isNotEmpty(serialNo)) {
            criteria.andEqualTo("serialNo", serialNo);
        }
        if (StringUtils.isNotEmpty(status)) {
            criteria.andEqualTo("status", status);
        }
        if (StringUtils.isNotEmpty(remark)) {
            criteria.andLike("remark", StringUtil.toLikeString(remark));
        }

        example.setOrderByClause("id desc");

        PageHelper.startPage(pageNum, pageSize);
        return myMapper.selectByExample(example);
    }

    @Override
    @Log
    public void saveDefinitionApply(DefinitionApply definitionApply) {
        myMapper.insertSelective(definitionApply);

        Map<String, Object> variables = new HashMap<>(1);
        variables.put("username", definitionApply.getUsername());

        // 查找流程定义
        ProcessDefinition definition = activitiService.findProcessDefinition(DEFINITION_KEY);

        // 启动实例
        activitiService.startProcessInstance(definition.getId(), definitionApply.getSerialNo(), variables);

        // 查找任务
        Task task = activitiService.findTaskByBusinessKey(definitionApply.getSerialNo());

        // 执行任务
        variables = new HashMap<>(1);
        variables.put("definitionApply", myMapper.selectByPrimaryKey(definitionApply.getId()));
        activitiService.executeTask(task.getId(), variables);
    }

    @Override
    @Log
    public void updateDefinitionApply(Long id, String taskId, String status, String replyMsg) {
        DefinitionApply definitionApply = new DefinitionApply();
        definitionApply.setId(id);
        definitionApply.setStatus(status);

        myMapper.updateByPrimaryKeySelective(definitionApply);

        // 执行任务
        Map<String, Object> variables = new HashMap<>(3);
        variables.put("status", status);
        variables.put(taskId + ":status", status);
        variables.put(taskId + ":replyUser", ShiroUtils.getShiroUser().getUsername());
        variables.put(taskId + ":replyMsg", replyMsg);

        activitiService.executeTask(taskId, variables);
    }

    @Override
    @Log
    public DefinitionApply findDefinitionById(Long id) {
        return myMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateDefinitionApply(DefinitionApply definitionApply) {
        definitionApply.setStatus(Status.APPLY.getStatus());
        myMapper.updateByPrimaryKeySelective(definitionApply);
        definitionApply = myMapper.selectByPrimaryKey(definitionApply.getId());

        // 查找任务
        Task task = activitiService.findTaskByBusinessKey(definitionApply.getSerialNo());

        // 执行任务
        Map<String, Object> variables = new HashMap<>(1);
        variables = new HashMap<>(1);
        variables.put("definitionApply", definitionApply);
        activitiService.executeTask(task.getId(), variables);
    }

    @Override
    public List<ReplyDto> findAllReply(String serialNo, String username) {
        return definitionApplyMapper.selectAllReply(serialNo, username);
    }
}
