<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>客房添加</title>
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
                    <li class="active">添加客房信息</li>
                </ol>
            </div>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">客房新增</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal add_emp_form" onsubmit="return false">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">楼栋</label>
                        <div class="col-sm-8">
                            <input type="text" name="buildingNum" class="form-control" id="add_buildingNum" placeholder="如：1#">
                            <span id="helpBlock_add_inputName" class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">房间号</label>
                        <div class="col-sm-8">
                            <input type="email" name="roomNum" class="form-control" id="add_roomNum" placeholder="如：201">
                            <span id="helpBlock_add_inputEmail" class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">客房类型</label>
                        <div class="col-sm-8">
                            <div class="checkbox">
                                <select class="form-control" name="roomTypeNum" id="add_roomTypeNum">
                                    <%-- <option value="1">CEO</option>--%>
                                </select>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-default roomTypePrice_save_btn">添加</button>
            </div>
        </div><!-- /.panel panel-success -->
    </div><!-- /.dept_info -->
</div><!-- /.hrms_dept_body -->

<!-- 尾部-->
<%@ include file="/WEB-INF/commom/foot.jsp"%>
<script type="text/javascript">
$(function(){
	//判断名字格式
	var addBuildingNum=false;
	//判断邮箱格式
	var addRoomNum=false;
	//判断房间号是否重复
	var code=false;
	
	//显示套餐名称列表
    $.post("get_roomPriceName_roomTypeNum",function (result) {
        var optionEle ="";
        for(var i=0;i<result.length;i++){
//	 	        	var optionEle = $("<option></option>").append(result[i].deptName).attr("value", result[i].deptId);
            optionEle+="<option value="+result[i].roomTypeNum+">"+result[i].roomPriceName+"</option>";
        }
        $("#add_roomTypeNum").html(optionEle);
    });	 //楼栋框失去焦点时
	 $("#add_buildingNum").blur(function(){
		 if($(this).val()== ''){
				$(this).next().css("color","red").html("X");
             addBuildingNum = false;
			}else{
				$(this).next().css("color","green").html("√");
             addBuildingNum = true;
			}
	 });
	 
	 //客房号文本框失去焦点时
	 $("#add_roomNum").blur(function(){
         if(!$(this).val().match(/^[1-9]\d{0,2}$/)){
             $(this).next().css("color","red").html("X");
             addRoomNum = false;
         }else{
             $(this).next().css("color","green").html("√");
             addRoomNum = true;
         }
	 });
	 
	 //单击保存时
	 $(".roomTypePrice_save_btn").click(function(){
		 $.post("get_not_room_id_byid",$(".add_emp_form").serialize(),function (result) {
				if(result.code==400){
					code=false;
				}else{
					code=true;
				}
				if(code==false){
					alert(result.msg);
				}else{
					if(addBuildingNum==true&&addRoomNum==true){
						 $.post("ins_room_info",$(".add_emp_form").serialize(),function (result) {
							if(result.code==200){
								alert(result.msg);
								 window.location.href="get_room_info_page";
							}else{
								alert(result.msg);
								window.location.href="add_room_info";
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
