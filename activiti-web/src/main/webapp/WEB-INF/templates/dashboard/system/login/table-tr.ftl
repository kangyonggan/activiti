<#assign ctx="${(rca.contextPath)!''}">

<tr id="loginLog-${loginLog.id}">
    <td>${loginLog.username}</td>
    <td>${loginLog.ip}</td>
    <td><@c.relative_date datetime=loginLog.createdTime/></td>
</tr>