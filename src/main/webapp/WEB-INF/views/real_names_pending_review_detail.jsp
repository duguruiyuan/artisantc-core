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

    <title id="head-title"> - <fmt:message key="text.page.real.name.status.pending.review"/> - <fmt:message key="text.page.head.title"/></title>

    <%-- Bootstrap --%>
    <link href="<c:url value='/css/bootstrap.min.css' />" rel="stylesheet">
    <link href="<c:url value='/css/bootstrap-theme.min.css' />" rel="stylesheet">
    <link href="<c:url value='/css/general.css' />" rel="stylesheet">

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
    <div class="row">
        <div class="col-sm-11 col-md-11 col-lg-11" role="main">
            <div class="page-header">
                <h3><strong id="real-name-title"></strong></h3>
                <p id="real-name-status"><span class="text-right" style="float: right;position: relative" id="real-name-createDateTime"></span></p>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-11 col-md-11 col-lg-11" role="main">
            <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                <%-- Indicators --%>
                <ol class="carousel-indicators" id="real-name-image-carousel-indicators">
                </ol>

                <%-- Wrapper for slides --%>
                <div class="carousel-inner" id="real-name-image-carousel-inner" role="listbox">
                </div>

                <%-- Controls --%>
                <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left"></span>
                    <span class="sr-only">上一张</span>
                </a>
                <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right"></span>
                    <span class="sr-only">下一张</span>
                </a>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-11 col-md-11 col-lg-11" role="main">
            <p id="real-name-identityNumber"></p>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-11 col-md-11 col-lg-11" role="main">
            <div class="alert alert-danger" id="error-403" role="alert"><fmt:message key="text.page.error.403"/></div>
            <div class="alert alert-success" id="ajax-success" role="alert"><fmt:message key="text.alert.success"/></div>
            <div class="alert alert-danger" id="ajax-failure" role="alert"><fmt:message key="text.alert.danger"/></div>
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

    <%-- footer starts --%>
    <%@include file="common/footer.jsp" %>
    <%-- footer ends --%>

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
<script>
    $(function () {
        $("#affix-nav").affix();
        $("#ajax-success").hide();
        $("#ajax-failure").hide();

        // 加载“拍品详情”数据
        var pathname = window.location.pathname;// 当前访问的URI
        var realNameId = pathname.substring(pathname.lastIndexOf("/") + 1);// 通过截取URI获得拍品的ID
        var ajaxUrl = "/administrator/real-name/pending-review/" + realNameId;
        $.ajax({
            method: "GET",
            url: ajaxUrl
        }).then(getDetailSuccessCallback, getDetailFailureCallback);

        function getDetailSuccessCallback(data, textStatus, jqXHR) {
            if (data.showAuditButtons) {// 是否显示操作按钮
                $("#button-group")
                        .append("<button class=\"btn btn-primary btn-lg btn-block\" id=\"approve-btn\"><span class=\"glyphicon glyphicon-ok\"> <fmt:message key="text.button.approve"/></span></button>")
                        .append("<button class=\"btn btn-danger btn-lg btn-block\" id=\"reject-btn\"><span class=\"glyphicon glyphicon-remove\"> <fmt:message key="text.button.reject"/></span></button>");
            }

            $("#head-title").prepend(data.realName);
            $("#real-name-title").append(data.realName);
            $("#real-name-status").append(data.status);
            $("#real-name-createDateTime").append(data.createDateTime);
            $("#real-name-identityNumber").append(data.identityNumber);

            // 遍历显示图片
            var imageIndex = 0;
            $.each(data.images, function () {
                if (imageIndex === 0) {
                    $("#real-name-image-carousel-indicators").append("<li data-target=\"#carousel-example-generic\" data-slide-to=\"" + imageIndex + "\" class=\"active\"></li>");
                    $("#real-name-image-carousel-inner").append("<div class=\"item active\"><img src=\"" + this.imageUrl + "\" alt=\"" + imageIndex + "\"></div>");
                } else {
                    $("#real-name-image-carousel-indicators").append("<li data-target=\"#carousel-example-generic\" data-slide-to=\"" + imageIndex + "\"></li>");
                    $("#real-name-image-carousel-inner").append("<div class=\"item\"><img src=\"" + this.imageUrl + "\" alt=\"" + imageIndex + "\"></div>");
                }
                imageIndex++;
            });

            $("#loading").hide();
        }

        function getDetailFailureCallback(jqXHR, textStatus, errorThrown) {
            $("#loading").hide();
        }


        $("body").on("click", "#approve-btn",// 绑定事件到“通过”按钮
                function () {
                    // 执行“通过”操作
                    $.ajax({
                        method: "PATCH",
                        url: "/administrator/real-name/pending-review/" + realNameId
                    }).then(approveSuccessCallback, approveFailureCallback);
                }
        ).on("click", "#reject-btn",// 绑定事件到“拒绝”按钮
                function () {
                    // 执行“通过”操作
                    $.ajax({
                        method: "DELETE",
                        url: "/administrator/real-name/pending-review/" + realNameId
                    }).then(rejectSuccessCallback, rejectFailureCallback);
                }
        );

        function approveSuccessCallback(data, textStatus, jqXHR) {
            $("#button-group").empty();
            $("#ajax-success").show();
            $("#loading").hide();
        }

        function approveFailureCallback(jqXHR, textStatus, errorThrown) {
            ajaxFailureCallback(jqXHR, textStatus, errorThrown);
        }

        function rejectSuccessCallback(data, textStatus, jqXHR) {
            $("#button-group").empty();
            $("#ajax-success").show();
            $("#loading").hide();
        }

        function rejectFailureCallback(jqXHR, textStatus, errorThrown) {
            ajaxFailureCallback(jqXHR, textStatus, errorThrown);
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