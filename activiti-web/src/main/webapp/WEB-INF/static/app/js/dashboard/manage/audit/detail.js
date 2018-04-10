$(function () {
    updateState("manage/audit");

    var $form = $('#form');
    var $btn = $("#submit");

    $form.validate({
        rules: {
            status: {
                required: true
            },
            replyMsg: {
                required: true,
                maxlength: 200
            }
        },
        submitHandler: function (form, event) {
            event.preventDefault();
            $btn.button('loading');
            $(form).ajaxSubmit({
                dataType: 'json',
                success: function (response) {
                    if (response.respCo == '0000') {
                        Message.success(response.respMsg);
                        window.location.hash = "manage/audit?r=" + Math.random();
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