<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<h1>${message}</h1>
	<p>${authToken}</p>
	<p>${refreshToken}</p>
	<p>CardType: ${cardTypeTest}</p>
	<c:forEach items="${receipts}" var="item">
		<p>id: ${item.id}</p>
		<p>store: ${item.store.company.name}</p>
		<p>Barcode: ${item.barcode}</p>
		<p>purchaseDate: ${item.purchaseDate}</p>
		<p>returnDate: ${item.returnDate}</p>
		<p>tax: ${item.tax}</p>
		<p>subTotal: ${item.subTotal}</p>
		<p>total: ${item.total}</p>
		<p>cardType: ${item.cardType}</p>
		<p>lastFourCardNumber: ${item.lastFourCardNumber}</p>
		<p>cashierId: ${item.cashierId}</p>
		<p>miscMessage: ${item.miscMessage}</p>
		<c:forEach items="${item.receiptItems}" var="items">
			<p>name: ${items.itemName}</p>
			<p>price: ${items.price}</p>
		</c:forEach>
		<p>latitude: ${item.latitude}</p>
		<p>longitude: ${item.longitude}</p>
	</c:forEach>
</body>
</html>
walmart: 40.740724, -111.901238
target:  40.744905, -111.902011