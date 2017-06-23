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
                    <h3 class="panel-title">科研项目</h3>
                </div>
                <div class="container-fluid">


                    <div class="row">
                        <div class="well well-sm"></div>
                        <div class="col-md-1">
                            <button class="btn btn-primary" onclick="getProjectList()">刷新</button>
                        </div>
                        <div class="col-md-1">
                            <button class="btn btn-primary" onclick="addProject()">添加科研项目</button>
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <table id="table_project" class="display">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>名称</th>
                            <th>描述</th>
                            <th>发起人</th>
                            <th>开始日期</th>
                            <th>结束日期</th>
                            <th>添加日期</th>
                            <th>状态</th>
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
        $("#project").addClass("active");
        getProjectList();
    });
    function getProjectList () {
        $.ajax({
            type : "POST",
            url : "../user/getBidProjectList",
            dataType : "json",
            data : {},
            success : function(data) {
                $('#table_project').DataTable({
                    data : data.list,
                    destroy : true,
                    "columns" : [ {
                        data : "id"
                    }, {
                        data : "projectName"
                    }, {
                        data : "description"
                    }, {
                        data : "userName"
                    }, {
                        data : "beginTime"
                    }, {
                        data : "endTime"
                    }, {
                        data : "addTime"
                    }, {
                        data : "status"
                    }, {
                        data : null
                    } ],
                    "columnDefs": [
                        {
                            "render" : function(data, type, row, meta){
                                var btnHTML = '';
                                btnHTML += '<button type="button" onclick="projectUpdateBox(\''+row.id+'\',\''+row.projectName+'\',\''+row.description+'\',\''+row.userName+'\',\''+row.beginTime+'\',\''+row.endTime+'\',\''+row.addTime+'\',\''+row.status+'\')" class="btn btn-primary">修改</button> ';
                                btnHTML += '<button type="button" onclick="projectDeleteBox(\''+row.id+'\')" class="btn btn-warning">删除</button> ';
                                console.log(btnHTML);
                                return btnHTML;
                            },
                            "targets" : 8
                        },
                        {
                            "render" : function(data, type, row, meta){
                            	if(data == "0"){
                                    return '报名中';
                                }else if(data == "1"){
                                    return '进行中';
                                }else if(data == "2"){
                                    return '已结项';
                                }
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

<!-- 项目审核模态框 -->
<div class="modal fade" id="projectUpdateBox" tabindex="-1" role="dialog"
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
                        <label class="col-sm-4 control-label">编号</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="id" id="id" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">名称</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="projectName" id="projectName" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">描述</label>
                        <div class="col-sm-8">
                            <input  type="text" class="DataInput form-control" name="description" id="description" /> 
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">发起人</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="userName" id="userName" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">开始时间</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="beginTime" id="beginTime" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">结束时间</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="endTime" id="endTime" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">添加时间</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="addTime" id="addTime" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">状态</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="status" id="status" >
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="submitProjectUpdateBox()">修改</button>
            </div>
        </div>
    </div>
</div>

<!-- 项目添加模态框 -->
<div class="modal fade" id="projectAddBox" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">×</button><h4 class="modal-title" id="myModalLabel">添加</h4>
            </div>
            <div style="width: 90%;margin: 10px auto;">
                <form class="form-horizontal" id="projectCheckForm">
                    <div class="form-group">
                        <label class="col-sm-4 control-label">名称</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="add_projectName" id="add_projectName" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">描述</label>
                        <div class="col-sm-8">
                            <input  type="text" class="DataInput form-control" name="add_description" id="add_description" /> 
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">发起人编号</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="add_addUser" id="add_addUser" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">开始时间</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="add_beginTime" id="add_beginTime" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">结束时间</label>
                        <div class="col-sm-8">
                            <input type="text" class="DataInput form-control" name="add_endTime" id="add_endTime" >
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="submitProjectAddBox()">添加</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    /**
     * 打开添加框
     */
   
	function addProject() {
		$("#add_projectName").val("");
		$("#add_description").val("");
		$("#add_addUser").val("");
		$("#add_beginTime").val("");
		$("#add_endTime").val("");
		
		$('#projectAddBox').modal({});
	}

	/**
	 * 提交添加数据
	 */
	function submitProjectAddBox() {
		var data = {
				"projectName":$("#add_projectName").val(),
				"description":$("#add_description").val(),
				"addUser":$("#add_addUser").val(),
				"beginTime":$("#add_beginTime").val(),
				"endTime":$("#add_endTime").val(),
		};
		console.log(data);
		$.ajax({
			type : "POST",
			url : "addProject",
			dataType : "text",
			data : data,
			success : function(data) {
				swal("提示", "添加成功", "success");
				$('#projectAddBox').modal('hide');
				getProjectList();
			},
			error : function(e) {
				swal("提示", "网络错误");
			}
		});
	}
</script>


<script type="text/javascript">
    /**
     * 打开修改框
     */
   
	function projectUpdateBox(id, projectName, description, userName, beginTime,
			endTime, addTime, status) {
		$("#id").attr("value", id);
		$("#projectName").attr("value", projectName);
		//                $("#content").attr("value",data.content);
		//$("#content").html(data.content);
		$("#description").attr("value", description);
		$("#userName").attr("value", userName);
		$("#beginTime").attr("value", beginTime);
		$("#endTime").attr("value", endTime);
		$("#addTime").attr("value", addTime);
		$("#status").attr("value", status);
		
		$('#projectUpdateBox').modal({});
	}

	/**
	 * 提交修改数据
	 */
	function submitProjectUpdateBox() {
		var data = $('input').serializeArray();
		console.log(data);
		$.ajax({
			type : "POST",
			url : "updateProject",
			dataType : "text",
			data : data,
			success : function(data) {
				swal("提示", "修改成功", "success");
				$('#projectUpdateBox').modal('hide');
				getProjectList();
			},
			error : function(e) {
				swal("提示", "网络错误");
			}
		});
	}
</script>

<script type="text/javascript">
    function projectDeleteBox (id) {
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
                    url: "projectDelete",
                    dataType: "text",
                    data: {
                        "id" : id
                    },
                    success: function (data) {
                        swal("提示","删除成功","success");
                        getProjectList();
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

</body>
</html>
