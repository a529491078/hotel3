<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>客户管理页面</title>
</head>
<body>
<div class="hrms_dept_container">
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
                        <li><a href="#">客户管理</a></li>
                        <li class="active">客户信息</li>
                    </ol>
                </div>
                <!-- Table -->
                <table class="table table-bordered table-hover" id="dept_table">
                    <thead>
                        <th>顾客编号</th>
                        <th>顾客昵称</th>
                        <th>顾客姓名</th>
                        <th>顾客性别</th>
                        <th>顾客电话</th>
                        <th>顾客身份证号</th>
                        <th>地址</th>
                    </thead>
                    <tbody>
                        <c:forEach items="${page.list}" var="customerInfo">
                            <tr>
                                <td>${customerInfo.customerNum}</td>
                                <td>${customerInfo.customerNickName}</td>
                                <td>${customerInfo.customerName}</td>
                                <c:if test="${customerInfo.customerSex==0}"><td>男</td></c:if>
                                <c:if test="${customerInfo.customerSex==1}"><td>女</td></c:if>
                                <td>${customerInfo.customerPhone}</td>
                                <td>${customerInfo.customerId}</td>
                                <td>${customerInfo.customerAddress}</td>
                                <td>
                                    <a href="get_customer_info_byid?customerNum=${customerInfo.customerNum}" role="button" class="btn btn-primary customer_edit_btn">编辑</a>
                                    <a href="#" role="button" class="btn btn-danger customer_delete_btn">删除</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <div class="panel-body">
                    <div class="table_items">
                        当前第<span class="badge">${page.pageNumber}</span>页，共有<span class="badge">${page.total}</span>页，总记录数<span class="badge">${page.totalNumber}</span>条。
                    </div>
                    <nav aria-label="Page navigation" class="pull-right">
                        <ul class="pagination">
                            <li><a href="get_customer_info_page?pageNumber=1">首页</a></li>
                            <c:if test="${page.pageNumber==1}">
                                <li class="disabled">
                                    <a href="#" aria-label="Previous" class="prePage">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </li>
                            </c:if>
                            <c:if test="${page.pageNumber!=1}">
                                <li>
                                    <a href="#" aria-label="Previous" class="prePage">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </li>
                            </c:if>

                            <c:forEach begin="1" end="${page.total<5?page.total:5}" step="1" var="itemPage">
                                <c:if test="${page.pageNumber == itemPage}">
                                    <li class="active"><a href="get_customer_info_page?pageNumber=${itemPage}">${itemPage}</a></li>
                                </c:if>
                                <c:if test="${page.pageNumber != itemPage}">
                                    <li><a href="get_customer_info_page?pageNumber=${itemPage}">${itemPage}</a></li>
                                </c:if>
                            </c:forEach>

                            <c:if test="${page.pageNumber==page.total}">
                                <li class="disabled" class="nextPage">
                                    <a href="#" aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </li>
                            </c:if>
                            <c:if test="${page.pageNumber!=page.total}">
                                <li>
                                    <a href="#" aria-label="Next" class="nextPage">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </li>
                            </c:if>
                            <li><a href="get_customer_info_page?pageNumber=${page.total}">尾页</a></li>
                        </ul>
                    </nav>
                </div>
            </div><!-- /.panel panel-success -->
        </div><!-- /.dept_info -->
    </div><!-- /.hrms_dept_body -->

    <!-- 尾部-->
    <%@ include file="/WEB-INF/commom/foot.jsp"%>

</div><!-- /.hrms_dept_container -->

<script type="text/javascript">
    var pageNumber = ${page.pageNumber};
    var total = ${page.total};
    //上一页
    $(".prePage").click(function () {
         if (pageNumber > 1){
             var pageNo = pageNumber - 1;
             $(this).attr("href", "get_customer_info_page?pageNumber="+pageNo);
         }
    });
    //下一页
    $(".nextPage").click(function () {
        if (pageNumber < total){
            var pageNo = pageNumber + 1;
            $(this).attr("href", "get_customer_info_page?pageNumber="+pageNo);
        }
    });
    //删除
    $(".customer_delete_btn").click(function () {
        var delCustomerNum = $(this).parent().parent().find("td:eq(0)").text();
        var delCustomerName = $(this).parent().parent().find("td:eq(2)").text();
        var pageNumber = ${page.pageNumber};
        if (confirm("确认删除顾客姓名为【"+ delCustomerName +"】的信息吗？")){
        	$.post("del_customer_info_byid?customerNum="+delCustomerNum,function(result){
        		 if (result.code == 200){
                     alert(result.msg);
                     window.location.href="get_customer_info_page?pageNumber="+pageNumber;
                 }else {
                     alert(result.msg);
                 }
        	})
        }
    });
</script>
</body>
</html>
