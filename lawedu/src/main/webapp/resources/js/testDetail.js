/**
 * Created by Linshen on 2017/6/1.
 */
// 获取试题id
var thisURL = decodeURI(window.location.search);
var getval = thisURL.split('?')[1];
var examId = getval.split("=")[1];
// 查询试题接口获取考试题目
$.ajax({
    url:lawedu+'student/getExamQuestionList',
    timeout:8000, //超时时间设置，单位毫秒
    type: 'POST',  //将要使用的HTTP方法，为POST
    dataType: 'json',
    data:{
        "examId":examId
    },
    success: function (data) {
        var obj1 = data.list;
        var html1 = template("temp1",{queList:obj1});
        $("#questionContent").html(html1);
    },
    error: function (e) {

        alert("服务无法访问!");

    }
});