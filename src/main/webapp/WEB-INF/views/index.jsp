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

    <title><fmt:message key="text.page.index"/> - <fmt:message key="text.page.head.title"/></title>

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

<body>
<jsp:include page="common/top_navbar.jsp"/>

<div class="container" role="main">
    <%-- 页面标题 --%>
    <div class="page-header">
        <h1>欢迎来到“艺匠说”的管理系统！</h1>
        <div class="alert alert-danger" id="error-403" role="alert"><fmt:message key="text.page.error.403"/></div>
    </div>
    <div class="row">
        <%-- 页面内容 --%>
        <div class="col-sm-11 col-md-11 col-lg-11" role="main">
            <div class="page-content">
                <div class="page-header">
                    <h3>概览</h3>
                </div>
                <dl class="dl-horizontal" id="environment-overview"></dl>
                <div class="page-header">
                    <h3>支付宝</h3>
                </div>
                <dl class="dl-horizontal" id="environment-alipay"></dl>
                <div class="page-header">
                    <h3>上传文件</h3>
                </div>
                <dl class="dl-horizontal" id="environment-upload"></dl>
                <div class="page-header">
                    <h3>初始化数据</h3>
                </div>
                <dl class="dl-horizontal" id="environment-initialization"></dl>
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

    <%-- “登录”模态框--%>
    <%@include file="modal/login_modal.jsp" %>

    <%-- footer starts --%>
    <%@include file="common/footer.jsp" %>
    <%-- footer ends --%>
</div>

