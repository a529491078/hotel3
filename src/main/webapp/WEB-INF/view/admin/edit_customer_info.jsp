<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>顾客修改</title>
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
                    <li><a href="#">顾客管理</a></li>
                    <li class="active">修改顾客信息</li>
                </ol>
            </div>
            <div class="modal-header">
                <h4 class="modal-title">顾客修改</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal add_customer_info_form" onsubmit="return false">
                    <input type="text" name="customerNum" class="form-control" style="display:none;" value="${customerInfo.customerNum}">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">昵称</label>
                        <div class="col-sm-8">
                            <input type="text" name="customerNickName" class="form-control" id="upd_customerNickName" value="${customerInfo.customerNickName}">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">姓名</label>
                        <div class="col-sm-8">
                            <input type="text" name="customerName" class="form-control" id="upd_customerName" value="${customerInfo.customerName}">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">性别</label>
                        <div class="col-sm-8">
                            <div class="checkbox">
                                <select class="form-control" name="customerSex" id="upd_customerSex">
                                   <option value="0" <c:if test="${customerInfo.customerSex==0}"> selected</c:if>>男</option>
                                   <option value="1" <c:if test="${customerInfo.customerSex==1}"> selected</c:if>>女</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">顾客电话</label>
                        <div class="col-sm-8">
                            <input type="text" name="customerPhone" class="form-control" id="upd_customerPhone" value="${customerInfo.customerPhone}">
                            <span  class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">顾客身份证号</label>
                        <div class="col-sm-8">
                            <input type="text" name="customerId" class="form-control" id="upd_customerId" value="${customerInfo.customerId}">
                            <span  class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">地址</label>
                        <div class="col-sm-8">
                            <input type="text" name="customerAddress" class="form-control" id="upd_customerAddress" value="${customerInfo.customerAddress}">
                            <span class="help-block"></span>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);">关闭</button>
                <button type="button" class="btn btn-default customerInfo_save_btn">添加</button>
            </div>
        </div><!-- /.panel panel-success -->
    </div><!-- /.dept_info -->
</div><!-- /.hrms_dept_body -->

<!-- 尾部-->
<%@ include file="/WEB-INF/commom/foot.jsp"%>
<script type="text/javascript">
$(function(){
	//判断昵称格式
	var customerNickName=false;
	//判断真实姓名格式
	var customerName=false;
	//判断性别格式
	var customerSex=false;
	//判断手机号格式
	var customerPhone=false;
	//判断身份证格式
	var customerId=false;
	//判断地址格式
	var customerAddress=false;
	//判断昵称是否重复
	var code=false;
    //昵称框失去焦点时
	 $("#upd_customerNickName").blur(function(){
		 if($(this).val()== ''){
				$(this).next().css("color","red").html("X");
             customerNickName = false;
			}else{
				$(this).next().css("color","green").html("√");
             customerNickName = true;
			}
	 });
    //真实姓名框失去焦点时
	 $("#upd_customerName").blur(function(){
		 if($(this).val()== ''){
				$(this).next().css("color","red").html("X");
             customerName = false;
			}else{
				$(this).next().css("color","green").html("√");
             customerName = true;
			}
	 });
    //手机号框失去焦点时
	 $("#upd_customerPhone").blur(function(){
         if(!$(this).val().match(/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/)){
             $(this).next().css("color","red").html("X");
             customerPhone = false;
         }else{
             $(this).next().css("color","green").html("√");
             customerPhone = true;
         }
	 });
    //身份证框失去焦点时
	 $("#upd_customerId").blur(function(){
         if(!$(this).val().match(/^[1-9]\d{5}(18|19|20|(3\d))\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/)){
             $(this).next().css("color","red").html("X");
             customerId = false;
         }else{
             $(this).next().css("color","green").html("√");
             customerId = true;
         }
	 });
    //地址框失去焦点时
	 $("#upd_customerAddress").blur(function(){
		 if($(this).val()== ''){
				$(this).next().css("color","red").html("X");
             customerAddress = false;
			}else{
				$(this).next().css("color","green").html("√");
             customerAddress = true;
			}
	 });
	 //单击保存时
	 $(".customerInfo_save_btn").click(function(){
        if(customerNickName==true&&customerName==true&&customerPhone==true
            &&customerId==true&&customerAddress==true){
             $.post("upd_customer_info_byid",$(".add_customer_info_form").serialize(),function (result) {
                if(result.code==200){
                    alert(result.msg);
                     window.location.href="get_customer_info_page";
                }else{
                    alert(result.msg);
                    window.location.href="edit_customer_info";
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
