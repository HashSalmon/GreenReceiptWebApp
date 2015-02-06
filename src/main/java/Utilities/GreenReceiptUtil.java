package Utilities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.springapp.mvc.*;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
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

        return gson.fromJson((String) responseEntity.getBody(), new TypeToken<List<Receipt>>() {}.getType());
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

        return gson.fromJson((String) responseEntity.getBody(), new TypeToken<Receipt>() {}.getType());
    }

    /**
     * Returns all of the users receipts with a return notification set that has a return date within the next 7 days
     *
     * @return a list of all of the users return receipts receipts
     */
    public static List<Receipt> getReturnNotifications() {
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();

        headers.set("Authorization", "Bearer " + userInfo.getAccess_token());
        ResponseEntity responseEntity = null;
        try {
            responseEntity = restTemplate.exchange("https://greenreceipt.net/api/Receipt/ReturnReceipts",
                    HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
        } catch (Exception e) {
            return null;
        }

        return gson.fromJson((String) responseEntity.getBody(), new TypeToken<List<Receipt>>() {}.getType());
    }

    public static Budget getCurrentBudget() {
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();
        String apiCall = "https://greenreceipt.net/api/Budget/CurrentBudget";

        headers.set("Authorization", "Bearer " + userInfo.getAccess_token());
        ResponseEntity responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(apiCall,
                    HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
        } catch (Exception e) {
            return null;
        }

        return gson.fromJson((String) responseEntity.getBody(), new TypeToken<Budget>() {}.getType());
    }

    public static Boolean createBudget(String budgetJson) {
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();
        String apiCall = "https://greenreceipt.net/api/Budget";

        headers.set("Authorization", "Bearer " + userInfo.getAccess_token());
        ResponseEntity responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(apiCall,
                    HttpMethod.POST, new HttpEntity<Object>(budgetJson, headers), String.class);
        } catch (Exception e) {
            return null;
        }

        return true;
    }

    public static List<Category> getCategories() {
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();

        headers.set("Authorization", "Bearer " + userInfo.getAccess_token());
        ResponseEntity responseEntity = null;
        try {
            responseEntity = restTemplate.exchange("https://greenreceipt.net/api/Category/GetCategories",
                    HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
        } catch (Exception e) {
            return null;
        }

        return gson.fromJson((String) responseEntity.getBody(), new TypeToken<List<Category>>() {}.getType());
    }

    public static Boolean updateBudgetItem(BudgetItem budgetItem) {
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();
        String apiCall = "https://greenreceipt.net/api/BudgetItem";
        String budgetItemJson = gson.toJson(budgetItem);
        headers.set("Authorization", "Bearer " + userInfo.getAccess_token());
        ResponseEntity responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(apiCall,
                    HttpMethod.POST, new HttpEntity<Object>(budgetItemJson, headers), String.class);
        } catch (Exception e) {
            return null;
        }

        return true;
    }
}
