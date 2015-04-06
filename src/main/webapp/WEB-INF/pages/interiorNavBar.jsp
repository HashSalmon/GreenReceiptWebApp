<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="/dashboard">
        <span class="glyphicon glyphicon-barcode"></span>
        Green Receipt
      </a>
    </div>
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <p class="navbar-text navbar-right">Welcome ${sessionScope.firstname}&nbsp;${sessionScope.lastname}&nbsp;&nbsp;|&nbsp;&nbsp;<a href="/login?logout">Logout</a>&nbsp;&nbsp;</p>
      <ul class="nav navbar-nav">
        <li class="${dashboardActive}"><a href="/dashboard">Dashboard <span class="sr-only">(current)</span></a></li>
        <li class="dropdown ${receiptsActive}">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">View Receipts <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="/receipts">List View</a></li>
            <li><a href="/receiptsMap">Map View</a></li>
          </ul>
        </li>
        <li class="${budgetActive}"><a href="/budget">Budget </a></li>
        <li class="dropdown ${reportsActive}">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Reports <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="/trending">Trending</a></li>
            <li><a href="/category">Category</a></li>
          </ul>
        </li>
        <li class="dropdown ${settingsActive}">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Settings <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="/manageCards">Manage Cards</a></li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</nav>

