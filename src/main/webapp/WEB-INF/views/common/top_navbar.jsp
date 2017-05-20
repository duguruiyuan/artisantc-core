<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"><fmt:message key="text.page.head.title"/></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li>
                    <a href="<c:url value='/console/' />">首页</a>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">审核管理 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="<c:url value='/console/items/pending-review' />">拍品审核</a></li>
                        <li><a href="<c:url value='/console/real-name/pending-review' />">实名认证</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">数据管理 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="<c:url value='/console/information' />">资讯</a></li>
                        <li><a href="<c:url value='/console/advertisements' />">广告</a></li>
                        <li><a href="<c:url value='/console/banks' />">银行</a></li>
                        <li><a href="<c:url value='/console/express-companies' />">快递公司</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">支付管理 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="<c:url value='/console/item-payment-orders' />">拍品支付订单</a></li>
                        <li><a href="<c:url value='/console/item-margin-orders' />">拍品保证金支付订单</a></li>
                        <li><a href="<c:url value='/console/merchant-margin-orders' />">商家保证金支付订单</a></li>
                        <li class="divider"></li>
                        <li><a href="<c:url value='/console/item-refund-orders' />">拍品退款订单</a></li>
                        <li><a href="<c:url value='/console/item-margin-refund-orders' />">拍品保证金退款订单</a></li>
                        <li class="divider"></li>
                        <li><a href="<c:url value='/console/withdrawal-balance' />">提现申请</a></li>
                        <li><a href="<c:url value='/console/withdrawal-margin' />">转出保证金申请</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">用户管理 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="<c:url value='/console/users' />">用户列表</a></li>
                        <li class="divider"></li>
                        <li><a href="<c:url value='/console/art-big-shots' />">大咖列表</a></li>
                        <li class="divider"></li>
                        <li><a href="<c:url value='/console/suggestions' />">意见反馈</a></li>
                    </ul>
                </li>
            </ul>

            <%-- 右对齐的菜单 --%>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">我的 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="<c:url value='/console/password' />">修改密码</a></li>
                        <li><a id="logout-menu" href="#">退出登录</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>
