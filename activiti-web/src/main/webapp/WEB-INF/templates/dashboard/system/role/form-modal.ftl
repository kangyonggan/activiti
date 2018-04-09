<#assign modal_title="${role.code???string('编辑角色', '添加新角色')}" />

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post"
      action="${ctx}/dashboard/system/role/${role.code???string('update', 'save')}">
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label class="required">角色代码</label>
            </div>
            <div class="col-md-7 controls">
                <#if role.code??>
                    <@s.formInput "role.code" 'class="form-control" readonly placeholder="格式参考:ROLE_ADMIN"'/>
                <#else>
                    <@s.formInput "role.code" 'class="form-control" placeholder="格式参考:ROLE_ADMIN"'/>
                </#if>
                <input type="hidden" id="old-code" value="${role.code!''}"/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label class="required">角色名称</label>
            </div>
            <div class="col-md-7 controls">
                <@s.formInput "role.name" 'class="form-control" placeholder="角色名称:2至12个汉字"'/>
            </div>
        </div>
    </div>
</@override>

<@override name="modal-footer">

    <@c.modal_form_tool/>

    <script src="${ctx}/static/app/js/dashboard/system/role/form-modal.js"></script>
</@override>

<@extends name="../../modal-layout.ftl"/>