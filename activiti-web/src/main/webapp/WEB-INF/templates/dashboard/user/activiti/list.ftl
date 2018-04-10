<#assign ctx="${(rca.contextPath)!''}">
<#assign serialNo = RequestParameters.serialNo!'' />
<#assign remark = RequestParameters.remark!'' />
<#assign status = RequestParameters.status!'' />

<div class="page-header">
    <h1>
        部署列表
        <small class="pull-right">
            <a href="${ctx}/dashboard/user/activiti/create" class="btn btn-sm btn-pink" data-toggle="modal"
               data-target="#myModal"
               data-backdrop="static">部署</a>
        </small>
    </h1>
</div>

<div class="space-10"></div>

<form class="form-inline" method="get">
    <div class="form-group">
        <input type="text" class="form-control" name="serialNo" value="${serialNo}" placeholder="业务流水号"
               autocomplete="off"/>
    </div>
    <div class="form-group">
        <input type="text" class="form-control" name="remark" value="${remark}" placeholder="备注"
               autocomplete="off"/>
    </div>
    <div class="form-group">
        <select name="status" class="form-control">
            <option value="">-- 状态 --</option>
        <#list statuses as s>
            <option value="${s.getStatus()}" <#if status=='${s.getStatus()}'>selected</#if>>${s.getName()}</option>
        </#list>
        </select>
    </div>
<@c.search_form_tool/>
</form>

<div class="space-10"></div>

<table id="activiti-table" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>业务流水号</th>
        <th>备注</th>
        <th>ZIP文件</th>
        <th>状态</th>
        <th>创建时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <#if page.list?? && page.list?size gt 0>
        <#list page.list as definitionApply>
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
<@c.pagination url="${ctx}/dashboard#user/activiti" param="serialNo=${serialNo}&remark=${remark}&status=${status}"/>

<script src="${ctx}/static/app/js/dashboard/user/activiti/list.js"></script>
