layui.use(['flow', 'rate'], function () {
    let $ = layui.jquery; //不用额外加载jQuery，flow模块本身是有依赖jQuery的，直接用即可。
    let flow = layui.flow;
    let rate = layui.rate;

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

// rate
layui.use(['rate'], function () {
    var rate = layui.rate;
    //渲染
    var rate = rate.render({
        elem: '#resourceRate', //绑定元素
        choose: function (value) {
            alert(value)
            /*$.ajax({
                url: '/api/rates',
                type: 'post',
                data: {"rate": value},
                async: false,
                dataType: 'json'
            }).then(function (res) {
                self.userRate = res;
                self.isRate = true;
            }).fail(function () {
                console.log('rate fail(未登录)');
                self.isRate = false;
            });*/
        }
    });
});

$(function () {
    var t = $("#download");
    t.click(function () {
        alert("111")
        var FileSaver = require('file-saver');
        FileSaver.saveAs("http://127.0.0.1:8080/js/Blob.js", "Blob.js");
        //FileSaver.saveAs(t.attr("href"), t.attr("download"));
    });
});

var app = new Vue({
    el: '#app',
    data: {
        isRate: {},
        userRate: {}
    },
    methods: {
        userRateSave: function () {
            $.ajax({
                url: '/api/rates',
                type: 'post',
                data: self.userRate,
                async: false,
                dataType: 'json'
            }).then(function (res) {
                self.userRate = res;
                self.isRate = true;
            }).fail(function () {
                console.log('rate fail(未登录)');
                self.isRate = false;
            });
        }
    },
    created: function () {
        self = this;
        let arr = window.location.href.split("?")[0].split("/");
        let r_id = arr[arr.length - 1];
        //加载用户评分信息
        $.ajax({
            url: '/api/rates?resource_id=' + r_id,
            type: 'get',
        }).then(function (res) {
            self.isRate = res == null;
            //alert(self.isRate)
            self.userRate = res;
            console.log(self.userRate)
            console.log(self.isRate)
            /*if (self.isRate) {
                layui.use(['rate'], function () {
                    var rate = layui.rate;
                    var userRate = rate.render({
                        elem: '#yourRate',//绑定元素
                        readonly: true,
                        value: self.userRate.rate
                    });
                });
            }else{
                //评分
                layui.use(['rate'], function () {
                    var rate = layui.rate;
                    var rate = rate.render({
                        elem: '#rate',//绑定元素
                    });
                });
            }*/
        }).fail(function () {
            console.log('rate fail');
        });
    }
});

