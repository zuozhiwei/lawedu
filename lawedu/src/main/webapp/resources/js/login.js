$(function(){
	
	$('#switch_qlogin').click(function(){
		$('#switch_login').removeClass("switch_btn_focus").addClass('switch_btn');
		$('#switch_qlogin').removeClass("switch_btn").addClass('switch_btn_focus');
		$('#switch_bottom').animate({left:'0px',width:'70px'});
		$('#qlogin').css('display','none');
		$('#web_qr_login').css('display','block');
		
		});
	$('#switch_login').click(function(){
		
		$('#switch_login').removeClass("switch_btn").addClass('switch_btn_focus');
		$('#switch_qlogin').removeClass("switch_btn_focus").addClass('switch_btn');
		$('#switch_bottom').animate({left:'154px',width:'70px'});
		
		$('#qlogin').css('display','block');
		$('#web_qr_login').css('display','none');
		});
if(getParam("a")=='0')
{
	$('#switch_login').trigger('click');
}

	});
	
function logintab(){
	scrollTo(0);
	$('#switch_qlogin').removeClass("switch_btn_focus").addClass('switch_btn');
	$('#switch_login').removeClass("switch_btn").addClass('switch_btn_focus');
	$('#switch_bottom').animate({left:'154px',width:'96px'});
	$('#qlogin').css('display','none');
	$('#web_qr_login').css('display','block');
	
}


//根据参数名获得该参数 pname等于想要的参数名 
function getParam(pname) { 
    var params = location.search.substr(1); // 获取参数 平且去掉？ 
    var ArrParam = params.split('&'); 
    if (ArrParam.length == 1) { 
        //只有一个参数的情况 
        return params.split('=')[1]; 
    } 
    else { 
         //多个参数参数的情况 
        for (var i = 0; i < ArrParam.length; i++) { 
            if (ArrParam[i].split('=')[0] == pname) { 
                return ArrParam[i].split('=')[1]; 
            } 
        } 
    } 
}  

//定义密码最小长度
var pwdmin = 6;

