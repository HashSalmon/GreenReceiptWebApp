<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
              <span class="label label-success" style="font-size: large;">Trending Chart</span>
              <%--Trending Chart--%>
              <div id="chart1" style="margin-top: 11px;"></div>
            </div>
          </div>
        </div>
        <div class="col-sm-12 col-md-6">
          <div class="panel panel-default">
            <div class="panel-body">
              <span class="label label-success" style="font-size: large;">Category Chart</span>
              <%--Category Chart--%>
              <div id="chart" style="margin-top: 11px;"></div>
            </div>
          </div>
        </div>
        <div class="col-sm-12 col-md-12">
          <div class="panel panel-default">
            <div class="panel-body">
              <span class="label label-success" style="font-size: large;">Current Budget</span>
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
          Notifications <span class="badge">2</span><br/><br/>
          <ul style="list-style: none; padding-left: 0;">
            <li>
              <span class="glyphicon glyphicon-warning-sign"></span><a href="/receipt?receiptId=1"> Upcoming Return</a>
            </li>
            <li>
              <span class="glyphicon glyphicon-random"></span><a href="/receipt?receiptId=2&exchange=true"> Exchange</a>
            </li>
          </ul>
        </div>
      </div>
      <div class="panel panel-default">
        <div class="panel-body">
          Recent Activity<br/><br/>
          <ul style="list-style: none; padding-left: 0;">
            <li>
              <span class="glyphicon glyphicon-barcode"></span><a href="/receipt?receiptId=1"> Walmart</a>
            </li>
            <li>
              <span class="glyphicon glyphicon-barcode"></span><a href="/receipt?receiptId=2"> Target</a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</div>
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
        data: [100, 75, 200, 150, 48, 500],
        color: "#9de219"
      }],
      valueAxis: {
        max: 600,
        line: {
          visible: false
        },
        minorGridLines: {
          visible: true
        }
      },
      categoryAxis: {
        categories: ["Food", "Clothing", "Gas", "Dining", "Entertainment", "Electronics"],
        majorGridLines: {
          visible: false
        }
      },
      tooltip: {
        visible: true,
        format: "/$/{0}",
        template: "#= series.name #: #= kendo.format('{0:C}',value) #"
      }
    });
  }

  $(document).ready(createChart);
  $(document).bind("kendo:skinChange", createChart);

  function createChart1() {
    var dataset = new Array(1545.65,1436.43,1567.23,1501.54,null);
    var highlighted = new Array(null,null,null,null,1512.71);
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
          name: 'Generated',
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
        categories: ["Jan", "Feb", "Mar", "April", "May"],
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
        data: [{
          category: "Clothing",
          value: 25,
          color: "#9de219"
        },{
          category: "Food",
          value: 27,
          color: "#90cc38"
        },{
          category: "Electronics",
          value: 11.5,
          color: "#068c35"
        },{
          category: "Dining",
          value: 9.5,
          color: "#006634"
        },{
          category: "Entertainment",
          value: 10,
          color: "#004d38"
        },{
          category: "Gas",
          value: 17,
          color: "#033939"
        }]
      }],
      tooltip: {
        visible: true,
        format: "{0}%"
      }
    });
  }

  $(document).ready(createChart2);
  $(document).bind("kendo:skinChange", createChart2);


  function initialize() {
    var mapOptions = {
      zoom: 12,
      center: new google.maps.LatLng(<c:out value="${receipts[0].latitude}"/>, <c:out value="${receipts[0].longitude}"/>)
    };
    var map = new google.maps.Map(document.getElementById('map_container'),
            mapOptions);
    var markers = [];
    var trafficLayer = new google.maps.TrafficLayer();
    trafficLayer.setMap(map);

    var length = 0;
    <c:forEach items="${receipts}" var="receipt">
      markers.push(["${receipt.store.company.name}", <c:out value="${receipt.latitude}"/>, <c:out value="${receipt.longitude}"/>, "${receipt.total}"]);
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
        '<h1 id="firstHeading" class="firstHeading">' + receipt[0] + ':' + receipt[3] + '</h1>'+
        '<div id="bodyContent">'+
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

  google.maps.event.addDomListener(window, 'load', initialize);
</script>

</body>
</html>