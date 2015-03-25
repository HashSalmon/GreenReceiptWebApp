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
        <table>
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
        </table>
      </div>
    </div>
  </div>
</div>
</body>

<script>


</script>

</html>



