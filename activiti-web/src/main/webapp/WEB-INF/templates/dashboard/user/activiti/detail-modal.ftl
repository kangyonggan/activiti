<#assign ctx="${(rca.contextPath)!''}">
<#assign modal_title="审批历史" />

<@override name="modal-body">

<table id="his-table" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>ID</th>
        <th>实例ID</th>
        <th>流程名称</th>
        <th>委托人</th>
        <th>审批意见</th>
        <th>开始时间</th>
        <th>结束时间</th>
    </tr>
    </thead>
    <tbody>
        <#if historicTaskInstances?size gt 0>
            <#list historicTaskInstances as his>
                <tr>
                    <td>${his.id}</td>
                    <td>${his.processInstanceId}</td>
                    <td>${his.name}</td>
                    <td>${his.assignee}</td>
                    <td>xxx</td>
                    <td>${his.startTime?datetime}</td>
                    <#if his.endTime??>
                        <td>${his.endTime?datetime}</td>
                    <#else>
                        <td></td>
                    </#if>
                </tr>
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
</@override>

<@override name="modal-footer">
<button class="btn btn-sm" data-dismiss="modal">
    <i class="ace-icon fa fa-times"></i>
    <@s.message "app.button.cancel"/>
</button>
<script src="${ctx}/static/app/js/dashboard/user/activiti/detail-modal.js"></script>
</@override>

<@extends name="../../modal-layout.ftl"/>