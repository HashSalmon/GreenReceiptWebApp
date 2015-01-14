package Utilities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.springapp.mvc.Receipt;
import com.springapp.mvc.UserInfo;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Holds the main utilities for the application.
 *
 * @author Jordan Wanlass
 */
public class GreenReceiptUtil {

    /**
     * Returns all of the users receipts that the user has in the system.
     *
     * @return a list of all of the users receipts
     */
    public static List<Receipt> getReceipts() {
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();


        headers.set("Authorization", "Bearer " + userInfo.getAccess_token());
        ResponseEntity responseEntity = null;
        try {
            responseEntity = restTemplate.exchange("https://greenreceipt.net/api/Receipt",
                    HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
        } catch (Exception e) {
            return null;
        }

        List<Receipt> receipts = gson.fromJson((String) responseEntity.getBody(), new TypeToken<List<Receipt>>() {
        }.getType());
        return receipts;
    }

    /**
     * Retrieves a specific receipt
     *
     * @param receiptId This is the id for the receipt that corresponds to the database.
     * @return          Returns a receipt object that corresponds to the id passed in.
     */
    public static Receipt getReceipt(String receiptId) {
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();
        String apiCall = "https://greenreceipt.net/api/Receipt?id=" + receiptId;

        headers.set("Authorization", "Bearer " + userInfo.getAccess_token());
        ResponseEntity responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(apiCall,
                    HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
        } catch (Exception e) {
            return null;
        }

        Receipt receipt = gson.fromJson((String) responseEntity.getBody(), new TypeToken<Receipt>() {
        }.getType());
        return receipt;
    }
}
