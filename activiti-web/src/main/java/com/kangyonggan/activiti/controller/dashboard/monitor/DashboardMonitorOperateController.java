package com.kangyonggan.activiti.controller.dashboard.monitor;

import com.github.pagehelper.PageInfo;
import com.kangyonggan.activiti.controller.BaseController;
import com.kangyonggan.activiti.model.Monitor;
import com.kangyonggan.activiti.service.MonitorService;
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
@RequestMapping("dashboard/monitor/operate")
public class DashboardMonitorOperateController extends BaseController {

    @Autowired
    private MonitorService monitorService;

    /**
     * 操作日志
     *
     * @param pageNum
     * @param app
     * @param type
     * @param hasReturnValue
     * @param model
     * @return
     * @throws ParseException
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("MONITOR_OPERATE")
    public String index(@RequestParam(value = "p", required = false, defaultValue = "1") int pageNum,
                        @RequestParam(value = "app", required = false, defaultValue = "") String app,
                        @RequestParam(value = "type", required = false, defaultValue = "") String type,
                        @RequestParam(value = "hasReturnValue", required = false, defaultValue = "") String hasReturnValue,
                        Model model) throws ParseException {
        List<Monitor> monitors = monitorService.searchMonitors(pageNum, app, type, hasReturnValue);
        PageInfo<Monitor> page = new PageInfo(monitors);

        model.addAttribute("page", page);
        return getPathList();
    }

}
