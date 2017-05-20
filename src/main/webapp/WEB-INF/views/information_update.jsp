<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ --%>

    <%-- 可以让部分国产浏览器默认采用高速模式渲染页面 --%>
    <meta name="renderer" content="webkit">

    <title id="head-title"> - <fmt:message key="text.page.information"/> - <fmt:message key="text.page.head.title"/></title>

    <%-- Bootstrap --%>
    <link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
    <link href="<c:url value='/css/bootstrap-theme.min.css' />" rel="stylesheet">
    <link href="<c:url value='/css/general.css' />" rel="stylesheet">
    <%-- 引入wangEditor.css --%>
    <link href="<c:url value='/css/wangEditor.min.css' />" rel="stylesheet">
    <%-- 引入fileinput.css --%>
    <link href="<c:url value='/css/fileinput.min.css' />" rel="stylesheet">

    <%-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries --%>
    <%-- WARNING: Respond.js doesn't work if you view the page via file:// --%>
    <!--[if lt IE 9]>
    <script src="/js/html5shiv/html5shiv.min.js"></script>
    <script src="/js/respond/respond.min.js"></script>
    <![endif]-->

    <%-- The fav icon --%>
    <link rel="shortcut icon" href="<c:url value='/img/favicon.ico' />">
</head>

<body class="body-no-padding" role="document">
<div class="container" role="main">
    <div class="row">
        <div class="col-sm-11 col-md-11 col-lg-11" role="main">
            <div class="page-header">
                <h3><strong>修改资讯</strong></h3>
                <p id="information-createDateTime">创建时间：<span class="text-right" id="information-updateDateTime" style="float: right;position: relative">修改时间：</span></p>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-11 col-md-11 col-lg-11" role="main">
            <%-- 页面内容 --%>
            <form id="informationForm" action="<c:url value="/administrator/information/cover"/>" method="post" enctype="multipart/form-data">
                <div class="alert alert-success" id="create-success" role="alert"><fmt:message key="text.alert.success"/></div>
                <div class="alert alert-danger" id="create-failure" role="alert"><fmt:message key="text.alert.danger"/></div>
                <div class="alert alert-warning" id="validation-message" role="alert">请上传一张图片做封面吧！</div>

                <div class="form-group">
                    <input class="form-control" name="title" type="text" placeholder="资讯标题" id="information-title" required="required" autofocus="autofocus">
                </div>
                <div class="form-group">
                    <input class="form-control" name="source" type="text" placeholder="资讯来源" id="information-source" required>
                </div>
                <div class="form-group">
                    <input name="content" id="information-content" type="hidden">
                    <div id="information-content-editor"></div>
                </div>
            </form>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-11 col-md-11 col-lg-11" id="button-group">
        </div>
        <div class="col-sm-1 col-md-1 col-lg-1">
            <%-- 附加导航 --%>
            <%@include file="common/affix.jsp" %>
        </div>
    </div>

    <%-- 数据加载提示 --%>
    <div class="row" id="loading">
        <img src="<c:url value='/img/loading.gif' />" alt="加载中"/>加载中......
    </div>

    <%-- “登录”模态框 --%>
    <%@include file="modal/login_modal.jsp" %>
</div>

