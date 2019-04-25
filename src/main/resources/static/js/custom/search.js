function getImages(pageIndex, pageSize, type, keyword) {
    $.ajax({
        url: "/search/resource",
        contentType: 'application/json',
        data: {
            "page": pageIndex,
            "limit": pageSize,
            "type": type,
            "keyword": keyword
        },
        success: function (data) {

        },
        error: function () {
            layui.use(['layer'], function () {
                let layer = layui.layer;
                layer.msg("上传异常！");
            });
        }
    });
}

$(function () {
    $("#searchButton").click(function () {
        var keyword = $("#searchText").val();
        let search = window.location.search.split("type=");
        let append = ""
        if (search.length == 2) {
            append = "&type=" + search[1];
        }
        window.location.href = "/search/resource?keyword=" + keyword + append;
    });
});