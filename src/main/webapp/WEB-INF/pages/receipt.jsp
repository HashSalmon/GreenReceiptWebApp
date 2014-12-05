<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
  <title>Receipt</title>
  <%@include file="mainHead.jsp"%>
</head>
<body>
<%@include file="interiorNavBar.jsp"%>
  <div class="container">
    <div class="row">
      <div class="col-md-4 col-md-offset-4">
        <div class="panel panel-defualt">
          <div class="panel-body">
            <c:if test="${receipt.returnDate != null && receipt.returnDate != ''}">
              <div class="alert alert-danger centerText">
                Your Return Date is coming up soon!<br> ${receipt.returnDate}
              </div>
            </c:if>
            <table class="receiptTable">
              <tr class="receiptLine">
                <td colspan="3" class="centerText"><h2>${receipt.store}</h2></td>
              </tr>
              <c:forEach items="${receipt.items}" var="item">
                <tr>
                  <td>${item.name}</td>
                  <td></td>
                  <td class="rightText">${item.price}</td>
                </tr>
              </c:forEach>
              <tr>
                <td>
                  Total
                </td>
                <td></td>
                <td class="rightText"> ${receipt.total}</td>
              </tr>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>
<script type="text/javascript">
  $('.nav.navbar-nav > li').on('click', function(e) {
    $('.nav.navbar-nav > li').removeClass('active');
    $(this).addClass('active');
  });
</script>