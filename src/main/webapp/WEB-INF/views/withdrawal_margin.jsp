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

    <title><fmt:message key="text.page.withdrawal.margin"/> - <fmt:message key="text.page.head.title"/></title>

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
    <%-- 页面标题 --%>
    <div class="col-sm-11 col-md-11 col-lg-11 page-header">
        <h1><fmt:message key="text.page.withdrawal.margin"/>
            <button class="btn btn-primary btn-lg pull-right" id="search-btn" title="<fmt:message key="text.button.search"/>"><span class="glyphicon glyphicon-search"></span></button>
        </h1>
        <div class="alert alert-danger" id="error-403" role="alert"><fmt:message key="text.page.error.403"/></div>
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
    <div id="loading">
        <img src="<c:url value='/img/loading.gif' />" alt="数据加载中"/>数据加载中......
    </div>

    <%-- footer starts --%>
    <%@include file="common/footer.jsp" %>
    <%-- footer ends --%>

    <%-- 登录模态框--%>
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
<%-- 分页列表JS --%>
<script src="<c:url value='/js/pagination.js' />"></script>
<%-- 引入masonry插件，瀑布流布局 --%>
<script src="<c:url value='/js/masonry.pkgd.min.js' />"></script>
<%-- 由masonry插件作者推荐使用，瀑布流布局 --%>
<script src="<c:url value='/js/imagesloaded.pkgd.min.js' />"></script>
<script>
    $(function () {
        $("#affix-nav").addClass("affix-nav-fixed-bottom").affix();
        firstSearch();
    });

    /* 分页列表功能 */
    var pageHref = "<c:url value='/administrator/withdrawal-margin' />";

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

        $.each(data.bills, function () {

            var $appendData = $("<div class=\"thumbnail img-cover\">" +
                    "<a href=\"<c:url value="/console/withdrawal-margin/"/>" + this.billId + "\" target=\"_blank\">" +
                    "<div class=\"caption\">" +
                    "<p>" + this.amount + "<span class=\"pull-right\">" + this.createDateTime + "</span></p>" +
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