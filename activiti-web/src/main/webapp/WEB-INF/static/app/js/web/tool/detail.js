$(function () {
    $("#navbar ul li.active").css("height", "60px");
    $(".breadcrumbs").css("min-height", "32px").css("border-bottom", "none");
    $(".top .form input").css("height", "34px");
    adOffset = "-130px";
    $(".ad").css("left", "-130px");
    $(".ad-btn").css("height", "160px");

    var $form = $('#tool-form');
    var $btn = $form.find("button");

    var file_input = $("#file");
    file_input.ace_file_input({
        style: 'well',
        btn_choose: '点击这里添加图片',
        btn_change: null,
        no_icon: 'ace-icon fa fa-picture-o',
        droppable: false,
        allowExt: ["jpeg", "jpg", "png", "gif"],
        allowMime: ["image/jpeg", "image/jpg", "image/png", "image/gif"],
        maxSize: 2097152,//bytes
        thumbnail: 'fit'
    });

    file_input.on('file.error.ace', function (event, info) {
        if (info.error_count['size']) Message.warning('超出最大上传限制。');
        if (info.error_count['ext'] || info.error_count['mime']) Message.warning('不合法的文件类型。');
        event.preventDefault();
    });

    var $props = $("#props");
    $props.ace_file_input({
        style: 'well',
        btn_choose: '点击这里添加两个properties文件',
        btn_change: null,
        no_icon: 'ace-icon fa fa-file',
        droppable: false,
        allowExt: ["properties"],
        maxSize: 2097152,//bytes
        thumbnail: 'fit'
    });

    $props.on('file.error.ace', function (event, info) {
        if (info.error_count['size']) Message.warning('超出最大上传限制。');
        if (info.error_count['ext']) Message.warning('不合法的文件类型。');
        event.preventDefault();
    });

    $form.validate({
        ignore: '',
        rules: {
            data: {
                required: true
            },
            file: {
                required: true
            },
            props: {
                required: true
            },
            size: {
                required: true
            },
            dialect: {
                required: true
            },
            startAge: {
                required: true,
                range: [1, 100]
            },
            endAge: {
                required: true,
                range: [1, 100]
            },
            sex: {
                required: true
            },
            len: {
                required: true
            },
            count: {
                required: true,
                range: [1, 100]
            },
            lunar: {
                required: true
            },
            year: {
                required: true,
                range: [1900, 2049]
            },
            month: {
                required: true,
                range: [1, 12]
            },
            day: {
                required: true,
                range: [1, 31]
            },
            hour: {
                required: true,
                range: [0, 23]
            },
            charset: {
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

    $("#reset").click(function () {
        $form.find("input,textarea,select").val("");
        return false;
    });

    $(".tool-result pre").addClass("prettyprint linenums");
    prettyPrint();
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