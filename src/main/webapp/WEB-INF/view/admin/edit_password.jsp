<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>客房修改</title>
</head>
<body>
<!-- 导航栏-->
<%@ include file="/WEB-INF/commom/head.jsp"%>

<!-- 中间部分（左侧栏+表格内容） -->
<div class="hrms_dept_body">
    <!-- 左侧栏 -->
    <%@ include file="/WEB-INF/commom/leftsidebar.jsp"%>

    <!-- 部门表格内容 -->
    <div class="dept_info col-sm-10">
        <div class="panel panel-success">
            <!-- 路径导航 -->
            <div class="panel-heading">
                <ol class="breadcrumb">
                    <li><a href="#">修改信息</a></li>
                    <li class="active">修改管理员密码</li>
                </ol>
            </div>
            <div class="modal-header">
                <h4 class="modal-title">修改密码</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal upd_pass_form" onsubmit="return false">
                        <div class="form-group">
                            <input type="hidden" name="id" th:field="${adminId}" />
                            <div class="col-sm-8">
                                用户名<input type="text" name="username" class="form-control"
                                          id="upd_name" value="${admin }"> <span></span>
                            </div>
                            <div class="col-sm-8">
                                原密码<input type="password" name="oldPwd" class="form-control"
                                         id="upd_pass"><span></span>
                            </div>
                            <div class="col-sm-8">
                                新密码<input type="password" name="newPass" class="form-control"
                                         id="upd_new_pass"><span></span>
                            </div>
                            <div class="col-sm-8">
                                重复新密码<input type="password" name="password" class="form-control"
                                         id="upd_new_pass2"><span></span>
                            </div>
                            <br><br><br><br><br>
                        </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);">关闭</button>
                <button type="button" class="btn btn-default pass_save_btn">保存</button>
            </div>
        </div><!-- /.panel panel-success -->
    </div><!-- /.dept_info -->
</div><!-- /.hrms_dept_body -->

<!-- 尾部-->
<%@ include file="/WEB-INF/commom/foot.jsp"%>
<script type="text/javascript">
$(function(){
	//判断第二次密码是否相同
	var upd_new_pass=false;
    //第二次密码框失去焦点时
    $("#upd_new_pass2").blur(function(){
        if($(this).val()!=$("#upd_new_pass").val()){
            $(this).next().css("color","red").html("两次输入的密码不一致");
            upd_new_pass = false;
        }else{
            $(this).next().css("color","green").html("√");
            upd_new_pass = true;
        }
    });
	 //单击保存时
	 $(".pass_save_btn").click(function(){
        if(upd_new_pass==true){
             $.post("upd_password",$(".upd_pass_form").serialize(),function (result) {
                if(result.code==200){
                    alert(result.msg);
                     window.location.href="main";
                }else{
                    alert(result.msg);
                    window.location.href="edit_password";
                }
             });
         }else{
             alert("请添加完整信息");
             return false;
         }
	 });
})
</script>
</body>
</html>
