layui.use(['flow', 'element'], function () {
    let $ = layui.jquery; //不用额外加载jQuery，flow模块本身是有依赖jQuery的，直接用即可。
    let flow = layui.flow;
    let element = layui.element;

    let rateLoad = false;
    let recentLoad = false;

    element.on('tab(resourceTab)', function (data) {
        console.log(this); //当前Tab标题所在的原始DOM元素
        console.log(data.index); //得到当前Tab的所在下标
        console.log(data.elem); //得到当前的Tab大容器
        if (data.index == 1 && !recentLoad) {
            recentLoad = true;
            flow.load({
                elem: '#loadResourceByTime' //流加载容器
                , scrollElem: '#loadResourceByTime' //滚动条所在元素，一般不用填，此处只是演示需要。
                , isAuto: false
                , isLazyimg: true
                , done: function (page, next) { //加载下一页
                    let lis = [];

                    //以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）
                    $.get('/api/resource/list?page=' + page + '&limit=9&order_by=created_at desc', function (res) {
                        //假设你的列表返回在data集合中
                        layui.each(res.data, function (index, item) {
                            var examine = item.status == 0 ? "未审核" : "已审核"
                            if (item.type == "picture") {
                                lis.push('<div class="column"  style="cursor: pointer;" onclick="window.open(\'/api/resource/' + item.id + '?keyword=' + item.fileName + '\',\'_self\')">\n' +
                                    '                    <div class="ui fluid card">\n' +
                                    '                        <div class="image">\n' +
                                    '                            <span style="margin-left: 3%">' + item.type + '</span>\n' +
                                    '                            <p style="float: right;margin-right: 3%">' + examine + '</p>' +
                                    '<img src="' + item.filePath + '">' +
                                    '                        </div>\n' +
                                    '                        <div class="content">\n' +
                                    '                            <a class="header" href="/api/resource/' + item.id + '">' + item.fileName + '</a>\n' +
                                    '                        </div>\n' +
                                    '                    </div>\n' +
                                    '                </div>')
                            } else {
                                lis.push('<div class="column"  style="cursor: pointer;" onclick="window.open(\'/api/resource/' + item.id + '?keyword=' + item.fileName + '\',\'_self\')">\n' +
                                    '                    <div class="ui fluid card">\n' +
                                    '                        <div class="image">\n' +
                                    '                            <span style="margin-left: 3%">' + item.type + '</span>\n' +
                                    '                            <p style="float: right;margin-right: 3%">' + examine + '</p>' +
                                    ' <img  src="/images/resource/' + item.type + '.jpg">' +
                                    '                        </div>\n' +
                                    '                        <div class="content">\n' +
                                    '                            <a class="header" href="/api/resource/' + item.id + '">' + item.fileName + '</a>\n' +
                                    '                        </div>\n' +
                                    '                    </div>\n' +
                                    '                </div>')
                            }
                        });

                        //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
                        //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
                        next(lis.join(''), page < res.count / 9);
                    });
                }
            });
        } else if (data.index == 2 && !rateLoad) {
            rateLoad = true;
            flow.load({
                elem: '#loadResourceByRate' //流加载容器
                , scrollElem: '#loadResourceByRate' //滚动条所在元素，一般不用填，此处只是演示需要。
                , isAuto: false
                , isLazyimg: true
                , done: function (page, next) { //加载下一页
                    let lis = [];

                    //以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）
                    $.get('/api/resource/list?page=' + page + '&limit=9&order_by=total_rate/total_rate_num desc', function (res) {
                        //假设你的列表返回在data集合中
                        layui.each(res.data, function (index, item) {
                            var examine = item.status == 0 ? "未审核" : "已审核"
                            if (item.type == "picture") {
                                lis.push('<div class="column"  style="cursor: pointer;" onclick="window.open(\'/api/resource/' + item.id + '?keyword=' + item.fileName + '\',\'_self\')">\n' +
                                    '                    <div class="ui fluid card">\n' +
                                    '                        <div class="image">\n' +
                                    '                            <span style="margin-left: 3%">' + item.type + '</span>\n' +
                                    '                            <p style="float: right;margin-right: 3%">' + examine + '</p>' +
                                    '<img src="' + item.filePath + '">' +
                                    '                        </div>\n' +
                                    '                        <div class="content">\n' +
                                    '                            <a class="header" href="/api/resource/' + item.id + '">' + item.fileName + '</a>\n' +
                                    '                        </div>\n' +
                                    '                    </div>\n' +
                                    '                </div>')
                            } else {
                                lis.push('<div class="column"  style="cursor: pointer;" onclick="window.open(\'/api/resource/' + item.id + '?keyword=' + item.fileName + '\',\'_self\')">\n' +
                                    '                    <div class="ui fluid card">\n' +
                                    '                        <div class="image">\n' +
                                    '                            <span style="margin-left: 3%">' + item.type + '</span>\n' +
                                    '                            <p style="float: right;margin-right: 3%">' + examine + '</p>' +
                                    ' <img  src="/images/resource/' + item.type + '.jpg">' +
                                    '                        </div>\n' +
                                    '                        <div class="content">\n' +
                                    '                            <a class="header" href="/api/resource/' + item.id + '">' + item.fileName + '</a>\n' +
                                    '                        </div>\n' +
                                    '                    </div>\n' +
                                    '                </div>')
                            }
                        });

                        //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
                        //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
                        next(lis.join(''), page < res.count / 9);
                    });
                }
            });
        }
    });

    flow.load({
        elem: '#loadResource' //流加载容器
        , scrollElem: '#loadResource' //滚动条所在元素，一般不用填，此处只是演示需要。
        , isAuto: false
        , isLazyimg: true
        , done: function (page, next) { //加载下一页
            let lis = [];

            //以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）
            $.get('/api/resource/list?page=' + page + '&limit=9', function (res) {
                //假设你的列表返回在data集合中
                layui.each(res.data, function (index, item) {
                    var examine = item.status == 0 ? "未审核" : "已审核"
                    if (item.type == "picture") {
                        lis.push('<div class="column"  style="cursor: pointer;" onclick="window.open(\'/api/resource/' + item.id + '?keyword=' + item.fileName + '\',\'_self\')">\n' +
                            '                    <div class="ui fluid card">\n' +
                            '                        <div class="image">\n' +
                            '                            <span style="margin-left: 3%">' + item.type + '</span>\n' +
                            '                            <p style="float: right;margin-right: 3%">' + examine + '</p>' +
                            '<img src="' + item.filePath + '">' +
                            '                        </div>\n' +
                            '                        <div class="content">\n' +
                            '                            <a class="header" href="/api/resource/' + item.id + '">' + item.fileName + '</a>\n' +
                            '                        </div>\n' +
                            '                    </div>\n' +
                            '                </div>')
                    } else {
                        lis.push('<div class="column"  style="cursor: pointer;" onclick="window.open(\'/api/resource/' + item.id + '?keyword=' + item.fileName + '\',\'_self\')">\n' +
                            '                    <div class="ui fluid card">\n' +
                            '                        <div class="image">\n' +
                            '                            <span style="margin-left: 3%">' + item.type + '</span>\n' +
                            '                            <p style="float: right;margin-right: 3%">' + examine + '</p>' +
                            ' <img  src="/images/resource/' + item.type + '.jpg">' +
                            '                        </div>\n' +
                            '                        <div class="content">\n' +
                            '                            <a class="header" href="/api/resource/' + item.id + '">' + item.fileName + '</a>\n' +
                            '                        </div>\n' +
                            '                    </div>\n' +
                            '                </div>')
                    }
                });

                //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
                //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
                next(lis.join(''), page < res.count / 9);
            });
        }
    });

    //按屏加载图片-懒加载
    /*flow.lazyimg({
        elem: '#loadImage img'
        ,scrollElem: '#loadImage' //一般不用设置，此处只是演示需要。
    });*/
});

function getResources(pageIndex, pageSize, category, mode) {
    $.ajax({
        url: "/api/resource/list",
        contentType: 'application/json',
        data: {
            "page": pageIndex,
            "limit": pageSize,
            "mode": mode,
            "category": category
        },
        success: function (res) {
            return res;
        },
        error: function () {
            layui.use(['layer'], function () {
                let layer = layui.layer;
                layer.msg("上传异常！");
            });
        }
    });
}

