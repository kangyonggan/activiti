package com.kangyonggan.activiti.handle;

import com.kangyonggan.extra.core.handle.MonitorHandle;
import com.kangyonggan.extra.core.model.MonitorInfo;
import lombok.extern.log4j.Log4j2;

/**
 * @author kangyonggan
 * @date 3/30/18
 */
@Log4j2
public class DbMonitorHandle implements MonitorHandle {

    @Override
    public Object handle(MonitorInfo monitorInfo) {
        return monitorInfo.getReturnValue();
    }

}
