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
        <li><a href="#" data-toggle="modal" data-target="#tutorialModal">Tutorial</a></li>
      </ul>
    </div>
  </div>
</nav>

<div class="modal fade" id="tutorialModal">
  <div class="modal-dialog">
    <div class="modal-content" style="width: 1080px; margin-left: -250px;">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Tutorial</h4>
      </div>
      <div class="modal-body">
        <iframe id="player" type="text/html" width="1050" height="600"
                src="http://www.youtube.com/embed/yc9zIXMZBmw"
                frameborder="0"></iframe>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal" id="tutorialClose">Close</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div>

<script type="text/javascript">
  $(function(){
    $('.close').click(function(){
      $('iframe').attr('src', $('iframe').attr('src'));
    });
  });

  $(function(){
    $('#tutorialClose').click(function(){
      $('iframe').attr('src', $('iframe').attr('src'));
    });
  });
</script>

