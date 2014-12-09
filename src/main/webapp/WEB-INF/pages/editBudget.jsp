<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<html>
<head>
  <title>Edit Budget</title>
  <%@include file="mainHead.jsp"%>
</head>
<body>
<%@include file="exteriorNavBar.jsp"%>
<div class="container">
  <form:form action="editBudgetForm" modelAttribute="editBudget" class="form-signin" method="post">
    <c:forEach items="${editBudget.budgetItems}" var="budgetItem" varStatus="index">
      <label>Category:&nbsp;</label><c:out value="${budgetItem.category}"/><br/>
      <input class="form-control" name="budgetItems[${index.index}].amountAllowed" value="${budgetItem.amountAllowed}" style="margin-bottom: 10px;"/>
    </c:forEach>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button class="btn btn-lg btn-success btn-block" type="submit">Set Budget</button>
  </form:form>
</div>
</body>
</html>