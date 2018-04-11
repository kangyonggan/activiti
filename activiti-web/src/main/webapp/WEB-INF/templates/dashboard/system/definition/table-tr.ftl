<#assign ctx="${(rca.contextPath)!''}">

<tr id="definition-${definition.id}">
    <td>${definition.deploymentId}</td>
    <td>${definition.id}</td>
    <td>${definition.name}</td>
    <td>${definition.key}</td>
    <td>${definition.version}</td>
    <td>
        <div class="btn-group">
            <a class="btn btn-xs btn-inverse" target="_blank"
               href="${ctx}/dashboard/system/definition/${definition.id}">查看</a>

            <button data-toggle="dropdown" class="btn btn-xs btn-inverse dropdown-toggle">
                <span class="ace-icon fa fa-caret-down icon-only"></span>
            </button>

            <ul class="dropdown-menu dropdown-menu-right dropdown-inverse">
                <li>
                    <a href="javascript:" data-role="definition-delete" title="删除流程定义"
                       data-url="${ctx}/dashboard/system/definition/${definition.deploymentId}/delete">
                        删除流程定义
                    </a>
                </li>
            </ul>
        </div>
    </td>
</tr>