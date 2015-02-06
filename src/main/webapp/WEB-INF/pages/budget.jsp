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
                  <td>${item.category.name}</td>
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
          <c:choose>
            <c:when test="${createNew}">
              <div class="centerText">
                <div class="btn btn-primary" onclick="window.location.href='/selectCategories'">
                  Create Budget
                </div>
              </div>
            </c:when>
            <c:otherwise>
              <div class="centerText">
                <div class="btn btn-primary" onclick="window.location.href='/editBudget'">
                  Edit Budget
                </div>
              </div>

              <button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
                Launch demo modal
              </button>


              <div class="modal fade" id="myModal">
                <div class="modal-dialog">
                  <div class="modal-content">
                    <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                      <h4 class="modal-title">Modal title</h4>
                    </div>
                    <div class="modal-body">
                      <p>One fine body&hellip;</p>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                      <button type="button" class="btn btn-primary">Save changes</button>
                    </div>
                  </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
              </div><!-- /.modal -->



            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>