/**
 * Created by Linshen on 2017/5/26.
 */
var thisURL = decodeURI(window.location.search);
var getval = thisURL.split('?')[1];
// 得到id=ct-001type=theory
var courseIdVal = getval.split('&')[0];
var typeVal = getval.split('&')[1];
//得到courseId和type
var courseId = courseIdVal.split("=")[1]
var type = typeVal.split("=")[1];
// 通过type判断课程类型,从而请求不同的接口
// ajax请求理论课程详情
if(type == "theory"){
    $.ajax({
        url:lawedu+'user/getCourseTheoryInfo',
        timeout:8000, //超时时间设置，单位毫秒
        type: 'POST',  //将要使用的HTTP方法，为POST
        dataType: 'json',
        data:{
            "courseTheoryId":courseId
        },
        success: function (data) {
            // console.log(data);
            $(".important-news-title").html(data.name);
            $("#timetime").html("时间:"+data.addTime);
            $(".important-news").html(data.content);

        },
        error: function (e) {

            alert("服务无法访问!");

        }
    });
}
// ajax请求实践课程详情
else if(type == "practice"){
    $.ajax({
        url:lawedu+'user/getCoursePracticeInfo',
        timeout:8000, //超时时间设置，单位毫秒
        type: 'POST',  //将要使用的HTTP方法，为POST
        dataType: 'json',
        data:{
            "coursePracticeId":courseId
        },
        success: function (data) {
            $(".important-news-title").html(data.name);
            $("#timetime").html("时间:"+data.addTime);
            $(".important-news").html(data.content);


        },
        error: function (e) {

            alert("服务无法访问!");

        }
    });
}
// ajax请求视频课程详情
else{
    $.ajax({
        url:lawedu+'user/getCourseVideoInfo',
        timeout:8000, //超时时间设置，单位毫秒
        type: 'POST',  //将要使用的HTTP方法，为POST
        dataType: 'json',
        data:{
            "courseVideoId":courseId
        },
        success: function (data) {
            $(".important-news-title").html(data.name);
            $("#timetime").html("时间:"+data.addTime);
            $(".important-news").html(data.description);
            $("#video_content").html( "<video src='http://www.w3school.com.cn/i/movie.ogg' controls='controls'> 您的浏览器不支持 video 标签。</video>")
            $("#questionList").css("display","block");
            $("#ask").css("display","block");
            $("#homework").append(data.assignmentTask);
            $("#homework").css("display","block");
            // 请求视频课程的问题列表
            $.ajax({
                url:lawedu+'user/getQuestionList',
                timeout:8000, //超时时间设置，单位毫秒
                type: 'POST',  //将要使用的HTTP方法，为POST
                dataType: 'json',
                data:{
                    "courseId":courseId
                },
                success: function (data) {
                    var obj1 = data.list;
                    var html1 = template("temp1",{questionList:obj1});
                    $("#questionList").append(html1);
                    // 提出问题
                    $("#askask").on('click',function () {
                        var con = $("#ask textarea").val();
                        if(con==null||con==""){
                            layer.open({
                                type: 1 //Page层类型
                                ,area: ['200px', '150px']
                                ,title: '提问信息'
                                ,shade: 0.6 //遮罩透明度
                                ,maxmin: false//允许全屏最小化
                                ,anim: 1 //0-6的动画形式，-1不开启
                                ,content: '<div style="padding:10px;">'+"内容不能为空!"+'</div>'
                            });
                        }
                        else{
                            $.ajax({
                                url:lawedu+'student/addQuestion',
                                timeout:8000, //超时时间设置，单位毫秒
                                type: 'POST',  //将要使用的HTTP方法，为POST
                                dataType: 'text',
                                data:{
                                    "courseId":courseId,
                                    "content":con
                                },
                                success: function (data) {
                                    layer.open({
                                        type: 1 //Page层类型
                                        ,area: ['200px', '150px']
                                        ,title: '提问信息'
                                        ,shade: 0.6 //遮罩透明度
                                        ,maxmin: false//允许全屏最小化
                                        ,anim: 1 //0-6的动画形式，-1不开启
                                        ,content: '<div style="padding:10px;">'+"发表成功!"+'</div>'
                                    });
                                    setTimeout(function () {
                                        window.location.reload();
                                    },1000)
                                },
                                error: function (e) {

                                    alert("服务无法访问!");

                                }
                            });
                        }

                    })
                },
                error: function (e) {

                    alert("服务无法访问!");

                }
            });
        },
        error: function (e) {

            alert("服务无法访问!");

        }
    });
}
