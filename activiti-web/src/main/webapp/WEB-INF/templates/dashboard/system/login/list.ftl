<#assign ctx="${(rca.contextPath)!''}">
<#assign username = RequestParameters.username!'' />
<#assign ip = RequestParameters.ip!'' />
<#assign beginDate = RequestParameters.beginDate!'' />
<#assign endDate = RequestParameters.endDate!'' />

<link rel="stylesheet" href="${ctx}/static/ace/dist/css/datepicker.min.css" />

<div class="page-header">
    <h1>
        登录日志
    </h1>
</div>

<div class="space-10"></div>

<form class="form-inline" method="get">
    <div class="form-group">
        <input type="text" class="form-control" name="username" value="${username}" placeholder="用户名"
               autocomplete="off"/>
    </div>
    <div class="form-group">
        <input type="text" class="form-control" name="ip" value="${ip}" placeholder="登录IP"
               autocomplete="off"/>
    </div>
    <div class="form-group">
        <input class="form-control date-picker readonly" readonly name="beginDate" value="${beginDate}" placeholder="起始日期" data-date-format="yyyy-mm-dd"/>
    </div>
    <div class="form-group">
        <input class="form-control date-picker readonly" readonly name="endDate" value="${endDate}" placeholder="终止日期" data-date-format="yyyy-mm-dd"/>
    </div>
<@c.search_form_tool/>
</form>

<div class="space-10"></div>

<table id="login-table" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>用户名</th>
        <th>登录IP</th>
        <th>登录时间</th>
    </tr>
    </thead>
    <tbody>
    <#if page.list?? && page.list?size gt 0>
        <#list page.list as loginLog>
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
<@c.pagination url="${ctx}/dashboard#system/login" param="username=${username}&ip=${ip}&beginDate=${beginDate}&endDate=${endDate}"/>

<script src="${ctx}/static/ace/dist/js/date-time/bootstrap-datepicker.min.js"></script>
<script src="${ctx}/static/app/js/dashboard/system/login/list.js"></script>
