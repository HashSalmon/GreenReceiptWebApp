package com.springapp.mvc;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("session")
public class ReceiptsContainer {

    private List<Receipt> Receipts;

    public List<Receipt> getReceipts() {
        return Receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        Receipts = receipts;
    }
}
