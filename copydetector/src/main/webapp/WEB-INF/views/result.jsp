<%--
  Created by IntelliJ IDEA.
  User: 11488
  Date: 2018/3/11
  Time: 16:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html"; charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>文本相似性比对系统</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/landing-page.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic,700italic" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script src="js/echarts.min.js"></script>
</head>

<body>
<!-- Navigation -->
<nav class="navbar navbar-default navbar-fixed-top topnav" role="navigation">
    <div class="container topnav">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand topnav" href="${pageContext.request.contextPath}/">首页</a>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="${pageContext.request.contextPath}/history">比对历史</a>
                </li>
                <li>
                    <a href="#about">About</a>
                </li>
                <li>
                    <a href="#contact">Contact</a>
                </li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>

<!-- Header -->
<a name="about"></a>
<div class="intro-header">
    <div class="container">
        <div class="row">
            <div id="result" class="col-lg-12" style="height:28%">

            </div>
            <div class="col-lg-12">
                <div class="col-lg-6" style="position:relative; height:65%; overflow:auto" align="left">
                    ${file1}
                </div>
                <div class="col-lg-6" style="position:relative; height:65%; overflow:auto" align="left">
                    ${file2}
                </div>
            </div>
        </div>

    </div>
    <!-- /.container -->

</div>

<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('result'));

    // 指定图表的配置项和数据
    var option = {
        title : {
            text: '文本比对结果',
            subtext: '重复比例：${duplicateWordRate}％',
            x:'left',
            textStyle: {
                color: 'white',
                fontStyle: 'normal',
                fontWeight: 'bolder',
                fontFamily: 'sans-serif',
                fontSize: 36,
            },
            subtextStyle: {
                color: 'white',
                fontStyle: 'normal',
                fontWeight: 'normal',
                fontFamily: 'sans-serif',
                fontSize: 24,
            }
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            top:'88',
            left: 'left',
            data: ['抄袭比例','原创比例'],
            textStyle: {
                color: 'white',
                fontStyle: 'normal',
                fontWeight: 'bolder',
                fontFamily: 'sans-serif',
                fontSize: 12,
            }
        },
        series : [
            {
                name: '原创/抄袭字数',
                type: 'pie',
                radius : '80%',
                center: ['35%', '50%'],
                data:[
                    {value:${duplicateLength}, name:'抄袭比例'},
                    {},
                    {value:${OriginalLength}, name:'原创比例'}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 24,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(255, 0, 0, 16)'
                    }
                }
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
</script>

<!-- /.intro-header -->

<!-- Page Content -->

<a  name="services"></a>
<!-- /.content-section-a -->
<!-- /.content-section-b -->
<!-- /.content-section-a -->
<a  name="contact"></a>
<!-- /.banner -->
<!-- Footer --><!-- jQuery -->
<script src="js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="js/bootstrap.min.js"></script>

</body>

</html>
