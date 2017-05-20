<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    <h4 class="modal-title" id="create-modal-label"><fmt:message key="text.label.advertisement.create"/></h4>
</div>

<div class="modal-body">
    <form id="advertisementForm" action="<c:url value="/administrator/advertisements/images"/>" method="post" enctype="multipart/form-data">
        <div class="alert alert-success" id="create-success" role="alert"><fmt:message key="text.alert.success"/></div>
        <div class="alert alert-danger" id="create-failure" role="alert"><fmt:message key="text.alert.danger"/></div>
        <div class="alert alert-warning" id="validation-message" role="alert">请输入“广告标题”，并且上传广告图片！</div>

        <div class="form-group">
            <input class="form-control" name="title" type="text" id="advertisement-title" placeholder="广告标题" required="required" autofocus="autofocus">
        </div>
        <div class="form-group">
            <input name="images" type="file" id="advertisement-file-input">
        </div>
    </form>
</div>

<div class="modal-footer">
    <button class="btn btn-lg btn-primary btn-block" id="save-btn" type="button"><fmt:message key="text.button.save"/></button>
</div>