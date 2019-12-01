<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>办理入住</title>
    <link rel="stylesheet" href="/static/home/css/index.css">
    <link rel="stylesheet" href="/static/home/css/order.css">
    <link rel="stylesheet" href="/static/home/css/jquery-ui.min.css">
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
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);">关闭</button>
                    <button type="button" class="btn btn-default customerInfo_save_btn">办理入住</button>
                </div>
            </div>
            <div class="book_info" id="hidden" style="display:none;">
                <form id="order_info">
                    <ul>
                        <input type="hidden" name="rid" value=""/>
                        <li>
                            <h3>预定信息</h3>
                            <div class="info_group">
                                <label>入住时间</label><input type="date" name="arriveDate" id="arriveDate" class="datepicker"/>
                                <label>离店时间</label><input type="date" name="leaveDate" id="leaveDate" class="datepicker"/>
                            </div>
                            <div class="info_group">
                                <label  class="col-sm-2 control-label">客房类型</label>
                                    <select class="form-control" name="roomTypeNum" id="add_roomTypeNum">
                                    </select>
                            </div>
                            <div class="info_group">
                                <label>房费总计</label><span class="total">￥</span>
                                <input type="hidden" value="0"/>
                            </div>
                        </li>
                        <li>
                            <h3>入住信息</h3>
                            <div class="info_group">
                                <label>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</label><input type="text" name="customerName" id="name" /><span class="msg"></span>
                            </div>
                            <div class="info_group">
                                <label>电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话</label><input type="text" maxlength="11" name="customerPhone" id="mobile"/><span class="msg"></span>
                            </div>
                        </li>
                        <li>
                            <div id="btn_booking">确认预定</div>
                        </li>
                    </ul>
                </form>
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
                     window.location.href="check_in";
                }else{
                    alert(result.msg+"跳转到预订界面!");
                    $("#name").val($("#add_customerName").val())
                    $("#mobile").val($("#add_customerPhone").val())
                    document.getElementById("hidden").style.display = "block";
                }
             });
         }else{
             alert("请添加完整信息");
             return false;
         }
	 });
})
</script>
<script src="/static/home/js/jquery-1.11.3.js"></script>
<script src="/static/home/js/jquery-ui.min.js"></script>
    <script>
        var roomTypeNum;
        var roomPrice;
        var roomPriceName;
        var arriveDate;
        var leaveDate;
        $(function() {
            //显示套餐名称列表
            $.post("get_roomPriceName_roomTypeNum",function (result) {
                var optionEle ="";
                for(var i=0;i<result.length;i++){
//	 	        	var optionEle = $("<option></option>").append(result[i].deptName).attr("value", result[i].deptId);
                    optionEle+="<option value="+result[i].roomTypeNum+">"+result[i].roomPriceName+"</option>";
                }
                $("#add_roomTypeNum").html(optionEle);
            });
            //显示房间总价
            $("#add_roomTypeNum").blur(function(){
                arriveDate = $("#arriveDate").val();
                leaveDate = $("#leaveDate").val();
                var d1 = new Date(arriveDate.replace(/\-/g, "\/"));
                var d2 = new Date(leaveDate.replace(/\-/g, "\/"));
                if(arriveDate==""&&leaveDate==""){
                    alert("时间不能为空！");
                    return ;
                }
                else if(d1 >=d2)
                {
                    alert("开始时间不能大于结束时间！");
                    return ;
                }else {
                roomTypeNum=$(this).val();
                $.post("get_price_byTypeNum?roomTypeNum="+roomTypeNum,null,function (result) {
                    //roomTypeNum=result.roomTypeNum;
                    roomPrice=result.roomPrice;
                    roomPriceName=result.roomPriceName;
                    $.ajax({
                        url:'calculate_roomDetailPrice',
                        type:'post',
                        dataType:'json',
                        data:{roomPrice:result.roomPrice , checkInDate:arriveDate,
                            checkOutDate:leaveDate,roomCount:1},
                        success:function(result){
                            if(result.code == 200){
                                $(".total").html("￥"+result.data);
                            }else{
                                $(".total").html("￥")
                            }
                        }
                    });
                });}
            })

            //确认预定
            $("#btn_booking").click(function(){
                var name = $("#name").val();
                if(name == ''){
                    $("#name").next("span.msg").text('请填写入住人!');
                    return;
                }
                $("#name").next("span.msg").text('');
                var mobile = $("#mobile").val();
                if(mobile == ''){
                    $("#mobile").next("span.msg").text('请填写手机号!');
                    return;
                }
                $("#mobile").next("span.msg").text('');
                $.ajax({
                    url:'order_confirm',
                    type:'post',
                    dataType:'json',
                    data:{roomTypeNum:roomTypeNum ,customerName:name,customerPhone:mobile,
                        checkInDate:arriveDate,checkOutDate:leaveDate,roomCount:1,
                        roomPriceName:roomPriceName },
                    success:function(result){
                        if(result.code == 200){
                            alert(result.msg);
                            document.getElementById("hidden").style.display = "none";
                        }else{
                            alert(result.msg);
                        }
                    }
                });
            })
        });
    </script>
</body>
</html>
