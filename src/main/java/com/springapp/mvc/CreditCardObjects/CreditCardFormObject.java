package com.springapp.mvc.CreditCardObjects;

/**
 * Created by jordanwanlass on 4/1/15.
 */
public class CreditCardFormObject {
    private String cardNumber;
    private String cardId;
    private String cardHash;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardHash() {
        return cardHash;
    }

    public void setCardHash(String cardHash) {
        this.cardHash = cardHash;
    }
}
