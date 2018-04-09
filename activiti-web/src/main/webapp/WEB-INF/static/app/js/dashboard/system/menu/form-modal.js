$(function () {
    var $form = $('#modal-form');
    var $btn = $("#submit");
    var $modal = $form.parents('.modal');

    $form.validate({
        rules: {
            code: {
                required: true,
                isMenuCode: true,
                remote: {
                    url: "/validate/menu",
                    type: 'post',
                    data: {
                        'code': function () {
                            return $('#code').val()
                        },
                        'oldCode': function () {
                            return $('#old-code').val();
                        }
                    }
                }
            },
            name: {
                required: true,
                isRealName: true
            },
            url: {
                required: true,
                isMenuUrl: true
            },
            sort: {
                required: true,
                range: [0, 100]
            }
        },
        submitHandler: function (form, event) {
            event.preventDefault();
            $btn.button('loading');
            $(form).ajaxSubmit({
                dataType: 'json',
                success: function (response) {
                    if (response.respCo == '0000') {
                        window.location.hash = "system/menu?r=" + Math.random();
                        Message.success(response.respMsg);
                        $modal.modal('hide');
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