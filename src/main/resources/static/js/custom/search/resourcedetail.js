layui.use('flow', function () {
    let $ = layui.jquery; //不用额外加载jQuery，flow模块本身是有依赖jQuery的，直接用即可。
    let flow = layui.flow;

    flow.load({
        elem: '#loadResourceDetail' //流加载容器
        , scrollElem: '#loadResourceDetail' //滚动条所在元素，一般不用填，此处只是演示需要。
        , isAuto: false
        , isLazyimg: true
        , done: function (page, next) { //加载下一页
            let lis = [];
            let data = window.location.search;
            let keyword = data;
            let arr = window.location.href.split("?")[0].split("/");
            let r_id = arr[arr.length - 1];
            //alert(r_id);
            //以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）
            $.get('/search/resource/list' + keyword + '&page=' + page + '&limit=10&contain_self=false&r_id=' + r_id, function (res) {
                //假设你的列表返回在data集合中
                layui.each(res.data, function (index, item) {
                    if (item.type == "picture") {
                        lis.push('<div class="column"  style="cursor: pointer;" onclick="window.open(\'/api/resource/' + item.esdocumentId + '?keyword=' + item.fileName + '\',\'_self\')">\n' +
                            '                    <div class="ui fluid card">\n' +
                            '                        <div class="image">\n' +
                            '                            <p>' + item.type + '</p>\n' +
                            '<img src="' + item.filePath + '">' +
                            '                        </div>\n' +
                            '                        <div class="content">\n' +
                            '                            <a class="header" href="/api/resource/' + item.esdocumentId + '">' + item.fileName + '</a>\n' +
                            '                        </div>\n' +
                            '                    </div>\n' +
                            '                </div>')
                    } else {
                        lis.push('<div class="column"  style="cursor: pointer;" onclick="window.open(\'/api/resource/' + item.esdocumentId + '?keyword=' + item.fileName + '\',\'_self\')">\n' +
                            '                    <div class="ui fluid card">\n' +
                            '                        <div class="image">\n' +
                            '                            <p>' + item.type + '</p>\n' +
                            ' <img  src="/images/resource/' + item.type + '.jpg">' +
                            '                        </div>\n' +
                            '                        <div class="content">\n' +
                            '                            <a class="header" href="/api/resource/' + item.esdocumentId + '">' + item.fileName + '</a>\n' +
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

