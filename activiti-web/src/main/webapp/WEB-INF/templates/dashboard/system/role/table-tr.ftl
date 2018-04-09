<#assign ctx="${(rca.contextPath)!''}">

<tr id="role-${role.id}">
    <td>${role.code}</td>
    <td>${role.name}</td>
    <td><#include "delete.ftl"></td>
    <td><@c.relative_date datetime=role.createdTime/></td>
    <td>
        <div class="btn-group">
            <a class="btn btn-xs btn-inverse" href="${ctx}/dashboard/system/role/${role.code}/edit"
               data-toggle="modal" data-target="#myModal"
               data-backdrop="static">编辑</a>

            <button data-toggle="dropdown" class="btn btn-xs btn-inverse dropdown-toggle">
                <span class="ace-icon fa fa-caret-down icon-only"></span>
            </button>

            <ul class="dropdown-menu dropdown-menu-right dropdown-inverse">
                <li>
                    <a href="${ctx}/dashboard/system/role/${role.code}/menus" data-toggle="modal" data-target="#myModal"
                       data-backdrop="static">设置权限</a>
                </li>
                <#if role.isDeleted==1>
                    <li>
                        <a href="javascript:" data-role="role-remove" title="彻底删除角色"
                           data-url="${ctx}/dashboard/system/role/${role.code}/remove">
                            彻底删除
                        </a>
                    </li>
                </#if>
            </ul>
        </div>
    </td>
</tr>