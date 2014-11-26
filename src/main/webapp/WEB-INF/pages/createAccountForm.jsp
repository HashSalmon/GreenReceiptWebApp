<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
    <head>
        <title>Sign Up Page</title>
        <%@include file="mainHead.jsp"%>
    </head>
    <body>
    <%@include file="exteriorNavBar.jsp"%>
        <div class="container">
            <form:form action="createAccountForm" commandName="createAccountObject" class="form-signin" method="post">
                <h2 class="form-signin-heading">${message}</h2>
                <form:input class="form-control" path="firstName" placeholder="First Name" autofocus="" cssStyle="margin-bottom: 10px;"/>
                <form:errors cssStyle="color: #ff0000;" path="firstName"/>

                <form:input class="form-control" path="lastName" placeholder="Last Name"/>
                <form:errors cssStyle="color: #ff0000;" path="lastName"/>

                <form:input class="form-control" path="username" placeholder="Username"/>
                <form:errors cssStyle="color: #ff0000;" path="username"/>

                <form:input type="password" class="form-control" path="password" placeholder="Password"/>
                <form:errors cssStyle="color: #ff0000;" path="password"/>

                <form:input class="form-control" path="email" placeholder="Email"/>
                <form:errors cssStyle="color: #ff0000;" path="email"/>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="btn btn-lg btn-success btn-block" type="submit">Create Account</button>
            </form:form>
        </div>
    </body>
</html>