<#assign ctx="${(rca.contextPath)!''}">

<tr id="task-${task.id}">
    <td>${task.id}</td>
    <td>${task.name}</td>
    <td><@c.relative_date datetime=task.createTime/></td>
    <td>
        <a class="btn btn-xs btn-inverse" href="${ctx}/dashboard#manage/audit/${task.id}">详情</a>
    </td>
</tr>