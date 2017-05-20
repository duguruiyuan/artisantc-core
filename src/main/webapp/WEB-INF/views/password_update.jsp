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

    <title id="head-title"><fmt:message key="text.page.password.update"/> - <fmt:message key="text.page.head.title"/></title>

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
                <h3><fmt:message key="text.page.password.update"/></h3>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-11 col-md-11 col-lg-11" role="main">
            <form id="passwordChangeForm">
                <div class="form-group">
                    <input class="form-control" name="oldPassword" type="password" placeholder="<fmt:message key="text.label.password.old"/>" required="required" autofocus="autofocus">
                </div>
                <div class="form-group">
                    <input class="form-control" name="newPassword" type="password" id="newPassword" placeholder="<fmt:message key="text.label.password.new"/>" required="required">
                </div>
                <div class="form-group">
                    <input class="form-control" name="confirmPassword" type="password" id="confirmPassword" placeholder="<fmt:message key="text.label.password.comfirm"/>" required="required">
                </div>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-11 col-md-11 col-lg-11" role="main">
            <div class="alert alert-danger" id="error-403" role="alert"><fmt:message key="text.page.error.403"/></div>
            <div class="alert alert-success" id="ajax-success" role="alert"><fmt:message key="text.alert.success"/></div>
            <div class="alert alert-danger" id="ajax-failure" role="alert"><fmt:message key="text.alert.danger"/></div>
            <div class="alert alert-danger" id="password-confirm-failure" role="alert">两次输入的新密码不一致！</div>
            <button class="btn btn-lg btn-primary btn-block" id="btn-save" type="button"><fmt:message key="text.button.save"/></button>
        </div>
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
        $("#ajax-success").hide();
        $("#ajax-failure").hide();
        $("#password-confirm-failure").hide();

        $("body").on("click", "#btn-save",// 绑定事件到“去转账”按钮
                function () {
                    $("#ajax-success").hide();
                    $("#ajax-failure").hide();
                    if ($("#newPassword").val() == $("#confirmPassword").val()) {
                        $("#password-confirm-failure").hide();
                        // 执行“保存”操作
                        $.ajax({
                            method: "PATCH",
                            url: "/administrator/i/password",
                            data: JSON.stringify($("#passwordChangeForm").serializeJSON())
                        }).then(saveSuccessCallback, saveFailureCallback);
                    } else {
                        $("#password-confirm-failure").show();
                    }
                }
        );

        function saveSuccessCallback(data, textStatus, jqXHR) {
            $("#ajax-failure").hide().empty();
            $("#button-group").empty();
            $("#ajax-success").show();
            $("#loading").hide();
        }

        function saveFailureCallback(jqXHR, textStatus, errorThrown) {
            ajaxFailureCallback(jqXHR, textStatus, errorThrown);
        }

        function ajaxFailureCallback(jqXHR, textStatus, errorThrown) {
            $("#ajax-success").hide();
            var errorMessage = $.parseJSON(jqXHR.responseText);
            $("#ajax-failure").html(errorMessage.errorDesc).show();
            $("#loading").hide();
        }
    });
</script>
</body>
</html>