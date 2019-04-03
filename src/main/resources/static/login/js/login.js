$(function(){
	var tab = 'account_number';
	// 选项卡切换
	$(".account_number").click(function () {
		$('.tel-warn').addClass('hide');
		tab = $(this).attr('class').split(' ')[0];
		checkBtn();
        $(this).addClass("on");
        $(".message").removeClass("on");
        $(".form2").addClass("hide");
        $(".form1").removeClass("hide");
    });
	// 选项卡切换
	$(".message").click(function () {
		$('.tel-warn').addClass('hide');
		tab = $(this).attr('class').split(' ')[0];
		checkBtn();
		$(this).addClass("on");
        $(".account_number").removeClass("on");
		$(".form2").removeClass("hide");
		$(".form1").addClass("hide");
		
    });

	$('#num').keyup(function(event) {
		$('.tel-warn').addClass('hide');
		checkBtn();
	});

	$('#pass').keyup(function(event) {
		$('.tel-warn').addClass('hide');
		checkBtn();
	});

	$('#veri').keyup(function(event) {
		$('.tel-warn').addClass('hide');
		checkBtn();
	});

	$('#num2').keyup(function(event) {
		$('.tel-warn').addClass('hide');
		checkBtn();
	});

	$('#veri-code').keyup(function(event) {
		$('.tel-warn').addClass('hide');
		checkBtn();
	});

	// 按钮是否可点击
	function checkBtn()
	{
		$(".log-btn").off('click');
		if (tab == 'account_number') {
			var inp = $.trim($('#num').val());
			var pass = $.trim($('#pass').val());
			if (inp != '' && pass != '') {
				if (!$('.code').hasClass('hide')) {
					code = $.trim($('#veri').val());
					if (code == '') {
						$(".log-btn").addClass("off");
					} else {
						$(".log-btn").removeClass("off");
						sendBtn();
					}
				} else {
					$(".log-btn").removeClass("off");
						sendBtn();
				}
			} else {
				$(".log-btn").addClass("off");
			}
		} else {
			var phone = $.trim($('#num2').val());
			var code2 = $.trim($('#veri-code').val());
			if (phone != '' && code2 != '') {
				$(".log-btn").removeClass("off");
				sendBtn();
			} else {
				$(".log-btn").addClass("off");
			}
		}
	}

	function checkAccount(username){
		if (username == '') {
			$('.num-err').removeClass('hide').find("em").text('请输入账户');
			return false;
		} else {
			$('.num-err').addClass('hide');
			return true;
		}
	}

	function checkPass(pass){
		if (pass == '') {
			$('.pass-err').removeClass('hide').text('请输入密码');
			return false;
		} else {
			$('.pass-err').addClass('hide');
			return true;
		}
	}

	function checkCode(code){
		if (code == '') {
			// $('.tel-warn').removeClass('hide').text('请输入验证码');
			return false;
		} else {
			// $('.tel-warn').addClass('hide');
			return true;
		}
	}

	function checkPhone(phone){
		var status = true;
		if (phone == '') {
			$('.num2-err').removeClass('hide').find("em").text('请输入手机号');
			return false;
		}
		var param = /^1[34578]\d{9}$/;
		if (!param.test(phone)) {
			// globalTip({'msg':'手机号不合法，请重新输入','setTime':3});
			$('.num2-err').removeClass('hide');
			$('.num2-err').text('手机号不合法，请重新输入');
			return false;
		}
        // 获取CSRF Token
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
		$.ajax({
            url: '/check/checkPhone',
            type: 'post',
            dataType: 'json',
            async: false,
            data: {phone:phone,type:"login"},
            beforeSend: function(request) {
                request.setRequestHeader(csrfHeader, csrfToken); // 添加CSRF Token
            },
            success:function(data){
            	//已注册
                if (data.code == '0') {
                    $('.num2-err').addClass('hide');
                    // console.log('aa');
                    // return true;
                } else {
                    //已注册
                    $('.num2-err').removeClass('hide').text(data.msg);
                    // console.log('bb');
					status = false;
					// return false;
                }
            },
            error:function(){
            	status = false;
                // return false;
            }
        });
		return status;
	}

	function checkPhoneCode(pCode){
		if (pCode == '') {
			$('.error').removeClass('hide').text('请输入验证码');
			return false;
		} else {
			$('.error').addClass('hide');
			return true;
		}
	}

	function showCode(){
        $(".log-btn").off('click').addClass("off");
        $('.pass-err').removeClass('hide').find('em').text(data.msg);
        $('.pass-err').find('i').attr('class', 'icon-warn').css("color","#d9585b");
        $('.code').removeClass('hide');
        // 为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳
        $('.code').find('img').attr('src','/login/verifyCode?'+Math.random()).click(function(event) {
            $(this).attr('src', '/login/verifyCode?'+Math.random());
        });
    }
    
    $("#verifyImg").click(function () {
        $(this).attr('src', '/login/verifyCode?'+Math.random());
    });

    $("#verifyImg1").click(function () {
        $(this).attr('src', '/login/verifyCode?'+Math.random());
    });
    
    function loginCustom(phone,pcode,username,email) {
        // 获取CSRF Token
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            url: '/login',
            type: 'post',
            dataType: 'json',
            async: true,
            data: {phone: phone, captcha: pcode, username: phone, email: email,password:pcode},
            beforeSend: function (request) {
                request.setRequestHeader(csrfHeader, csrfToken); // 添加CSRF Token
            },
            success: function (data) {
                alert(data.data)
                if (data.data == true) {
                    //登录失败
                    $('.num2-err').removeClass('hide').text(data.msg);
                } else {
                    window.location.href = "/index";
                }
            },
            error: function () {

            }
        });
    }

    function loginMobile(phone,pcode,username,email) {
        // 获取CSRF Token
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            url: '/login/mobile',
            type: 'post',
            dataType: 'json',
            async: true,
            data: {phone: phone, captcha: pcode, username: phone, email: email,password:pcode},
            beforeSend: function (request) {
                request.setRequestHeader(csrfHeader, csrfToken); // 添加CSRF Token
            },
            success: function (data) {
                console.log(data)
                if (data == true) {
                    //登录失败
                    $('.num2-err').removeClass('hide').text(data.msg);
                } else {
                    if (self != top) {
                        //alert('在iframe中');
                        //window.location.reload();
                        parent.location.reload();//刷新父亲对象(用于框架);
                    }else{
                        window.location.href = "/index";
                    }
                }
            },
            error: function () {

            }
        });
    }

	function loginVerify(username,password) {
        // 获取CSRF Token
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            url: '/login',
            type: 'post',
            dataType: 'json',
            async: true,
            data: {username:username,password:password},
            beforeSend: function(request) {
                request.setRequestHeader(csrfHeader, csrfToken); // 添加CSRF Token
            },
            success:function(data){
                console.log(data)
                //error=false 登录成功
                if(data.data==false){
                    //alert("登录成功")
                    $('.pass-err').addClass('hide');
                    if (self != top) {
                        //alert('在iframe中');
                        //window.location.reload();
                        parent.location.reload();//刷新父亲对象(用于框架);
                    }else{
                        window.location.href = "/index";
                    }
                }else{
                    //登录失败 自定义登录
                    $('.pass-err').removeClass('hide');
                    //更改验证码
                    $("#verifyImg").attr('src', '/login/verifyCode?'+Math.random());
                    //loginCustom(phone,pcode,null,null);
                }
            },
            error:function(){

            }
        });
    }
    
    function loginVerifyPhone(phone,pcode) {
        // 获取CSRF Token
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            url: '/login/phone',
            type: 'post',
            dataType: 'json',
            async: true,
            data: {phone:phone,pcode:pcode},
            beforeSend: function(request) {
                request.setRequestHeader(csrfHeader, csrfToken); // 添加CSRF Token
            },
            success:function(data){
                if(data.data==false){
                    //验证码不正确
                    $('.error').removeClass('hide');
                    //更改验证码
                    $("#verifyImg").attr('src', '/login/verifyCode?'+Math.random());
                }else{
                    $('.error').addClass('hide');
                    loginMobile(phone,pcode,null,null);
                }
            },
            error:function(){

            }
        });
    }

	// 登录点击事件
	function sendBtn(){
		if (tab == 'account_number') {
			$(".log-btn").click(function(){
				// var type = 'phone';
				var inp = $.trim($('#num').val());
				//var pass = $.md5($.trim($('#pass').val()));
                var pass = $.trim($('#pass').val());
				if (checkAccount(inp) && checkPass(pass)) {
					var ldata = {username:inp,password:pass};
					if (!$('.code').hasClass('hide')) {
						code = $.trim($('#veri').val());
						if (!checkCode(code)) {
							return false;
						}
						ldata.code = code;
					}

					var code = $("#veri").val();
					console.log(code);
					//验证码
                    $.ajax({
                        url: '/login/verify?verifyCode='+code,
                        type: 'get',
                        success:function(data){
                            if (data.data==true) {
                                $('.img-err').addClass('hide');
                                loginVerify(inp,pass);
                            }else{
                                $('.img-err').removeClass('hide');
                                $("#verifyImg").attr('src', '/login/verifyCode?'+Math.random());
                            }
                        }
                    });

				} else {
					return false;
				}
			});
		} else {
			$(".log-btn").click(function(){
				// var type = 'phone';
				var phone = $.trim($('#num2').val());
				var pcode = $.trim($('#veri-code').val());
				if (checkPhone(phone) && checkPass(pcode)) {

                    var code = $("#veri1").val();
                    console.log(code);
                    //验证码
                    $.ajax({
                        url: '/login/verify?verifyCode='+code,
                        type: 'get',
                        success:function(data){
                            if (data.data==true) {
                                $('.img-err1').addClass('hide');
                                loginVerifyPhone(phone,pcode);
                            }else{
                                $('.img-err1').removeClass('hide');
                                $("#verifyImg1").attr('src', '/login/verifyCode?'+Math.random());
                            }
                        }
                    });

					
				} else {
					$(".log-btn").off('click').addClass("off");
					// $('.tel-warn').removeClass('hide').text('登录失败');
					return false;
				}
			});
		}
	}

	// 登录的回车事件
	$(window).keydown(function(event) {
    	if (event.keyCode == 13) {
    		$('.log-btn').trigger('click');
    	}
    });


	$(".form-data").delegate(".send","click",function () {
		var phone = $.trim($('#num2').val());
		if (checkPhone(phone)) {
            // 获取CSRF Token
            var csrfToken = $("meta[name='_csrf']").attr("content");
            var csrfHeader = $("meta[name='_csrf_header']").attr("content");
			$.ajax({
				url: '/login/phoneCode',
				type: 'post',
				dataType: 'json',
				async: true,
				data: {phone:phone,type:"login"},
				beforeSend: function(request) {
					request.setRequestHeader(csrfHeader, csrfToken); // 添加CSRF Token
				},
				success:function(data){
					if (data.code == '0') {

					} else {

					}
				},
				error:function(){

				}
			});
	       	var oTime = $(".form-data .time"),
			oSend = $(".form-data .send"),
			num = parseInt(oTime.text()),
			oEm = $(".form-data .time em");
		    $(this).hide();
		    oTime.removeClass("hide");
		    var timer = setInterval(function () {
		   	var num2 = num-=1;
	            oEm.text(num2);
	            if(num2==0){
	                clearInterval(timer);
	                oSend.text("重新发送验证码");
				    oSend.show();
	                oEm.text("120");
	                oTime.addClass("hide");
	            }
	        },1000);
		}
    });

    $('#num').attr("value","dyq");
    $('#pass').attr("value","123456");
    $('#num2').attr("value","17729845523");
    $('#veri-code').attr("value","111111");
});