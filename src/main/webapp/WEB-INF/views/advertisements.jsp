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

    <title><fmt:message key="text.page.advertisement"/> - <fmt:message key="text.page.head.title"/></title>

    <%-- Bootstrap --%>
    <link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
    <link href="<c:url value='/css/bootstrap-theme.min.css' />" rel="stylesheet">
    <link href="<c:url value='/css/general.css' />" rel="stylesheet">
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

<body role="document">
<jsp:include page="common/top_navbar.jsp"/>

<div class="container" role="main">
    <%-- 页面标题 --%>
    <div class="row">
        <div class="col-sm-11 col-md-11 col-lg-11 page-header">
            <h1><fmt:message key="text.page.advertisement"/>
                <button class="btn btn-success btn-lg pull-right" id="create-btn" title="<fmt:message key="text.button.create"/>"><span class="glyphicon glyphicon-plus"></span></button>
                <button class="btn btn-primary btn-lg pull-right" id="search-btn" title="<fmt:message key="text.button.search"/>"><span class="glyphicon glyphicon-search"></span></button>
            </h1>
            <div class="alert alert-danger" id="error-403" role="alert"><fmt:message key="text.page.error.403"/></div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-11 col-md-11 col-lg-11" role="main">
            <%-- 页面内容 --%>
            <div class="page-content">
                <%-- masonry插件使用的样式 --%>
                <div class="img-cover"></div>
                <div class="img-gutter"></div>
            </div>
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

    <%-- footer starts --%>
    <%@include file="common/footer.jsp" %>
    <%-- footer ends --%>

    <%-- “登录”模态框 --%>
    <%@include file="modal/login_modal.jsp" %>

    <%-- “新增广告”模态框 --%>
    <%@include file="modal/create_modal.jsp" %>
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
<%-- 分页列表JS --%>
<script src="<c:url value='/js/pagination.js' />"></script>
<%-- 引入fileinput.js--%>
<script src="<c:url value='/js/fileinput.min.js' />"></script>
<script src="<c:url value='/js/fileinput-locale-zh.js' />"></script>
<%-- 引入masonry插件，瀑布流布局 --%>
<script src="<c:url value='/js/masonry.pkgd.min.js' />"></script>
<%-- 由masonry插件作者推荐使用，瀑布流布局 --%>
<script src="<c:url value='/js/imagesloaded.pkgd.min.js' />"></script>
<script>
    $(function () {
        /* “新增广告”模态框的JS */
        $("#create-modal").modal({
            show: false
        });

        // “新增广告”模态框内容“加载成功”回调函数
        var createContent, imageName, imageWidth, imageHeight;

        function getModalContentSuccessCallback(data, textStatus, jqXHR) {
            // 清空并更新数据
            $("#create-modal-header").empty().append($(data).filter("div.modal-header").html());
            $("#create-modal-body").empty().append($(data).filter("div.modal-body").html());
            $("#create-modal-footer").empty().append($(data).filter("div.modal-footer").html());

            // 隐藏返回的html代码中的元素
            $("#create-success").hide();
            $("#create-failure").hide();
            $("#validation-message").hide();

            /* 文件上传设置 */
            $("#advertisement-file-input").fileinput({
                language: "zh",
                uploadUrl: "/administrator/advertisements/images",
                maxFileCount: 1,
                validateInitialCount: true,
                allowedFileTypes: ['image'],
                fileActionSettings: {
                    /*
                     必须设置为false，因为本身已经是在“新增广告”模态框内了，如果不设置为false，则默认户显示“预览”按钮，
                     导致该插件又会显示一个“预览用的模态框”，在关闭“预览用的模态框”时会连同“新增广告”模态框一起给关闭掉。
                     */
                    showZoom: false
                },
                showUpload: false,
                showCancel: false
            }).on("fileuploaded", function (event, data, previewId, index) {
                $("#validation-message").hide();// 总是隐藏“校验信息”
                var dataJSON = jQuery.parseJSON(data.jqXHR.responseText);
                imageName = dataJSON.imageName;
                imageWidth = dataJSON.imageWidth;
                imageHeight = dataJSON.imageHeight;
            }).on("fileuploaderror", function (event, data, previewId, index) {
//                var form = data.form, files = data.files, extra = data.extra,
//                        response = data.response, reader = data.reader;
                // todo：403
                console.log("File upload error: " + data.jqXHR.status);
                if (data.jqXHR.status === 403) {
                    <%--data.jqXHR.responseText = "{error:\"<fmt:message key="text.page.error.403"/>\"}";--%>
                    data.jqXHR.responseText = "{error: 'You are not allowed to upload such a file.'}";
                    console.log("403: " + data.jqXHR.responseText);
                }
            });

            // 设置“保存”按钮事件
            $("#save-btn").click(
                    function () {
                        var title = $("#advertisement-title").val();
                        if ((undefined == imageName || null == imageName || "" === imageName.trim())
                                || (undefined == title || null == title || "" === title.trim())) {
                            $("#validation-message").show();
                        } else {
                            createContent = "{\"title\":\"" + title + "\",\"imageName\":\"" + imageName + "\",\"imageWidth\":\"" + imageWidth + "\",\"imageHeight\":\"" + imageHeight + "\"}";
                            $.ajax({
                                method: "POST",
                                url: "/administrator/advertisements",
                                data: createContent
                            }).then(createAdvertisementSuccessCallback, createAdvertisementFailureCallback)
                                    .always(function () {
                                        $("#validation-message").hide();// 总是隐藏“校验信息”
                                        createContent = "";// 清空数据
                                    });
                        }
                    }
            );

            // 隐藏“加载提示”
            $("#login-modal-loading").hide();
        }

        // “新增广告”模态框内容“加载失败”回调函数
        function getModalContentFailureCallback(jqXHR, textStatus, errorThrown) {
            $("#create-modal-body").empty().append(loadingFailureDiv);
        }

        $("#create-success").hide();
        $("#create-failure").hide();

        // “新增广告”“操作成功”回调函数
        function createAdvertisementSuccessCallback(data, textStatus, jqXHR) {
            $("#create-success").show();
            $("#create-modal").modal("hide");
        }

        // “新增广告”“操作失败”回调函数
        function createAdvertisementFailureCallback(jqXHR, textStatus, errorThrown) {
            var errorMessage = $.parseJSON(jqXHR.responseText);
            $("#create-failure").html(errorMessage.errorDesc).show();
        }

        // 绑定事件到“新增广告”按钮，触发“新增广告”模态框
        $("#create-btn").click(
                function () {
                    // 清空可能存在的数据
                    $("#create-modal-footer").empty()
                    $("#create-modal-body").empty().append(loadingDiv);
                    // 显示“新增广告”模态框
                    $("#create-modal").modal("show");
                    // 加载“新增广告”模态框的内容
                    $.ajax({
                        method: "GET",
                        url: "/console/advertisement-modal"
                    }).then(getModalContentSuccessCallback, getModalContentFailureCallback);
                }
        );

        $("#affix-nav").addClass("affix-nav-fixed-bottom").affix();

        firstSearch();
    });

    /* 分页列表功能 */
    var pageHref = "/administrator/advertisements";// 分页数据的URL

    // 初始化“masonry”插件
    $container.imagesLoaded(function () {
        $container.masonry({
            itemSelector: ".thumbnail",
            columnWidth: ".img-cover",
            gutter: ".img-gutter",
            transitionDuration: 0
        });
    });

    // 分页数据请求成功的回调函数
    function getListSuccessCallback(data, textStatus, jqXHR) {
        if (pageNumber >= data.nextPage) {
            isEnd = true;// 已经是最后一页数据了
        }
        pageNumber = data.nextPage;

        $.each(data.advertisements, function () {
            var advertisementImg = "";
            if (null != this.coverUrl && this.coverUrl != "") {
                advertisementImg = "<img src=\"" + this.coverUrl + "\" data-src=\"holder.js/100%x100%\" alt=\"" + this.title + "\">";
            }
            var advertisementTitle = this.title;
            if (advertisementTitle.length > 8) {
                advertisementTitle = advertisementTitle.substring(0, 8) + "...";
            }

            var $appendData = $("<div class=\"thumbnail img-cover\">" +
                    "<a href=\"<c:url value="/web/advertisements/"/>" + this.advertisementId + "\" target=\"_blank\" title=\"" + this.title + "\">" +
                    advertisementImg +
                    "<div class=\"caption\">" +
                    "<p>" + advertisementTitle + "<span class=\"pull-right\">浏览" + this.browseTimes + "次</span></p>" +
                    "</div>" +
                    "</a>" +
                    "</div>");


            // on ajax call append or prepend more items
            $container.append($appendData).imagesLoaded(function () {
                $container.masonry("appended", $appendData);
            });
        });

        // 其他逻辑
        $("#loading").hide();
        canLoad = true;
    }
</script>
</body>
</html>