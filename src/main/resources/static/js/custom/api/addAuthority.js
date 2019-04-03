layui.use(['form','layer'], function(){
    var form = layui.form
        ,layer = layui.layer
        ,$ = layui.jquery;

});

function alertLayer(res) {
    //示范一个公告层
    layer.open({
        type: 1
        ,title: false //不显示标题栏
        ,closeBtn: false
        ,area: '300px;'
        ,shade: 0.8
        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
        ,btn: ['回到列表', '继续添加']
        ,btnAlign: 'c'
        ,moveType: 1 //拖拽模式，0或者1
        ,content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">你已经添加角色 ^_^.<br/>服务器消息：' +
        res.msg+'</div>'
        ,success: function(layero){
            var btn = layero.find('.layui-layer-btn');
            btn.find('.layui-layer-btn0').attr({
                href: '/api/authority'
                ,target: '_self'
            });
        }
    });
}

function addAuthority(data) {
    // 获取CSRF Token
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url: "/api/authority/save",
        type:'post',
        //contentType : 'application/json',//加就是get方法传参（&连接） 不加上是post传参 ？？？
        data:data,
        beforeSend: function(request) {
            request.setRequestHeader(csrfHeader, csrfToken); // 添加CSRF Token
        },
        success: function(res){
            if(res.code==0){
                layui.use([ 'layer'], function() {
                    var layer = layui.layer;
                    alertLayer(res);
                });
            }
        },
        error : function() {
            layui.use([ 'layer'], function() {
                var layer = layui.layer;
                layer.msg("上传异常！");
            });
        }
    });
};

$(function () {
   $("#addAuthority").click(function () {
        let name = $("input[name='name']").val();
        data = {
            "name":name
        };
        console.log(data);
       addAuthority(data);
        //禁止默认跳转？？？
        return false;
   });
});
