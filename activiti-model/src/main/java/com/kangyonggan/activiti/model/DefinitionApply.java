package com.kangyonggan.activiti.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author kangyonggan
 * @date 2018/04/02
 */
@Table(name = "tb_definition_apply")
@Data
public class DefinitionApply implements Serializable {
    /**
     * 主键, 自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 申请流水
     */
    @Column(name = "serial_no")
    private String serialNo;

    /**
     * 备注
     */
    private String remark;

    /**
     * ZIP路径
     */
    @Column(name = "zip_path")
    private String zipPath;

    /**
     * 状态:{APPLY:申请中, BACK:已退回, REJECT:已拒绝, COMPLETE:已完成}
     */
    private String status;

    /**
     * 申请人
     */
    private String username;

    /**
     * 逻辑删除:{0:未删除, 1:已删除}
     */
    @Column(name = "is_deleted")
    private Byte isDeleted;

    /**
     * 创建时间
     */
    @Column(name = "created_time")
    private Date createdTime;

    /**
     * 更新时间
     */
    @Column(name = "updated_time")
    private Date updatedTime;

    private static final long serialVersionUID = 1L;
}