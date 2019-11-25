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
                    <li><a href="#">客房管理</a></li>
                    <li class="active">修改客房信息</li>
                </ol>
            </div>
            <div class="modal-header">
                <h4 class="modal-title">客房修改</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal upd_room_info_form" onsubmit="return false">
                    <input type="text" name="roomId" class="form-control" style="display:none;" value="${roomInfo.roomId}">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">楼栋</label>
                        <div class="col-sm-8">
                            <input type="text" name="buildingNum" class="form-control" id="upd_buildingNum" value="${roomInfo.buildingNum}">
                            <span id="helpBlock_add_inputName" class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">房间号</label>
                        <div class="col-sm-8">
                            <input type="email" name="roomNum" class="form-control" id="upd_roomNum" value="${roomInfo.roomNum}">
                            <span id="helpBlock_add_inputEmail" class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">客房类型</label>
                        <div class="col-sm-8">
                            <div class="checkbox">
                                <select class="form-control" name="roomTypeNum" id="upd_roomTypeNum">
                                </select>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);">关闭</button>
                <button type="button" class="btn btn-default room_info_save_btn">保存</button>
            </div>
        </div><!-- /.panel panel-success -->
    </div><!-- /.dept_info -->
</div><!-- /.hrms_dept_body -->

<!-- 尾部-->
<%@ include file="/WEB-INF/commom/foot.jsp"%>
<script type="text/javascript">
$(function(){
	//判断楼栋格式
	var updBuildingNum=false;
	//判断房间号格式
	var updRoomNum=false;
	//判断房间号是否重复
	var code=false;

    //显示套餐名称列表
    $.post("get_roomPriceName_roomTypeNum",function (result) {
        var optionEle ="";
        var roomTypeNum="${roomInfo.roomTypeNum}";
        for(var i=0;i<result.length;i++){
//	 	        	var optionEle = $("<option></option>").append(result[i].deptName).attr("value", result[i].deptId);
            if (roomTypeNum==result[i].roomTypeNum){
                optionEle += "<option value=" + result[i].roomTypeNum + "+ selected >" + result[i].roomPriceName + "</option>";
            }
            else {
                optionEle += "<option value=" + result[i].roomTypeNum + ">" + result[i].roomPriceName + "</option>";
            }
        }
        $("#upd_roomTypeNum").html(optionEle);
    });
    //楼栋框失去焦点时
    $("#upd_buildingNum").blur(function(){
        if($(this).val()== ''){
            $(this).next().css("color","red").html("X");
            updBuildingNum = false;
        }else{
            $(this).next().css("color","green").html("√");
            updBuildingNum = true;
        }
    });
	 //客房号文本框失去焦点时
	 $("#upd_roomNum").blur(function(){
         if(!$(this).val().match(/^[1-9]\d{0,2}$/)){
             $(this).next().css("color","red").html("X");
             updRoomNum = false;
         }else{
             $(this).next().css("color","green").html("√");
             updRoomNum = true;
         }
	 });
	 //单击保存时
	 $(".room_info_save_btn").click(function(){
		 $.post("get_not_room_id_byid",$(".upd_room_info_form").serialize(),function (result) {
				if(result.code==400){
					code=false;
				}else{
					code=true;
				}
				if(code==false){
					alert(result.msg);
				}else{
					if(updBuildingNum==true&&updRoomNum==true){
						 $.post("upd_room_info_byid",$(".upd_room_info_form").serialize(),function (result) {
							if(result.code==200){
								alert(result.msg);
								 window.location.href="get_room_info_page";
							}else{
								alert(result.msg);
								window.location.href="edit_room_info";
							}
						 });
					 }else{
						 alert("请添加完整信息");
						 return false;
					 }
				}
		   });
	 });
})
</script>
</body>
</html>
