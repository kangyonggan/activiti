<#assign ctx="${(rca.contextPath)!''}">

<div class="space-10"></div>

<form id="form" method="post" action="${ctx}/dashboard/system/sql/submit" class="form-horizontal">

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right">查询/更新</label>

        <div class="col-xs-12 col-sm-5">
            <select name="type" class="form-control">
                <option value="query">查询</option>
                <option value="exec">更新</option>
            </select>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-2 control-label no-padding-right">SQL脚本</label>

        <div class="col-xs-12 col-sm-9">
            <textarea class="form-control" name="sql" rows="8" placeholder="请输入SQL脚本"></textarea>
        </div>
    </div>


    <div class="clearfix form-actions">
        <div class="col-xs-offset-3">
            <button id="submit" class="btn btn-success" data-loading-text="正在提交...">
                <i class="ace-icon fa fa-check"></i>
            <@s.message "app.button.save"/>
            </button>
        </div>
    </div>
</form>

<div class="space-10"></div>

<table id="sql-table" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
    </tr>
    </thead>
    <tbody>
    </tbody>
</table>

<script src="${ctx}/static/app/js/dashboard/system/sql/index.js"></script>
