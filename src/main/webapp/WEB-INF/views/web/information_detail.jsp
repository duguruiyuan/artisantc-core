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

    <title><c:out value="${view.title}"/> - <fmt:message key="text.page.information"/> - <fmt:message key="text.page.head.title"/></title>

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
                <h3><strong><c:out value="${view.title}"/></strong></h3>
                <p>来源：<c:out value="${view.source}"/> <span class="text-right" style="float: right;position: relative"><c:out value="${view.createDateTime}"/></span></p>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-11 col-md-11 col-lg-11" role="main">
            <%-- 页面内容 --%>
            <p><c:out value="${view.content}" escapeXml="false"/></p>
        </div>
        <div class="col-sm-1 col-md-1 col-lg-1">
        </div>
    </div>
    <c:if test="${not empty _currentLoginUser}">
        <div class="row">
            <div class="col-sm-11 col-md-11 col-lg-11" role="main">
                <p>
                    <c:out value="${comments.totalRecords}"/>条评论
                </p>
            </div>
        </div>
        <c:if test="${not empty comments.commentViews}">
            <c:forEach var="comment" items="${comments.commentViews}">
                <div class="row">
                    <div class="col-sm-11 col-md-11 col-lg-11" role="main">
                        <div class="row comment-top">
                            <div class="col-sm-1 col-md-1 col-lg-1" role="main">
                                <img class="img-thumbnail img-circle img-lazy avatar" data-src="holder.js/100%x100%" data-original="${comment.ownerAvatarUrl}">
                            </div>
                            <div class="col-sm-11 col-md-11 col-lg-11" role="main">
                                <div class="row">
                                    <div class="col-sm-8 col-md-8 col-lg-8" role="main">
                                        <p><c:out value="${comment.ownerNickname}"/></p>
                                        <p class="text-b6b6b6"><c:out value="${comment.createDateTime}"/></p>
                                    </div>
                                    <div class="col-sm-2 col-md-2 col-lg-2" role="main">
                                        <img class="comment-img" id="comment-img-${comment.id}" src="<c:url value="/img/comment.png"/>">
                                        <span id="comment-text-${comment.id}"><c:out value="${comment.commentTimes}"/></span>
                                    </div>
                                    <div class="col-sm-2 col-md-2 col-lg-2" role="main">
                                        <c:choose>
                                            <c:when test="${comment.liked}"><img class="like-img" id="like-img-${comment.id}" src="<c:url value="/img/liked.png"/>"></c:when>
                                            <c:otherwise><img class="like-img" id="like-img-${comment.id}" data-container="body" src="<c:url value="/img/like.png"/>"></c:otherwise>
                                        </c:choose>
                                        <span id="like-text-${comment.id}"><c:out value="${comment.likeTimes}"/></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-1 col-md-1 col-lg-1" role="main">
                            </div>
                            <div class="col-sm-11 col-md-11 col-lg-11" role="main">
                                <div class="row <c:if test="${empty comment.childrenComments}">comment-bottom</c:if>">
                                    <div role="main">
                                        <p><c:out value="${comment.comment}"/></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <c:if test="${not empty comment.childrenComments}">
                            <div class="row">
                                <div class="col-sm-1 col-md-1 col-lg-1" role="main">
                                </div>
                                <div class="col-sm-11 col-md-11 col-lg-11" role="main">
                                    <div class="row comment-bottom">
                                        <div class="jumbotron" id="comment-jumbotron-${comment.id}">
                                            <c:forEach var="childrenComment" items="${comment.childrenComments}">
                                                <p><span class="text-1ba39c"><c:out value="${childrenComment.ownerNickname}"/></span> <strong>回复</strong> <span class="text-1ba39c"><c:out value="${comment.ownerNickname}"/></span>：<span
                                                        class="text-7b7b7b"><c:out value="${childrenComment.comment}"/></span></p>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
            <div class="row comment-top">
                <p class="text-center text-button">查看更多</p>
            </div>
        </c:if>
    </c:if>
    <div class="row">
        <div class="col-sm-11 col-md-11 col-lg-11" role="main">
        </div>
        <div class="col-sm-1 col-md-1 col-lg-1">
            <%-- 附加导航 --%>
            <%@include file="../common/affix.jsp" %>
        </div>
    </div>

    <%-- “登录”模态框 --%>
    <%@include file="../modal/login_modal.jsp" %>

    <%-- “评论”模态框 --%>
    <%@include file="../modal/create_modal.jsp" %>
