<#assign ctx="${(rca.contextPath)!''}">
<#assign key = RequestParameters.key!'' />

<div class="page-header">
    <h1>
        缓存列表
        <small class="pull-right">
            <a href="${ctx}/dashboard/system/cache/clearall?key=${key}" id="clearall" class="btn btn-sm btn-danger">清空列表</a>
        </small>
    </h1>
</div>

<div class="space-10"></div>

<form class="form-inline" method="get" novalidate>
    <div class="form-group">
        <input type="text" name="key" value="${key}" class="form-control" placeholder="缓存的键"/>
    </div>

<@c.search_form_tool/>
</form>

<div class="space-10"></div>

<table id="cache-table" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>缓存的键</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <#if keys?size gt 0>
        <#list keys as k>
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

<script>
    var key = '${key}';
</script>
<script src="${ctx}/static/app/js/dashboard/system/cache/list.js"></script>
