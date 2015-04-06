<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
  <title>Manage Cards</title>
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
  <div class="row">

    <div class="col-sm-12 col-md-6 col-md-offset-3">
      <div class="panel panel-default">
        <div class="panel-heading">

          <div>
            Manage Cards
            <div class="floatRight btn btn-success btn-xs" data-toggle="modal" data-target="#addCardModal">
              Add Card
            </div>
          </div>

        </div>
        <div class="panel-body">

          <%--<div class="panel panel-info">--%>
            <%--<div class="panel-heading">--%>
              <%--<div>--%>
                <%--Credit Card 1--%>
                <%--<div class="btn-group floatRight">--%>
                  <%--<button type="button" class="btn btn-primary btn-xs dropdown-toggle" data-toggle="dropdown" aria-expanded="false">--%>
                    <%--Options <span class="caret"></span>--%>
                  <%--</button>--%>
                  <%--<ul class="dropdown-menu" role="menu">--%>
                    <%--<li><a href="#" data-toggle="modal" data-target="#myModal">Edit Card</a></li>--%>
                    <%--<li><a href="#">Remove Card</a></li>--%>
                  <%--</ul>--%>
                <%--</div>--%>
              <%--</div>--%>
            <%--</div>--%>
            <%--<div class="panel-body">--%>
              <%--<p>Anthony J Wanlass</p>--%>
              <%--<p>0000-0000-0000-0000</p>--%>
            <%--</div>--%>
          <%--</div>--%>

          <c:forEach items="${cards}" var="card" varStatus="status">
            <div class="panel panel-info">
              <div class="panel-heading">
                <div>
                  Credit Card ${status.count}
                  <div class="btn-group floatRight">
                    <button type="button" class="btn btn-primary btn-xs dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                      Options <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu">
                      <li><a href="#" data-toggle="modal" data-target="#cardModal${card.id}">Edit Card</a></li>
                      <li><a href="#" onclick="deleteCard(${card.id})">Remove Card</a></li>
                    </ul>
                  </div>
                </div>
              </div>
              <div class="panel-body">
                <p>${sessionScope.firstname}&nbsp;${sessionScope.lastname}</p>
                <p>xxxx-xxxx-xxxx-${card.lastFour}</p>
              </div>
            </div>
          </c:forEach>

        </div>
      </div>
    </div>

  </div>
</div>

<c:forEach items="${cards}" var="card" varStatus="status">
  <div class="modal fade" id="cardModal${card.id}">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title">Edit the information for this card</h4>
        </div>
        <form:form action="editCard" modelAttribute="cardFormObject" class="form-signin" method="post">
          <div class="modal-body">
            <label for="cardNumber${card.id}">Card Number</label>
            <input type="text" name="cardNumber" id="cardNumber${card.id}" />
          </div>
          <div class="modal-footer">
            <input type="hidden" name="cardId" style="display: none;" value="${card.id}"/>
            <input type="hidden" name="cardHash" value="${card.accountId}"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            <button type="submit" class="btn btn-primary">Save changes</button>
          </div>
        </form:form>
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
  </div>
</c:forEach>

<div class="modal fade" id="addCardModal">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Add a new to your account</h4>
      </div>
      <form:form action="addCard" modelAttribute="cardFormObject" class="form-signin" method="post">
        <div class="modal-body">
          <label for="cardNumber">Card Number</label>
          <input type="text" name="cardNumber" id="cardNumber" />
        </div>
        <div class="modal-footer">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="submit" class="btn btn-primary">Submit</button>
        </div>
      </form:form>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div>

<script type="text/javascript">

  function editCard(id) {
    window.location.href='editCard?id=' + id;
  }

  function deleteCard(id) {
    confirmed = confirm("Are you sure that you want to delete this card?");
    if (confirmed) {
      window.location.href='deleteCard?id=' + id;
    }
  }

</script>
</body>
</html>