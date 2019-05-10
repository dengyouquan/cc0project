layui.use('util', function () {
    var util = layui.util;

    //固定块
    util.fixbar({
        bar1: true
        , bar2: true
        , css: {right: 50, bottom: 50}
        , showHeight: 0 //用于控制出现TOP按钮的滚动条高度临界值。默认：200
        //要改成0，不然滚动200不会加载，我也不知道为什么
        , bgcolor: '#393D49'
        , click: function (type) {
            console.log(type);
            if (type === 'bar1') {
                layer.msg('如有问题，可以通过邮箱联系我们。<br>denggyouquan@foxmail.com', {
                    time: 20000, //20s后自动关闭
                    btn: ['明白了']
                });
            } else if (type === 'bar2') {
                window.location.href = "/help";
            }
        }
    });
});