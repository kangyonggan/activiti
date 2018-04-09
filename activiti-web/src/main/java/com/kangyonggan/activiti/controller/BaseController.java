package com.kangyonggan.activiti.controller;

import com.kangyonggan.activiti.constants.AppConstants;
import com.kangyonggan.activiti.constants.Resp;
import com.kangyonggan.activiti.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author kangyonggan
 * @date 2016/12/10
 */
@Log4j2
public class BaseController {

    private static final String DASHBOARD = "dashboard";
    private String pathRoot;
    private static final String LIST = "/list";
    private static final String INDEX = "/index";
    private static final String FORM = "/form";
    private static final String FORM_MODAL = "/form-modal";
    private static final String DETAIL = "/detail";
    private static final String DETAIL_MODAL = "/detail-modal";
    private static final String TABLE_TR = "/table-tr";

    public BaseController() {
        String className = getClass().getSimpleName();
        String[] arr = StringUtil.camelToArray(className);

        pathRoot = "";
        for (int i = 0; i < arr.length - 1; i++) {
            if (i != 0) {
                pathRoot += "/";
            }
            pathRoot += arr[i];
        }

        if (!pathRoot.startsWith(DASHBOARD)) {
            pathRoot = "web/" + pathRoot;
        }
    }

    protected String getPathRoot() {
        return pathRoot;
    }

    protected String getPathIndex() {
        return pathRoot + INDEX;
    }

    protected String getPathList() {
        return pathRoot + LIST;
    }

    protected String getPathForm() {
        return pathRoot + FORM;
    }

    protected String getPathDetail() {
        return pathRoot + DETAIL;
    }

    protected String getPathFormModal() {
        return pathRoot + FORM_MODAL;
    }

    protected String getPathDetailModal() {
        return pathRoot + DETAIL_MODAL;
    }

    protected String getPathTableTr() {
        return pathRoot + TABLE_TR;
    }

    protected Map<String, Object> getResultMap() {
        Map<String, Object> resultMap = new HashedMap();
        resultMap.put(AppConstants.RESP_CO, Resp.SUCCESS.getRespCo());
        resultMap.put(AppConstants.RESP_MSG, Resp.SUCCESS.getRespMsg());

        return resultMap;
    }

    protected void setResultMapFailure(Map<String, Object> resultMap) {
        setResultMapFailure(resultMap, null);
    }

    protected void setResultMapFailure(Map<String, Object> resultMap, String msg) {
        resultMap.put(AppConstants.RESP_CO, Resp.FAILURE.getRespCo());
        resultMap.put(AppConstants.RESP_MSG, StringUtils.isEmpty(msg) ? Resp.FAILURE.getRespMsg() : msg);
    }
}

