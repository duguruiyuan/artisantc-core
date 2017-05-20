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

    <title id="head-title"> - <fmt:message key="text.page.item.payment.orders"/> - <fmt:message key="text.page.head.title"/></title>

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

<body class="body-no-padding" role="document">

<div class="container" role="main">
    <div class="row">
        <div class="col-sm-11 col-md-11 col-lg-11" role="main">
            <div class="page-header">
                <h3><strong id="item-title"></strong></h3>
                <p id="item-bidPrice">成交金额：<span class="text-right" style="float: right;position: relative" id="itemPaymentOrder-createDateTime">成交时间：</span></p>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-11 col-md-11 col-lg-11" role="main">
            <p id="item-coverUrl"></p>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-11 col-md-11 col-lg-11" role="main">
            <p id="itemPaymentOrder-paymentOrderNumber">支付订单号：<span class="text-right" style="float: right;position: relative" id="itemPaymentOrder-paymentOrderStatus">支付状态：</span></p>
            <p id="itemPaymentOrder-paymentAmount">支付金额：<span class="text-right" style="float: right;position: relative" id="itemPaymentOrder-paymentChannel">支付渠道：</span></p>
            <p id="itemPaymentOrder-deliveryAddressName">收货人：<span class="text-right" style="float: right;position: relative" id="itemPaymentOrder-deliveryAddressMobile">收货人手机号：</span></p>
            <p id="itemPaymentOrder-deliveryAddressDetail">收货地址：</p>
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
        var itemOrderNumber = pathname.substring(pathname.lastIndexOf("/") + 1);// 通过截取URI获得拍品订单的订单号
        var ajaxUrl = "/administrator/item-payment-orders/" + itemOrderNumber;
        $.ajax({
            method: "GET",
            url: ajaxUrl
        }).then(getDetailSuccessCallback, getDetailFailureCallback);

        function getDetailSuccessCallback(data, textStatus, jqXHR) {
            if (data.showSynchronizeButton) {// 是否显示操作按钮
                $("#button-group")
                        .append("<button class=\"btn btn-primary btn-lg btn-block\" id=\"synchronize-btn\"><span class=\"glyphicon glyphicon-ok\"> <fmt:message key="text.button.synchronize"/></span></button>")
            }

            $("#head-title").prepend(data.title);
            $("#item-title").append(data.title);
            $("#item-bidPrice").append(data.bidPrice);
            $("#itemPaymentOrder-createDateTime").append(data.paymentOrderCreateDateTime);
            $("#itemPaymentOrder-paymentOrderNumber").append(data.paymentOrderNumber);
            $("#itemPaymentOrder-paymentOrderStatus").append(data.paymentOrderStatus);
            $("#itemPaymentOrder-paymentAmount").append(data.paymentAmount);
            $("#itemPaymentOrder-paymentChannel").append(data.paymentChannel);
            $("#itemPaymentOrder-deliveryAddressName").append(data.itemOrderDeliveryAddressName);
            $("#itemPaymentOrder-deliveryAddressMobile").append(data.itemOrderDeliveryAddressMobile);
            $("#itemPaymentOrder-deliveryAddressDetail").append(data.itemOrderDeliveryAddressDetail);

            var itemImg = "";
            if (null != data.coverUrl && data.coverUrl != "") {
                itemImg = "<img class=\"img-thumbnail\" src=\"" + data.coverUrl + "\" data-src=\"holder.js/100%x100%\" alt=\"" + data.title + "\">";
            }
            $("#item-coverUrl").append(itemImg);

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
                        url: "/administrator/items/pending-review/" + itemId
                    }).then(approveSuccessCallback, approveFailureCallback);
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