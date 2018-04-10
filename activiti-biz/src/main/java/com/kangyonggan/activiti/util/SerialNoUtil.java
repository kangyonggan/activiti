package com.kangyonggan.activiti.util;

import com.kangyonggan.activiti.service.RedisService;
import org.apache.commons.lang3.StringUtils;

/**
 * @author kangyonggan
 * @date 4/10/18
 */
public class SerialNoUtil {

    private static final String DEFINITION_APPLY_KEY = "ACTIVITI:DEFINITION:APPLY:SERIAL";

    /**
     * 获取下一个流程申请流水号
     *
     * @return
     */
    public static String getDefinitionApplySerialNo() {
        long nextVal = SpringUtils.getBean(RedisService.class).incr(DEFINITION_APPLY_KEY);
        return "DA" + DateUtil.getDate() + StringUtils.leftPad(String.valueOf(nextVal), 8, "0");
    }

}
