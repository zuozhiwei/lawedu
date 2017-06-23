/**
 * Created by Linshen on 2017/5/27.
 */
getStudentInfo();
function getStudentInfo() {
    $.ajax({
        type : "POST",
        url : lawedu + "student/getStudentInfo",
        dataType : "json",
        success : function(data) {
             // console.log(data);
            // 修改性别
            if(data.gender==0){
                data.gender = "保密";
            }
            else if(data.gender==1){
                data.gender = "男";
            }
            else{
                data.gender = "女";
            }
            $("#ids").val(data.id);
            $("#addTimes").val(data.addTime);
            $("#births").val(data.birth);
            $("#genders").val(data.gender);
            $("#idCards").val(data.idCard);
            $("#mobiles").val(data.mobile);
            $("#userNames").val(data.userName);

        },error : function () {
            swal("提示","网络错误");
        }
    });
}
// 提交学生个人信息
$("#subStudent").on('click',function () {
    var data = {};

    data.birth = $("#births").val();
    data.gender = $("#genders").val();
    data.userName = $("#userNames").val();
    // 修改性别
    if(data.gender=="保密"){
        data.gender = "0";
    }
    else if(data.gender=="男"){
        data.gender = "1";
    }
    else{
        data.gender = "2";
    }
    // console.log(data);
    $.ajax({
        type : "POST",
        url : lawedu + "student/updateUser",
        data:data,
        dataType : "text",
        success : function(ins) {
            swal("提示","更新成功");
        },error : function () {
            swal("提示","网络错误");
        }
    });
})