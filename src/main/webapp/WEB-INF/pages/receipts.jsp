<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
  <title>Receipts</title>
  <%@include file="mainHead.jsp"%>
</head>
<body>
<%@include file="interiorNavBar.jsp"%>
<div class="container">
  <div class="panel panel-default">
    <div class="panel-heading">
      <h4>Receipts List</h4>

    </div>
    <div class="panel-body">
      <form:form action="numReceiptsForm" modelAttribute="receiptViewAmount" method="post" id="numReceiptsForm">
        <div class="col-sm-4">
          <label for="numReceipts">Select number of receipts to view:</label>
          <select id="numReceipts" name="numReceipts" class="form-control" onchange="submitNumReceiptsForm()">
            <option value="25" ${sessionScope.numReceipts == "25" ? 'selected' : ''}>Up to 25</option>
            <option value="100" ${sessionScope.numReceipts == "100" ? 'selected' : ''}>Up to 100</option>
            <option value="1000" ${sessionScope.numReceipts == "1000" ? 'selected' : ''}>Up to 1000</option>
            <option value="10000" ${sessionScope.numReceipts == "10000" ? 'selected' : ''}>Up to 10000</option>
            <option value="100000" ${sessionScope.numReceipts == "100000" ? 'selected' : ''}>Up to 100000</option>
          </select>
          <input type="hidden" name="view" value="/receipts"/>
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </div>
      </form:form>
      <div class="col-md-12">
        <div id="grid"></div>
      </div>
      <div id="receiptRow" style="display: none;"></div>
    </div>
  </div>
</div>
</body>
<script>

  function submitNumReceiptsForm() {
    $("#numReceiptsForm").submit();
  }

  function onChange(arg) {
    var selected = $.map(this.select(), function(item) {
      $('#receiptRow').html($(item).html());
      window.location.href = "/receipt?receiptId=" + $("#receiptRow td[role=gridcell]").first().text();
    });

    kendoConsole.log("Selected: " + selected.length + " item(s), [" + selected.join(", ") + "]");
  }

  $(document).ready(function () {
    var receipts = [];
    <c:forEach var="receipt" items="${receipts}" varStatus="status">
      var id = "${receipt.id}";
      var store = "${receipt.store.company.name}";
      var total = "${receipt.total}";
      var returnDate = "${receipt.returnDateString}";
      receipts.push({Id: id, Store: store, Total: total, ReturnDate: returnDate});
    </c:forEach>

    $("#grid").kendoGrid({
      dataSource: {
        data: receipts,
        pageSize: 10
      },
      height: 525,
      change: onChange,
      selectable: "row",
      groupable: true,
      sortable: true,
      columnMenu: true,
      pageable: {
        refresh: true,
        pageSizes: true,
        buttonCount: 5
      }, filterable: {
        extra: false,
        operators: {
          string: {
            startswith: "Starts with",
            eq: "Is equal to",
            neq: "Is not equal to",
            contains: "Contains"
          }
        }
      },
      columns: [{
        field: "Id",
        title: "Id",
        width: 100
      },{
        field: "Store",
        title: "Store Name",
        filterable: true,
        width: 400
      }, {
        field: "Total",
        title: "Total"
      }, {
        field: "ReturnDate",
        title: "Return Date"
      }]
    });
  });

  $(document).ready(function () {
    $('.k-icon span').each().css("margin-top", "10px;");
  });
</script>

</html>



