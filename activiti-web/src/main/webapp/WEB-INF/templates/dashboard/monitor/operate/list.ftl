<#assign ctx="${(rca.contextPath)!''}">
<#assign app = RequestParameters.app!'' />
<#assign type = RequestParameters.type!'' />
<#assign hasReturnValue = RequestParameters.hasReturnValue!'' />

<div class="page-header">
    <h1>
        操作日志
    </h1>
</div>

<div class="space-10"></div>

<form class="form-inline" method="get">
    <div class="form-group">
        <input type="text" class="form-control" name="app" value="${app}" placeholder="应用名称"
               autocomplete="off"/>
    </div>
    <div class="form-group">
        <select class="form-control" name="type">
            <option value="">-- 操作类型 --</option>
            <option value="INSERT" <#if type=="INSERT">selected</#if>>新增</option>
            <option value="UPDATE" <#if type=="UPDATE">selected</#if>>更新</option>
            <option value="DELETE" <#if type=="DELETE">selected</#if>>删除</option>
        </select>
    </div>
    <div class="form-group">
        <select class="form-control" name="hasReturnValue">
            <option value="">-- 有无返回值 --</option>
            <option value="1" <#if hasReturnValue=="1">selected</#if>>有返回值</option>
            <option value="0" <#if hasReturnValue=="0">selected</#if>>无返回值</option>
        </select>
    </div>
<@c.search_form_tool/>
</form>

<div class="space-10"></div>

<table id="login-table" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>应用名称</th>
        <th>操作类型</th>
        <th>描述</th>
        <th>有无返回值</th>
        <th>操作用户</th>
        <th>开始时间</th>
        <th>结束时间</th>
    </tr>
    </thead>
    <tbody>
    <#if page.list?? && page.list?size gt 0>
        <#list page.list as monitor>
            <#include "table-tr.ftl"/>
        </#list>
    <#else>
    <tr>
        <td colspan="20">
            <div class="empty">暂无查询记录</div>
        </td>
    </tr>
    </#if>
    </tbody>
</table>
<@c.pagination url="${ctx}/dashboard#monitor/operate" param="app=${app}&type=${type}&hasReturnValue=${hasReturnValue}"/>

<script src="${ctx}/static/app/js/dashboard/monitor/operate/list.js"></script>
