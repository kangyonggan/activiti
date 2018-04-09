$(function () {
    updateState("manage/article");

    /**
     * 初始化markdown编辑器
     */
    $("#content").markdown({resize: 'vertical'});

    $('#files').ace_file_input({
        style: 'well',
        btn_choose: '点击此处或拖拽文件到这里（支持多选）',
        btn_change: null,
        no_icon: 'ace-icon fa fa-cloud-upload',
        droppable: true,
        thumbnail: 'small'
    });

    var $form = $('#article-form');
    var $btn = $("#submit");

    $form.validate({
        rules: {
            title: {
                required: true,
                rangelength: [1, 128]
            },
            summary: {
                required: true,
                rangelength: [1, 1024]
            },
            categoryCode: {
                required: true
            },
            isTop: {
                required: true
            },
            content: {
                required: true
            }
        },
        submitHandler: function (form, event) {
            event.preventDefault();
            $btn.button('loading');
            $(form).ajaxSubmit({
                dataType: 'json',
                success: function (response) {
                    if (response.respCo == '0000') {
                        window.location.hash = "manage/article?r=" + Math.random()
                        Message.success(response.respMsg);
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

    /**
     * 删除附件
     */
    $(".attachment .remove").click(function () {
        var articleId = $(this).attr("data-article-id");
        var url = $(this).attr("data-url");
        $.messager.confirm("提示", "确定删除[" + $(this).attr("data-name") + "]吗?", function () {
            $.get(url).success(function () {
                window.location.hash = "manage/article/" + articleId + "/edit?r=" + Math.random();
                Message.success("操作成功");
            }).error(function () {
                Message.error("网络错误，请稍后重试");
            })
        });
    });
});