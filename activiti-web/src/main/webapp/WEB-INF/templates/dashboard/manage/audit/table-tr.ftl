<#assign ctx="${(rca.contextPath)!''}">

<tr id="task-${task.taskId}">
    <td>${task.taskId}</td>
    <td>${task.taskName}</td>
    <td>${task.instanceId}</td>
    <td>${task.definitionId}</td>
    <td>${task.definitionName}</td>
    <td>${task.definitionKey}</td>
    <td>${task.definitionVersion}</td>
    <td>${task.deploymentId}</td>
    <td><@c.relative_date datetime=task.createdTime/></td>
    <td>
        <a class="btn btn-xs btn-inverse" href="${ctx}/dashboard#manage/audit/${task.taskId}">详情</a>
    </td>
</tr>