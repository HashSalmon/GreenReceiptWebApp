<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<html>
<head>
  <title>Create Budget</title>
  <%@include file="mainHead.jsp"%>
  <style>html { font-size: 12px; font-family: Arial, Helvetica, sans-serif; }</style>
  <link rel="stylesheet" href="http://cdn.kendostatic.com/2014.3.1316/styles/kendo.common.min.css" />
  <link rel="stylesheet" href="http://cdn.kendostatic.com/2014.3.1316/styles/kendo.default.min.css" />
  <link rel="stylesheet" href="http://cdn.kendostatic.com/2014.3.1316/styles/kendo.dataviz.min.css" />
  <link rel="stylesheet" href="http://cdn.kendostatic.com/2014.3.1316/styles/kendo.dataviz.default.min.css" />

  <script src="http://cdn.kendostatic.com/2014.3.1316/js/jquery.min.js"></script>
  <script src="http://cdn.kendostatic.com/2014.3.1316/js/kendo.all.min.js"></script>
  <style scoped>
    .demo-section {
      width: 350px;
      min-height: 140px;
    }
    .demo-section h2 {
      font-weight: normal;
    }
    .demo-section label {
      display: inline-block;
      margin: 15px 0 5px 0;
    }
    .demo-section select {
      width: 350px;
    }
    #get {
      float: right;
      margin: 25px auto 0;
    }
  </style>
</head>
<body>
<%@include file="exteriorNavBar.jsp"%>
<div class="container">

    <div class="row">
      <div class="col-md-6 col-md-offset-4">
        <label for="multiselect">Select categories you wish to track with your budget:</label>
        <select id="multiselect" multiple="multiple" class="categoriesSelect">
          <c:forEach items="${categories}" var="category">
            <option>${category.name}</option>
          </c:forEach>
        </select>
        <button class="btn btn-success categoriesSelectBtn" id="get">Next</button>
      </div>
    </div>


  <form:form action="selectCategoriesForm" modelAttribute="categoryList" class="form-signin" method="post" cssStyle="display: none;">
    <input name="categoriesForBudget" id="categoriesFormInput" type="text"/>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button id="submitButton" class="btn btn-lg btn-success btn-block" type="submit">Set Budget</button>
  </form:form>
</div>
<script>
  $(document).ready(function() {
    // create MultiSelect from select HTML element
    var required = $("#multiselect").kendoMultiSelect().data("kendoMultiSelect");

    $("#get").click(function() {
      $("#categoriesFormInput").val(required.value());
      $("#submitButton").click();
    });
  });
</script>
</body>
</html>