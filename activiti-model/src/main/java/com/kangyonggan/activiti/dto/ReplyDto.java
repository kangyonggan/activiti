package com.kangyonggan.activiti.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 审批历史
 *
 * @author kangyonggan
 * @date 4/10/18
 */
@Data
public class ReplyDto implements Serializable {

    /**
     * 业务流水
     */
    private String serialNo;

    /**
     * 审批结果
     */
    private String status;

    /**
     * 审批人
     */
    private String replyUser;

    /**
     * 审批意见
     */
    private String replyMsg;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;
}
