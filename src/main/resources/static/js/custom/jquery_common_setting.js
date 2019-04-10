//自定义模块代码:
layui.define(['jquery'],function(exports){
    var $ = layui.jquery;
// 设置jQuery Ajax全局的参数
    $.ajaxSetup({
        // 同步
        async:true, // 默认true，异步
        // 发送cookie
        xhrFields: {
            withCredentials: true
        },
        // 请求发送前
        beforeSend:function(){
            alert(send)
            // 获取CSRF Token
            let csrfToken = $("meta[name='_csrf']").attr("content");
            let csrfHeader = $("meta[name='_csrf_header']").attr("content");
            // 发送请求前，可以对data、url等处理
            request.setRequestHeader(csrfHeader, csrfToken); // 添加CSRF Token
        },
        // 请求返回
        complete:function(){
            // 返回数据，根据数据调转页面等
        }
    });
    exports('jquery_common_setting', {});
});