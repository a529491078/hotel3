<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>订单修改</title>
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
                    <li><a href="#">订单管理</a></li>
                    <li class="active">修改订单信息</li>
                </ol>
            </div>
            <div class="modal-header">
                <h4 class="modal-title">订单修改</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal upd_order_info_form" onsubmit="return false">
                    <input type="text" name="roomOrderDetailNum" class="form-control" style="display:none;" value="${orderInfo.roomOrderDetailNum}">
                    <input type="text" name="roomOrderNum" class="form-control" style="display:none;" value="${orderInfo.roomOrderNum}" >
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">客房套餐名称</label>
                        <div class="col-sm-8">
                            <input type="text" name="roomPriceName" class="form-control" id="upd_roomPriceName" value="${orderInfo.roomPriceName}">
                            <span  class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">套餐价格</label>
                        <div class="col-sm-8">
                            <input type="text" name="roomPrice" class="form-control" id="upd_roomPrice" value="${orderInfo.roomPrice}">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">早餐类型</label>
                        <div class="col-sm-8">
                            <input type="text" name="breakfastType" class="form-control" id="upd_breakfastType" value="${orderInfo.breakfastType}">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">入住时间</label>
                        <div class="col-sm-8">
                            <input type="date" name="checkInTime" class="form-control" id="upd_checkInTime" value="${orderInfo.checkInTime}">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">离开时间</label>
                        <div class="col-sm-8">
                            <input type="date" name="checkOutTime" class="form-control" id="upd_checkOutTime" value="${orderInfo.checkOutTime}">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">预定间数</label>
                        <div class="col-sm-8">
                            <input type="text" name="roomCount" class="form-control" id="upd_roomCount" value="${orderInfo.roomCount}">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">顾客姓名</label>
                        <div class="col-sm-8">
                            <input type="text" name="customerName" class="form-control" id="upd_customerName" value="${orderInfo.customerName}">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">顾客电话</label>
                        <div class="col-sm-8">
                            <input type="text" name="customerPhone" class="form-control" id="upd_customerPhone" value="${orderInfo.customerPhone}">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">订单状态</label>
                        <div class="col-sm-8">
                            <select class="form-control" name="roomOrderState" id="upd_roomOrderState">
                                <option value="0" <c:if test="${orderInfo.roomOrderState==0}"> selected</c:if>>未入住</option>
                                <option value="1" <c:if test="${orderInfo.roomOrderState==1}"> selected</c:if>>已入住</option>
                                <option value="2" <c:if test="${orderInfo.roomOrderState==2}"> selected</c:if>>已取消</option>
                            </select>
                            <span class="help-block"></span>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);">关闭</button>
                <button type="button" class="btn btn-default roomTypePrice_save_btn">修改</button>
            </div>
        </div><!-- /.panel panel-success -->
    </div><!-- /.dept_info -->
</div><!-- /.hrms_dept_body -->

<!-- 尾部-->
<%@ include file="/WEB-INF/commom/foot.jsp"%>
<script type="text/javascript">
$(function(){
	//订单编号
	var roomOrderNum=false;
	//客房套餐名称
	var roomPriceName=false;
	//套餐价格
	var roomPrice=false;
	//早餐类型
	var breakfastType=false;
	//顾客姓名
	var customerName=false;
	//顾客电话
	var customerPhone=false;
	//预订间数
	var roomCount=false;
	//入住状态
	var roomOrderState=false;
    //套餐名称类型框失去焦点时
	 $("#upd_roomPriceName").blur(function(){
		 if($(this).val()== ''){
				$(this).next().css("color","red").html("X");
             roomPriceName = false;
			}else{
				$(this).next().css("color","green").html("√");
             roomPriceName = true;
			}
	 });
    //价格类型框失去焦点时
	 $("#upd_roomPrice").blur(function(){
         if(!$(this).val().match(/^[1-9]+[0-9]*]*$/)){
				$(this).next().css("color","red").html("X");
             roomPrice = false;
			}else{
				$(this).next().css("color","green").html("√");
             roomPrice = true;
			}
	 });
    //早餐类型框失去焦点时
	 $("#upd_breakfastType").blur(function(){
		 if($(this).val()== ''){
				$(this).next().css("color","red").html("X");
             breakfastType = false;
			}else{
				$(this).next().css("color","green").html("√");
             breakfastType = true;
			}
	 });
    //顾客名称框失去焦点时
	 $("#upd_customerName").blur(function(){
         if($(this).val()== ''){
             $(this).next().css("color","red").html("X");
             customerName = false;
         }else{
             $(this).next().css("color","green").html("√");
             customerName = true;
         }
	 });
    //顾客电话框失去焦点时
	 $("#upd_customerPhone").blur(function(){
         if(!$(this).val().match(/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/)){
             $(this).next().css("color","red").html("X");
             customerPhone = false;
         }else{
             $(this).next().css("color","green").html("√");
             customerPhone = true;
         }
	 });
    //预定间数框失去焦点时
	 $("#upd_roomCount").blur(function(){
         if(!$(this).val().match(/^[1-9]+[0-9]*]*$/)){
             $(this).next().css("color","red").html("X");
             roomCount = false;
         }else{
             $(this).next().css("color","green").html("√");
             roomCount = true;
         }
	 });
    //入住状态框失去焦点时
	 $("#upd_roomOrderState").blur(function(){
		 if($(this).val()== ''){
				$(this).next().css("color","red").html("X");
             roomOrderState = false;
			}else{
				$(this).next().css("color","green").html("√");
             roomOrderState = true;
			}
	 });
	 //单击保存时
	 $(".roomTypePrice_save_btn").click(function(){
         var beginDate=$("#upd_checkInTime").val();
         var endDate=$("#upd_checkOutTime").val();
         var d1 = new Date(beginDate.replace(/\-/g, "\/"));
         var d2 = new Date(endDate.replace(/\-/g, "\/"));
         if(beginDate!=""&&endDate!=""&&d1 >=d2)
         {
             alert("开始时间不能大于结束时间！");
             return false;
         }
        if(roomPriceName==true&&roomPrice==true&&breakfastType==true
         &&customerName==true&&customerPhone==true&&roomCount==true){
             $.post("upd_room_order_byid",$(".upd_order_info_form").serialize(),function (result) {
                if(result.code==200){
                    alert(result.msg);
                     window.location.href="get_room_order_page";
                }else{
                    alert(result.msg);
                    window.location.href="edit_order_indo";
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
