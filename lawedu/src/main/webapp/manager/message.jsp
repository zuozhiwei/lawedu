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
                    <h3 class="panel-title">咨询问题</h3>
                </div>
                <div class="container-fluid">

 					<div class="row">
                        <div class="well well-sm"></div>
                        <div class="col-md-1">
                            <button class="btn btn-primary" onclick="getMessageList()">刷新</button>
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <table id="table_message" class="display">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>提问者id</th>
                            <th>提问者姓名</th>
                            <th>内容</th>
                            <th>律师id</th>
                            <th>律师姓名</th>
                            <th>添加时间</th>
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
    $( function () {
        $("#message").addClass("active");
        getMessageList();
    });
    function getMessageList () {
        $.ajax({
            type : "POST",
            url : "getMessageList",
            dataType : "json",
            data : {},
            success : function(data) {
            	console.log(data);
                $('#table_message').DataTable({
                    data : data,
                    destroy : true,
                    "columns" : [ {
                        data : "id"
                    }, {
                        data : "fromId"
                    }, {
                        data : "fromName"
                    }, {
                        data : "content"
                    }, {
                        data : "toId"
                    }, {
                        data : "toName"
                    }, {
                        data : "addTime"
                    }, {
                        data : null
                    } ],
                    "columnDefs": [
                        
                        {
                            "render" : function(data, type, row, meta){
                                var btnHTML = '';
                                btnHTML += '<button type="button" onclick="replyBox(\''+row.id+'\')" class="btn btn-primary">查看</button> ';
                                btnHTML += '<button type="button" onclick="messageUpdateBox(\''+row.id+'\',\''+row.fromId+'\',\''+row.fromName+'\',\''+row.content+'\',\''+row.toId+'\',\''+row.toName+'\',\''+row.addTime+'\')" class="btn btn-primary">修改</button> ';
                                btnHTML += '<button type="button" onclick="messageDeleteBox(\''+row.id+'\')" class="btn btn-warning">删除</button> ';
                                return btnHTML;
                            },
                            "targets" : 7
                        },
                        {
                            "render" : function(data, type, row, meta){
                                if(typeof data == "undefined") {
                                    return ''
                                }
                                return data;
                            },
                            "targets" : [0,1,2,3,4,5,6,7]
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

<!-- 修改模态框 -->
<div class="modal fade" id="messageUpdateBox" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">×</button><h4 class="modal-title" id="myModalLabel">修改</h4>
            </div>
            <div style="width: 90%;margin: 10px auto;">
                <form class="form-horizontal" id="assignmentCheckForm">
                    <div class="form-group">
                        <label class="col-sm-4 control-label">编号</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="id" id="id" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">提问者id</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="fromId" id="fromId" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">提问者姓名</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="fromName" id="fromName" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">内容</label>
                        <div class="col-sm-8">
                            <textarea style = "height:40px" type="text" class="DataInput form-control" name="content" id="content" ></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">律师id</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="toId" id="toId" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">律师姓名</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="toName" id="toName" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">留言时间</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="addTime" id="addTime" >
                        </div>
                    </div>                 
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="submitMessageUpdateBox()">提交</button>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">
    /**
     * 打开修改框
     */
    function messageUpdateBox(id,fromId,fromName,content,toId,toName,addTime) {
        $("#id").val(id);
        $("#fromId").val(fromId);
        $("#fromName").val(fromName);
        $("#content").val(content);
        $("#toId").val(toId);
        $("#toName").val(toName);
        $("#addTime").val(addTime);

        $('#messageUpdateBox').modal({});
    }

    /**
     * 提交修改数据
     */
    function submitMessageUpdateBox () {
        var data = {
        		"id":$("#id").val(),
        		"fromId":$("#fromId").val(),
        		"content":$("#content").val(),
        		"toId":$("#toId").val(),
        		"addTime":$("#addTime").val()
        };
        console.log(data);
        $.ajax({
            type: "POST",
            url: "updateMessage",
            dataType: "text",
            data: data,
            success: function (data) {
                swal("提示","修改成功","success");
                $('#messageUpdateBox').modal('hide');
                getMessageList();
            },error: function (e) {
                swal("提示","网络错误");
            }
        });
    }
</script>

<script type="text/javascript">
    function messageDeleteBox (id) {
        swal({
            title: "确认删除?",
            text: "您将删除此问题。",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "删除",
            closeOnConfirm: false
        },
        function () {
                $.ajax({
                    type: "POST",
                    url: "deleteMessage",
                    dataType: "text",
                    data: {
                        "id" : id
                    },
                    success: function (data) {
                        swal("提示","删除成功","success");
                        getMessageList();
                    },error: function (e) {
                        swal("提示","网络错误");
                    }
                });
            }
        )
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
<%--查看回答--%>
<div class="modal fade" id="replyBox" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">×</button><h4 class="modal-title" id="myModalLabel">查看回复</h4>
            </div>
            <div style="width: 100%;margin: 10px auto;">
                <table id="table_answer" class="display">
                    <thead>
                    <tr>
                        <th>编号</th>
                        <th>内容</th>
                        <th>律师id</th>
                        <th>律师姓名</th>
                        <th>添加时间</th>
                    </tr>
                    </thead>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    /**
     * 打开修改框
     */
    function replyBox(id) {
        $.ajax({
            type : "POST",
            url : "getReplyLawQuestion",
            dataType : "json",
            data : {
                "id":id
            },
            success : function(data) {
                console.log(data);
                $('#table_answer').DataTable({
                    data : data,
                    destroy : true,
                    "columns" : [ {
                        data : "id"
                    }, {
                        data : "content"
                    }, {
                        data : "fromId"
                    }, {
                        data : "fromName"
                    }, {
                        data : "addTime"
                    }],
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

        $('#replyBox').modal({});
    }
</script>


</body>
</html>
