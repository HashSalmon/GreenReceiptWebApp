package com.springapp.mvc.CreditCardObjects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by jordanwanlass on 4/1/15.
 */
public class CreditCardFormObject {

    @NotNull
    @Size(min=1,max=20)
    private String cardName;

    @Pattern(regexp="[\\d]{4}")
    private String firstFour;

    @Pattern(regexp="[\\d]{4}")
    private String lastFour;

    private String cardNumber;
    private String cardId;
    private String cardHash;

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getFirstFour() {
        return firstFour;
    }

    public void setFirstFour(String firstFour) {
        this.firstFour = firstFour;
    }

    public String getLastFour() {
        return lastFour;
    }

    public void setLastFour(String lastFour) {
        this.lastFour = lastFour;
    }

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
