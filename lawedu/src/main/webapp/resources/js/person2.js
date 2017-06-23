/**
 * Created by Linshen on 2017/5/27.
 */
$(
    getCourseVideoList(),
    getTeacherInfo()
    
);
function getTeacherInfo() {
    $.ajax({
        type : "POST",
        url : lawedu + "teacher/getTeacherInfo",
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
            // 修改教师级别
            if(data.teachLevel==0){
                data.teachLevel = "教授";
            }
            else if(data.teachLevel==1){
                data.teachLevel = "副教授";
            }
            else{
                data.teachLevel = "讲师";
            }
            $("#ids").val(data.id);
            $("#addTimes").val(data.addTime);
            $("#births").val(data.birth);
            $("#genders").val(data.gender);
            $("#idCards").val(data.idCard);
            $("#mobiles").val(data.mobile);
            $("#teachLevels").val(data.teachLevel);
            $("#userNames").val(data.userName);

        },error : function () {
            swal("提示","网络错误");
        }
    });
}
// 提交教师个人信息
$("#subTeacher").on('click',function () {
    var data = {};

    data.birth = $("#births").val();
    data.gender = $("#genders").val();
    data.teachLevel = $("#teachLevels").val();
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
    // 修改教师级别
    if(data.teachLevel=="教授"){
        data.teachLevel = "0";
    }
    else if(data.teachLevel=="副教授"){
        data.teachLevel = "1";
    }
    else{
        data.teachLevel = "2";
    }
    // console.log(data);
    $.ajax({
        type : "POST",
        url : lawedu + "teacher/updateTeacherInfo",
        data:data,
        dataType : "text",
        success : function(ins) {
            swal("提示","更新成功");
        },error : function () {
            swal("提示","网络错误");
        }
    });
})

function getCourseVideoList () {
    $.ajax({
        type : "POST",
        url : lawedu + "user/getCourseVideoList",
        dataType : "json",
        success : function(data) {
            // console.log(data);
            $('#table_course').DataTable({
                data : data.list,
                destroy : true,
                "columns" : [ {
                    data : "id"
                }, {
                    data : "name"
                }, {
                    data : "teacherName"
                }, {
                    data : "videoUrl"
                }, {
                    data : "categoryId"
                }, {
                    data : "description"
                },{
                    data : "addTime"
                },{
                    data : "assignmentTask"
                },{
                    data : "url"
                }, {
                    data : null
                } ],
                "columnDefs": [
                    {
                        "render" : function(data, type, row, meta){
                            var btnHTML = '';
                            btnHTML += '<button type="button" onclick="courseUpdateBox(\''+row.id+'\')" class="btn btn-primary">修改</button> ';
                            btnHTML += '<button type="button" onclick="courseDeleteBox(\''+row.id+'\')" class="btn btn-warning">删除</button> ';
                            //console.log(btnHTML);
                            return btnHTML;
                        },
                        "targets" : 9
                    },
                    {
                        "render" : function(data, type, row, meta){
                            return data.substring(0,11);
                        },
                        "targets" : [0,1,2,3,4,5,6,7,8,9]
                    },
                    {
                        "render" : function(data, type, row, meta){
                            if(typeof data == "undefined") {
                                return ''
                            }
                            return data;
                        },
                        "targets" : [0,1,2,3,4,5,6,7,8,9]
                    }
                ],
                language : {
                    "processing" : "处理中...",
                    "lengthMenu" : "显示 _MENU_ 项结果",
                    "zeroRecords" : "没有匹配结果",
                    "info" : "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                    "infoEmpty" : "显示第 0 至 0 项结果，共 0 项",
                    "infoFiltered" : "(由 _MAX_ 项结果过滤)",
                    "infoPostFix" : "",
                    "search" : "搜索:",
                    "url" : "",
                    "emptyTable" : "没有数据",
                    "loadingRecords" : "载入中...",
                    "infoThousands" : "20",
                    "paginate" : {
                        "first" : "首页",
                        "previous" : "上页",
                        "next" : "下页",
                        "last" : "末页"
                    }
                }
            });
        },error : function () {
            swal("提示","网络错误");
        }
    });
}