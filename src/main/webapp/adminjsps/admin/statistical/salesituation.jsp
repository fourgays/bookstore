<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="UTF-8" />
    <script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
    <script src="https://code.highcharts.com/highcharts.js"></script>
</head>
<body>
<div id="container" style="width: 550px; height: 400px; margin: 0 auto"></div>
<script language="JavaScript">
    $(document).ready(function() {
        var title = {
            text: '最近七日销售量'
        };
        var subtitle = {
            text: ''
        };
        var xAxis = {
            categories: []
        };
        var yAxis = {
            title: {
                text: '销售量(元)'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        };

        var tooltip = {
            valueSuffix: '\xB0C'
        };

        var legend = {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        };

        var series =  [{
                data: []
            }];
        $.ajax({
            type:"GET",
            dataType:"JSON",
            contentType : "application/json",
            async:false,
            url:"<c:url value='/admin/StatisticalServlet?method=salesituation'/>",
            success:function (result) {
                for(var i = 0;i<7;i++){
                    var time = new Date(result[i].ordertime);
                    xAxis.categories[i] = time.getFullYear()+"年"+(time.getMonth()+1)+"月"+time.getDate()+"日";
                    series[0].data[i]=result[i].total;
                }
            }
        });
        var json = {};

        json.title = title;
        json.subtitle = subtitle;
        json.xAxis = xAxis;
        json.yAxis = yAxis;
        json.tooltip = tooltip;
        json.legend = legend;
        json.series = series;

        $('#container').highcharts(json);
    });
</script>
</body>
</html>