package com.kangyonggan.activiti.mapper;

import com.kangyonggan.activiti.dto.ReplyDto;
import com.kangyonggan.activiti.model.DefinitionApply;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author kangyonggan
 * @date 2018/04/02
 */
@Repository
public interface DefinitionApplyMapper extends MyMapper<DefinitionApply> {

    /**
     * 查找审批历史
     *
     * @param serialNo
     * @param username
     * @return
     */
    List<ReplyDto> selectAllReply(@Param("serialNo") String serialNo, @Param("username") String username);

}