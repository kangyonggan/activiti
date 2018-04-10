<#assign ctx="${(rca.contextPath)!''}">

<tr id="monitor-${monitor.id}">
    <td>${monitor.app}</td>
    <td>
        <#if monitor.type=="INSTALL">
            新增
        <#elseif monitor.type=="UPDATE">
            更新
        <#elseif monitor.type=="DELETE">
            删除
        <#else>
            ${monitor.type}
        </#if>
    </td>
    <td>${monitor.description}</td>
    <td>${(monitor.hasReturnValue==1)?string('有', '无')}</td>
    <td>${monitor.username}</td>
    <td>${monitor.beginTime?datetime}</td>
    <td>${monitor.endTime?datetime}</td>
</tr>