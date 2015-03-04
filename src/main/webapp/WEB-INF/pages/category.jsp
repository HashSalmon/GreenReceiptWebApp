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
  <c:if test="${error != null}">
    <div class="alert alert-danger centerText">
        ${error}
    </div>
  </c:if>
    <c:if test="${noResults != null}">
      <div class="alert alert-info centerText">
          ${noResults}
      </div>
    </c:if>
    <c:if test="${startDateError != null}">
    <div class="alert alert-danger centerText">
        ${startDateError}
    </div>
    </c:if>
    <c:if test="${endDateError != null}">
    <div class="alert alert-danger centerText">
        ${endDateError}
    </div>
    </c:if>
  <div class="row">
    <form:form action="categoryDateForm" modelAttribute="categoryReportDates" method="post">
      <div class="col-md-6">
        <label for="startDatePicker">Start Date:</label><input id="startDatePicker" name="startDate" value="${startDate}" style="width:200px;" />
        <label for="endDatePicker">End Date:</label><input id="endDatePicker" name="endDate" value="${endDate}" style="width:200px" />
      </div>
      <div class="col-md-6">
        <button class="btn btn-default">Submit</button>
      </div>
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form:form>
    <div class="col-md-12">
      <div class="panel panel-default">
        <div class="panel-body">
          <div id="chart"></div>
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

  $(document).ready(function() {
    // create DatePicker from input HTML element
    $("#startDatePicker").kendoDatePicker({format: "yyyy-MM-dd"});
    $("#endDatePicker").kendoDatePicker({format: "yyyy-MM-dd"});
  });
</script>
</body>
</html>