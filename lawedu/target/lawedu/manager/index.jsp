<%--
  Created by IntelliJ IDEA.
  User: zuozhiwei
  Date: 2017/5/12
  Time: 19:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->

    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" type="text/css" href="../resources/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../resources/css/jquery.dataTables.min.css">

    <script type="text/javascript" src="../resources/js/jquery-2.1.0.js"></script>
    <script type="text/javascript" src="../resources/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="../resources/js/bootstrap.min.js"></script>
    <title>法学教育管理平台</title>
</head>
<body  style="background:url(resources/images/bgp.jpg);background-size:100% 1000px;">
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
                    <a class="navbar-brand" href="index.jsp">法学教育平台</a>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse"
                     id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="#">管理 <span class="sr-only">(current)</span></a></li>
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
        <div class="col-md-4"></div>

        <div class="col-md-4" >
            <div class="panel panel-default" >
                <div class="panel-heading">
                    <h3 class="panel-title">登录</h3>
                </div>
                <div class="panel-body" style="height: 300px;">
                    <br />
                    <label for="mobile" class="sr-only">用户名</label>
                    <input type="useremail" name="mobile" id="mobile" class="form-control" placeholder="用户名" required autofocus />
                    <br />
                    <br />
                    <label for="password" class="sr-only">密码</label>
                    <input type="password" name="password" id="password" class="form-control" placeholder="密码" required />
                    <br />
                    <br />
                    <button class="btn btn-lg btn-primary btn-block" onclick="login()">登录</button>

                </div>
            </div>
        </div>
        <div class="col-md-4"></div>
    </div>

</div>
<footer class="footer navbar-fixed-bottom ">
    <div class="container" style="text-align: center">
        <b>版权所有：河北工业大学</b>
    </div>
</footer>
<script type="text/javascript">
    function login() {
        var mobile = $("#mobile").val();
        var password = $("#password").val();

        $.ajax({
            type : "POST",
            url : "loginCheck",
            dataType : "text",
            data : {
                "mobile":mobile,
                "password":password
            },
            success : function(data) {
                if("success"==data){
                    window.location.href = "home.jsp";
                }else {
                    alert("账号或密码错误");
                }
            },error:function (error) {
                console.log(error);
                alert("网络错误");
            }
        });
    }
</script>

</body>
</html>
