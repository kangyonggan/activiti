<#assign ctx="${(rca.contextPath)!''}">

<div class="page-header">
    <h1>
        流程定义审核
    </h1>
</div>

<div class="space-10"></div>

<table id="task-table" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>任务ID</th>
        <th>任务名称</th>
        <th>创建时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <#if page.list?? && page.list?size gt 0>
        <#list page.list as task>
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
<@c.pagination url="${ctx}/dashboard#manage/audit"/>

<script src="${ctx}/static/app/js/dashboard/manage/audit/list.js"></script>
