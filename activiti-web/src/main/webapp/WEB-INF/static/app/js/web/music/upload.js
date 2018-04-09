$(function () {
    $("#navbar ul li.active").css("height", "60px");
    $(".breadcrumbs").css("min-height", "32px").css("border-bottom", "none");
    $(".top .form input").css("height", "34px");
    adOffset = "-130px";
    $(".ad").css("left", "-130px");
    $(".ad-btn").css("height", "160px");

    var $form = $('#music-form');
    var $btn = $form.find("button");

    var file_input = $form.find('input[type=file]');
    file_input.ace_file_input({
        style: 'well',
        btn_choose: '点击这里添加MP3',
        btn_change: null,
        no_icon: 'ace-icon fa fa-file',
        droppable: false,
        allowExt: ["mp3"],
        maxSize: 209715200,//bytes
        thumbnail: 'fit'
    });

    file_input.on('file.error.ace', function (event, info) {
        if (info.error_count['size']) Message.warning('超出最大上传限制。');
        if (info.error_count['ext'] || info.error_count['mime']) Message.warning('不合法的文件类型。');
        event.preventDefault();
    });

    $form.validate({
        ignore: '',
        rules: {
            uploadRemark: {
                required: true,
                maxlength: 256
            },
            categoryCode: {
                required: true
            },
            uploadUsername: {
                required: true,
                maxlength: 16
            },
            file: {
                required: true
            }
        },
        submitHandler: function (form, event) {
            $btn.button('loading');
            return true;
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