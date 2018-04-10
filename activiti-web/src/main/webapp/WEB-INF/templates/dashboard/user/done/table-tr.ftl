<#assign ctx="${(rca.contextPath)!''}">

<tr id="task-${task.taskId}">
    <td>${task.serialNo}</td>
    <td>
        <span class="label label-pink arrowed-right">${task.definitionName}</span>
    </td>
    <td>${task.startTime?datetime}</td>
    <td>${task.endTime?datetime}</td>
    <td>
        <a class="btn btn-xs btn-inverse" href="${ctx}/dashboard#user/done/${task.taskId}">查看</a>
    </td>
</tr>