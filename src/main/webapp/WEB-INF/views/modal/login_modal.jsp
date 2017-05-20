<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<%-- “登录”模态框 --%>
<div class="modal fade" id="login-modal" tabindex="-1" role="dialog" aria-labelledby="login-modal-label" aria-hidden="true">
    <%-- 模态框的内容会通过ajax的方式来获得 --%>
    <%-- 模态框的内容 --%>
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <%-- 模态框的header --%>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title"><fmt:message key="text.label.login.title"/></h4>
            </div>
            <%-- 模态框的body --%>
            <div class="modal-body" id="login-modal-body">
            </div>
            <%-- 模态框的footer --%>
            <div class="modal-footer" id="login-modal-footer">
            </div>
        </div>
    </div>
</div>
