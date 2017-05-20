<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    <h4 class="modal-title" id="create-modal-label"><fmt:message key="text.label.comment.on"/></h4>
</div>

<div class="modal-body">
    <form id="commentForm" action="" method="post" enctype="multipart/form-data">
        <div class="alert alert-success" id="alert-success" role="alert"><img class="img-icon" src="<c:url value="/img/success.png"/>"> <fmt:message key="text.alert.success"/></div>
        <div class="alert alert-danger" id="alert-danger" role="alert"><fmt:message key="text.alert.danger"/></div>

        <div class="form-group">
            <textarea class="form-control" name="comment" placeholder="我想说..." rows="7" required="required"></textarea>
        </div>
    </form>
</div>

<div class="modal-footer">
    <button class="btn btn-lg btn-primary btn-block" id="ok-btn" type="button"><fmt:message key="text.button.ok"/></button>
    <button class="btn btn-lg btn-default btn-block" id="cancel-btn" type="button"><fmt:message key="text.button.cancel"/></button>
</div>