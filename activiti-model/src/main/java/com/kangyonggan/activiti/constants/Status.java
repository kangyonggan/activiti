package com.kangyonggan.activiti.constants;

import lombok.Getter;

/**
 * 状态枚举
 *
 * @author kangyonggan
 * @date 2018/4/9 0009
 */
public enum Status {

    APPLY("APPLY", "申请中"),
    BACK("BACK", "已退回"),
    REJECT("REJECT", "已拒绝"),
    COMPLETE("COMPLETE", "已完成");

    /**
     * 状态
     */
    @Getter
    private String status;

    /**
     * 名称
     */
    @Getter
    private String name;

    Status(String status, String name) {
        this.status = status;
        this.name = name;
    }

}
