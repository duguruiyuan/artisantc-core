$(function () {
    /* 隐藏“登录”模态框 */
    $("#login-modal").modal({
        show: false
    });

    /* 登录JS */
    $(document).on("click", "#btn-login", function () {
        $("#login-failure").hide();
        $.ajax({
            method: "POST",
            url: "/api/login",
            data: JSON.stringify($("#loginForm").serializeJSON())
        }).then(loginSuccessCallback, loginFailureCallback);
    });

    // “登录成功”回调函数
    function loginSuccessCallback(data, textStatus, jqXHR) {
        $("#login-success").show();
        $("#login-modal").modal("hide");
    }

    // “登录失败”回调函数
    function loginFailureCallback(jqXHR, textStatus, errorThrown) {
        $("#login-failure").show();
    }

    /* 退出登录JS */
    $('#logout-menu').on("click", function () {
        $.ajax({
            method: "GET",
            url: "/api/logout"
        }).always(afterLogoutCallback)
    });

    // “退出登录”后的回调函数，无论成功失败
    function afterLogoutCallback() {
        window.location.reload();
    }
});