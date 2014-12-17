<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
  <title>Login Page</title>
  <%@include file="mainHead.jsp"%>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">
        <span class="glyphicon glyphicon-barcode"></span>
        Green Receipt
      </a>
    </div>
  </div>
</nav>
<%@include file="exteriorNavBar.jsp"%>
<div class="container">
  <c:url value="/login" var="loginUrl"/>
  <%--<form class="form-signin" role="form" action="${loginUrl}" method="post">--%>
    <%--<c:if test="${param.error != null}">--%>
      <%--<div class="alert alert-danger" role="alert">--%>
        <%--Invalid username and/or password.--%>
      <%--</div>--%>
    <%--</c:if>--%>
    <%--<c:if test="${param.logout != null}">--%>
      <div class="alert alert-success" role="alert">
        You have been logged out.
      </div>
    <%--</c:if>--%>
    <h2 class="form-signin-heading">Please, Sign In</h2>
    <input type="text" name="username" class="form-control" placeholder="Username" required="" autofocus="">
    <input type="password" name="password" class="form-control" placeholder="Password" required="">
    <input type="hidden"
           name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
    <div>
      Don't have an account?<a href="/createAccountForm"> Create one here!</a>
    </div>
    <button class="btn btn-lg btn-success btn-block" type="submit">Sign in</button>
  </form>
</div>
</body>
</html>