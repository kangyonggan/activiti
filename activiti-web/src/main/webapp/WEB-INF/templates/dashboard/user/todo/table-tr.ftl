<#assign ctx="${(rca.contextPath)!''}">

<tr id="task-${task.taskId}">
    <td>${task.serialNo}</td>
    <td>
        <span class="label label-pink arrowed-right">${task.definitionName}</span>
    </td>
    <td><@c.relative_date datetime=task.createdTime/></td>
    <td>
        <a class="btn btn-xs btn-inverse" href="${ctx}/dashboard#user/todo/${task.taskId}">处理</a>
    </td>
</tr>