<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>Head Page</title>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>
    <script src="/static/admin/login/js/jquery-1.8.0.min.js"></script>
</head>
<body>
<div class="hrms_brand_nav">
    <nav class="navbar navbar-inverse navbar-static-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#hrms-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" id="company_logo" href="#">XXX酒店LOGO</a>
            </div>

            <div class="collapse navbar-collapse" id="hrms-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li>
                        <a href="main">酒店介绍<span class="sr-only">(Hotel)</span>  </a>
                    </li>
                    <li><a href="check_in">办理入住</a></li>
                    <li><a href="#">房态查看</a></li>
                    <li><a href="#">账单统计</a></li>
                </ul>

                <form class="navbar-form navbar-left">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="站内搜索">
                    </div>
                    <button type="submit" class="btn btn-default">搜索</button>
                </form>
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">账号管理(${admin}) <span class="caret"></span></a>
                        <ul class="dropdown-menu nav nav-pills nav-stacked">
                            <li class="active"><a href="#" role="button" data-toggle="modal" data-target=".user-updata-modal"><span class="glyphicon glyphicon-pencil" aria-hidden="true" ></span> 修改信息</a></li>
                            <li><a href="#" role="button" data-toggle="modal" data-target=".user-phone-modal"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> 头像更换</a></li>
                            <li><a href="#" class="hrms_logout"><span class="glyphicon glyphicon-off" aria-hidden="true"></span> 账号退出</a></li>
                        </ul>
                    </li>
                </ul><!-- /.nav navbar-nav navbar-right -->
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>
</div><!-- /.hrms_brand_nav -->
<script type="text/javascript">
    //主页面
    $("#company_logo").click(function () {
        $(this).attr("href", "main");
    });
    //账号退出
    $(".hrms_logout").click(function () {
        window.location.href = "logout";
    });
</script>
</body>
</html>
