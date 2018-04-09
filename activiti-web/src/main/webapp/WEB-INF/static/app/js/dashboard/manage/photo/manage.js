$(function () {
    updateState("manage/photo");

    try {
        Dropzone.autoDiscover = false;
        var myDropzone = new Dropzone("#photo-form", {
            init: function () {

            },

            // 上传文件的名称
            paramName: "file",

            // 其他参数
            params: {
                id: $("#photoId").val()
            },

            // 文件类型
            acceptedFiles: "image/jpg, image/jpeg, image/png, image/gif, image/bmp",
            dictInvalidFileType: "不支持此文件类型！",

            // 文件大小
            maxFilesize: 30, // MB
            dictFileTooBig: "图片太大({{filesize}}MB). 最大限制: {{maxFilesize}}MB.",

            // 删除按钮
            addRemoveLinks: true,
            dictRemoveFile: "删除文件",
            dictRemoveFileConfirmation: "确认删除文件？",

            // 删除文件
            removedfile: function (file) {
                $.get(ctx + "/dashboard/manage/photo/delete?fileId=" + file.fileId, function (data, status) {
                    console.log(data);
                    console.log(status);
                });
                var _ref;
                if (file.previewElement) {
                    if ((_ref = file.previewElement) != null) {
                        _ref.parentNode.removeChild(file.previewElement);
                    }
                }
                return this._updateMaxFilesReachedClass();
            },

            // 默认提示信息
            dictDefaultMessage:
                '<span class="bigger-150 bolder"><i class="ace-icon fa fa-caret-right red"></i>点击此处</span> 上传图片<br />\<span class="smaller-80 grey">每张不大于30MB，仅限*.jpg;*.png;*.jpeg格式</span> <br /> \<i class="upload-icon ace-icon fa fa-cloud-upload blue fa-3x"></i>',
            // 错误提示
            dictResponseError: '上传文件失败!',


            previewTemplate: "<div class=\"dz-preview dz-file-preview\">\n  <div class=\"dz-details\">\n    <div class=\"dz-filename\"><span data-dz-name></span></div>\n    <div class=\"dz-size\" data-dz-size></div>\n    <img data-dz-thumbnail />\n  </div>\n  <div class=\"progress progress-small progress-striped active\"><div class=\"progress-bar progress-bar-success\" data-dz-uploadprogress></div></div>\n  <div class=\"dz-success-mark\"><span></span></div>\n  <div class=\"dz-error-mark\"><span></span></div>\n  <div class=\"dz-error-message\"><span data-dz-errormessage></span></div>\n</div>"
        });

        if (attachmentsLen > 0) {
            $(".dz-default").remove();
        }

        // 上传完成回调事件, 获取到附件ID，以便删除
        Dropzone.prototype._finished = function (files, responseText, e) {
            var file, _i, _len;
            for (_i = 0, _len = files.length; _i < _len; _i++) {
                file = files[_i];
                file.status = Dropzone.SUCCESS;
                this.emit("success", file, responseText, e);
                this.emit("complete", file);
                var fileId = responseText.fileId;
                file.fileId = fileId;
            }
            if (this.options.uploadMultiple) {
                this.emit("successmultiple", files, responseText, e);
                this.emit("completemultiple", files);
            }
            if (this.options.autoProcessQueue) {
                return this.processQueue();
            }
        };

    } catch (e) {
        Message.warning('文件上传插件不支持此版本浏览器');
    }

    $(".dz-remove-old").click(function () {
        var fileId = $(this).attr("data-file-id");
        $.messager.confirm("提示", "确定删除照片吗?", function () {
            $.get(ctx + "/dashboard/manage/photo/delete?fileId=" + fileId, function (data, status) {
                if (status == "success") {
                    data = eval('(' + data + ')');
                    if (data.respCo == "0000") {
                        Message.success(data.respMsg);
                        window.location.hash = "manage/photo/" + photoId + "/manage?r=" + Math.random();
                    } else {
                        Message.error(data.respMsg);
                    }
                } else {
                    Message.error("网络错误，请稍后重试");
                }
            });
        });
    });
});