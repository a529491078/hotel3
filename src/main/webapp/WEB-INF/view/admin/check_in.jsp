<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>办理入住</title>
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
                    <li><a href="#">办理入住</a></li>
                </ol>
            </div>
            <div class="modal-body">
                <form class="form-horizontal add_customer_info_form" onsubmit="return false">
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">姓名</label>
                        <div class="col-sm-8">
                            <input type="text" name="customerName" class="form-control" id="add_customerName" placeholder="如：陈祖陆">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">顾客电话</label>
                        <div class="col-sm-8">
                            <input type="text" name="customerPhone" class="form-control" id="add_customerPhone" placeholder="如：1806568####">
                            <span  class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">顾客身份证号</label>
                        <div class="col-sm-8">
                            <input type="text" name="customerId" class="form-control" id="add_customerId" placeholder="如：352203############">
                            <span  class="help-block"></span>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);">关闭</button>
                <button type="button" class="btn btn-default customerInfo_save_btn">确定</button>
            </div>
        </div><!-- /.panel panel-success -->
    </div><!-- /.dept_info -->
</div><!-- /.hrms_dept_body -->

<!-- 尾部-->
<%@ include file="/WEB-INF/commom/foot.jsp"%>
<script type="text/javascript">
$(function(){
	//判断真实姓名格式
	var customerName=false;
	//判断手机号格式
	var customerPhone=false;
	//判断身份证格式
	var customerId=false;

    //真实姓名框失去焦点时
	 $("#add_customerName").blur(function(){
		 if($(this).val()== ''){
				$(this).next().css("color","red").html("X");
             customerName = false;
			}else{
				$(this).next().css("color","green").html("√");
             customerName = true;
			}
	 });
    //手机号框失去焦点时
	 $("#add_customerPhone").blur(function(){
         if(!$(this).val().match(/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/)){
             $(this).next().css("color","red").html("X");
             customerPhone = false;
         }else{
             $(this).next().css("color","green").html("√");
             customerPhone = true;
         }
	 });
    //身份证框失去焦点时
	 $("#add_customerId").blur(function(){
         if(!$(this).val().match(/^[1-9]\d{5}(18|19|20|(3\d))\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/)){
             $(this).next().css("color","red").html("X");
             customerId = false;
         }else{
             $(this).next().css("color","green").html("√");
             customerId = true;
         }
	 });
	 //单击保存时
	 $(".customerInfo_save_btn").click(function(){
        if(customerName==true&&customerPhone==true
            &&customerId==true){
             $.post("checkIn",$(".add_customer_info_form").serialize(),function (result) {
                if(result.code==200){
                    alert(result.msg+"房间号为:"+result.data);
                     window.location.href="main";
                }else{
                    alert(result.msg);
                    window.location.href="check_in";
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
