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
                The last day to return these items is coming up soon!<br> ${receipt.returnDate}
              </div>
            </c:if>
            <c:if test="${exchange != null && exchange != ''}">
              <div class="alert alert-warning centerText">
                Another user has recently purchased this item for less than you did!<br>
                You may want to consider returning this item.<br><br>
                From:  Amazon<br>
                Price: $450.00
              </div>
            </c:if>
            <table class="receiptTable">
              <tr class="receiptLine">
                <td colspan="3" class="centerText receiptHeader"><h2>${receipt.store}</h2></td>
              </tr>
              <c:forEach items="${receipt.items}" var="item" varStatus="status">
                <tr>
                  <td class="receiptItem" style="${status.first ? 'padding-top: 1em;' : ''}">${item.name}:</td>
                  <td class="receiptItem" style="${status.first ? 'padding-top: 1em;' : ''}"></td>
                  <td class="rightText receiptItem" style="${status.first ? 'padding-top: 1em;' : ''}">${item.price}</td>
                </tr>
              </c:forEach>
              <tr>
                <td>
                  Total:
                </td>
                <td></td>
                <td class="rightText">${receipt.total}</td>
              </tr>
            </table>
          </div>
        </div>
        <button id="sendEmail" class="btn btn-success col-md-offset-4" onclick="sendEmail();">Send Email</button>
      </div>
    </div>
  </div>
</body>
</html>
<script type="text/javascript">

  function sendEmail() {
    $.ajax({
      url : "/sendEmail",
      data : "receiptId=" + "${receipt.receiptId}",
      type : "GET",

      success : function(response) {
        alert( response );
      },
      error : function(xhr, status, error) {
        alert(xhr.responseText);
      }
    });
  }

</script>