<%-- jQuery (necessary for Bootstrap's JavaScript plugins) --%>
<script src="<c:url value='/js/jquery-3.1.1.min.js' />"></script>
<%-- Include all compiled plugins of Bootstrap --%>
<script src="<c:url value='/js/bootstrap.min.js' />"></script>
<%-- Plugin for jQuer: Adds the method .serializeJSON() to jQuery that serializes a form into a JavaScript Object --%>
<script src="<c:url value='/js/jquery.serializejson.min.js' />"></script>
<%-- 通用JS --%>
<script src="<c:url value='/js/general.js' />"></script>
<%-- 登录/退出登录JS --%>
<script src="<c:url value='/js/login.js' />"></script>
<%-- 引入fileinput.js--%>
<script src="<c:url value='/js/fileinput.min.js' />"></script>
<script src="<c:url value='/js/fileinput-locale-zh.js' />"></script>
<%-- 引入wangEditor.js --%>
<script src="<c:url value='/js/wangEditor/wangEditor.min.js' />"></script>
<script>
    $(function () {
        $("#affix-nav").affix();
        $("#ajax-success").hide();
        $("#ajax-failure").hide();
        $("#create-success").hide();
        $("#create-failure").hide();
        $("#validation-message").hide();

        // 加载“拍品详情”数据
        var pathname = window.location.pathname;// 当前访问的URI
        var informationId = pathname.substring(pathname.lastIndexOf("/") + 1);// 通过截取URI获得资讯的ID
        var ajaxUrl = "/administrator/information/" + informationId;
        $.ajax({
            method: "GET",
            url: ajaxUrl
        }).then(getDetailSuccessCallback, getDetailFailureCallback);

        function getDetailSuccessCallback(data, textStatus, jqXHR) {
            $("#button-group")
                    .append("<button class=\"btn btn-primary btn-lg btn-block\" id=\"save-btn\"><span class=\"glyphicon glyphicon-ok\"> <fmt:message key="text.button.save"/></span></button>")
                    .append("<button class=\"btn btn-success btn-lg btn-block\" id=\"save-and-publish-btn\"><span class=\"glyphicon glyphicon-ok\"> <fmt:message key="text.button.save.and.publish"/></span></button>");
            $("#head-title").prepend(data.title);
            $("#information-title").val(data.title);
            $("#information-source").val(data.source);
            $("#information-author").val(data.author);
            $("#information-createDateTime").append(data.createDateTime);
            $("#information-updateDateTime").append(data.updateDateTime);

            // 初始化封面
            /* 文件上传设置 */
            <%--$("#information-file-input").fileinput({--%>
            <%--dropZoneTitle: "上传封面图片：拖拽文件到这里 &hellip;",--%>
            <%--language: "zh",--%>
            <%--uploadUrl: "/administrator/information/cover",--%>
            <%--maxFileCount: 1,--%>
            <%--validateInitialCount: true,--%>
            <%--allowedFileTypes: ['image'],--%>
            <%--initialPreview: [--%>
            <%--data.coverView.initialPreview--%>
            <%--],--%>
            <%--initialPreviewConfig: [--%>
            <%--{--%>
            <%--url: data.coverView.initialPreviewConfig[0].url--%>
            <%--}--%>
            <%--],--%>
            <%--fileActionSettings: {--%>
            <%--/*--%>
            <%--必须设置为false，因为本身已经是在“新增广告”模态框内了，如果不设置为false，则默认户显示“预览”按钮，--%>
            <%--导致该插件又会显示一个“预览用的模态框”，在关闭“预览用的模态框”时会连同“新增广告”模态框一起给关闭掉。--%>
            <%--*/--%>
            <%--showZoom: false--%>
            <%--},--%>
            <%--showUpload: false,--%>
            <%--showCancel: false--%>
            <%--}).on("fileuploaded", function (event, data, previewId, index) {--%>
            <%--$("#validation-message").hide();// 总是隐藏“校验信息”--%>
            <%--var dataJSON = jQuery.parseJSON(data.jqXHR.responseText);--%>
            <%--imageName = dataJSON.imageName;--%>
            <%--imageWidth = dataJSON.imageWidth;--%>
            <%--imageHeight = dataJSON.imageHeight;--%>
            <%--}).on("fileuploaderror", function (event, data, previewId, index) {--%>
            <%--//                var form = data.form, files = data.files, extra = data.extra,--%>
            <%--//                        response = data.response, reader = data.reader;--%>
            <%--// todo：403--%>
            <%--console.log("File upload error: " + data.jqXHR.status);--%>
            <%--if (data.jqXHR.status === 403) {--%>
            <%--&lt;%&ndash;data.jqXHR.responseText = "{error:\"<fmt:message key="text.page.error.403"/>\"}";&ndash;%&gt;--%>
            <%--data.jqXHR.responseText = "{error: 'You are not allowed to upload such a file.'}";--%>
            <%--console.log("403: " + data.jqXHR.responseText);--%>
            <%--}--%>
            <%--});--%>

            /* 富文本编辑器设置 */
            // 设置“富文本编辑器”的高度
            $("#information-content-editor").css({
                height: $(window).height() / 2
            });
            wangEditor.config.printLog = false;
            var editor = new wangEditor('information-content-editor');
            // 移除“表情”和“地图”菜单
            editor.config.menus = $.map(wangEditor.config.menus, function (item) {
                if (item === 'emotion') {
                    return null;
                }
                if (item === 'location') {
                    return null;
                }
                return item;
            });
            // 上传图片的URL
            editor.config.uploadImgUrl = '/administrator/information/images';
            // 上传图片的名称
            editor.config.uploadImgFileName = 'images';
            // 上传图片的超时时间
            editor.config.uploadTimeout = 60 * 1000;// 60秒
            // 上传图片的自定义load事件
            editor.config.uploadImgFns.onload = function (data, xhr) {
                // xhr 是 xmlHttpRequest 对象，IE8、9中不支持
                if (401 == xhr.status) {
                    // 需要“登录”
                    $("#create-modal").modal("hide");
                    $("#login-modal").modal("show");
                    return;
                } else if (400 == xhr.status) {// todo: 400
                    return;
                } else if (500 == xhr.status) {// todo: 500
                    return;
                } else if (403 == xhr.status) {// todo: 403
                    return;
                }
                // 获取返回数据
                var imageUrl = JSON.parse(data);
                // 在编辑器中插入图片
                editor.command(null, 'InsertImage', imageUrl.url);
            };

            // 上传图片的自定义error事件
            editor.config.uploadImgFns.onerror = function (xhr) {
                // xhr 是 xmlHttpRequest 对象，IE8、9中不支持
                console.log("xhr: " + xhr);// todo:上传失败
            };
            editor.create();
            // 初始化编辑器的内容
            editor.$txt.html(data.content);

            // 设置“保存”按钮事件
            $("#save-btn").click(
                    function () {
                        saveInformation("/administrator/information/" + informationId);
                    }
            );

            // 设置“保存并发布”按钮事件
            $("#save-and-publish-btn").click(
                    function () {
                        saveInformation("/administrator/information/" + informationId + "/publish");
                    }
            );

            $("#loading").hide();

            function saveInformation(url) {
                $("#information-content").val(editor.$txt.html());

                $.ajax({
                    method: "PUT",
                    url: url,
                    data: JSON.stringify($("#informationForm").serializeJSON())
                }).then(createInformationSuccessCallback, createInformationFailureCallback)
                        .always(function () {
                            $("#validation-message").hide();// 总是隐藏“校验信息”
                        });
            }

            // “保存资讯”“操作成功”回调函数
            function createInformationSuccessCallback(data, textStatus, jqXHR) {
                $("#create-success").show();
                $("#create-modal").modal("hide");
                window.location.reload();
            }

            // “保存资讯”“操作失败”回调函数
            function createInformationFailureCallback(jqXHR, textStatus, errorThrown) {
                var errorMessage = $.parseJSON(jqXHR.responseText);
                $("#create-failure").html(errorMessage.errorDesc).show();
            }
        }

        function getDetailFailureCallback(jqXHR, textStatus, errorThrown) {
            $("#loading").hide();
        }

        function ajaxFailureCallback(jqXHR, textStatus, errorThrown) {
            var errorMessage = $.parseJSON(jqXHR.responseText);
            $("#ajax-failure").html(errorMessage.errorDesc).show();
            $("#loading").hide();
        }
    });
</script>
</body>
</html>