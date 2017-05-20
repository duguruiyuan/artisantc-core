<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%-- “登录”模态框的内容 --%>
<div class="modal-body">
    <form id="loginForm">
        <div class="alert alert-success" id="login-success" role="alert">登录成功！</div>
        <div class="alert alert-danger" id="login-failure" role="alert">用户名/密码错误！</div>

        <div class="form-group">
            <input class="form-control" name="mobile" type="text" placeholder="<fmt:message key="text.label.username"/>" required="required" autofocus="autofocus">
        </div>
        <div class="form-group">
            <input class="form-control" name="password" type="password" placeholder="<fmt:message key="text.label.password"/>" required="required">
        </div>
    </form>
</div>

<div class="modal-footer">
    <button class="btn btn-lg btn-primary btn-block" id="btn-login" type="button"><fmt:message key="text.button.login"/></button>
</div>