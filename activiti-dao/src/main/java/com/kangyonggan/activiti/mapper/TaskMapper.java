package com.kangyonggan.activiti.mapper;

import com.kangyonggan.activiti.dto.TaskDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author kangyonggan
 * @date 4/10/18
 */
@Repository
public interface TaskMapper {

    /**
     * 搜索待办任务
     *
     * @param definitionKey
     * @param serialNo
     * @param roles
     * @return
     */
    List<TaskDto> selectTasks(@Param("definitionKey") String definitionKey, @Param("serialNo") String serialNo, @Param("roles") List<String> roles);

    /**
     * 搜索已办任务
     *
     * @param definitionKey
     * @param serialNo
     * @param roles
     * @return
     */
    List<TaskDto> selectHisTasks(@Param("definitionKey") String definitionKey, @Param("serialNo") String serialNo, @Param("roles") List<String> roles);

    /**
     * 查找待办任务
     *
     * @param taskId
     * @return
     */
    TaskDto selectTaskByTaskId(@Param("taskId") String taskId);

    /**
     * 查找已办任务
     *
     * @param taskId
     * @return
     */
    TaskDto selectHisTaskByTaskId(@Param("taskId") String taskId);

}
