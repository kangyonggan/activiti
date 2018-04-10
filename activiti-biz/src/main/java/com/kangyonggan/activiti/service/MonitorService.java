package com.kangyonggan.activiti.service;

import com.kangyonggan.activiti.model.Monitor;
import com.kangyonggan.extra.core.model.MonitorInfo;

import java.util.List;

/**
 * @author kangyonggan
 * @date 3/30/18
 */
public interface MonitorService {

    /**
     * 保存监控信息
     *
     * @param monitorInfo
     */
    void saveMonitor(MonitorInfo monitorInfo);

    /**
     * 搜索监控日志
     *
     * @param pageNum
     * @param app
     * @param type
     * @param hasReturnValue
     * @return
     */
    List<Monitor> searchMonitors(int pageNum, String app, String type, String hasReturnValue);
}
