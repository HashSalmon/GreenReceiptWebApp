<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
  <title>Category Report</title>
  <%@include file="mainHead.jsp"%>
</head>
<body>
<%@include file="interiorNavBar.jsp"%>
<div class="container">



  <div class="col-sm-12">
    <div class="panel panel-default">
      <div class="panel-body">
        <div class="label label-success chartLabel">
          ${category} Category Items
          <div style="float: right;">
            <i class="glyphicon glyphicon-new-window export" onclick="generate()"></i>
          </div>
        </div>
        <table class="table table-striped">
          <tr>
            <th>
              Item Name
            </th>
            <th>
              Amount
            </th>
          </tr>
        <c:forEach var="receiptItem" items="${receiptItems}">
          <tr>
            <td>
              ${receiptItem.itemName}
            </td>
            <td>
              ${receiptItem.price}
            </td>
          </tr>
        </c:forEach>
          <tr>
            <th>Count</th>
            <th>${numItems}</th>
          </tr>
          <tr>
            <th>Total</th>
            <th>${total}</th>
          </tr>
        </table>
      </div>
    </div>
  </div>
</div>
</body>

<script>

  function generate() {
    confirmed = confirm("Are you sure that you want to export the receipts for these items?");
    if (confirmed) {
      window.open("/downloadPDF?categoryId=${categoryId}&startDate=${sessionScope.CategoryReportStartDate}&endDate=${sessionScope.CategoryReportEndDate}");
    }
  }

</script>

</html>



