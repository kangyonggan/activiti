$(function () {
    updateState("manage/tool");

    var $form = $('#tool-form');
    var $btn = $("#submit");

    var file_input = $form.find('input[type=file]');
    file_input.ace_file_input({
        style: 'well',
        btn_choose: '点击这里添加图片',
        btn_change: null,
        no_icon: 'ace-icon fa fa-picture-o',
        droppable: false,
        allowExt: ["jpeg", "jpg", "png", "gif"],
        allowMime: ["image/jpeg", "image/jpg", "image/png", "image/gif"],
        maxSize: 104857600,//bytes
        thumbnail: 'fit'
    });

    file_input.on('file.error.ace', function (event, info) {
        if (info.error_count['size']) Message.warning('超出最大上传限制。');
        if (info.error_count['ext'] || info.error_count['mime']) Message.warning('不合法的文件类型。');
        event.preventDefault();
    });

    $form.validate({
        rules: {
            code: {
                required: true,
                rangelength: [1, 32]
            },
            name: {
                required: true,
                rangelength: [1, 32]
            },
            isTop: {
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
                        window.location.hash = "manage/tool?r=" + Math.random()
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
});