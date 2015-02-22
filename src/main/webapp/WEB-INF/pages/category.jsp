<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
  <title>Category Report</title>
  <%@include file="mainHead.jsp"%>
</head>
<body>
<%@include file="interiorNavBar.jsp"%>
<div class="container">
  <div class="row">
    <div class="col-md-12">
      <div class="panel panel-default">
        <div class="panel-body">
          <div id="chart"></div>
        </div>
      </div>
    </div>
  </div>
</div>
<script>
  function createChart() {
    $("#chart").kendoChart({
      title: {
        text: "Totals By Category"
      },
      legend: {
        visible: false
      },
      seriesDefaults: {
        type: "bar"
      },
      series: [{
        name: "Total Amount",
        data: ${categoryReportValues == null ? "[]" : categoryReportValues},
        color: "#9de219"
      }],
      valueAxis: {
        max: ${categoryReportTotal == null ? 100 : categoryReportTotal},
        line: {
          visible: false
        },
        minorGridLines: {
          visible: true
        }
      },
      categoryAxis: {
        categories: ${categoryReportNames == null ? "[]" : categoryReportNames},
        majorGridLines: {
          visible: false
        }
      },
      tooltip: {
        visible: true,
        format: "/$/{0}",
        template: "#= series.name #: #= kendo.format('{0:C}',value) #"
      }
    });
  }

  $(document).ready(createChart);
  $(document).bind("kendo:skinChange", createChart);
</script>
</body>
</html>