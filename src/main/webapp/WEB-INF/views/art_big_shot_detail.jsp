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

    <title id="head-title"> - <fmt:message key="text.page.art.big.shots"/> - <fmt:message key="text.page.head.title"/></title>

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
                <h3><strong id="user-title"></strong></h3>
                <p id="user-nickname"><span class="text-right" style="float: right;position: relative" id="user-createDateTime"></span></p>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-11 col-md-11 col-lg-11 text-center" role="main">
            <img class="img-circle" src="" id="user-avatar">
        </div>
    </div>
    <div class="row">
        <div class="col-sm-11 col-md-11 col-lg-11" role="main">
            <p id="user-serialNumber">匠号：</p>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-11 col-md-11 col-lg-11" role="main">
            <p id="user-fans">粉丝：<span class="text-right" style="float: right;position: relative" id="user-followers">关注：</span></p>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-11 col-md-11 col-lg-11" role="main">
            <p id="user-overview"></p>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-11 col-md-11 col-lg-11" role="main">
            <p id="user-introduce"></p>
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

    <%-- “确认取消大咖资格”模态框 --%>
    <div class="modal fade" id="confirm-modal" tabindex="-1" role="dialog" aria-labelledby="confirm-modal-label" aria-hidden="true">
        <%-- 模态框的内容会通过ajax的方式来获得 --%>
        <%-- 模态框的内容 --%>
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <%-- 模态框的header --%>
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="create-modal-label">操作确认</h4>
                </div>
                <%-- 模态框的body --%>
                <div class="modal-body">
                    <p>确定取消该用户的“大咖”资格吗？</p>
                </div>
                <%-- 模态框的footer --%>
                <div class="modal-footer">
                    <button class="btn btn-lg btn-primary btn-block" id="ok-btn" type="button"><fmt:message key="text.button.ok"/></button>
                    <button class="btn btn-lg btn-default btn-block" id="cancel-btn" type="button"><fmt:message key="text.button.cancel"/></button>
                </div>
            </div>
        </div>
    </div>
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
        $("#loading").hide();
        $("#ajax-success").hide();
        $("#ajax-failure").hide();

        // 加载“大咖详情”数据
        var pathname = window.location.pathname;// 当前访问的URI
        var artBigShotsId = pathname.substring(pathname.lastIndexOf("/") + 1);// 通过截取URI获得用户的ID
        var ajaxUrl = "/administrator/art-big-shots/" + artBigShotsId;
        $.ajax({
            method: "GET",
            url: ajaxUrl
        }).then(getDetailSuccessCallback, getDetailFailureCallback);

        // 加载“大咖详情”成功
        function getDetailSuccessCallback(data, textStatus, jqXHR) {
            var artBigShotImg = "";
            if (data.argBigShot) {
                // 显示操作按钮
                $("#button-group")
                    .append("<button class=\"btn btn-primary btn-lg btn-block\" id=\"cancel-art-big-shot-btn\"><fmt:message key="text.button.cancel.art.big.shot"/></button>");

                // 显示“艺术大咖”标志
                var artBigShotValue = "艺术大咖";
                artBigShotImg = "<img class=\"img-icon\" src=\"/img/art_big_shot.png\" alt=\"" + artBigShotValue + "\" title=\"" + artBigShotValue + "\">";
            }

            // “性别”标志
            var sexImg = "";
            var sxeValue = data.sexValue;
            if (data.male) {
                sexImg = "<img class=\"img-icon\" src=\"/img/male.png\" alt=\"" + sxeValue + "\" title=\"" + sxeValue + "\">";
            } else if (data.female) {
                sexImg = "<img class=\"img-icon\" src=\"/img/female.png.png\" alt=\"" + sxeValue + "\" title=\"" + sxeValue + "\">";
            }

            $("#head-title").prepend(data.nickname);
            $("#real-name-title").append(data.nickname);
            $("#user-nickname").append(data.nickname);
            if (artBigShotImg != "") {
                $("#user-nickname").append("&nbsp;&nbsp;").append(artBigShotImg);
            }
            if (sexImg != "") {
                $("#user-nickname").append("&nbsp;&nbsp;").append(sexImg);
            }
            $("#user-createDateTime").append(data.createDateTime);
            $("#user-avatar").attr("src", data.avatarFileUrl);
            $("#user-serialNumber").append(data.serialNumber);
            $("#user-fans").append(data.fans);
            $("#user-followers").append(data.followers);
            $("#user-overview").append(data.overview);
            $("#user-introduce").append(data.introduce);

            $("#loading").hide();
        }

        // 加载“大咖详情”失败
        function getDetailFailureCallback(jqXHR, textStatus, errorThrown) {
            var errorMessage = $.parseJSON(jqXHR.responseText);
            $("#ajax-failure").html(errorMessage.errorDesc).show();
            $("#loading").hide();
        }

        /* “成为大咖”模态框的JS */
        $("#confirm-modal").modal({
            show: false
        });

        // 隐藏返回的html代码中的元素
        $("#alert-success").hide();
        $("#alert-danger").hide();

        // 设置“确定”按钮事件
        $("#ok-btn").click(
            function () {
                cancelArtBigShot("/administrator/art-big-shots/" + artBigShotsId);
            }
        );

        // 设置“取消”按钮事件
        $("#cancel-btn").click(
            function () {
                // 隐藏模态框
                $("#confirm-modal").modal("hide");
            }
        );

        // 隐藏“加载提示”
        $("#login-modal-loading").hide();

        function cancelArtBigShot(url) {
            var ajaxData = $("#artBigShotForm").serializeJSON();// 表单数据，JSON格式
            ajaxData.artBigShotsId = artBigShotsId;

            $.ajax({
                method: "DELETE",
                url: url,
                data: JSON.stringify(ajaxData)
            }).then(cancelArtBigShotSuccessCallback, cancelArtBigShotFailureCallback)
                .always(function () {
                    ajaxData = "";// 清空数据
                });
        }

        // “成为大咖”“操作成功”回调函数
        function cancelArtBigShotSuccessCallback(data, textStatus, jqXHR) {
            $("#button-group").empty();
            $("#alert-success").show();
            $("#confirm-modal").modal("hide");
        }

        // “成为大咖”“操作失败”回调函数
        function cancelArtBigShotFailureCallback(jqXHR, textStatus, errorThrown) {
            var errorMessage = $.parseJSON(jqXHR.responseText);
            $("#alert-danger").html(dangerImg + errorMessage.errorDesc).show();
        }

        // 绑定事件到“成为大咖”按钮，触发“成为大咖”模态框
        $("body").on("click", "#cancel-art-big-shot-btn",
            function () {
                // 显示模态框
                $("#confirm-modal").modal("show");
            }
        );
    });
</script>
</body>
</html>