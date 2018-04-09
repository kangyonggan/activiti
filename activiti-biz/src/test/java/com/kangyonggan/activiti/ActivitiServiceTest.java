package com.kangyonggan.activiti;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.activiti.constants.AppConstants;
import com.kangyonggan.activiti.service.ActivitiService;
import lombok.extern.log4j.Log4j2;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author kangyonggan
 * @date 4/9/18
 */
@Log4j2
public class ActivitiServiceTest extends AbstractServiceTest {

    @Autowired
    private ActivitiService activitiService;

    /**
     * 保存流程定义
     */
    @Test
    public void testSaveProcessDefinition() {
        for (int i = 0; i < 25; i++) {
            boolean result = activitiService.saveProcessDefinition("/Users/kyg/code/kyg/acti-leave/src/main/resources/leave.zip");
            Assert.assertTrue(result);
        }
    }

    /**
     * 搜索流程定义
     */
    @Test
    public void testSearchProcessDefinition() {
        PageInfo<ProcessDefinition> page = activitiService.searchProcessDefinition(2, AppConstants.PAGE_SIZE, null, "请假", null);
        log.info(page);
    }

}
