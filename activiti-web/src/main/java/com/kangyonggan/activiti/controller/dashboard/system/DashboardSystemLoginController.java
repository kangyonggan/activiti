package com.kangyonggan.activiti.controller.dashboard.system;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.activiti.controller.BaseController;
import com.kangyonggan.activiti.model.LoginLog;
import com.kangyonggan.activiti.service.LoginLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.List;

/**
 * @author kangyonggan
 * @date 2017/1/8
 */
@Controller
@RequestMapping("dashboard/system/login")
public class DashboardSystemLoginController extends BaseController {

    @Autowired
    private LoginLogService loginLogService;

    /**
     * 登录日志
     *
     * @param pageNum
     * @param username
     * @param ip
     * @param beginDate
     * @param endDate
     * @param model
     * @return
     * @throws ParseException
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("SYSTEM_LOGIN")
    public String index(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNum,
                        @RequestParam(value = "username", required = false, defaultValue = "") String username,
                        @RequestParam(value = "ip", required = false, defaultValue = "") String ip,
                        @RequestParam(value = "beginDate", required = false, defaultValue = "") String beginDate,
                        @RequestParam(value = "endDate", required = false, defaultValue = "") String endDate,
                        Model model) throws ParseException {
        List<LoginLog> loginLogs = loginLogService.searchLoginLogs(pageNum, username, ip, beginDate, endDate);
        PageInfo<LoginLog> page = new PageInfo(loginLogs);

        model.addAttribute("page", page);
        return getPathList();
    }


}
