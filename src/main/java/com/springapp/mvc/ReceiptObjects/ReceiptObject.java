package com.springapp.mvc.ReceiptObjects;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@Scope("session")
public class ReceiptObject {
    private String receiptId;
    private Integer Id;
    private String Barcode;
    private String PurchaseDate;
    private Double Tax;
    private Double SubTotal;
    private String CardType;
    private String LastFourCardNumber;
    private String MiscMessage;
    private Store Store;
    private List<ReceiptItem> items;
    private List<ReceiptItem> ReceiptItems;
    private Double Total;
    private String ReturnDate;
    private Boolean ReturnReminder;
    private Boolean IsToday;
    private String CashierId;
    private Double lat;
    private Double lon;
    private Double Latitude;
    private Double Longitude;
    private String address;

    public ReceiptObject() {
        //Default
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public Store getStore() {
        return Store;
    }

    public List<ReceiptItem> getItems() {
        return items;
    }

    public void setItems(List<ReceiptItem> items) {
        this.items = items;
    }

    public Date getReturnDate() throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date d = sf.parse(ReturnDate);
        return d;
    }

    public String getReturnDateString() throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("MM-dd-yyyy");
        return sf.format(getReturnDate());
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public Date getPurchaseDate() throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date d = sf.parse(PurchaseDate);
        return d;
    }

    public String getPurchaseDateString() throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("MM-dd-yyyy");
        return sf.format(getPurchaseDate());
    }

    public void setPurchaseDate(String purchaseDate) {
        PurchaseDate = purchaseDate;
    }

    public Double getTax() {
        return Tax;
    }

    public void setTax(Double tax) {
        Tax = tax;
    }

    public Double getSubTotal() {
        return SubTotal;
    }

    public String getCurrencySubTotal() {
        return NumberFormat.getCurrencyInstance().format(this.SubTotal);
    }

    public void setSubTotal(Double subTotal) {
        SubTotal = subTotal;
    }

    public String getCardType() {
        return CardType;
    }

    public void setCardType(String cardType) {
        CardType = cardType;
    }

    public String getLastFourCardNumber() {
        return LastFourCardNumber;
    }

    public void setLastFourCardNumber(String lastFourCardNumber) {
        LastFourCardNumber = lastFourCardNumber;
    }

    public String getMiscMessage() {
        return MiscMessage;
    }

    public Double getTotal() {
        return Total;
    }

    public String getCurrencyTotal() {
       return NumberFormat.getCurrencyInstance().format(this.Total);
    }

    public void setMiscMessage(String miscMessage) {
        MiscMessage = miscMessage;
    }

    public void setStore(Store store) {
        Store = store;
    }

    public List<ReceiptItem> getReceiptItems() {
        return ReceiptItems;
    }

    public void setReceiptItems(List<ReceiptItem> receiptItems) {
        ReceiptItems = receiptItems;
    }

    public void setTotal(Double total) {
        Total = total;
    }

    public void setReturnDate(String returnDate) {
        ReturnDate = returnDate;
    }

    public String getCashierId() {
        return CashierId;
    }

    public void setCashierId(String cashierId) {
        CashierId = cashierId;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public Boolean getReturnReminder() throws ParseException {
        if(ReturnReminder && !isToday() && this.getReturnDate().before(Calendar.getInstance().getTime())) {
            return false;
        }
        return ReturnReminder;
    }

    public Boolean isToday() throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = sf.format(Calendar.getInstance().getTime());
        String returnDateString = sf.format(this.getReturnDate());
        return todayString.equals(returnDateString);
    }

    public void setReturnReminder(Boolean returnReminder) {
        ReturnReminder = returnReminder;
    }
}
