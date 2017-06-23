<%--
  Created by IntelliJ IDEA.
  User: zuozhiwei
  Date: 2017/5/12
  Time: 19:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" isELIgnored="true"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
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
                <li role="presentation"><a href="user.jsp">用户管理</a></li>
                <hr/>
                <li role="presentation"><a href="courseCategory.jsp">课程类别管理</a></li>
                <li role="presentation"><a href="courseTheory.jsp">理论课程管理</a></li>
                <li role="presentation"><a href="coursePractice.jsp">实践课程管理</a></li>
                <li role="presentation"><a href="courseVideo.jsp">视频课程管理</a></li>
                <li role="presentation"><a href="assignment.jsp">作业管理</a></li>
                <li role="presentation"><a href="ask.jsp">课堂提问管理</a></li>
                <hr/>
                <li role="presentation"><a href="exam.jsp">考试列表管理</a></li>
                <li role="presentation"><a href="examQuestionList.jsp">考试试题管理</a></li>
                <li role="presentation"><a href="examUserList.jsp">考试成绩管理</a></li>
                <hr/>
                <li role="presentation"><a href="message.jsp">社会咨询管理</a></li>
                <hr/>
                <li role="presentation"><a href="article.jsp">作品管理</a></li>
                <hr/>
                <li role="presentation"><a href="project.jsp">项目管理</a></li>
                <hr/>
                <li role="presentation"><a href="menu.jsp">菜单管理</a></li>
                <li role="presentation"><a href="log.jsp">系统日志</a></li>

            </ul>
        </div>
        <div class="col-md-10">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">系统管理</h3>
                </div>
                <div class="container-fluid">


                    <div class="row">
                        <div class="well well-sm"></div>
                        <div class="col-md-1">
                            <button class="btn btn-primary" onclick="getCourseTheoryList()">刷新</button>
                        </div>
                        <div class="col-md-1">
                            <button class="btn btn-primary" onclick="addCourseTheory()">添加用户</button>
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <table id="table_course" class="display">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>名称</th>
                            <th>内容</th>
                            <th>分类号</th>
                            <th>添加时间</th>
                            <th>图片</th>
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
    $(getCourseTheoryList());
    function getCourseTheoryList () {
        $.ajax({
            type : "POST",
            url : "../user/getCourseTheoryList",
            dataType : "json",
            data : {},
            success : function(data) {
                $('#table_course').DataTable({
                    data : data.list,
                    destroy : true,
                    "columns" : [ {
                        data : "id"
                    }, {
                        data : "name"
                    }, {
                        data : "content"
                    }, {
                        data : "categoryId"
                    }, {
                        data : "addTime"
                    }, {
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
                                console.log(btnHTML);
                                return btnHTML;
                            },
                            "targets" : 6
                        },
                        {
                            "render" : function(data, type, row, meta){
                                return data.substring(0,15);
                            },
                            "targets" : 2
                        },
                        {
                            "render" : function(data, type, row, meta){
                                if(typeof data == "undefined") {
                                    return ''
                                }
                                return data;
                            },
                            "targets" : [0,1,2,3,4,5,6]
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
<div class="modal fade" id="courseUpdateBox" tabindex="-1" role="dialog"
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
                        <label class="col-sm-4 control-label">课程id</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="id" id="id" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">课程名称</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="name" id="name" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">课程内容</label>
                        <div class="col-sm-8">
                            <textarea style="min-height: 200px" type="text" class="DataInput form-control" name="content" id="content" >
                            </textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">课程分类</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="categoryId" id="categoryId" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">添加时间</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="addTime" id="addTime" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">图片url</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="url" id="url" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">图片</label>
                        <div class="col-sm-8">
                            <img  src="" name="urlPic" id="urlPic" >
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="submitCourseUpdateBox()">修改</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    /**
     * 打开修改框
     */
    function courseUpdateBox (id) {
        console.log(id);
        $.ajax({
            type : "POST",
            url : "../user/getCourseTheoryInfo",
            dataType : "json",
            data : {
                "courseTheoryId" : id
            },
            success : function(data) {
                $("#id").attr("value",data.id);
                $("#name").attr("value",data.name);
//                $("#content").attr("value",data.content);
                $("#content").html(data.content);
                $("#categoryId").attr("value",data.categoryId);
                $("#addTime").attr("value",data.addTime);
                $("#url").attr("value",data.url);
                $("#urlPic").attr("src", "../"+data.url);
            },error : function (e) {
                swal("提示","网络错误");
            }
        });
        $('#courseUpdateBox').modal({});
    }

    /**
     * 提交修改数据
     */
    function submitCourseUpdateBox () {
        var data = $('input').serializeArray();
        console.log(data);
        $.ajax({
            type: "POST",
            url: "updateCourseTheory",
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
    function courseDeleteBox (id) {
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
                    url: "courseDelete",
                    dataType: "text",
                    data: {
                        "type" : "theory",
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

</body>
</html>
