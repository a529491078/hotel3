<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<body>
<div class="panel-group col-sm-2" id="hrms_sidebar_left" role="tablist" aria-multiselectable="true">
    <ul class="nav nav-pills nav-stacked emp_sidebar">
        <li role="presentation" class="active">
            <a href="#" data-toggle="collapse" data-target="#collapse_emp">
                <span class="glyphicon glyphicon-user" aria-hidden="true">订单管理</span>
            </a>
            <ul class="nav nav-pills nav-stacked" id="collapse_emp">
                <li role="presentation"><a href="get_room_order_page" class="order_info">订单信息</a></li>
                <li role="presentation"><a href="#" class="order_clearall_btn">订单清零</a></li>
            </ul>
        </li>
    </ul>
    <ul class="nav nav-pills nav-stacked dept_sidebar">
        <li role="presentation" class="active">
            <a href="#"  data-toggle="collapse" data-target="#collapse_dept">
                <span class="glyphicon glyphicon-cloud" aria-hidden="true">客房管理</span>
            </a>
            <ul class="nav nav-pills nav-stacked" id="collapse_dept">
                <li role="presentation"><a href="get_room_info_page" class="room_info">客房信息</a></li>
                <li role="presentation"><a href="add_room_info" class="room_add_btn">客房新增</a></li>
                <li role="presentation"><a href="get_room_type_price_page" class="room_type_price_info">客房套餐信息</a></li>
                <li role="presentation"><a href="add_room_type_price" class="room_type_price_btn">客房套餐新增</a></li>
                <li role="presentation"><a href="#" class="room_clearall_btn">客房清零</a></li>
            </ul>
        </li>
    </ul>
    <ul class="nav nav-pills nav-stacked dept_sidebar">
        <li role="presentation" class="active">
            <a href="#"  data-toggle="collapse" data-target="#collapse_dept">
                <span class="glyphicon glyphicon-cloud" aria-hidden="true">顾客管理</span>
            </a>
            <ul class="nav nav-pills nav-stacked">
                <li role="presentation"><a href="get_customer_info_page" class="room_info">顾客信息</a></li>
                <li role="presentation"><a href="add_customer_info" class="room_add_btn">顾客新增</a></li>
                <li role="presentation"><a href="#" class="room_clearall_btn">客房清零</a></li>
            </ul>
        </li>
    </ul>

</div><!-- /.panel-group，#hrms_sidebar_left -->

<script type="text/javascript">
    $(".order_clearall_btn").click(function () {
        alert("对不起，您暂无权限进行操作！请先获取权限");
    });
    $(".room_clearall_btn").click(function () {
        alert("对不起，您暂无权限进行操作！请先获取权限");
    });
</script>
</body>
</html>
