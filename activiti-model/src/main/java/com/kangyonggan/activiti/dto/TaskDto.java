package com.kangyonggan.activiti.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务
 *
 * @author kangyonggan
 * @date 4/10/18
 */
@Data
public class TaskDto implements Serializable {

    /**
     * 业务流水
     */
    private String serialNo;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 所属实例ID
     */
    private String instanceId;

    /**
     * 流程定义ID
     */
    private String definitionId;

    /**
     * 流程定义名称
     */
    private String definitionName;

    /**
     * 流程定义的KEY
     */
    private String definitionKey;

    /**
     * 流程定义的版本
     */
    private Integer definitionVersion;

    /**
     * 部署ID
     */
    private String deploymentId;

    /**
     * 委托者
     */
    private String assignee;

    /**
     * 创建时间
     */
    private Date createdTime;
}
