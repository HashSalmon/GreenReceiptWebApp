package Utilities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.springapp.mvc.*;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

    public static Boolean updateOrAddBudgetItem(BudgetItem budgetItem) {
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

    public static Boolean deleteBudgetItem(String id) {
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String apiCall = "https://greenreceipt.net/api/BudgetItem?id=" + id;
        headers.set("Authorization", "Bearer " + userInfo.getAccess_token());
        ResponseEntity responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(apiCall,
                    HttpMethod.DELETE, new HttpEntity<Object>(headers), String.class);
        } catch (Exception e) {
            return null;
        }

        return true;
    }

    public static CategoryReport getCategoryReportItems() {
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();
        Calendar startDate = Calendar.getInstance();
        startDate.set(Calendar.DAY_OF_MONTH, 1);
        Calendar endDate  = Calendar.getInstance();
        endDate.add(Calendar.DAY_OF_MONTH, 1);


        headers.set("Authorization", "Bearer " + userInfo.getAccess_token());
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = sf.format(startDate.getTime());
        String endDateString = sf.format(endDate.getTime());
        String json = "https://greenreceipt.net/api/CategoryReport?startDate="  + startDateString + "&endDate=" + endDateString;
        ResponseEntity responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(json,
                    HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
        } catch (Exception e) {
            return null;
        }

        return gson.fromJson((String) responseEntity.getBody(), new TypeToken<CategoryReport>() {}.getType());
    }

    public static List<String> getChartColors(int numCategories) {
        List<String> colors;

        if(numCategories == 1 || numCategories == 0) {
            return new ArrayList<String>(Arrays.asList("#9de219"));
        } else if(numCategories == 2) {
            return new ArrayList<String>(Arrays.asList("#033939"));
        }

        int firstSteps = numCategories;// This is the number of steps for the first to the second color.
        int lastSteps = numCategories   - firstSteps;// If the number of categories aren't divisible by three, the second to the last color will take whatever the remainder is.
        int divisibleByTwo = numCategories % 2;

        if(divisibleByTwo == 0) {// if the number of categories is divisible by three, both sections will use first steps
            colors = addColors(firstSteps, firstSteps);
        } else {// if the number of categories is not divisible by three, the second to the last color will have the remainder as its number of steps
            colors = addColors(firstSteps, lastSteps);
        }
        return colors;
    }

    public static List<String> addColors(int first, int last) {
        List<String> colors = new ArrayList<String>();
        int[] mainGreen = new int[]{157, 226, 25};
        int[] lastGreen = new int[]{3, 57, 57};

        generateColors(first, colors, mainGreen, lastGreen, false);

        return colors;
    }

    public static void generateColors(int step, List<String> colors, int[] firstColor, int[] secondColor, boolean includeLast) {
        int[] increment = new int[3];
        String color = "";
        for(int i = 0; i < 3; i++) {
            increment[i] =  (secondColor[i] - firstColor[i]) / (step);// get the distance between the colors, then divide it by the number of steps to create an increment.
        }

        // take the colors and apply the increment
        for(int i = 0; i < step; i++) {
            color = "#";
            color += String.format("%02X",firstColor[0] + i * increment[0]);
            color += String.format("%02X",firstColor[1] + i * increment[1]);
            color += String.format("%02X",firstColor[2] + i * increment[2]);
            colors.add(color);
        }

        if(includeLast) {
            // add the last color or else it will be left out.
            color = "#";
            color += String.format("%02X",secondColor[0]);
            color += String.format("%02X",secondColor[1]);
            color += String.format("%02X",secondColor[2]);
            colors.add(color);
        }
    }

    public static Double getBudgetTotal(List<BudgetItem> budgetItems) {
        Double total = 0.0;

        for(BudgetItem item : budgetItems) {
            total += item.getAmountAllowed();
        }

        return total;
    }
}
