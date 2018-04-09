$(function () {
    updateState("system/sql");

    var $form = $('#form');
    var $btn = $("#submit");

    $form.validate({
        rules: {
            sql: {
                required: true,
                maxlength: 256
            }
        },
        submitHandler: function (form, event) {
            event.preventDefault();
            $("#sql-table thead tr").empty();
            $("#sql-table tbody").empty();
            $btn.button('loading');
            $(form).ajaxSubmit({
                dataType: 'json',
                success: function (response) {
                    if (response.respCo == '0000') {
                        if (response.list) {
                            Message.success("查询成功");
                            drawTable(response.list);
                        } else {
                            Message.success("执行成功， 影响行数：" + response.count);
                        }

                    } else {
                        Message.error(response.respMsg);
                    }
                    $btn.button('reset');
                },
                error: function () {
                    Message.error("服务器内部错误，请稍后再试。");
                    $btn.button('reset');
                }
            });
        },
        errorPlacement: function (error, element) {
            error.appendTo(element.parent());
        },
        errorElement: "div",
        errorClass: "error"
    });

    // 查询结果显示
    function drawTable(list) {
        var len = 0;
        if (list.length > 0) {
            var item = list[0];
            $("#sql-table thead tr").append("<td>#</td>");
            Object.keys(item).forEach(function (key) {
                $("#sql-table thead tr").append("<td>" + key + "</td>");
                len++;
            });
        }

        var str = "";
        for (var i = 0; i < list.length; i++) {
            var item = list[i];

            str += "<tr>";
            str += "<td>" + (i + 1) + "</td>";
            Object.keys(item).forEach(function (key) {
                str += "<td>" + item[key] + "</td>";
            });
            str += "</tr>";
        }

        $("#sql-table tbody").append(str);
    }
});