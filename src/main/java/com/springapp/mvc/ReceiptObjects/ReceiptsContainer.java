package com.springapp.mvc.ReceiptObjects;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("session")
public class ReceiptsContainer {

    private List<ReceiptObject> Receipts;

    public List<ReceiptObject> getReceipts() {
        return Receipts;
    }

    public void setReceipts(List<ReceiptObject> receipts) {
        Receipts = receipts;
    }
}
