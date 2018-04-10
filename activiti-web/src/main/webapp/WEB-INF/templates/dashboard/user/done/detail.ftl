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
                审批信息
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
                        <b>部署信息</b>
                    </div>
                </div>

                <div>
                    <ul class="list-unstyled spaced">
                        <div class="space-6"></div>
                        <li>
                            <i class="ace-icon fa fa-caret-right green"></i>业务流水号：${definitionApply.serialNo}
                        </li>

                        <div class="space-6"></div>
                        <li>
                            <i class="ace-icon fa fa-caret-right green"></i>备注：${definitionApply.remark}
                        </li>

                        <div class="space-6"></div>
                        <li>
                            <i class="ace-icon fa fa-caret-right green"></i>ZIP文件：<a target="_blank"
                                                                                     href="${ctx}/${definitionApply.zipPath}">${definitionApply.zipName}</a>
                        </li>
                    </ul>
                </div>
            </div>

            <div style="clear: both;height: 1px;width: 1px;"></div>
        </div>

        <div id="reply-info" class="tab-pane">
            <form class="form-horizontal">
                <div class="space-10"></div>

                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right required">审批结果</label>

                    <div class="col-xs-12 col-sm-5">
                        <select name="status" class="form-control readonly" disabled>
                            <option value="">${status}</option>
                            <option value="COMPLETE" <#if status=="COMPLETE">selected</#if>>同意</option>
                            <option value="REJECT" <#if status=="REJECT">selected</#if>>拒绝</option>
                            <option value="BACK" <#if status=="BACK">selected</#if>>退回</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right required">审批意见</label>

                    <div class="col-xs-12 col-sm-5">
                        <textarea class="form-control readonly" name="replyMsg" rows="4" readonly
                                  placeholder="请输入审批意见，不超过200字">${replyMsg}</textarea>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="${ctx}/static/app/js/dashboard/user/done/detail.js"></script>