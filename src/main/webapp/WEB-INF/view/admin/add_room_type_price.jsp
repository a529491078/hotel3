<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>客房套餐添加</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
                    <li><a href="#">客房套餐管理</a></li>
                    <li class="active">添加客房套餐信息</li>
                </ol>
            </div>
            <div class="modal-header">
                <h4 class="modal-title">客房套餐新增</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal add_room_type_price_form" method="post" enctype="multipart/form-data">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">客房类型</label>
                        <div class="col-sm-8">
                            <input type="text" name="roomTypeName" class="form-control" id="add_roomTypeName" placeholder="如：普通房">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">床型</label>
                        <div class="col-sm-8">
                            <input type="text" name="bedType" class="form-control" id="add_bedType" placeholder="如：大床、小床、双人床">
                            <span  class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">详图</label>
                        <div class="col-sm-8">
                            <input type="file" name="file_img" id="add_roomTypeImg">
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">楼层</label>
                        <div class="col-sm-8">
                            <input type="text" name="floor" class="form-control" id="add_floor" placeholder="如：1楼">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">套餐名称</label>
                        <div class="col-sm-8">
                            <input type="text" name="roomPriceName" class="form-control" id="add_roomPriceName" placeholder="如：普通房双人床">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">早餐类型</label>
                        <div class="col-sm-8">
                            <input type="text" name="breakfastType" class="form-control" id="add_breakfastType" placeholder="如：不含早">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">套餐价格</label>
                        <div class="col-sm-8">
                            <input type="text" name="roomPrice" class="form-control" id="add_roomPrice" placeholder="如：138">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">套餐描述</label>
                        <div class="col-sm-8">
                            <input type="text" name="roomTypeDecs" class="form-control" id="add_roomTypeDecs">
                            <span class="help-block"></span>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);" >关闭</button>
                <button type="button" class="btn btn-default roomTypePrice_save_btn">添加</button>
            </div>
        </div><!-- /.panel panel-success -->
    </div><!-- /.dept_info -->
</div><!-- /.hrms_dept_body -->

<!-- 尾部-->
<%@ include file="/WEB-INF/commom/foot.jsp"%>
<script type="text/javascript">
$(function(){
	//判断床型格式
	var bedType=false;
	//判断早餐格式
	var breakfastType=false;
	//判断楼层格式
	var floor=false;
	//判断价格格式
	var roomPrice=false;
	//判断套餐名称格式
	var roomPriceName=false;
	//判断套餐描述格式
	var roomTypeDecs=false;
	//判断图片格式
	var roomTypeImg=false;
	//判断客房类型格式
	var roomTypeName=false;

    //客房类型框失去焦点时
	 $("#close").click(function(){
         window.location.href="get_room_type_price_page";
	 });
    //客房类型框失去焦点时
	 $("#add_roomTypeName").blur(function(){
		 if($(this).val()== ''){
				$(this).next().css("color","red").html("X");
             roomTypeName = false;
			}else{
				$(this).next().css("color","green").html("√");
             roomTypeName = true;
			}
	 });
     //套餐价格框失去焦点时
	 $("#add_roomPrice").blur(function(){
         if(!$(this).val().match(/^[1-9]\d{0,2}$/)){
             $(this).next().css("color","red").html("X");
             roomPrice = false;
         }else{
             $(this).next().css("color","green").html("√");
             roomPrice = true;
         }
	 });
    //图片失去焦点时
	 $("#add_roomTypeImg").blur(function(){
         if($(":file").val()==""){
             roomTypeImg = false;
         }else{
             roomTypeImg = true;
         }
	 });
    //早餐类型框失去焦点时
	 $("#add_breakfastType").blur(function(){
		 if($(this).val()== ''){
				$(this).next().css("color","red").html("X");
             breakfastType = false;
			}else{
				$(this).next().css("color","green").html("√");
             breakfastType = true;
			}
	 });
    //床型框失去焦点时
	 $("#add_bedType").blur(function(){
		 if($(this).val()== ''){
				$(this).next().css("color","red").html("X");
             bedType = false;
			}else{
				$(this).next().css("color","green").html("√");
             bedType = true;
			}
	 });
    //楼层框失去焦点时
	 $("#add_floor").blur(function(){
		 if($(this).val()== ''){
				$(this).next().css("color","red").html("X");
             floor = false;
			}else{
				$(this).next().css("color","green").html("√");
             floor = true;
			}
	 });
    //楼栋框失去焦点时
	 $("#add_roomTypeDecs").blur(function(){
		 if($(this).val()== ''){
				$(this).next().css("color","red").html("X");
             roomTypeDecs = false;
			}else{
				$(this).next().css("color","green").html("√");
             roomTypeDecs = true;
			}
	 });
    //套餐名称框失去焦点时
	 $("#add_roomPriceName").blur(function(){
		 if($(this).val()== ''){
				$(this).next().css("color","red").html("X");
             roomPriceName = false;
			}else{
				$(this).next().css("color","green").html("√");
             roomPriceName = true;
			}
	 });
	 //单击保存时
	 $(".roomTypePrice_save_btn").click(function(){
        if(bedType==true&&breakfastType==true&&roomTypeImg==true&&floor==true&&roomPrice==true
            &&roomPriceName==true&&roomTypeDecs==true&&roomTypeName==true){
            var formData=new FormData();
            formData.append("file_img",$('#add_roomTypeImg').prop('files')[0]);
            formData.append('type',"multipart/form-data");
            $.ajax({
                type: 'POST',
                url: 'ins_room_type_price_info?'+$(".add_room_type_price_form").serialize(),
                data:formData,
                contentType: false,// 注意：让jQuery不要处理数据
                processData: false,// 注意：让jQuery不要设置contentType
                success: function (result) {
                    if (result.code == 200) {
                        alert(result.msg);
                        window.location.href = "get_room_type_price_page";
                    } else {
                        alert(result.msg);
                        window.location.href = "add_room_type_price";
                    }
                }, error: function (mag) {
                    console.log("上传失败，请重试");
                    alert("上传失败，请重试");
                    // window.location.reload();
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
