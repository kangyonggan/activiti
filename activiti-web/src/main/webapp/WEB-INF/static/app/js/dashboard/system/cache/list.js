$(function () {
    updateState("system/cache");
    var $table = $('#cache-table');

    $table.on('click', 'a[data-role=clear-cache]', function () {
        var $trigger = $(this);
        var url = $trigger.data('url');
        var title = $trigger.attr("title");

        $.messager.confirm("提示", "确定" + title + "吗?", function () {
            $.get(url).success(function () {
                window.location.hash = "system/cache?key=" + key + "&r=" + Math.random();
                Message.success("操作成功");
            }).error(function () {
                Message.error("网络错误，请稍后重试");
            })
        });
    });

    $("#clearall").click(function () {
        var href = $(this).attr("href");
        $.messager.confirm("提示", "确定清空吗?", function () {
            $.get(href).success(function () {
                window.location.hash = "system/cache?key=" + key + "&r=" + Math.random();
                Message.success("操作成功");
            }).error(function () {
                Message.error("网络错误，请稍后重试");
            })
        });

        return false;
    });
});