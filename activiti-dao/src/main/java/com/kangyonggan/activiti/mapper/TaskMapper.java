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
     * 搜索任务
     *
     * @param definitionKey
     * @param roles
     * @return
     */
    List<TaskDto> selectTasks(@Param("definitionKey") String definitionKey, @Param("roles") List<String> roles);

    /**
     * 查找任务
     *
     * @param taskId
     * @return
     */
    TaskDto selectTaskByTaskId(@Param("taskId") String taskId);

}
