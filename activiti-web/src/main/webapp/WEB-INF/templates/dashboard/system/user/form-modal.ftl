<#assign ctx="${(rca.contextPath)!''}">
<#assign modal_title="${user.username???string('编辑用户', '添加新用户')}" />

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post"
      action="${ctx}/dashboard/system/user/${user.username???string('update', 'save')}">
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label class="required">用户名</label>
            </div>
            <div class="col-md-7 controls">
                <#if user.username??>
                    <@s.formInput "user.username" 'class="form-control" readonly placeholder="用户名：5至20位的字母数字组合"'/>
                <#else>
                    <@s.formInput "user.username" 'class="form-control" placeholder="用户名：5至20位的字母数字组合"'/>
                </#if>
                <input type="hidden" id="old-username" value="${user.username!''}"/>
            </div>
        </div>
    </div>
    <#if !user.username??>
        <div class="row">
            <div class="row form-group">
                <div class="col-md-3 control-label">
                    <label class="required">密码</label>
                </div>
                <div class="col-md-7 controls">
                    <input type="password" id="password" name="password" class="form-control" placeholder="密码：6至20位的字母数字组合"/>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="row form-group">
                <div class="col-md-3 control-label">
                    <label class="required">确认密码</label>
                </div>
                <div class="col-md-7 controls">
                    <input type="password" name="rePassword" class="form-control" placeholder="确认密码：6至20位的字母数字组合"/>
                </div>
            </div>
        </div>
    </#if>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label class="required">真实姓名</label>
            </div>
            <div class="col-md-7 controls">
                <@s.formInput "user.realname" 'class="form-control" placeholder="真实姓名：最多12个汉字"'/>
            </div>
        </div>
    </div>
</form>
</@override>

<@override name="modal-footer">

    <@c.modal_form_tool/>

<script src="${ctx}/static/app/js/dashboard/system/user/form-modal.js"></script>
</@override>

<@extends name="../../modal-layout.ftl"/>