$(document).ready(function() {

	$("#login_btn").on('click',function(){
		var data = {};
		data.mobile = $("#u").val();
		data.password = $("#p").val();
		$.ajax({
		    url:lawedu+"user/loginCheck",
		    timeout:8000, //超时时间设置，单位毫秒
		    type: 'POST',  //将要使用的HTTP方法，为POST
		    dataType: 'json',
		    data:data,
            success: function (insss) {
                if(insss.info=="mobileError"){
                    layer.open({
                        type: 1 //Page层类型
                        ,area: ['200px', '150px']
                        ,title: '注册信息'
                        ,shade: 0.6 //遮罩透明度
                        ,maxmin: false//允许全屏最小化
                        ,anim: 1 //0-6的动画形式，-1不开启
                        ,content: '<div style="padding:10px;">'+"手机号错误！"+'</div>'
                    });
                }
                else if(insss.info=="passwordError"){
                    layer.open({
                        type: 1 //Page层类型
                        ,area: ['200px', '150px']
                        ,title: '登录信息'
                        ,shade: 0.6 //遮罩透明度
                        ,maxmin: false//允许全屏最小化
                        ,anim: 1 //0-6的动画形式，-1不开启
                        ,content: '<div style="padding:10px;">'+"密码错误！"+'</div>'
                    });
                }
                else{
                    layer.open({
                        type: 1 //Page层类型
                        ,area: ['200px', '150px']
                        ,title: '登录信息'
                        ,shade: 0.6 //遮罩透明度
                        ,maxmin: false//允许全屏最小化
                        ,anim: 1 //0-6的动画形式，-1不开启
                        ,content: '<div style="padding:10px;">'+"登录成功!"+'</div>'
                    });
                    localStorage.name = insss.info;
                    localStorage.role = insss.role;
                    // console.log(localStorage.name);
                    setTimeout(function () {
                         window.location.href="home.html";
                    },3000)
                }
            },
		    error: function (e) {
		    	
		        alert("服务无法访问!");
		                
		    }
		});
	});
//	登录的按钮触发事件
	$('#reg').click(function() {
		
		$('#userCue').html("<font>快速注册请注意格式</font>");
		
		if ($('#user').val() == "") {
			$('#user').focus().css({
				border: "1px solid red",
				boxShadow: "0 0 2px red"
			});
			$('#userCue').html("<font color='red'><b>×用户名不能为空</b></font>");
			return false;
		}
		else {
			$('#user').css({
				border: "1px solid #D7D7D7",
				boxShadow: "none"
			});
		}
//
//

//		第一次密码长度校验
		if ($('#passwd').val().length < pwdmin) {
			$('#passwd').focus().css({
				border: "1px solid red",
				boxShadow: "0 0 2px red"
			});
			$('#userCue').html("<font color='red'><b>×密码不能小于" + pwdmin + "位</b></font>");
			return false;
		}
		else {
			$('#passwd').css({
				border: "1px solid #D7D7D7",
				boxShadow: "none"
			});
		}
//		第二次密码和第一次密码进行对比
		if ($('#passwd2').val() != $('#passwd').val()) {
			$('#passwd2').focus().css({
				border: "1px solid red",
				boxShadow: "0 0 2px red"
			});
			$('#userCue').html("<font color='red'><b>×两次密码不一致！</b></font>");
			return false;
		}
		else {
			$('#passwd2').css({
				border: "1px solid #D7D7D7",
				boxShadow: "none"
			});
		}
//		手机号码校验
		var stel = /^1[34578]\d{9}$/;
		if (!stel.test($('#tel').val()) || $('#tel').val().length != 11 ) {
			$('#tel').focus().css({
				border: "1px solid red",
				boxShadow: "0 0 2px red"
			});
			$('#userCue').html("<font color='red'><b>×手机号码格式不正确</b></font>");
			return false;
		} else {
			$('#tel').css({
				border: "1px solid #D7D7D7",
				boxShadow: "none"
			});
		}
//		身份证校验
		var sIDcard = /(^\d{15}$)|(^\d{17}([0-9]|X)$)/;
		if (!sIDcard.test($('#IDcard').val()) || $('#IDcard').val().length != 18 ) {
			$('#IDcard').focus().css({
				border: "1px solid red",
				boxShadow: "0 0 2px red"
			});
			$('#userCue').html("<font color='red'><b>×身份证号码格式不正确</b></font>");
			return false;
		} else {
			$('#IDcard').css({
				border: "1px solid #D7D7D7",
				boxShadow: "none"
			});
		}		
//		注册
		var data = {};
		data.userName = $("#user").val();
		data.password = $("#passwd").val();
		data.mobile = $("#tel").val();
		data.idCard = $("#IDcard").val();
		data.gender = $("#gender_select option:selected").val();
		data.birth = $("#birthday").val();
		data.role = $("#type_select option:selected").val();
		// console.log(data);
		$.ajax({
		    url:lawedu+'user/addUser',
		    timeout:8000, //超时时间设置，单位毫秒
		    type: 'POST',  //将要使用的HTTP方法，为POST
		    dataType: 'text',
		    data:data,
		    success: function (insss) {
		    	if(insss=="mobileExist") {
                    layer.open({
                        type: 1 //Page层类型
                        , area: ['200px', '150px']
                        , title: '注册信息'
                        , shade: 0.6 //遮罩透明度
                        , maxmin: false//允许全屏最小化
                        , anim: 1 //0-6的动画形式，-1不开启
                        , content: '<div style="padding:10px;">' + "手机号已经存在，请重新注册！" + '</div>'
                    });
                }
				else{
                    layer.open({
                        type: 1 //Page层类型
                        , area: ['200px', '150px']
                        , title: '注册信息'
                        , shade: 0.6 //遮罩透明度
                        , maxmin: false//允许全屏最小化
                        , anim: 1 //0-6的动画形式，-1不开启
                        , content: '<div style="padding:10px;">' + "注册成功！" + '</div>'
                    });
                    setTimeout(function () {
                        window.location.reload();
                    },3000)
				}
		    },
		    error: function (e) {
//		    	alert(lawedu+'user/addUser');
		        alert("服务无法访问!");
		                
		    }
		});
		
	});
	

});