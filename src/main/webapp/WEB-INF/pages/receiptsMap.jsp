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

  <div class="panel panel-default">
    <div class="panel-heading">
      <h4>Receipts Map</h4>

    </div>
    <div class="panel-body">
        <form:form action="numReceiptsForm" modelAttribute="receiptViewAmount" method="post" id="numReceiptsForm">
          <div class="col-sm-3">
            <label for="numReceipts">Select number of receipts to view:</label>
            <select id="numReceipts" name="numReceipts" class="form-control" onchange="submitNumReceiptsForm()">
              <option value="25" ${sessionScope.numReceipts == "25" ? 'selected' : ''}>Up to 25</option>
              <option value="100" ${sessionScope.numReceipts == "100" ? 'selected' : ''}>Up to 100</option>
              <option value="1000" ${sessionScope.numReceipts == "1000" ? 'selected' : ''}>Up to 1000</option>
              <option value="10000" ${sessionScope.numReceipts == "10000" ? 'selected' : ''}>Up to 10000</option>
              <option value="100000" ${sessionScope.numReceipts == "100000" ? 'selected' : ''}>Up to 100000</option>
            </select>
            <input type="hidden" name="view" value="/receiptsMap"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          </div>
        </form:form>
        <div class="col-sm-12">
          <div class="panel panel-default">
            <div class="panel-body">
              <span class="label label-success" style="font-size: large;">Receipts: ${receiptsCount}</span>
              <div id="map_container" class="receiptsMap"></div>
            </div>
          </div>
        </div>
    </div>
  </div>



</div>
</body>
<script type="text/javascript"
        src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
<script>

  function submitNumReceiptsForm() {
    $("#numReceiptsForm").submit();
  }

  function initialize() {
    var mapOptions = {
      <c:if test="${fn:length(receipts) > 0}">
      zoom: 10,
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

      var myLatLng = new google.maps.LatLng(receipt[1], receipt[2]);

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

  <c:if test="${fn:length(receipts) > 0}">
    google.maps.event.addDomListener(window, 'load', initialize);
  </c:if>

</script>

</html>



