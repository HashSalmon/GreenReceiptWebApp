<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
  <title>Dashboard</title>
  <%@include file="mainHead.jsp"%>
</head>
<body>
<%@include file="interiorNavBar.jsp"%>
<div class="container">
  <div class="row">
    <div class="col-md-10">
        <div class="col-sm-12 col-md-6">
          <div class="panel panel-default">
            <div class="panel-body">
              <div class="label label-success chartLabel">Trending Chart</div>
              <div class="label label-success dateRangeLabel">Date Range: ${sessionScope.TrendingReportStartDateDisplay} - ${sessionScope.TrendingReportEndDateDisplay}</div>

            <%--Trending Chart--%>
              <div id="chart1" style="margin-top: 11px;"></div>
            </div>
          </div>
        </div>
        <div class="col-sm-12 col-md-6">
          <div class="panel panel-default">
            <div class="panel-body">
              <div class="label label-success chartLabel">Category Chart</div>
              <div class="label label-success dateRangeLabel">Date Range: ${sessionScope.CategoryReportStartDateDisplay} - ${sessionScope.CategoryReportEndDateDisplay}</div>
              <%--Category Chart--%>
              <div id="chart" style="margin-top: 11px;"></div>
            </div>
          </div>
        </div>
        <div class="col-sm-12 col-md-12">
          <div class="panel panel-default">
            <div class="panel-body">
              <span class="label label-success" style="font-size: large; display: block;">Current Budget</span>
              <%--Current Budget--%>
              <div id="chart2"></div>
            </div>
          </div>
        </div>
        <div class="col-sm-12">
          <div class="panel panel-default">
            <div class="panel-body">
              <span class="label label-success" style="font-size: large;">Recent Purchase Locations</span>
              <%--Recent Purchase Locations<br>--%>
              <div id="map_container" style="width: 100%; height: 350px; margin-top: 11px;"></div>
            </div>
          </div>
        </div>
    </div>
    <div class="col-md-2">
      <div class="panel panel-default">
        <div class="panel-body">
          Notifications <span class="badge">${fn:length(returnNotifications)}</span><br/><br/>
          <ul style="list-style: none; padding-left: 0;">
          <c:forEach var="notification" items="${returnNotifications}">
            <li>
              <span class="glyphicon glyphicon-warning-sign"></span><a href="/receipt?receiptId=${notification.id}">${notification.store.company.name} Return</a>
            </li>
          </c:forEach>
          </ul>
        </div>
      </div>
      <div class="panel panel-default">
        <div class="panel-body">
          Recent Activity<br/><br/>
          <ul style="list-style: none; padding-left: 0;">
            <c:forEach items="${recentReceipts}" var="receipt">
              <li>
                <span class="glyphicon glyphicon-barcode"></span><a href="/receipt?receiptId=${receipt.id}"> ${receipt.store.company.name}</a>
              </li>
            </c:forEach>
          </ul>
        </div>
      </div>
    </div>
  </div>
</div>

<c:set value="${100}" var="max"/>
<c:forEach var="budgetItem" items="${budgetItems}">
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

<script type="text/javascript"
        src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
<script>
  function createChart() {
    $("#chart").kendoChart({
      title: {
        text: "Totals By Category"
      },
      legend: {
        visible: false
      },
      seriesDefaults: {
        type: "bar"
      },
      series: [{
        name: "Total Amount",
        data: ${categoryReportValues == null ? "[]" : categoryReportValues},
        color: "#9de219"
      }],
      valueAxis: {
        max: ${categoryReportTotal == null ? 100 : categoryReportTotal},
        line: {
          visible: false
        },
        minorGridLines: {
          visible: true
        }
      },
      categoryAxis: {
        categories: ${categoryReportNames == null ? "[]" : categoryReportNames},
        majorGridLines: {
          visible: false
        }
      },
      tooltip: {
        visible: true,
        format: "/$/{0}",
        template: "#= series.name #: #= kendo.format('{0:C}',value) #"
      },
      seriesClick: onSeriesClick
    });
  }
  function onSeriesClick(e) {
    window.location.href= "/categoryReceiptItems?category=" + e.category + "&startDate=${sessionScope.CategoryReportStartDate}&endDate=${sessionScope.CategoryReportEndDate}";
  }
  $(document).ready(createChart);
  $(document).bind("kendo:skinChange", createChart);

  function createChart1() {
    var dataset = ${dataset};
    var highlighted = ${highlighted};
    $("#chart1").kendoChart({
      title: {
        text: "Trending By Months"
      },
      legend: {
        position: "top"
      },
      seriesDefaults: {
        type: "column"
      },
      series: [
        {
          name: 'History',
          data: dataset,
          color: '#9de219'
        },
        {
          name: 'Projected',
          data: highlighted,
          color: '#033939'
        }
      ],
      valueAxis: {
        line: {
          visible: false
        },
        axisCrossingValue: 0
      },
      categoryAxis: {
        categories: ${months},
        line: {
          visible: false
        }
      },
      tooltip: {
        visible: true,
        format: "{0}%",
        template: "#= kendo.format('{0:C}',value) #"
      }
    });
  }

  $(document).ready(createChart1);
  $(document).bind("kendo:skinChange", createChart1);

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



  function initialize() {
    var mapOptions = {
      <c:if test="${fn:length(recentReceipts) > 0}">
      zoom: 10,
      center: new google.maps.LatLng(<c:out value="${recentReceipts[0].latitude}"/>, <c:out value="${recentReceipts[0].longitude}"/>)
      </c:if>
    };
    var map = new google.maps.Map(document.getElementById('map_container'),
            mapOptions);
    var markers = [];
    var trafficLayer = new google.maps.TrafficLayer();
    trafficLayer.setMap(map);

    var length = 0;
    <c:forEach items="${recentReceipts}" var="receipt">
      markers.push(["${receipt.store.company.name}", <c:out value="${receipt.latitude}"/>, <c:out value="${receipt.longitude}"/>, "${receipt.currencyTotal}", "${receipt.id}"]);
      length++;
    </c:forEach>
    setMarkers(map, markers, length);
    AutoCenter(markers);
  }

  function setMarkers(map, locations, length) {
    var infowindow = new google.maps.InfoWindow();
    for (var i = 0; i < length; i++) {
      var receipt = locations[i];
      console.log(receipt);

      var myLatLng = new google.maps.LatLng(receipt[1], receipt[2]);
      console.log(myLatLng);
      var marker = new google.maps.Marker({
        position: myLatLng,
        map: map,
        title: receipt[0]
      });
      var contentString = '<div id="content">'+
        '<div id="siteNotice">'+
        '</div>'+
        '<h1 id="firstHeading" class="firstHeading">' + receipt[0] + ': ' + receipt[3] + '</h1>'+
        '<div id="bodyContent"><a href="/receipt?receiptId=' + receipt[4] + '">View Receipt</a>' +
        '</div>'+
        '</div>';



      google.maps.event.addListener(marker, 'click', function(content) {
        return function(){
          infowindow.setContent(content);
          infowindow.open(map,this);
        }
      }(contentString));
    }

  }

  function AutoCenter(markers) {
    var bounds = new google.maps.LatLngBounds();
    $.each(markers, function (index, marker) {
      bounds.extend(marker.position);
    });
    map.fitBounds(bounds);
  }

  <c:if test="${fn:length(recentReceipts) > 0}">
    google.maps.event.addDomListener(window, 'load', initialize);
  </c:if>
</script>

</body>
</html>