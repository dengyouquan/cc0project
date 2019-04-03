function getImages(pageIndex, pageSize, type, keyword) {
    $.ajax({
        url: "/search/image",
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
            layui.use([ 'layer'], function() {
                let layer = layui.layer;
                layer.msg("上传异常！");
            });
        }
    });
}

$(function () {
    $("#searchButton").click(function () {
        var keyword = $("#searchText").val();
        window.location.href = "/search/image?keyword=" + keyword;
    });
});