<%-- jQuery (necessary for Bootstrap's JavaScript plugins) --%>
<script src="<c:url value='/js/jquery-3.1.1.min.js' />"></script>
<%-- Include all compiled plugins (below), or include individual files as needed --%>
<script src="<c:url value='/js/bootstrap.min.js' />"></script>
<%-- Plugin for jQuer: Adds the method .serializeJSON() to jQuery that serializes a form into a JavaScript Object --%>
<script src="<c:url value='/js/jquery.serializejson.min.js' />"></script>
<%-- 通用JS --%>
<script src="<c:url value='/js/general.js' />"></script>
<%-- 登录/退出登录JS --%>
<script src="<c:url value='/js/login.js' />"></script>
<script>
    $(function () {
        /* 下面本页面使用的JS */
        $("#login-btn").click(callLoginModal);

        function getEnvironments() {
            var ajaxUrl = "/administrator/environments";
            $.ajax({
                method: "GET",
                url: ajaxUrl
            }).then(getDetailSuccessCallback, getDetailFailureCallback);
        }

        function getDetailSuccessCallback(data, textStatus, jqXHR) {
            $("#environment-overview").empty()
                    .append("<dt>操作系统：</dt>").append("<dd>" + data.os + "</dd>")
                    .append("<dt>Java版本：</dt>").append("<dd>" + data.javaVersion + "</dd>")
                    .append("<dt>短信验证码(ShareSDK)：</dt>").append("<dd>" + data.smsAppKeyIOS + " (iOS)，" + data.smsAppKeyAndroid + " (Android)</dd>")
                    .append("<br>")
                .append("<dt>即时消息(融云)App Key：</dt>").append("<dd>" + data.messageAppKey + "</dd>")
                    .append("<dt>即时消息(融云)App Secret：</dt>").append("<dd>" + data.messageAppSecret + "</dd>")
                    .append("<dt>即时消息(融云)Domain：</dt>").append("<dd>" + data.messageDomain + "</dd>")
                    .append("<dt>即时消息(融云)URI：</dt>").append("<dd>" + data.messageUri + "</dd>");
            $("#environment-alipay").empty()
                    .append("<dt>App ID：</dt>").append("<dd>" + data.aLiPaySetting.appId + "</dd>")
                    .append("<dt>请求地址：</dt>").append("<dd>" + data.aLiPaySetting.url + "</dd>")
                    .append("<dt>签名私钥：</dt>").append("<dd>" + data.aLiPaySetting.privateKey + "</dd>")
                    .append("<dt>签名公钥：</dt>").append("<dd>" + data.aLiPaySetting.publicKey + "</dd>")
                    .append("<dt>签名算法：</dt>").append("<dd>" + data.aLiPaySetting.signType + "</dd>")
                    .append("<dt>接口版本：</dt>").append("<dd>" + data.aLiPaySetting.version + "</dd>")
                    .append("<dt>异步通知的回调地址：</dt>").append("<dd>" + data.aLiPaySetting.notifyUrl + "</dd>")
                    .append("<dt>销售产品码：</dt>").append("<dd>" + data.aLiPaySetting.productCode + "</dd>")
                    .append("<dt>卖家支付宝用户号：</dt>").append("<dd>" + data.aLiPaySetting.sellerEmail + "</dd>")
                    .append("<dt>卖家支付宝账号：</dt>").append("<dd>" + data.aLiPaySetting.sellerId + "</dd>");
            $("#environment-upload").empty()
                    .append("<dt>“广告图片”的存储路径：</dt>").append("<dd>" + data.uploadFileSetting.advertisementStorePath + "</dd>")
                    .append("<dt>“广告图片”的访问URL地址：</dt>").append("<dd>" + data.uploadFileSetting.advertisementUrlAddress + "</dd>")
                    .append("<dt>“广告图片”的访问URL路径：</dt>").append("<dd>" + data.uploadFileSetting.advertisementUrlPath + "</dd>")
                    .append("<br>")
                    .append("<dt>“用户头像”的存储路径：</dt>").append("<dd>" + data.uploadFileSetting.avatarStorePath + "</dd>")
                    .append("<dt>“用户头像”的访问URL地址：</dt>").append("<dd>" + data.uploadFileSetting.avatarUrlAddress + "</dd>")
                    .append("<dt>“用户头像”的访问URL路径：</dt>").append("<dd>" + data.uploadFileSetting.avatarUrlPath + "</dd>")
                    .append("<br>")
                    .append("<dt>“资讯图片”的存储路径：</dt>").append("<dd>" + data.uploadFileSetting.informationStorePath + "</dd>")
                    .append("<dt>“资讯图片”的访问URL地址：</dt>").append("<dd>" + data.uploadFileSetting.informationUrlAddress + "</dd>")
                    .append("<dt>“资讯图片”的访问URL路径：</dt>").append("<dd>" + data.uploadFileSetting.informationUrlPath + "</dd>")
                    .append("<br>")
                    .append("<dt>“拍品分类图片”的访问URL地址：</dt>").append("<dd>" + data.uploadFileSetting.itemCategoryUrlAddress + "</dd>")
                    .append("<dt>“拍品分类图片”的访问URL路径：</dt>").append("<dd>" + data.uploadFileSetting.itemCategoryUrlPath + "</dd>")
                    .append("<br>")
                    .append("<dt>“商户图片”的存储路径：</dt>").append("<dd>" + data.uploadFileSetting.merchantStorePath + "</dd>")
                    .append("<dt>“商户图片”的访问URL地址：</dt>").append("<dd>" + data.uploadFileSetting.merchantUrlAddress + "</dd>")
                    .append("<dt>“商户图片”的访问URL路径：</dt>").append("<dd>" + data.uploadFileSetting.merchantUrlPath + "</dd>")
                    .append("<br>")
                    .append("<dt>“艺文图片”的存储路径：</dt>").append("<dd>" + data.uploadFileSetting.momentStorePath + "</dd>")
                    .append("<dt>“艺文图片”的访问URL地址：</dt>").append("<dd>" + data.uploadFileSetting.momentUrlAddress + "</dd>")
                    .append("<dt>“艺文图片”的访问URL路径：</dt>").append("<dd>" + data.uploadFileSetting.momentUrlPath + "</dd>")
                    .append("<br>")
                    .append("<dt>“实名认证图片”的存储路径：</dt>").append("<dd>" + data.uploadFileSetting.realNameStorePath + "</dd>")
                    .append("<dt>“实名认证图片”的访问URL地址：</dt>").append("<dd>" + data.uploadFileSetting.realNameUrlAddress + "</dd>")
                    .append("<dt>“实名认证图片”的访问URL路径：</dt>").append("<dd>" + data.uploadFileSetting.realNameUrlPath + "</dd>")
                    .append("<br>")
                    .append("<dt>“用户个人展示图片”的存储路径：</dt>").append("<dd>" + data.uploadFileSetting.userShowStorePath + "</dd>")
                    .append("<dt>“用户个人展示图片”的访问URL地址：</dt>").append("<dd>" + data.uploadFileSetting.userShowUrlAddress + "</dd>")
                    .append("<dt>“用户个人展示图片”的访问URL路径：</dt>").append("<dd>" + data.uploadFileSetting.userShowUrlPath + "</dd>")
                    .append("<br>")
                    .append("<dt>“水印图片”的存储路径：</dt>").append("<dd>" + data.uploadFileSetting.watermarkStorePath + "</dd>");
            $("#environment-initialization").empty()
                    .append("<dt>“银行”记录：</dt>").append("<dd>" + data.initializationData.bankTotalRecords + "</dd>")
                    .append("<dt>“快递公司”记录：</dt>").append("<dd>" + data.initializationData.expressCompanyTotalRecords + "</dd>")
                    .append("<dt>“管理员角色”记录：</dt>").append("<dd>" + data.initializationData.administratorRoleTotalRecords + "</dd>")
                    .append("<dt>“管理员权限”记录：</dt>").append("<dd>" + data.initializationData.administratorPermissionTotalRecords + "</dd>")
                    .append("<dt>“用户权限”记录：</dt>").append("<dd>" + data.initializationData.userPermissionTotalRecords + "</dd>")
                    .append("<dt>“用户角色”记录：</dt>").append("<dd>" + data.initializationData.userRoleTotalRecords + "</dd>")
                    .append("<dt>“商家的保证金场”记录：</dt>").append("<dd>" + data.initializationData.merchantMarginTotalRecords + "</dd>");


            $("#loading").hide();
        }

        function getDetailFailureCallback(jqXHR, textStatus, errorThrown) {
            $("#loading").hide();
        }

        $("#affix-nav").addClass("affix-nav-fixed-bottom").affix();
        getEnvironments();
    });
</script>
</body>
</html>
