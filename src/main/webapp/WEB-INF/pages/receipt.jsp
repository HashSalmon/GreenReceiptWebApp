<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
  <title>Receipt</title>
  <%@include file="mainHead.jsp"%>
</head>
<body>
<%@include file="interiorNavBar.jsp"%>
  <div class="container">
    <div class="row">
      <div class="col-md-4 col-md-offset-4">
        <div class="panel panel-defualt">
          <div class="panel-body">
            <c:if test="${receipt.returnReminder}">
              <div class="alert alert-danger centerText">
                The last day to return these items is coming up soon!<br> ${receipt.returnDate}
              </div>
            </c:if>
            <table class="receiptTable">
              <tr class="receiptLine">
                <td colspan="3" class="centerText receiptHeader"><h2>${receipt.store.company.name}</h2></td>
              </tr>
              <c:forEach items="${receipt.receiptItems}" var="item" varStatus="status">
                <tr>
                  <td class="receiptItem" style="${status.first ? 'padding-top: 1em;' : ''}">${item.itemName}:</td>
                  <td class="receiptItem" style="${status.first ? 'padding-top: 1em;' : ''}"></td>
                  <td class="rightText receiptItem" style="${status.first ? 'padding-top: 1em;' : ''}">$${item.price}</td>
                </tr>
              </c:forEach>
              <tr>
                <td>
                  SubTotal:
                </td>
                <td></td>
                <td class="rightText">${receipt.currencySubTotal}</td>
              </tr>
              <tr>
                <td>
                  Total:
                </td>
                <td></td>
                <td class="rightText">${receipt.currencyTotal}</td>
              </tr>
              <tr>
                <td colspan="3" class="centerText">
                  ${receipt.barcode}
                </td>
              </tr>
            </table>
            <button id="deleteReceipt" class="btn btn-danger col-md-offset-4 receiptButton" onclick="deleteReceipt()">Delete</button>
            <button class="btn btn-success col-md-offset-4 receiptButton" onclick="generate()">Download</button>
          </div>
        </div>
      </div>
    </div>
    <div class="row" style="margin-top: 15px;">
      <div class="col-md-6 col-md-offset-3">
        <div class="panel panel-default">
          <div class="panel-body">
            <div id="map_container" style="width: 100%; height: 250px;"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>
<script type="text/javascript"
        src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
<script type="text/javascript">

  function generate() {
    confirmed = confirm("Are you sure that you want to export this receipt?");
    if (confirmed) {
      window.open("/downloadReceiptPDF?receiptId=${receipt.id}");
    }
  }


  function deleteReceipt() {
    confirmed = confirm("Are you sure that you want to delete this receipt");
    if (confirmed) {
      window.location.href = "/deleteReceipt?id=${receipt.id}";
    }
  }

  function loadMap() {
    var latlng = new google.maps.LatLng(<c:out value="${receipt.latitude}"/> ,<c:out value="${receipt.longitude}"/>);
    var myOptions = {
      zoom: 12,
      center: latlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    var store = "${receipt.store.company.name}";
    var map = new google.maps.Map(document.getElementById("map_container"),myOptions);
    var marker = new google.maps.Marker({
      position: latlng,
      map: map,
      title: store
    });

    var total = "${receipt.currencyTotal}";
    var contentString = '<div id="content">'+
            '<div id="siteNotice">'+
            '</div>'+
            '<h1 id="firstHeading" class="firstHeading">' + store + ': ' +  total + '</h1>'+
            '<div id="bodyContent">'+
            '</div>'+
            '</div>';

    var infowindow = new google.maps.InfoWindow({
      content: contentString
    });


    google.maps.event.addListener(marker, 'click', function() {
      infowindow.open(map,marker);
    });

    var trafficLayer = new google.maps.TrafficLayer();
    trafficLayer.setMap(map);

  }


  $(document).ready(loadMap());

</script>