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

    <title id="head-title"><fmt:message key="text.page.head.title"/></title>

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
                <h3><strong id="withdrawal-title"></strong></h3>
                <p id="withdrawal-amount"><span class="text-right" style="float: right;position: relative" id="withdrawal-createDateTime">申请时间：</span></p>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-11 col-md-11 col-lg-11" role="main">
            <p id="withdrawal-bankCardInfo">目标银行卡：</p>
            <p id="withdrawal-availableAmount">实际到帐金额：<span class="text-right" style="float: right;position: relative" id="withdrawal-status">状态：</span></p>
            <p id="withdrawal-charge">手续费：<span class="text-right" style="float: right;position: relative" id="withdrawal-updateDateTime">最新处理时间：</span></p>
            <p id="withdrawal-chargeRate">手续费费率：</p>
            <p id="withdrawal-express"><span class="text-right" style="float: right;position: relative" id="withdrawal-freeReturn"></span></p>
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

        // 加载“提现详情”数据
        var pathname = window.location.pathname;// 当前访问的URI
        var billId = pathname.substring(pathname.lastIndexOf("/") + 1);// 通过截取URI获得拍品的ID
        var ajaxUrl = "/administrator/withdrawal-${category}/" + billId;
        var solvingUrl = "/administrator/withdrawal-${category}/" + billId + "/solving";
        var solvedUrl = "/administrator/withdrawal-${category}/" + billId + "/solved";
        $.ajax({
            method: "GET",
            url: ajaxUrl
        }).then(getDetailSuccessCallback, getDetailFailureCallback);

        function getDetailSuccessCallback(data, textStatus, jqXHR) {
            if (data.showSolving) {// 根据逻辑增加按钮
                $("#button-group")
                        .append("<button class=\"btn btn-primary btn-lg btn-block\" id=\"solving-btn\"><span class=\"glyphicon glyphicon-ok\"> <fmt:message key="text.button.solving"/></span></button>")
            } else if (data.showSolved) {// 根据逻辑增加按钮
                $("#button-group")
                        .append("<button class=\"btn btn-primary btn-lg btn-block\" id=\"solved-btn\"><span class=\"glyphicon glyphicon-ok\"> <fmt:message key="text.button.solved"/></span></button>");
            }

            $("#head-title").prepend(data.title + " - " + data.status + " - ");
            $("#withdrawal-title").append(data.title);
            $("#withdrawal-amount").prepend("申请金额：" + data.amount);
            $("#withdrawal-createDateTime").append(data.createDateTime);
            $("#withdrawal-bankCardInfo").append(data.bankCardInfo);
            $("#withdrawal-status").append(data.status);
            $("#withdrawal-availableAmount").append("<strong>" + data.availableAmount + "</strong>");
            $("#withdrawal-charge").append(data.charge);
            $("#withdrawal-chargeRate").append(data.chargeRate);
            $("#withdrawal-updateDateTime").append(data.updateDateTime);

            $("#loading").hide();
        }

        function getDetailFailureCallback(jqXHR, textStatus, errorThrown) {
            $("#loading").hide();
        }

        $("body").on("click", "#solving-btn",// 绑定事件到“去转账”按钮
                function () {
                    // 执行“通过”操作
                    $.ajax({
                        method: "PATCH",
                        url: solvingUrl
                    }).then(approveSuccessCallback, approveFailureCallback);
                }
        ).on("click", "#solved-btn",// 绑定事件到“已转账”按钮
                function () {
                    // 执行“通过”操作
                    $.ajax({
                        method: "PATCH",
                        url: solvedUrl
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