<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Trending Report</title>
    <%@include file="mainHead.jsp"%>
</head>
<body>
<%@include file="interiorNavBar.jsp"%>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4>Trending Report</h4>
                </div>
                <div class="panel-body">
                    <div id="chart"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    function createChart() {
        var dataset = ${dataset};
        var highlighted = ${highlighted};
        $("#chart").kendoChart({
            title: {
                text: "Trending By Months"
            },
            legend: {
                position: "top"
            },
            seriesDefaults: {
                type: "column"
            },
            series: [
                {
                    name: 'History',
                    data: dataset,
                    color: '#9de219'
                },
                {
                    name: 'Projected',
                    data: highlighted,
                    color: '#033939'
                }
            ],
            valueAxis: {
                line: {
                    visible: false
                },
                axisCrossingValue: 0
            },
            categoryAxis: {
                categories: ${months},
                line: {
                    visible: false
                }
            },
            tooltip: {
                visible: true,
                format: "{0}%",
                template: "#= kendo.format('{0:C}',value) #"
            }
        });
    }

    $(document).ready(createChart);
    $(document).bind("kendo:skinChange", createChart);;
</script>
</body>
</html>