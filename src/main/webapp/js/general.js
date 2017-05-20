var loadingDiv = "<div class=\"row\" id=\"login-modal-loading\"><img src=\"/img/loading.gif\" alt=\"加载中\"/>加载中......</div>";// 加载提示
var loadingFailureDiv = "<div class=\"alert alert-danger\" role=\"alert\">加载失败！</div>";// 加载失败
var dangerImg = "<img class=\"img-icon\" src=\"/img/fail.png\"> ";

$(function () {
    var ajaxContentType = "application/json";// ajax请求使用的“Content-Type”
    var ajaxTimeout = 20000;// ajax请求的超时时间：20秒

    // ajax的通用设置
    $.ajaxSetup({
        contentType: ajaxContentType,
        timeout: ajaxTimeout,
        statusCode: {
            401: callLoginModal,
            403: function () {
                $("#error-403").show();
            },
            404: function () {
                console.log("404, page not found!");// todo: 404
            },
            500: function (jqXHR) {
                console.log("500");// todo: 500
                console.log("jqXHR.responseText: " + jqXHR.responseText);
            }
        }
    });

    // “当前访问菜单”的样式
    $("ul>li>a").each(function () {
        var pathname = window.location.pathname;// 当前访问的URI
        var href = $(this).attr("href");// 菜单的URI
        if (pathname === href) {
            $(this).parent('li').addClass("active");
            return false;
        }
    });

    // 页面加载完成后执行的操作
    $("#error-403").hide();
});

function callLoginModal() {
    // 关闭可能已经显示的模态框，否则“登录”模态框会被挡住
    $.each($(".modal"), function () {
        $(this).modal("hide")
    });

    // 清空可能存在的数据
    $("#login-modal-footer").empty()
    $("#login-modal-body").empty().append(loadingDiv);
    // 显示“登录”模态框
    $("#login-modal").modal("show");
    // 加载“登录”模态框的内容
    $.ajax({
        method: "GET",
        url: "/console/login-modal"
    }).then(getLoginPageSuccessCallback, getLoginPageFailureCallback);
}

// “登录”模态框内容“加载成功”回调函数
function getLoginPageSuccessCallback(data, textStatus, jqXHR) {
    // 清空并更新数据
    $("#login-modal-body").empty().append($(data).filter("div.modal-body").html());
    $("#login-modal-footer").empty().append($(data).filter("div.modal-footer").html());

    // 隐藏返回的html代码中的元素
    $("#login-success").hide();
    $("#login-failure").hide();

    // 隐藏“加载提示”
    $("#login-modal-loading").hide();
}

// “登录”模态框内容“加载失败”回调函数
function getLoginPageFailureCallback(jqXHR, textStatus, errorThrown) {
    $("#login-modal-body").empty().append(loadingFailureDiv);
}