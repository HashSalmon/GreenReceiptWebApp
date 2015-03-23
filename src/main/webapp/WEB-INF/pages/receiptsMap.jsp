<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
  <title>Receipts</title>
  <%@include file="mainHead.jsp"%>
</head>
<body>
<%@include file="interiorNavBar.jsp"%>
<div class="container">
  <div class="col-sm-12">
    <div class="panel panel-default">
      <div class="panel-body">
        <span class="label label-success" style="font-size: large;">Receipts</span>
        <div id="map_container" class="receiptsMap"></div>
      </div>
    </div>
  </div>
</div>
</body>
<script type="text/javascript"
        src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
<script>
  function initialize() {
    var mapOptions = {
      <c:if test="${fn:length(receipts) > 0}">
      zoom: 12,
      center: new google.maps.LatLng(<c:out value="${receipts[0].latitude}"/>, <c:out value="${receipts[0].longitude}"/>)
      </c:if>
    };
    var map = new google.maps.Map(document.getElementById('map_container'),
            mapOptions);
    var markers = [];
    var trafficLayer = new google.maps.TrafficLayer();
    trafficLayer.setMap(map);

    var length = 0;
    <c:forEach items="${receipts}" var="receipt">
    markers.push(["${receipt.store.company.name}", <c:out value="${receipt.latitude}"/>, <c:out value="${receipt.longitude}"/>, "${receipt.total}", "${receipt.id}", "${receipt.address}"]);
    length++;
    </c:forEach>
    setMarkers(map, markers, length);
    AutoCenter(markers);
  }

  function setMarkers(map, locations, length) {
    var infowindow = new google.maps.InfoWindow();
    for (var i = 0; i < length; i++) {
      var receipt = locations[i];

      var myLatLng = new google.maps.LatLng(receipt[1], receipt[2]);

      var marker = new google.maps.Marker({
        position: myLatLng,
        map: map,
        title: receipt[0]
      });
      var contentString = '<div id="content">'+
              '<div id="siteNotice">'+
              '</div>'+
              '<h1 id="firstHeading" class="firstHeading">' + receipt[0] + ': $' + receipt[3] + '</h1>'+
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

  <c:if test="${fn:length(receipts) > 0}">
    google.maps.event.addDomListener(window, 'load', initialize);
  </c:if>

</script>

</html>



