package com.kangyonggan.activiti.controller.dashboard.system;

import com.kangyonggan.activiti.controller.BaseController;
import com.kangyonggan.activiti.service.SqlService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author kangyonggan
 * @date 3/30/18
 */
@Controller
@RequestMapping("dashboard/system/sql")
public class DashboardSystemSqlController extends BaseController {

    @Autowired
    private SqlService sqlService;

    private static final String SLQ_TYPE_EXEC = "exec";

    /**
     * 执行脚本
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("SYSTEM_SQL")
    public String index() {
        return getPathIndex();
    }

    /**
     * 提交
     *
     * @param sql
     * @param type
     * @return
     */
    @RequestMapping(value = "submit", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("SYSTEM_SQL")
    public Map<String, Object> submit(@RequestParam("sql") String sql, @RequestParam("type") String type) {
        Map<String, Object> resultMap = getResultMap();

        try {
            if (SLQ_TYPE_EXEC.equals(type)) {
                int count = sqlService.exec(sql);
                resultMap.put("count", count);
            } else {
                List<Map<String, Object>> list = sqlService.query(sql);
                resultMap.put("list", list);
            }
        } catch (Exception e) {
            setResultMapFailure(resultMap, e.getMessage());
        }

        return resultMap;
    }

}
