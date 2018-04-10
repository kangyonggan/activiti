package com.kangyonggan.activiti.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.kangyonggan.activiti.constants.AppConstants;
import com.kangyonggan.activiti.dto.ShiroUser;
import com.kangyonggan.activiti.model.Monitor;
import com.kangyonggan.activiti.service.MonitorService;
import com.kangyonggan.activiti.util.ShiroUtils;
import com.kangyonggan.extra.core.model.MonitorInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author kangyonggan
 * @date 3/30/18
 */
@Service
public class MonitorServiceImpl extends BaseService<Monitor> implements MonitorService {

    @Override
    public void saveMonitor(MonitorInfo monitorInfo) {
        Monitor monitor = new Monitor();
        BeanUtils.copyProperties(monitorInfo, monitor);

        monitor.setBeginTime(new Date(monitorInfo.getMethodStartTime()));
        monitor.setEndTime(new Date(monitorInfo.getMethodEndTime()));

        monitor.setHasReturnValue((byte) (monitorInfo.getHasReturnValue() ? 1 : 0));
        if (monitorInfo.getHasReturnValue()) {
            monitor.setReturnValue(JSONObject.toJSONString(monitorInfo.getReturnValue()));
        }
        if (StringUtils.isEmpty(monitor.getReturnValue())) {
            monitor.setReturnValue(StringUtils.EMPTY);
        }
        monitor.setArgs(JSONObject.toJSONString(monitorInfo.getArgs()));
        ShiroUser shiroUser = ShiroUtils.getShiroUser();
        if (shiroUser != null) {
            monitor.setUsername(shiroUser.getUsername());
        }

        myMapper.insertSelective(monitor);
    }

    @Override
    public List<Monitor> searchMonitors(int pageNum, String app, String type, String hasReturnValue) {
        Example example = new Example(Monitor.class);

        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(app)) {
            criteria.andEqualTo("app", app);
        }
        if (StringUtils.isNotEmpty(type)) {
            criteria.andEqualTo("type", type);
        }
        if (StringUtils.isNotEmpty(hasReturnValue)) {
            criteria.andEqualTo("hasReturnValue", hasReturnValue);
        }

        example.selectProperties("id", "app", "type", "description", "beginTime", "endTime", "hasReturnValue", "username");
        example.setOrderByClause("id desc");

        PageHelper.startPage(pageNum, AppConstants.PAGE_SIZE);
        return myMapper.selectByExample(example);
    }
}
