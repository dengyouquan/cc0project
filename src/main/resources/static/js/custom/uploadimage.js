layui.use(['form','upload'], function(){
    var form = layui.form
        ,layer = layui.layer
        ,$ = layui.jquery
        ,upload = layui.upload;

    //自定义验证规则
    form.verify({
        title: function(value){
            if(value.length < 2){
                return '标题至少得2个字符啊';
            }
        }
    });


    //普通图片上传
    var uploadInst = upload.render({
        elem: '#imageupload'
        //,url: '/uploadfile'
        ,url: '/upload/image'
        ,before: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
                $('#image').attr('src', result); //图片链接（base64）
            });
        }
        ,done: function(res){
            //如果上传失败
            if(res.code > 0){
                return layer.msg('上传失败');
            }
            //上传成功
            $("#filepath").attr("value",res.data);
            $("#filesize").attr("value",res.count);
        }
        ,error: function(){
            //演示失败状态，并实现重传
            var demoText = $('#demoText');
            demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
            demoText.find('.demo-reload').on('click', function(){
                uploadInst.upload();
            });
        }
    });

    //拖拽上传
    /* upload.render({
         elem: '#uploadfile'
         //,url: '/uploadfile'
         ,url: '/api/image/upload'
         ,accept: 'file' //普通文件
         ,size: 4096 //限制文件大小，单位 KB
         ,done: function(res){
             if(res.code > 0){
                 layer.msg("文件上传失败！");
             }
             else {
                 $("#filepath").attr("value",res);
                 console.log(res);
                 layer.msg("文件上传成功！");
             }
         }
     });*/
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
        ,btn: ['回到主页', '继续上传']
        ,btnAlign: 'c'
        ,moveType: 1 //拖拽模式，0或者1
        ,content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">你已经上传图片 ^_^.<br/>服务器消息：' +
        res.msg+'</div>'
        ,success: function(layero){
            var btn = layero.find('.layui-layer-btn');
            btn.find('.layui-layer-btn0').attr({
                href: '/index'
                ,target: '_self'
            });
        }
    });
}

function uploadImage(data) {
    alert("uploadImage")
    // 获取CSRF Token
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        url: "/api/image/save",
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
   $("#uploadImage").click(function () {
        let fileName = $("input[name='fileName']").val();
        let type = $("select[name='type']").val();
        let description = $("textarea[name='description']").val();
        let fileSize = $("input[name='fileSize']").val();
        let filePath = $("input[name='filePath']").val();
        data = {
            "fileName":fileName,
            "type":type,
            "description":description,
            "fileSize":fileSize,
            "filePath":filePath
        };
        console.log(data);
        uploadImage(data);
        //禁止默认跳转？？？
        return false;
   });
});
