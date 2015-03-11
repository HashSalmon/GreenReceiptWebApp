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
  <c:if test="${errorMessage != null}">
    <div class="alert alert-danger centerText">
        ${errorMessage}
    </div>
  </c:if>
  <div class="row">

    <div class="col-sm-12 col-md-6">
      <div class="panel panel-default">
        <div class="panel-body">
          <%--Current Budget--%>
          <div id="chart2"></div>
        </div>
      </div>
    </div>

    <div class="col-md-6">
      <div class="panel panel-defualt">
        <div class="panel-body">
          <c:choose>
            <c:when test="${createNew}">
            <!-- do nothing -->
            </c:when>
            <c:otherwise>
              <div class="col-md-12" style="text-align: right;">
                <div class="btn-group">
                  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                    Options <span class="caret"></span>
                  </button>
                  <ul class="dropdown-menu" role="menu">
                    <li><a href="#" data-toggle="modal" data-target="#myModal">Add Category</a></li>
                    <li><a href="/editBudget">Edit Budget</a></li>
                  </ul>
                </div>
              </div>
            </c:otherwise>
          </c:choose>
          <div class="col-md-12" style="text-align: center;">
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
            <div class="centerText">
              <button class="btn btn-danger" onclick="deleteCategory(${item.id})">Delete</button>
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

              <div class="modal fade" id="myModal">
                <div class="modal-dialog">
                  <div class="modal-content">
                    <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                      <h4 class="modal-title">Add a category to track in your budget</h4>
                    </div>
                    <div class="modal-body">
                      <label for="categoryName">Category</label>
                      <input type="text" name="categoryName" id="categoryName" />
                      <label for="amountAllowed">Limit</label>
                      <input type="text" name="amountAllowed" id="amountAllowed"/>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                      <button type="button" class="btn btn-primary" onclick="addCategory()">Save changes</button>
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

<c:forEach var="budgetItem" items="${budget.budgetItems}">
  <div class="modal fade" id="${budgetItem.category.name}Modal">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title">${budgetItem.category.name} Budget Item Details</h4>
        </div>
        <div class="modal-body">
          <div><strong>${budgetItem.amountUsedCurrency}</strong> of <strong>${budgetItem.amountAllowedCurrency}</strong> used</div>
          <c:choose>
            <c:when test="${budgetItem.status == 'success'}">
              <div class="budgetItemDetails">Status: <span class="green">Under Budget</span></div>
            </c:when>
            <c:when test="${budgetItem.status == 'warning'}">
              <div class="budgetItemDetails">Status: <span class="yellow">Close To Limit</span></div>
            </c:when>
            <c:when test="${budgetItem.status == 'danger'}">
              <div class="budgetItemDetails">Status: <span class="red">Over Budget</span></div>
            </c:when>
          </c:choose>

          <div class="progress">
            <div class="progress-bar progress-bar-${budgetItem.status}" role="progressbar" aria-valuenow="${budgetItem.value}" aria-valuemin="0"
                 aria-valuemax="100" style="width: ${budgetItem.value <= 100 ? budgetItem.value : max}%;">
                ${budgetItem.percentUsedString}
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div>
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
  </div>
</c:forEach>

<script type="text/javascript">
  function addCategory() {
    categoryName = $('#categoryName').val();
    amountAllowed = $('#amountAllowed').val();

    window.location.href='addBudgetItem?categoryName=' + categoryName + '&amountAllowed=' + amountAllowed;
  }

  function deleteCategory(id) {
    confirmed = confirm("Are you sure that you want to delete this category");
    if (confirmed) {
      window.location.href='deleteBudgetItem?id=' + id;
    }
  }

  function createChart2() {
    $("#chart2").kendoChart({
      title: {
        position: "bottom",
        text: "Budget Distribution"
      },
      legend: {
        visible: false
      },
      chartArea: {
        background: ""
      },
      seriesDefaults: {
        labels: {
          visible: true,
          background: "transparent",
          template: "#= category #: \n #= value#%"
        }
      },
      series: [{
        type: "pie",
        startAngle: 150,
        data: ${pieChartJson == null ? "[]" : pieChartJson}
      }],
      tooltip: {
        visible: true,
        format: "{0}%"
      },
      seriesClick: pieChartClick
    });
  }

  function pieChartClick(e) {
    $('#' + e.category + 'Modal').modal('show');
  }
  $(document).ready(createChart2);
  $(document).bind("kendo:skinChange", createChart2);
</script>
</body>
</html>