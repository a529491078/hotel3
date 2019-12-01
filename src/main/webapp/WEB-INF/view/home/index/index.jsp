<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="Author" content="猿来入此">
  <meta name="Keywords" content="猿来入此酒店管理系统">
  <meta name="Description" content="猿来入此酒店管理系统">
  <link href="/static/home/css/reservation.css" type="text/css" rel="Stylesheet"/>
  <link href="/static/home/css/index.css" type="text/css" rel="Stylesheet"/>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>
    <script src="/static/admin/login/js/jquery-1.8.0.min.js"></script>
<title>猿来入此|酒店管理系统首页</title>
</head>
<body>
<!--头部-->
<div id="c_header"></div>
<!--主体内容-->
<section>
  <div id="subject">
    <p style="float:right;">
        <c:if test="${user == null }">
            <a href="/user/user_to_login">登录</a>&nbsp;&nbsp;|&nbsp;&nbsp;
            <a href="/user/user_register">注册</a>&nbsp;
        </c:if>
        <c:if test="${user != null }">
            <font color="red">欢迎您：${user }&nbsp;&nbsp;|&nbsp;&nbsp;</font>
            <a href="/user/use_center_order?userName=${user }">用户中心</a>&nbsp;&nbsp;|&nbsp;&nbsp;
            <a href="log_out">注销登录</a>&nbsp;&nbsp;|&nbsp;&nbsp;
        </c:if>
    </p>
    <img src="/static/home/images/index_02.jpg" alt="" height="256px" width="1200px">
    <!--遮罩-->
    <ul class="shade_mag">
      <li><img src="/static/home/images/s_02.png" alt=""></li>
      <li><img src="/static/home/images/s_01.png" alt=""></li>
    </ul>
  </div>

  <!---预订菜单--->
  <div id="due_menu">
    <!--客房-->
    <div id="guest_rooms">
      <p class="booking_tab"><span></span>客房列表</p>
      <div class="chioce">
        <input type="text" placeholder="关键字" value="${kw }" id="kw"/>
        <input type="button" value="搜索" id="search-btn"/>
      </div>
      <form style="display:none;" action="index" method="get" id="search-form"><input type="hidden" name="name" id="search-name"></form>
      <!--列表-->
      <table id="pro_list" >
        <thead>
          <tr>
            <th width="200px">客房</th>
            <th>房型</th>
            <th>客房套餐</th>
              <th>早餐类型</th>
            <th>房价</th>
              <th>房间描述</th>
            <th>房态</th>
            <th>预订</th>
          </tr>
        </thead>
        <tbody >
        <c:forEach items="${page.list}" var="type_price">
        <tr>
          <td><a href="#"><img src="${type_price.roomTypeImg}" alt=""></a>
          </td>
          <td align="center">
            <p>${type_price.roomTypeName}</p>
          </td>
          <td>${type_price.roomPriceName}</td>
          <td>${type_price.breakfastType}</td>
          <td>${type_price.roomPrice}</td>
            <td>${type_price.roomTypeDecs}</td>
          <td>
          	<c:if test="${type_price.roomState == 0 }">
          		可预订
          	</c:if>
          	<c:if test="${type_price.roomState == 1 }">
                已满房
          	</c:if>
          </td>
          <td>
          	<c:if test="${type_price.roomState == 1 }">
          		<input type="button" class="disable" value="满房" >
          	</c:if>
          	<c:if test="${type_price.roomState == 0 }">
          		<input type="button" value="预订" onclick="window.location.href=
                        '/user/book_order?roomTypeId=${type_price.roomTypeNum }&userName=${user }'" >
          	</c:if>
          </td>
        </tr>
		</c:forEach>
        </tbody>
      </table>

        <%--分页导航--%>
        <div class="panel-body">
            <div class="table_items">
                当前第<span class="badge">${page.pageNumber}</span>页，共有<span class="badge">${page.total}</span>页，总记录数<span class="badge">${page.totalNumber}</span>条。
            </div>
            <nav aria-label="Page navigation" class="pull-right">
                <ul class="pagination">
                    <li><a href="index?pageNumber=1&userName=${user }">首页</a></li>
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
                            <li class="active"><a href="index?pageNumber=${itemPage}&userName=${user }">${itemPage}</a></li>
                        </c:if>
                        <c:if test="${page.pageNumber != itemPage}">
                            <li><a href="index?pageNumber=${itemPage}&userName=${user }">${itemPage}</a></li>
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
                    <li><a href="index?pageNumber=${page.total}&userName=${user }'">尾页</a></li>
                </ul>
            </nav>
        </div>

  </div>
  </div>
</section>
<%@include file="../common/footer.jsp"%>
<script src="/static/home/js/jquery-1.11.3.js"></script>
<script>
$(document).ready(function(){
    //房间搜索
	$("#search-btn").click(function(){
		$("#search-name").val($("#kw").val());
		$("#search-form").submit();
	})
});
</script>
</body>