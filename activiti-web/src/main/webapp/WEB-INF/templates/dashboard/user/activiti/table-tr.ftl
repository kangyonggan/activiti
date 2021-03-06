<#assign ctx="${(rca.contextPath)!''}">

<tr id="definitionApply-${definitionApply.id}">
    <td>${definitionApply.serialNo}</td>
    <td>${definitionApply.remark}</td>
    <td>
        <a href="${ctx}/${definitionApply.zipPath}" target="_blank">${definitionApply.zipName}</a>
    </td>
    <td><#include "status.ftl"></td>
    <td><@c.relative_date datetime=definitionApply.createdTime/></td>
    <td>
        <div class="btn-group">
            <a class="btn btn-xs btn-inverse"
               href="${ctx}/dashboard#user/activiti/${definitionApply.serialNo}/progress">查看进度</a>

            <button data-toggle="dropdown" class="btn btn-xs btn-inverse dropdown-toggle">
                <span class="ace-icon fa fa-caret-down icon-only"></span>
            </button>

            <ul class="dropdown-menu dropdown-menu-right dropdown-inverse">
                <li>
                    <a href="${ctx}/dashboard/user/activiti/${definitionApply.serialNo}"
                       data-toggle="modal" data-target="#myLargeModal"
                       data-backdrop="static">审批历史</a>
                </li>
            <#if definitionApply.status == "BACK">
                <li>
                    <a href="${ctx}/dashboard/user/activiti/${definitionApply.id}/edit" data-toggle="modal"
                       data-target="#myModal"
                       data-backdrop="static">重新修改</a>
                </li>
            </#if>
            </ul>
        </div>
    </td>
</tr>