</div>

<%-- jQuery (necessary for Bootstrap's JavaScript plugins) --%>
<script src="<c:url value='/js/jquery-3.1.1.min.js' />"></script>
<%-- Include all compiled plugins of Bootstrap --%>
<script src="<c:url value='/js/bootstrap.min.js' />"></script>
<%-- Include lazy load plugin for jQuery --%>
<script src="<c:url value='/js/jquery.lazyload.min.js' />"></script>
<%-- Plugin for jQuer: Adds the method .serializeJSON() to jQuery that serializes a form into a JavaScript Object --%>
<script src="<c:url value='/js/jquery.serializejson.min.js' />"></script>
<%-- 通用JS --%>
<script src="<c:url value='/js/general.js' />"></script>
<%-- 登录/退出登录JS --%>
<script src="<c:url value='/js/login.js' />"></script>
<script>
    $(function () {
        /* 替换v2.2版本以前“资讯”内容中的图片 */
        $("p img").each(function () {
            var imgSrc = $(this).attr("src");// 当前图片的src
            // 替换“img”元素为bootstrap的栅格，只能做“替换”操作，而不能做“先删除再追加”操作，因为“先删除再追加”操作会让图片的位置发生变化
//            $(this).replaceWith("<img class=\"img-thumbnail img-lazy\" data-src=\"holder.js/100%x100%\" data-original=\"" + imgSrc + "\">");
            $(this).replaceWith("<a href=\"" + imgSrc + "\" target=\"_blank\"><img class=\"img-thumbnail img-lazy\" data-src=\"holder.js/100%x100%\" src=\"" + imgSrc + "\"></a>");
        });

        /* 替换v2.2版本以后用户自己发布的“资讯”内容中的图片 */
        $("img .replace-img").each(function () {
            var imgSrc = $(this).attr("src");// 当前图片的src
            // 替换“img”元素为bootstrap的栅格，只能做“替换”操作，而不能做“先删除再追加”操作，因为“先删除再追加”操作会让图片的位置发生变化
//            $(this).replaceWith("<img class=\"img-thumbnail img-lazy\" data-src=\"holder.js/100%x100%\" data-original=\"" + imgSrc + "\">");
            $(this).replaceWith("<a href=\"" + imgSrc + "\" target=\"_blank\"><img class=\"img-thumbnail img-lazy\" data-src=\"holder.js/100%x100%\" src=\"" + imgSrc + "\"></a>");
        });

        // 绑定事件到“点赞”图片，触发“点赞/取消点赞”操作
        var likeCommentId, commentOnCommentId;
        $("body")
            .on("click", ".like-img",
                function () {
                    likeCommentId = this.id;
                    likeCommentId = likeCommentId.substring(likeCommentId.lastIndexOf("like-img-") + 9);
                    giveLike();
                }
            )
            // 绑定事件到“评论”图片，触发“评论一条评论”操作
            .on("click", ".comment-img",
                function () {
                    // 清空可能存在的数据
                    $("#create-modal-footer").empty();
                    $("#create-modal-body").empty().append(loadingDiv);
                    // 显示模态框
                    $("#create-modal").modal("show");

                    // 获得要评论的评论ID
                    commentOnCommentId = this.id;
                    commentOnCommentId = commentOnCommentId.substring(commentOnCommentId.lastIndexOf("comment-img-") + 12);

                    // 加载“评论”模态框的内容
                    $.ajax({
                        method: "GET",
                        url: "/console/comment-on-modal"
                    }).then(getModalContentSuccessCallback, getModalContentFailureCallback);
                }
            );

        $(".img-lazy").lazyload();
        $("#affix-nav").addClass("affix-nav-bottom").affix();

        function giveLike() {
            var informationId = ${view.id};// 资讯ID
            var url = "/api/information/" + informationId + "/comments/" + likeCommentId + "/likes";
            $.ajax({
                method: "POST",
                url: url
            }).then(giveLikeSuccessCallback, giveLikeFailureCallback);
        }

        function giveLikeSuccessCallback(data, textStatus, jqXHR) {
            // 更新“点赞数量”
            $("#like-text-" + likeCommentId).empty().text(data.likeTimes);

            // 更新“点赞图片”
            var likeImg = $("#like-img-" + likeCommentId);
            var likeImgSrc = likeImg.attr("src");
            var regExp = new RegExp("like.png");
            if (regExp.test(likeImgSrc)) {
                likeImgSrc = likeImgSrc.replace("like.png", "liked.png")
            } else {
                likeImgSrc = likeImgSrc.replace("liked.png", "like.png")
            }
            likeImg.attr("src", likeImgSrc);

            // 清空“likeCommentId”
            likeCommentId = "";
        }

        function giveLikeFailureCallback(jqXHR, textStatus, errorThrown) {
            likeCommentId = "";
            alert("点赞失败！");// todo：点赞失败
        }

        function commentOn() {
            var informationId = ${view.id};// 资讯ID
            var url = "/api/information/" + informationId + "/comments";

            var ajaxData = $("#commentForm").serializeJSON();// 表单数据，JSON格式
            ajaxData.parentCommentId = commentOnCommentId;

            $.ajax({
                method: "POST",
                url: url,
                data: JSON.stringify(ajaxData)
            }).then(commentOnSuccessCallback, commentOnFailureCallback);
        }

        function commentOnSuccessCallback(data, textStatus, jqXHR) {
            // 更新“评论数量”
            $("#comment-text-" + commentOnCommentId).empty().text(data.parentCommentCommentTimes);

            // 追加“新评论”
            var newComment = "<p><span class=\"text-1ba39c\">" + data.ownerNickname + "</span> <strong>回复</strong> <span class=\"text-1ba39c\">" + data.parentCommentUserNickname + "</span>：<span class=\"text-7b7b7b\">" + data.comment + "</span></p>";
            $("#comment-jumbotron-" + commentOnCommentId).append(newComment);

            // 清空“likeCommentId”
            commentOnCommentId = "";

            $("#create-modal").modal("hide");
        }

        function commentOnFailureCallback(jqXHR, textStatus, errorThrown) {
            commentOnCommentId = "";
            alert("评论失败！");// todo：评论失败
        }

        /* “评论”模态框的JS */
        $("#create-modal").modal({
            show: false
        });

        // “评论”模态框内容“加载成功”回调函数
        function getModalContentSuccessCallback(data, textStatus, jqXHR) {
            // 清空并更新数据
            $("#create-modal-header").empty().append($(data).filter("div.modal-header").html());
            $("#create-modal-body").empty().append($(data).filter("div.modal-body").html());
            $("#create-modal-footer").empty().append($(data).filter("div.modal-footer").html());

            // 隐藏返回的html代码中的元素
            $("#alert-success").hide();
            $("#alert-danger").hide();

            // 设置“确定”按钮事件
            $("#ok-btn").click(
                function () {
                    commentOn();
                }
            );

            // 设置“取消”按钮事件
            $("#cancel-btn").click(
                function () {
                    // 隐藏模态框
                    $("#create-modal").modal("hide");
                }
            );

            // 隐藏“加载提示”
            $("#login-modal-loading").hide();
        }

        // “评论”模态框内容“加载失败”回调函数
        function getModalContentFailureCallback(jqXHR, textStatus, errorThrown) {
            $("#create-modal-body").empty().append(loadingFailureDiv);
        }
    });
</script>
</body>
</html>