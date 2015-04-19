<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<html>
<head>
  <title>Create Budget</title>
  <%@include file="mainHead.jsp"%>
</head>
<body>
<%@include file="exteriorNavBar.jsp"%>
<div class="container">
  <h2 class="centerText">Enter in your desired limit for each category in your budget</h2>
  <form:form action="createBudgetForm" modelAttribute="createBudget" class="form-signin" method="post">
    <c:forEach items="${createBudget.budgetItems}" var="budgetItem" varStatus="index">
      <label>Category:&nbsp;</label><c:out value="${budgetItem.category.name}"/><br/>
      <input type="hidden" name="budgetItem.category.name" value="${budgetItem.category.name}">
      <input class="form-control" name="budgetItems[${index.index}].amountAllowed" value="" style="margin-bottom: 10px;"/>
    </c:forEach>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button class="btn btn-lg btn-success btn-block" type="submit">Create Budget</button>
  </form:form>
</div>
</body>
</html>