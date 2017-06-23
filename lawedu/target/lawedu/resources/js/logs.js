/**
 * Created by Linshen on 2017/5/27.
 */
$("#logout").on('click',function(){
    $.ajax({
        url:lawedu+'user/logoutUser',
        timeout:8000, //超时时间设置，单位毫秒
        type: 'POST',  //将要使用的HTTP方法，为POST
        dataType: 'text',
        success: function (insss) {
            localStorage.clear();
            layer.open({
                type: 1 //Page层类型
                , area: ['200px', '150px']
                , title: '退出信息'
                , shade: 0.6 //遮罩透明度
                , maxmin: false//允许全屏最小化
                , anim: 1 //0-6的动画形式，-1不开启
                , content: '<div style="padding:10px;">' + "退出成功！" + '</div>'
            });
            setTimeout(function () {
                window.location.href = "home.html";
            },2000);

        },
        error: function (e) {
//		    	alert(lawedu+'user/addUser');
            alert("服务无法访问!");

        }
    });
});
//			console.log(localStorage.name);
if((localStorage.name)!= ""&&(localStorage.name)!= undefined){
    $("#nav_top_btnss").hide();

    if(localStorage.role=="1"){
        $("#logafter1").css("display","inline-block");
        $("#logout").css("display","inline-block");
        $("#logafter1 a").html("欢迎您,"+localStorage.name);
    }
    else{
        $("#logafter2").css("display","inline-block");
        $("#logout").css("display","inline-block");
        $("#logafter2 a").html("欢迎您,"+localStorage.name);
    }
}
else{
    $("#nav_top_btnss").show();
    $("#logafter").hide();
}