<#assign ctx="${(rca.contextPath)!''}">
<#assign modal_title="审批历史" />

<@override name="modal-body">

<table id="his-table" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>审批人</th>
        <th>审批结果</th>
        <th>审批意见</th>
        <th>开始时间</th>
        <th>结束时间</th>
    </tr>
    </thead>
    <tbody>
        <#if replyDtos?size gt 0>
            <#list replyDtos as reply>
                <tr>
                    <td>${reply.replyUser}</td>
                    <#assign definitionApply=reply/>
                    <td><#include "status.ftl"></td>
                    <td>${reply.replyMsg}</td>
                    <td>${reply.startTime?datetime}</td>
                    <#if reply.endTime??>
                        <td>${reply.endTime?datetime}</td>
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
</@override>

<@extends name="../../modal-layout.ftl"/>