<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
  <title>Budget</title>
  <%@include file="mainHead.jsp"%>
</head>
<body>
<%@include file="interiorNavBar.jsp"%>
<div class="container">
  <div class="row">
    <div class="col-md-6 col-md-offset-3">
      <div class="panel panel-defualt">
        <div class="panel-body">
          <div class="centerText">
            <h2>Budget</h2>
          </div>
          <c:forEach items="${budget.budgetItems}" var="item">
            <div style="margin-top: 60px;">
              <table style="width: 100%; margin-bottom: 5px;">
                <tr>
                  <td>${item.category}</td>
                  <td class="centerText">Amount Used: ${item.amountUsedCurrency}</td>
                  <td class="rightText">Limit: ${item.amountAllowedCurrency}</td>
                </tr>
              </table>
              <c:set value="${100}" var="max"/>
              <div class="progress">
                <div class="progress-bar progress-bar-${item.status}" role="progressbar" aria-valuenow="${item.value}" aria-valuemin="0"
                     aria-valuemax="100" style="width: ${item.value <= 100 ? item.value : max}%;">
                  ${item.percentUsedString}
                </div>
              </div>
            </div>
          </c:forEach>
          <div class="centerText">
            <div class="btn btn-primary" onclick="window.location.href='/editBudget'">
              Edit Budget
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>