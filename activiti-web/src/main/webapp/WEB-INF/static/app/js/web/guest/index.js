$(function () {
    $("#navbar ul li.active").css("height", "60px");
    $(".breadcrumbs").css("min-height", "32px").css("border-bottom", "none");
    $(".top .form input").css("height", "34px");
    adOffset = "-130px";
    $(".ad").css("left", "-130px");
    $(".ad-btn").css("height", "160px");

    var $form = $('#guest-form');
    var $btn = $form.find("button");

    $form.validate({
        rules: {
            content: {
                required: true,
                maxlength: 2048
            },
            realname: {
                required: true,
                maxlength: 32
            },
            email: {
                required: true,
                maxlength: 64,
                email: true
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
                        $form.find("input,textarea").val("");
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

// 提示信息
var last_gritter;
var showMessage = function (type, message) {
    if (last_gritter) {
        $.gritter.remove(last_gritter);
    }
    last_gritter = $.gritter.add({
        title: '消息',
        text: message,
        time: 1500,
        class_name: type
    });
};

var Message = {
    success: function (message) {
        showMessage('gritter-success', message);
    },

    warning: function (message) {
        showMessage('gritter-warning', message);
    },

    error: function (message) {
        showMessage('gritter-error', message);
    },

    info: function (message) {
        showMessage('gritter-info', message);
    }
};