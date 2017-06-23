<%--
  Created by IntelliJ IDEA.
  User: zuozhiwei
  Date: 2017/5/12
  Time: 19:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@include file="resource.jsp"%>
    <%--Ckeditor JS--%>
    <script src="resources/ckeditor/ckeditor.js"></script>
    <title>法学教育管理平台</title>
    <style type="text/css">
        table th,td{
            text-align:center;
        }
        *{
            font-weight:bold;
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed"
                            data-toggle="collapse"
                            data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                        <span class="sr-only">Toggle navigation</span> <span
                            class="icon-bar"></span> <span class="icon-bar"></span> <span
                            class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="home.jsp">法学教育平台</a>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse"
                     id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="#">管理系统<span class="sr-only">(current)</span></a></li>
                        <!-- 							<li><a href="#">Link</a></li>
                        <li class="dropdown"><a href="#" class="dropdown-toggle"
                            data-toggle="dropdown" role="button" aria-haspopup="true"
                            aria-expanded="false">Dropdown <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="#">Action</a></li>
                                <li><a href="#">Another action</a></li>
                                <li><a href="#">Something else here</a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="#">Separated link</a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="#">One more separated link</a></li>
                            </ul></li> -->
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="">
                            <% String userName = "";
                                if (null != request.getSession().getAttribute("userName") && !"".equals(request.getSession().getAttribute("userName"))) {
                                    userName = request.getSession().getAttribute("userName").toString();
                                }%>
                            <%=userName %>
                        </a></li>
                        <li><a href="javascript:logOut()">退出</a></li>
                        <li><a href="javascript:alert('请联系管理员：18142200203')">帮助</a></li>
                    </ul>
                </div>
                <!-- /.navbar-collapse -->
            </div>
            <!-- /.container-fluid -->
        </nav>
    </div>
    <div class="row"></div>

    <div class="row">
        <div class="col-md-2">
            <ul class="nav nav-pills nav-stacked">
                <c:forEach items="${menuList}" var="menuItem">
                    <li role="presentation" id="${fn:split(menuItem.menuPage,'.')[0]}"><a href="${menuItem.menuPage}">${menuItem.menuName}</a></li>
                </c:forEach>
            </ul>
        </div>
        <div class="col-md-10">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">用户管理</h3>
                </div>
                <div class="container-fluid">


                    <div class="row">
                        <div class="well well-sm"></div>
                        <div class="col-md-1">
                            <button class="btn btn-primary" onclick="getUserList()">刷新</button>
                        </div>
                        <div class="col-md-1">
                            <button class="btn btn-primary" onclick="AddUserBox()">添加用户</button>
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <table id="table_question" class="display">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>姓名</th>
                            <th>密码</th>
                            <th>手机号</th>
                            <th>身份证号</th>
                            <th>审核标记</th>
                            <th>添加时间</th>
                            <th>用户类型</th>
                            <th>性别</th>
                            <th>生日</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        $("#user").addClass("active");
        getUserList();
    });
    function getUserList () {

        $.ajax({
            type : "POST",
            url : "getUserList",
            dataType : "json",
            data : {},
            success : function(data) {
                $('#table_question').DataTable({
                    data : data,
                    destroy : true,
                    "columns" : [ {
                        data : "id"
                    }, {
                        data : "userName"
                    }, {
                        data : "password"
                    }, {
                        data : "mobile"
                    }, {
                        data : "idCard"
                    }, {
                        data : "checkMark"
                    }, {
                        data : "addTime"
                    }, {
                        data : "role"
                    }, {
                        data : "gender"
                    }, {
                        data : "birth"
                    }, {
                        data : null
                    } ],
                    "columnDefs": [
                        {
                            "render" : function(data, type, row, meta){
                                if(data == "0"){
                                    return '待审核'
                                }else if(data == "1"){
                                    return '不通过'
                                }else if(data == "2"){
                                    return '已通过'
                                }
                            },
                            "targets" : 5
                        },
                        {
                            "render" : function(data, type, row, meta){
                                if(data == "0"){
                                    return '管理员'
                                }else if(data == "1"){
                                    return '学生'
                                }else if(data == "2"){
                                    return '教师'
                                }else if(data == "3"){
                                    return '社会人士'
                                }else if(data == "4"){
                                    return '律师'
                                }
                            },
                            "targets" : 7
                        },
                        {
                            "render" : function(data, type, row, meta){
                                if(data == "0"){
                                    return '保密'
                                }else if(data == "1"){
                                    return '男'
                                }else if(data == "2"){
                                    return '女'
                                }
                            },
                            "targets" : 8
                        },
                        {
                            "render" : function(data, type, row, meta){
                                var btnHTML = '';
                                btnHTML += '<button type="button" onclick="userUpdateBox(\''+row.id+'\',\''+row.userName+'\',\''+row.password+'\',\''+row.mobile+'\',\''+row.idCard+'\',\''+row.checkMark+'\',\''+row.addTime+'\',\''+row.role+'\',\''+row.gender+'\',\''+row.birth+'\')" class="btn btn-primary">修改</button> ';
                                btnHTML += '<button type="button" onclick="userDeleteBox(\''+row.id+'\')" class="btn btn-warning">删除</button> ';
                                return btnHTML;
                            },
                            "targets" : 10
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
</script>

<!-- 项目审核模态框 -->
<div class="modal fade" id="userUpdateBox" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">×</button><h4 class="modal-title" id="myModalLabel">修改</h4>
            </div>
            <div style="width: 90%;margin: 10px auto;">
                <form class="form-horizontal" id="projectCheckForm">
                    <div class="form-group">
                        <label class="col-sm-4 control-label">用户id</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="id" id="id" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">用户姓名</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="userName" id="userName" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">密码</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="password" id="password" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">手机号</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="mobile" id="mobile" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">身份证号</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="idCard" id="idCard" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">审核状态</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="checkMark" id="checkMark" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">添加时间</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="addTime" id="addTime" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">用户类型</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="role" id="role" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">性别</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="gender" id="gender" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">出生日期</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="birth" id="birth" >
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="submitUserUpdateBox()">提交</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    /**
     * 打开修改框
     */
    function userUpdateBox(id,userName,password,mobile,idCard,checkMark,addTime,role,gender,birth) {
        $("#id").attr("value",id);
        $("#userName").attr("value",userName);
        $("#password").attr("value",password);
        $("#mobile").attr("value",mobile);
        $("#idCard").attr("value",idCard);
        $("#checkMark").attr("value",checkMark);
        $("#addTime").attr("value",addTime);
        $("#role").attr("value",role);
        $("#gender").attr("value",gender);
        $("#birth").attr("value",birth);
        $('#userUpdateBox').modal({});
    }

    /**
     * 提交修改数据
     */
    function submitUserUpdateBox () {
        var data = {
            "id": $("#id").val(),
            "userName": $("#userName").val(),
            "password": $("#password").val(),
            "mobile": $("#mobile").val(),
            "checkMark": $("#checkMark").val(),
            "idCard": $("#idCard").val(),
            "role": $("#role").val(),
            "addTime": $("#addTime").val(),
            "gender": $("#gender").val(),
            "birth": $("#birth").val()
        };
        console.log(data);
        $.ajax({
            type: "POST",
            url: "updateUser",
            dataType: "text",
            data: data,
            success: function (data) {
                swal("提示","修改成功","success");
                $('#userUpdateBox').modal('hide');
                getUserList();
            },error: function (e) {
                swal("提示","网络错误");
            }
        });
    }
</script>

<script type="text/javascript">
    function userDeleteBox (id) {
        swal({
            title: "确认删除?",
            text: "您将删除此用户。",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "删除",
            closeOnConfirm: false
        },
        function () {
                $.ajax({
                    type: "POST",
                    url: "deleteUser",
                    dataType: "text",
                    data: {
                        "id" : id
                    },
                    success: function (data) {
                        swal("提示","删除成功","success");
                        getUserList();
                    },error: function (e) {
                        swal("提示","网络错误");
                    }
                });
            }
        )
    }
</script>

<!-- 添加用户模态框 -->
<div class="modal fade" id="addUserBox" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">×</button><h4 class="modal-title" id="myModal1Label">修改</h4>
            </div>
            <div style="width: 90%;margin: 10px auto;">
                <form class="form-horizontal" id="addUserForm">
                    <div class="form-group">
                        <label class="col-sm-4 control-label">用户姓名</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="add_userName" id="add_userName" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">密码</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="add_password" id="add_password" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">手机号</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="add_mobile" id="add_mobile" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">身份证号</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="add_idCard" id="add_idCard" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">审核状态</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="add_checkMark" id="add_checkMark" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">用户类型</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="add_role" id="add_role" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">性别</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="add_gender" id="add_gender" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">出生日期</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="add_birth" id="add_birth" >
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="submitUserAddBox()">提交</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    /**
     * 打开修改框
     */
    function AddUserBox() {
        $('#addUserBox').modal({});
    }

    /**
     * 提交修改数据
     */
    function submitUserAddBox () {
        var data = {
            "userName": $("#add_userName").val(),
            "password": $("#add_password").val(),
            "mobile": $("#add_mobile").val(),
            "idCard": $("#add_idCard").val(),
            "role": $("#add_role").val(),
            "gender": $("#add_gender").val(),
            "birth": $("#add_birth").val()
        };
        console.log(data);
        $.ajax({
            type: "POST",
            url: "../user/addUser",
            dataType: "text",
            data: data,
            success: function (data) {
                swal("提示","添加成功","success");
                $('#addUserBox').modal('hide');
                getUserList();
            },error: function (e) {
                swal("提示","网络错误");
            }
        });
    }
    function logOut () {
        swal({
                title: "确认退出?",
                text: "您将注销登录。",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "注销",
                closeOnConfirm: false
            },
            function () {
                $.ajax({
                    type: "POST",
                    url: "../user/logoutUser",
                    dataType: "text",
                    data: {},
                    success: function (data) {
                        swal("提示","注销成功","success");
                        console.log("before location")
                        window.location.href = "index.jsp";
                        console.log("after location");
                    },error: function (e) {
                        swal("提示","网络错误");
                    }
                });
            }
        )
    }
</script>

</body>
</html>
