<#assign ctx="${(rca.contextPath)!''}">
<#assign id = RequestParameters.id!'' />
<#assign name = RequestParameters.name!'' />
<#assign key = RequestParameters.key!'' />

<div class="page-header">
    <h1>
        流程定义列表
    </h1>
</div>

<div class="space-10"></div>

<form class="form-inline" method="get">
    <div class="form-group">
        <input type="text" class="form-control" name="id" value="${id}" placeholder="定义ID"
               autocomplete="off"/>
    </div>
    <div class="form-group">
        <input type="text" class="form-control" name="name" value="${name}" placeholder="定义名称"
               autocomplete="off"/>
    </div>
    <div class="form-group">
        <input type="text" class="form-control" name="key" value="${key}" placeholder="定义的KEY"
               autocomplete="off"/>
    </div>

<@c.search_form_tool/>
</form>

<div class="space-10"></div>

<table id="definition-table" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>ID</th>
        <th>定义名称</th>
        <th>定义的KEY</th>
        <th>版本</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <#if page.list?? && page.list?size gt 0>
        <#list page.list as definition>
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
<@c.pagination url="${ctx}/dashboard#manage/definition" param="id=${id}&name=${name}&key=${key}"/>

<script src="${ctx}/static/app/js/dashboard/manage/definition/list.js"></script>
