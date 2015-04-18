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
            <div class="panel panel-default col-sm-6 col-sm-offset-3">
                <div class="panel-body">
                    <form:form action="createAccountForm" commandName="createAccountObject" class="form-signin" method="post" cssStyle="background-color: #ffffff;">
                        <h2 class="form-signin-heading">Please enter your information</h2>
                        <c:if test="${error != null}">
                            <div class="alert alert-danger" role="alert">
                                    ${error}
                            </div>
                        </c:if>
                        <form:input class="form-control" path="Email" placeholder="Email"/>
                        <form:errors cssStyle="color: #ff0000;" path="Email"/>

                        <form:input class="form-control" path="FirstName" placeholder="First Name" autofocus="" cssStyle="margin-bottom: 10px;"/>
                        <form:errors cssStyle="color: #ff0000;" path="FirstName"/>

                        <form:input class="form-control" path="LastName" placeholder="Last Name"/>
                        <form:errors cssStyle="color: #ff0000;" path="LastName"/>

                        <form:input type="password" class="form-control" path="Password" placeholder="Password"/>
                        <form:errors cssStyle="color: #ff0000;" path="Password"/>

                        <form:input type="password" class="form-control" path="ConfirmPassword" placeholder="Confirm Password"/>
                        <form:errors cssStyle="color: #ff0000;" path="ConfirmPassword"/>

                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button class="btn btn-lg btn-success btn-block" type="submit">Create Account</button>
                    </form:form>
                </div>
            </div>
        </div>
    </body>
</html>