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
</div>
</body>
<script src="<c:url value="/resources/js/jquery-1.11.1.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery.min.js" />"></script>
<script src="<c:url value="/resources/js/kendo.dataviz.min.js" />"></script>
<script src="<c:url value="/resources/js/kendo.web.min.js" />"></script>
<script src="<c:url value="/resources/js/kendo.all.min.js" />"></script>
<script src="<c:url value="/resources/js/kendo.timezones.min.js" />"></script>
<script src="<c:url value="/resources/js/kendo-dojo.js" />"></script>
<script src="<c:url value="/resources/js/console.js" />"></script>
<script src="<c:url value="/resources/js/prettify.js" />"></script>
<script>
  $(document).ready(function () {
    var receipts = [];
    <c:forEach var="receipt" items="${receipts}" varStatus="status">
      var store = "${receipt.store}";
      var total = "${receipt.total}";
      var returnDate = "${receipt.returnDate}";
      receipts.push({Store: store, Total: total, ReturnDate: returnDate});
    </c:forEach>
//    alert(receipts);

    $("#grid").kendoGrid({
      dataSource: {
        data: receipts,
        pageSize: 20
      },
      height: 550,
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
        field: "Store",
        title: "Store Name",
        filterable: true,
        width: 200
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

<%--<script>--%>
  <%--$(document).ready(function() {--%>
    <%--$("#grid").kendoGrid({--%>
      <%--dataSource: {--%>
        <%--data: createRandomData(50),--%>
        <%--schema: {--%>
          <%--model: {--%>
            <%--fields: {--%>
              <%--City: { type: "string" },--%>
              <%--Title: { type: "string" },--%>
              <%--BirthDate: { type: "date" }--%>
            <%--}--%>
          <%--}--%>
        <%--},--%>
        <%--pageSize: 15--%>
      <%--},--%>
      <%--height: 550,--%>
      <%--scrollable: true,--%>
      <%--filterable: {--%>
        <%--extra: false,--%>
        <%--operators: {--%>
          <%--string: {--%>
            <%--startswith: "Starts with",--%>
            <%--eq: "Is equal to",--%>
            <%--neq: "Is not equal to"--%>
          <%--}--%>
        <%--}--%>
      <%--},--%>
      <%--pageable: true,--%>
      <%--columns: [--%>
        <%--{--%>
          <%--title: "Name",--%>
          <%--width: 160,--%>
          <%--filterable: false,--%>
          <%--template: "#=FirstName# #=LastName#"--%>
        <%--},--%>
        <%--{--%>
          <%--field: "City",--%>
          <%--width: 130,--%>
          <%--filterable: {--%>
            <%--ui: cityFilter--%>
          <%--}--%>
        <%--},--%>
        <%--{--%>
          <%--field: "Title",--%>
          <%--filterable: {--%>
            <%--ui: titleFilter--%>
          <%--}--%>
        <%--},--%>
        <%--{--%>
          <%--field: "BirthDate",--%>
          <%--title: "Birth Date",--%>
          <%--format: "{0:MM/dd/yyyy HH:mm tt}",--%>
          <%--filterable: {--%>
            <%--ui: "datetimepicker"--%>
          <%--}--%>
        <%--}--%>
      <%--]--%>
    <%--});--%>
  <%--});--%>

  <%--function titleFilter(element) {--%>
    <%--element.kendoAutoComplete({--%>
      <%--dataSource: titles--%>
    <%--});--%>
  <%--}--%>

  <%--function cityFilter(element) {--%>
    <%--element.kendoDropDownList({--%>
      <%--dataSource: cities,--%>
      <%--optionLabel: "--Select Value--"--%>
    <%--});--%>
  <%--}--%>

<%--</script>--%>