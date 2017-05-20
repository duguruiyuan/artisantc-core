<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    <h4 class="modal-title" id="create-modal-label"><fmt:message key="text.label.information.create"/></h4>
</div>

<div class="modal-body">
    <form id="informationForm" action="<c:url value="/administrator/information/cover"/>" method="post" enctype="multipart/form-data">
        <div class="alert alert-success" id="create-success" role="alert"><fmt:message key="text.alert.success"/></div>
        <div class="alert alert-danger" id="create-failure" role="alert"><fmt:message key="text.alert.danger"/></div>
        <div class="alert alert-warning" id="validation-message" role="alert">请上传一张图片做封面吧！</div>

        <div class="form-group">
            <input class="form-control" name="title" type="text" placeholder="资讯标题" required="required" autofocus="autofocus">
        </div>
        <div class="form-group">
            <input class="form-control" name="source" type="text" placeholder="资讯来源" required>
        </div>
        <div class="form-group">
            <input name="images" type="file" id="information-file-input">
        </div>
        <div class="form-group">
            <input name="content" id="information-content" type="hidden">
            <div id="information-content-editor"></div>
        </div>
    </form>
</div>

<div class="modal-footer">
    <button class="btn btn-lg btn-primary btn-block" id="save-btn" type="button"><fmt:message key="text.button.save"/></button>
    <button class="btn btn-lg btn-success btn-block" id="save-and-publish-btn" type="button"><fmt:message key="text.button.save.and.publish"/></button>
</div>