<#assign ctx="${(rca.contextPath)!''}">
<#assign modal_title="${definitionApply.serialNo???string('编辑流程定义', '申请流程定义')}" />

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post" enctype="multipart/form-data"
      action="${ctx}/dashboard/user/activiti/${definitionApply.serialNo???string('update', 'save')}">
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <#if definitionApply.serialNo??>
                    <label>重新上传ZIP文件</label>
                    <input type="hidden" name="id" value="${definitionApply.id}"/>
                <#else>
                    <label class="required">上传ZIP文件</label>
                </#if>
            </div>
            <div class="col-md-7 controls">
                <input type="file" id="zipFile" name="zipFile"/>
            </div>
        </div>
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label class="required">备注</label>
            </div>
            <div class="col-md-7 controls">
                <textarea class="form-control" name="remark" rows="4" placeholder="备注：最多128个字符">${definitionApply.remark!''}</textarea>
            </div>
        </div>
    </div>
</form>
</@override>

<@override name="modal-footer">

    <@c.modal_form_tool/>

<script>
    var requireZip = "${definitionApply.serialNo???string('0', '1')}";
</script>
<script src="${ctx}/static/app/js/dashboard/user/activiti/form-modal.js"></script>
</@override>

<@extends name="../../modal-layout.ftl"/>