package com.kangyonggan.activiti.service.impl;

import com.github.pagehelper.PageHelper;
import com.kangyonggan.activiti.constants.AppConstants;
import com.kangyonggan.activiti.model.LoginLog;
import com.kangyonggan.activiti.service.LoginLogService;
import com.kangyonggan.activiti.util.DateUtil;
import com.kangyonggan.activiti.util.StringUtil;
import com.kangyonggan.extra.core.annotation.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author kangyonggan
 * @date 3/27/18
 */
@Service
public class LoginLogServiceImpl extends BaseService<LoginLog> implements LoginLogService {

    @Override
    @Log
    public void saveLoginLog(String username, String ip) {
        LoginLog loginLog = new LoginLog();
        loginLog.setUsername(username);
        loginLog.setIp(ip);

        myMapper.insertSelective(loginLog);
    }

    @Override
    public List<LoginLog> searchLoginLogs(int pageNum, String username, String ip, String beginDate, String endDate) throws ParseException {
        Example example = new Example(LoginLog.class);
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(username)) {
            criteria.andLike("username", StringUtil.toLikeString(username));
        }
        if (StringUtils.isNotEmpty(ip)) {
            criteria.andLike("ip", StringUtil.toLikeString(ip));
        }
        if (StringUtils.isNotEmpty(beginDate)) {
            Date beginDt = DateUtil.fromDate10(beginDate);
            criteria.andGreaterThanOrEqualTo("createdTime", beginDt);
        }
        if (StringUtils.isNotEmpty(endDate)) {
            Date endDt = DateUtil.fromDate10(endDate);
            criteria.andLessThanOrEqualTo("createdTime", endDt);
        }

        PageHelper.startPage(pageNum, AppConstants.PAGE_SIZE);
        return myMapper.selectByExample(example);
    }
}
