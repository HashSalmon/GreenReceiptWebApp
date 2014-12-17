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
  <div class="col-md-12">
    <div id="grid"></div>
  </div>
  <div id="receiptRow" style="display: none;"></div>
</div>
</body>
<script>
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
      var returnDate = "${receipt.returnDate}";
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



