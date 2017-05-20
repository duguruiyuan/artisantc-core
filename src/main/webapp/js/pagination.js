var pageNumber = 1;
var isEnd = false;// 列表结果是否已经加载到最后一页了
var canLoad = true;// 滚动条到底部后是否执行加载数据动作
$(function () {
    $("#loading").hide();

    // Each time the user scrolls
    var win = $(window);
    win.scroll(function () {
        // End of the document reached?
        if ($(document).height() - win.height() == win.scrollTop()) {
            if (!isEnd && canLoad) {
                canLoad = false;
                getList(pageHref + "?page=" + pageNumber);
            }
        }
    });

    $("#search-btn").click(function () {
        window.location.reload();
    });
});

function getList(url) {
    $("#loading").show();
    $.ajax({
        method: "GET",
        url: url,
        dataType: "json"
    }).then(getListSuccessCallback, getListFailureCallback);
}

function getListFailureCallback(jqXHR, textStatus, errorThrown) {
    $("#loading").hide();
    canLoad = true;
}

var $container = $(".page-content");
function firstSearch() {
    if ($container == undefined) {
        return;
    }
    // 清空之前可能存在的数据
    $container.empty();
    // 重置影响分页查询的参数
    isEnd = false;
    pageNumber = 1;
    // 永远只查询第一页数据
    getList(pageHref + "?page=" + pageNumber);
}