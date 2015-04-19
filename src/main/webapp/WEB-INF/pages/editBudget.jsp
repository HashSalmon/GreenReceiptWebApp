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
<%@include file="interiorNavBar.jsp"%>
<div class="container">
  <c:if test="${errorMessage != null}">
    <div class="alert alert-danger centerText">
      ${errorMessage}
    </div>
  </c:if>
  <div class="panel panel-default col-sm-4 col-sm-offset-4">
    <div class="panel-body">
      <form:form action="editBudgetForm" modelAttribute="editBudget" class="form-signin" method="post">
        <c:forEach items="${editBudget.budgetItems}" var="budgetItem" varStatus="index">
          <label>Category:&nbsp;</label><c:out value="${budgetItem.category.name}"/><br/>
          <input type="hidden" name="budgetItems[${index.index}].id" value="${budgetItem.id}">
          <input class="form-control" name="budgetItems[${index.index}].amountAllowed" value="${budgetItem.amountAllowed}" style="margin-bottom: 10px;"/>
        </c:forEach>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button class="btn btn-lg btn-success btn-block" type="submit">Set Budget</button>
      </form:form>
    </div>
  </div>
</div>
</body>
</html>