layui.use(['form','layer'], function(){
    var form = layui.form
        ,layer = layui.layer
        ,$ = layui.jquery;

    //自定义验证规则
    form.verify({
        title: function(value){
            if(value.length < 2){
                return '标题至少得2个字符啊';
            }
        }
        ,pass: [/(.+){6,12}$/, '密码必须6到12位']
    });
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
        ,content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">你已经添加用户 ^_^.<br/>服务器消息：' +
        res.msg+'</div>'
        ,success: function(layero){
            var btn = layero.find('.layui-layer-btn');
            btn.find('.layui-layer-btn0').attr({
                href: '/api/user'
                ,target: '_self'
            });
        }
    });
}

function addUser(data) {
    // 获取CSRF Token
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url: "/api/user/save",
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
   $("#addUser").click(function () {
       let name = $("input[name='name']").val();
        let username = $("input[name='username']").val();
        let introduction = $("textarea[name='introduction']").val();
        let tel = $("input[name='tel']").val();
        let email = $("input[name='email']").val();
       let password = $("input[name='password']").val();
        data = {
            "name":name,
            "username":username,
            "introduction":introduction,
            "tel":tel,
            "email":email,
            "password":password
        };
        console.log(data);
       addUser(data);
        //禁止默认跳转？？？
        return false;
   });
});
