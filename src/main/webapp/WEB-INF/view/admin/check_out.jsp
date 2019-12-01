<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>办理退房</title>
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
                    <li><a href="#">办理退房</a></li>
                </ol>
            </div>
            <div class="modal-body">
                <form class="form-horizontalo check_out_form" onsubmit="return false">
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">房间号</label>
                        <div class="col-sm-8">
                            <input type="text" name="roomNum" class="form-control" id="add_roomNum" placeholder="如：203">
                            <span  class="help-block"></span>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);">关闭</button>
                <button type="button" class="btn btn-default check_out_save_btn">确定</button>
            </div>
        </div><!-- /.panel panel-success -->
    </div><!-- /.dept_info -->
</div><!-- /.hrms_dept_body -->

<!-- 尾部-->
<%@ include file="/WEB-INF/commom/foot.jsp"%>
<script type="text/javascript">
$(function(){
	//房间号格式
	var add_roomNum=false;
    //房间号框失去焦点时
	 $("#add_roomNum").blur(function(){
         if(!$(this).val().match(/^[1-9]\d{0,2}$/)){
             $(this).next().css("color","red").html("X");
             add_roomNum = false;
         }else{
             $(this).next().css("color","green").html("√");
             add_roomNum = true;
         }
	 });
	 //单击保存时
	 $(".check_out_save_btn").click(function(){
            if(add_roomNum==true){
             $.post("room_check_out",$(".check_out_form").serialize(),function (result) {
                if(result.code==200){
                    alert(result.msg);
                     window.location.href="main";
                }else{
                    alert(result.msg);
                    //window.location.href="check_in";
                    alert("跳转到预订界面")
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
