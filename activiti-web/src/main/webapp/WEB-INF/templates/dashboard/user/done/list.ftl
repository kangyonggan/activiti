<#assign ctx="${(rca.contextPath)!''}">
<#assign definitionKey = RequestParameters.definitionKey!'' />
<#assign serialNo = RequestParameters.serialNo!'' />

<div class="space-10"></div>

<form class="form-inline" method="get">
    <div class="form-group">
        <input name="serialNo" value="${serialNo}" class="form-control" placeholder="业务流水" autocomplete="off"/>
    </div>
    <div class="form-group">
        <select name="definitionKey" class="form-control">
            <option value="">-- 业务类型 --</option>
            <option value="audit_process" <#if definitionKey=="audit_process">selected</#if>>流程部署审核</option>
        </select>
    </div>
<@c.search_form_tool/>
</form>

<div class="space-10"></div>

<table id="task-table" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>业务流水</th>
        <th>业务类型</th>
        <th>审批人</th>
        <th>审批结果</th>
        <th>审批意见</th>
        <th>开始时间</th>
        <th>结束时间</th>
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
<@c.pagination url="${ctx}/dashboard#user/done" param="definitionKey=${definitionKey}&serialNo=${serialNo}"/>

<script src="${ctx}/static/app/js/dashboard/user/done/list.js"></script>
