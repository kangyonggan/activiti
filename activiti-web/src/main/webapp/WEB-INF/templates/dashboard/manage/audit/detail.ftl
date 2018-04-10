<#assign ctx="${(rca.contextPath)!''}">

<div class="space-10"></div>

<div class="tabbable">
    <ul class="nav nav-tabs padding-16">
        <li class="active">
            <a data-toggle="tab" href="#apply-info" aria-expanded="true">
                <i class="blue ace-icon fa fa-pencil-square-o bigger-125"></i>
                申请信息
            </a>
        </li>

        <li class="">
            <a data-toggle="tab" href="#reply-info" aria-expanded="false">
                <i class="green ace-icon fa fa-check-square-o bigger-125"></i>
                审核信息
            </a>
        </li>
    </ul>

    <div class="tab-content profile-edit-tab-content">
        <div id="apply-info" class="tab-pane active">
            <div class="space-10"></div>

            <div class="col-sm-6">
                <div class="row">
                    <div class="col-xs-11 label label-lg label-info arrowed-in arrowed-right">
                        <b>用户信息</b>
                    </div>
                </div>

                <div>
                    <ul class="list-unstyled spaced">
                        <div class="space-6"></div>
                        <li>
                            <i class="ace-icon fa fa-caret-right blue"></i>用户号：${applyUser.username}
                        </li>

                        <div class="space-6"></div>
                        <li>
                            <i class="ace-icon fa fa-caret-right blue"></i>真实姓名：${applyUser.realname}
                        </li>

                        <div class="space-6"></div>
                        <li>
                            <i class="ace-icon fa fa-caret-right blue"></i>创建时间：${applyUser.createdTime?datetime}
                        </li>
                    </ul>
                </div>
            </div>

            <div class="col-sm-6">
                <div class="row">
                    <div class="col-xs-11 label label-lg label-success arrowed-in arrowed-right">
                        <b>流程包信息</b>
                    </div>
                </div>

                <div>
                    <ul class="list-unstyled spaced">
                        <div class="space-6"></div>
                        <li>
                            <i class="ace-icon fa fa-caret-right green"></i>流水号：${definitionApply.serialNo}
                        </li>

                        <div class="space-6"></div>
                        <li>
                            <i class="ace-icon fa fa-caret-right green"></i>备注：${definitionApply.remark}
                        </li>

                        <div class="space-6"></div>
                        <li>
                            <i class="ace-icon fa fa-caret-right green"></i>ZIP文件：<a target="_blank" href="${ctx}/${definitionApply.zipPath}">${definitionApply.zipName}</a>
                        </li>
                    </ul>
                </div>
            </div>

            <div style="clear: both;height: 1px;width: 1px;"></div>
        </div>

        <div id="reply-info" class="tab-pane">
            <form id="form" method="post" action="${ctx}/dashboard/manage/audit/${task.taskId}" class="form-horizontal">
                <div class="space-10"></div>

                <input type="hidden" name="applyId" value="${definitionApply.id}"/>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right required">审批结果</label>

                    <div class="col-xs-12 col-sm-5">
                        <select name="status" class="form-control">
                            <option value="">-- 选择审批结果 --</option>
                            <option value="COMPLETE">同意</option>
                            <option value="REJECT">拒绝</option>
                            <option value="BACK">退回</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right required">审批意见</label>

                    <div class="col-xs-12 col-sm-5">
                        <textarea class="form-control" name="replyMsg" rows="4" placeholder="请输入审批意见，不超过200字"></textarea>
                    </div>
                </div>

                <div class="clearfix form-actions">
                    <div class="col-xs-offset-3">
                        <button id="submit" class="btn btn-success" data-loading-text="正在<@s.message "app.button.save"/>...">
                            <i class="ace-icon fa fa-check"></i>
                        <@s.message "app.button.save"/>
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="${ctx}/static/app/js/dashboard/manage/audit/detail.js"></script>