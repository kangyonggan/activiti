$(function () {
    updateState("manage/definition");

    var $table = $('#definition-table');

    $table.on('click', 'a[data-role=definition-delete]', function () {
        var $trigger = $(this);
        var url = $trigger.data('url');

        var title = $trigger.attr("title");

        $.messager.confirm("提示", "确定" + title + "吗?", function () {
            $.get(url).success(function (response) {
                response = eval('(' + response + ')');
                if (response.respCo == '0000') {
                    window.location.hash = "manage/definition?r=" + Math.random();
                    Message.success(response.respMsg);
                } else {
                    Message.error(response.respMsg);
                }
            }).error(function () {
                Message.error("网络错误，请稍后重试");
            })
        });
    });
});