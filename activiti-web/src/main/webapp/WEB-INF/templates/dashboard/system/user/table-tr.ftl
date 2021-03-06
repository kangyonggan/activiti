<#assign ctx="${(rca.contextPath)!''}">

<tr id="user-${user.id}">
    <td>${user.username}</td>
    <td>${user.realname}</td>
    <td><#include "delete.ftl"></td>
    <td><@c.relative_date datetime=user.createdTime/></td>
    <td>
        <div class="btn-group">
            <a class="btn btn-xs btn-inverse" href="${ctx}/dashboard/system/user/${user.username}/edit"
               data-toggle="modal" data-target="#myModal"
               data-backdrop="static">编辑</a>

            <button data-toggle="dropdown" class="btn btn-xs btn-inverse dropdown-toggle">
                <span class="ace-icon fa fa-caret-down icon-only"></span>
            </button>

            <ul class="dropdown-menu dropdown-menu-right dropdown-inverse">
                <li>
                    <a href="${ctx}/dashboard/system/user/${user.username}/password" data-toggle="modal"
                       data-target="#myModal"
                       data-backdrop="static">修改密码</a>
                </li>
                <li>
                    <a href="${ctx}/dashboard/system/user/${user.username}/roles" data-toggle="modal"
                       data-target="#myModal"
                       data-backdrop="static">设置角色</a>
                </li>
            <#if user.isDeleted == 1>
                <li>
                    <a href="javascript:" data-role="user-remove" title="彻底删除用户"
                       data-url="${ctx}/dashboard/system/user/${user.id}/remove">
                        彻底删除
                    </a>
                </li>
            </#if>
            </ul>
        </div>
    </td>
</tr>