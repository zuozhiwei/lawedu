<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="stylesheet" type="text/css" href="resources/css/theme.css">
<link rel="stylesheet" type="text/css" href="resources/css/layout.css">
<link rel="stylesheet" type="text/css" href="resources/css/form-page.css">

<%--bootstrap--%>
<script type="text/javascript" src="resources/jquery/jquery-2.1.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="resources/bootstrap/css/bootstrap.min.css" />
<script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>

<link rel="stylesheet" type="text/css" href="resources/bootstrap/extend/bootstrapDatetimepicker/css/bootstrap-datetimepicker.min.css" />
<script type="text/javascript" src="resources/bootstrap/extend/bootstrapDatetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="resources/bootstrap/extend/bootstrapDatetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"  charset="UTF-8"></script>

<%--表格--%>
<link rel="stylesheet" type="text/css" href="resources/jquery/datatables/media/css/jquery.dataTables.css">
<script type="text/javascript" src="resources/jquery/datatables/media/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="resources/jquery/datatables/media/js/DataTableUtil.js"></script>
<script type="text/javascript" src="resources/js/common.js"></script>

<%--jquery-validation--%>
<script type="text/javascript" src="resources/jquery/jquery-validation/jquery.validate.js"></script>
<script type="text/javascript" src="resources/jquery/jquery-validation/localization/messages_zh.js"></script>

<%--alert--%>
<link rel="stylesheet" type="text/css" href="resources/sweetalert/sweetalert.css">
<link rel="stylesheet" type="text/css" href="resources/sweetalert/themes/facebook.css">
<script type="text/javascript" src="resources/sweetalert/sweetalert.min.js"></script>
<script type="text/javascript">
    window.alert = function(text){
        swal("提示",text);
    }
    //全局的ajax访问，处理ajax清求时sesion超时
    $.ajaxSetup({
        contentType:"application/x-www-form-urlencoded;charset=utf-8",
        complete:function(XMLHttpRequest,textStatus){
            var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus"); //通过XMLHttpRequest取得响应头，sessionstatus，
            if(sessionstatus=="timeout"){
                parent.timeout();
            }
        }
    });
</script>

<style>
    /* 修正datatable中p元素受bootstrap样式影响 */
    .display p {
        margin: auto !important;
    }
</style>