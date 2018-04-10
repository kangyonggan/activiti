<#assign ctx="${(rca.contextPath)!''}">

<tr id="definitionApply-${definitionApply.id}">
    <td>${definitionApply.serialNo}</td>
    <td>${definitionApply.remark}</td>
    <td><#include "status.ftl"></td>
    <td><@c.relative_date datetime=definitionApply.createdTime/></td>
    <td>
        操作
    </td>
</tr>