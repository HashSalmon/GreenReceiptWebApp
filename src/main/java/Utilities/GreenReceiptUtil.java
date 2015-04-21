package Utilities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.springapp.mvc.BudgetObjects.BudgetItem;
import com.springapp.mvc.BudgetObjects.BudgetObject;
import com.springapp.mvc.CategoryReportObjects.CategoryReport;
import com.springapp.mvc.CategoryReportObjects.CategoryReportItem;
import com.springapp.mvc.CreditCardObjects.CreditCardObject;
import com.springapp.mvc.PageObjects.PageObject;
import com.springapp.mvc.ReceiptObjects.Category;
import com.springapp.mvc.ReceiptObjects.ReceiptImageObject;
import com.springapp.mvc.ReceiptObjects.ReceiptItem;
import com.springapp.mvc.ReceiptObjects.ReceiptObject;
import com.springapp.mvc.TrendingReportObjects.TrendingReport;
import com.springapp.mvc.TrendingReportObjects.TrendingReportItem;
import com.springapp.mvc.UserObjects.UserInfo;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Holds the main utilities (api calls) for the application.
 *
 * @author Jordan Wanlass
 */
public class GreenReceiptUtil {

    /**
     * Returns all of the users receipts that the user has in the system.
     *
     * @return a list of all of the users receipts
     */
    public static List<ReceiptObject> getReceipts() {
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();


        headers.set("Authorization", "Bearer " + userInfo.getAccess_token());
        ResponseEntity responseEntity = null;
        try {
            responseEntity = restTemplate.exchange("https://api.greenreceipt.net/api/Receipt/Receipts",
                    HttpMethod.POST, new HttpEntity<Object>(headers), String.class);
        } catch (Exception e) {
            return null;
        }

        return gson.fromJson((String) responseEntity.getBody(), new TypeToken<List<ReceiptObject>>() {}.getType());
    }

    /**
     * Returns all of the users receipts that the user has in the system.
     *
     * @return a list of all of the users receipts
     */
    public static List<ReceiptObject> getReceipts(Integer amount) {
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Gson gson = new Gson();
        String pageObjectJson = gson.toJson(new PageObject(amount, 1, 1));

        headers.set("Authorization", "Bearer " + userInfo.getAccess_token());
        ResponseEntity responseEntity = null;
        try {
            responseEntity = restTemplate.exchange("https://api.greenreceipt.net/api/Receipt/Receipts",
                    HttpMethod.POST, new HttpEntity<Object>(pageObjectJson, headers), String.class);
        } catch (Exception e) {
            return null;
        }

        return gson.fromJson((String) responseEntity.getBody(), new TypeToken<List<ReceiptObject>>() {}.getType());
    }

    /**
     * Retrieves a specific receipt
     *
     * @param receiptId This is the id for the receipt that corresponds to the database.
     * @return          Returns a receipt object that corresponds to the id passed in.
     */
    public static ReceiptObject getReceipt(String receiptId) {
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();
        String apiCall = "https://api.greenreceipt.net/api/Receipt?id=" + receiptId;

        headers.set("Authorization", "Bearer " + userInfo.getAccess_token());
        ResponseEntity responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(apiCall,
                    HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
        } catch (Exception e) {
            return null;
        }

        return gson.fromJson((String) responseEntity.getBody(), new TypeToken<ReceiptObject>() {}.getType());
    }

    /**
     * Removes a receipt
     * @param id The id of the receipt to remove
     * @return Returns true if the delete is successful, null if an exception occurs
     */
    public static Boolean deleteReceipt(String id) {
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String apiCall = "https://api.greenreceipt.net/api/Receipt?id=" + id;
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

    /**
     * Returns all of the users receipts with a return notification set that has a return date within the next 7 days
     *
     * @return a list of all of the users return receipts receipts
     */
    public static List<ReceiptObject> getReturnNotifications() {
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();

        headers.set("Authorization", "Bearer " + userInfo.getAccess_token());
        ResponseEntity responseEntity = null;
        try {
            responseEntity = restTemplate.exchange("https://api.greenreceipt.net/api/Receipt/ReturnReceipts",
                    HttpMethod.POST, new HttpEntity<Object>(headers), String.class);
        } catch (Exception e) {
            return null;
        }

        return gson.fromJson((String) responseEntity.getBody(), new TypeToken<List<ReceiptObject>>() {}.getType());
    }

    /**
     * Returns all of the users most recent receipts, their 5 most recent receipts
     *
     * @return a list of all of the users most recent receipts
     */
    public static List<ReceiptObject> getMostRecentReceipts() {
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();

        headers.set("Authorization", "Bearer " + userInfo.getAccess_token());
        ResponseEntity responseEntity = null;
        try {
            responseEntity = restTemplate.exchange("https://api.greenreceipt.net/api/Receipt/RecentReceipts?recentCount=5",
                    HttpMethod.POST, new HttpEntity<Object>(headers), String.class);
        } catch (Exception e) {
            return null;
        }

        return gson.fromJson((String) responseEntity.getBody(), new TypeToken<List<ReceiptObject>>() {}.getType());
    }

    /**
     * This call gets the users currently set budget
     * @return return a budget item that contains a full list of BudgetItems
     */
    public static BudgetObject getCurrentBudget() {
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();
        String apiCall = "https://api.greenreceipt.net/api/Budget/CurrentBudget";

        headers.set("Authorization", "Bearer " + userInfo.getAccess_token());
        ResponseEntity responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(apiCall,
                    HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
        } catch (Exception e) {
            return null;
        }

        return gson.fromJson((String) responseEntity.getBody(), new TypeToken<BudgetObject>() {}.getType());
    }

    /**
     * Send the budget to the server to be created
     *
     * @param budgetJson The json representation of the budget object with budgetItems
     * @return Returns true if the budget is successfully created, null if an exception occurs
     */
    public static Boolean createBudget(String budgetJson) {
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String apiCall = "https://api.greenreceipt.net/api/Budget";

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

    /**
     * Gets a list of all of the categories in the system
     * @return The list of Category objects
     */
    public static List<Category> getCategories() {
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();

        headers.set("Authorization", "Bearer " + userInfo.getAccess_token());
        ResponseEntity responseEntity = null;
        try {
            responseEntity = restTemplate.exchange("https://api.greenreceipt.net/api/Category/GetCategories",
                    HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
        } catch (Exception e) {
            return null;
        }

        return gson.fromJson((String) responseEntity.getBody(), new TypeToken<List<Category>>() {}.getType());
    }

    /**
     * Sends a budgetItem to the server.  If it already exists, it updates the item, if it doesn't exist, it adds the item
     * @param budgetItem The budget item to be added or updated
     * @return Returns true if the update/add is successful, null if an exception occurs
     */
    public static Boolean updateOrAddBudgetItem(BudgetItem budgetItem) {
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();
        String apiCall = "https://api.greenreceipt.net/api/BudgetItem";
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

    /**
     * Removes a budgetItem (category) from the users current budget
     * @param id The id of the budgetItem to remove
     * @return Returns true if the delete is successful, null if an exception occurs
     */
    public static Boolean deleteBudgetItem(String id) {
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String apiCall = "https://api.greenreceipt.net/api/BudgetItem?id=" + id;
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

    /**
     * Retries the Category Report for the user
     * @param startDateString The earliest date you want to see category information
     * @param endDateString The latest date you want to see category information
     * @param model Model object for accessing variables in the jsp
     * @return The category report object
     * @throws ParseException If the dates cannot be parsed because they are in the incorrect format, throw an exception
     */
    public static CategoryReport getCategoryReportItems(String startDateString, String endDateString, ModelAndView model, HttpSession session) throws ParseException {
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();

        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        if(startDateString == null) {
            startDate.set(Calendar.DAY_OF_MONTH, 1);
        } else {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = sf.parse(startDateString);
            startDate.setTime(d);
        }
        if(endDateString == null) {
            endDate.add(Calendar.DAY_OF_MONTH, 1);
        } else {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = sf.parse(endDateString);
            endDate.setTime(d);
        }

        headers.set("Authorization", "Bearer " + userInfo.getAccess_token());
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        startDateString = sf.format(startDate.getTime());
        endDateString = sf.format(endDate.getTime());
        model.addObject("startDate", startDateString);
        model.addObject("endDate", endDateString);

        session.setAttribute("CategoryReportStartDate", startDateString);
        session.setAttribute("CategoryReportEndDate", endDateString);
        String displayStartDate = GreenReceiptUtil.formatDateString(startDateString);
        String displayEndDate = GreenReceiptUtil.formatDateString(endDateString);
        session.setAttribute("CategoryReportStartDateDisplay", displayStartDate);
        session.setAttribute("CategoryReportEndDateDisplay", displayEndDate);

        String json = "https://api.greenreceipt.net/api/CategoryReport?startDate="  + startDateString + "&endDate=" + endDateString;
        ResponseEntity responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(json,
                    HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
        } catch (Exception e) {
            return null;
        }

        return gson.fromJson((String) responseEntity.getBody(), new TypeToken<CategoryReport>() {}.getType());
    }

    /**
     * Retrieve all of the receipts that have an item in the category
     * @param categoryId id of the category you wish to get receipts for
     * @param startDateString The earliest date you want to see category information
     * @param endDateString The latest date you want to see category information
     * @return a list of all the receipts
     * @throws ParseException if the date strings cannot be parsed correctly
     */
    public static List<ReceiptObject> getCategoryReceipts(String categoryId, String startDateString, String endDateString, HttpSession session) throws ParseException {
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();

        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        if(startDateString == null) {
            startDate.set(Calendar.DAY_OF_MONTH, 1);
        } else {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = sf.parse(startDateString);
            startDate.setTime(d);
        }
        if(endDateString == null) {
            endDate.add(Calendar.DAY_OF_MONTH, 1);
        } else {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = sf.parse(endDateString);
            endDate.setTime(d);
        }

        headers.set("Authorization", "Bearer " + userInfo.getAccess_token());
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        startDateString = sf.format(startDate.getTime());
        endDateString = sf.format(endDate.getTime());


        String json = "https://api.greenreceipt.net/api/Receipt/CategoryReceipts?categoryId=" + categoryId + "&startDate="  + startDateString + "&endDate=" + endDateString;
        ResponseEntity responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(json,
                    HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
        } catch (Exception e) {
            return null;
        }

        return gson.fromJson((String) responseEntity.getBody(), new TypeToken<List<ReceiptObject>>() {}.getType());
    }

    /**
     * Retrieve all of the receipt items that belong to the category category
     * @param categoryId id of the category you wish to get receip itemss for
     * @param startDateString The earliest date you want to see category information
     * @param endDateString The latest date you want to see category information
     * @return a list of all the receipt items
     * @throws ParseException if the date strings cannot be parsed correctly
     */
    public static List<ReceiptItem> getCategoryReceiptItems(Integer categoryId, String startDateString, String endDateString) throws ParseException {
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();

        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        if(startDateString == null) {
            startDate.set(Calendar.DAY_OF_MONTH, 1);
        } else {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = sf.parse(startDateString);
            startDate.setTime(d);
        }
        if(endDateString == null) {
            endDate.add(Calendar.DAY_OF_MONTH, 1);
        } else {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = sf.parse(endDateString);
            endDate.setTime(d);
        }

        headers.set("Authorization", "Bearer " + userInfo.getAccess_token());
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        startDateString = sf.format(startDate.getTime());
        endDateString = sf.format(endDate.getTime());


        String json = "https://api.greenreceipt.net/api/ReceiptItem/ReceiptItems?categoryId=" + categoryId + "&startDate="  + startDateString + "&endDate=" + endDateString;
        ResponseEntity responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(json,
                    HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
        } catch (Exception e) {
            return null;
        }

        return gson.fromJson((String) responseEntity.getBody(), new TypeToken<List<ReceiptItem>>() {}.getType());
    }

    /**
     * Retries the Trending Report for the user
     * @param startDateString The earliest date you want to see trending information (if null is passed in, it will be january first of the current year)
     * @param endDateString The latest date you want to see trending information (if null is passed in, it will be the next month from the current one)
     * @param model Model object for accessing variables in the jsp
     * @return The trending report object
     * @throws ParseException If the dates cannot be parsed because they are in the incorrect format, throw an exception
     */
    public static TrendingReport getTrendingReportItems(String startDateString, String endDateString, ModelAndView model, HttpSession session) throws ParseException {
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();

        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        if(startDateString == null) {
            startDate.set(Calendar.DAY_OF_MONTH, 1);
            startDate.set(Calendar.MONTH, 0);
        } else {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = sf.parse(startDateString);
            startDate.setTime(d);
        }
        if(endDateString == null) {
            endDate.add(Calendar.DAY_OF_MONTH, 1);
            endDate.add(Calendar.MONTH, 1);
        } else {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = sf.parse(endDateString);
            endDate.setTime(d);
        }

        headers.set("Authorization", "Bearer " + userInfo.getAccess_token());
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        startDateString = sf.format(startDate.getTime());
        endDateString = sf.format(endDate.getTime());
        model.addObject("startDate", startDateString);
        model.addObject("endDate", endDateString);
        session.setAttribute("TrendingReportStartDate", startDateString);
        session.setAttribute("TrendingReportEndDate", endDateString);

        String displayStartDate = GreenReceiptUtil.formatDateString(startDateString);
        String displayEndDate = GreenReceiptUtil.formatDateString(endDateString);
        session.setAttribute("TrendingReportStartDateDisplay", displayStartDate);
        session.setAttribute("TrendingReportEndDateDisplay", displayEndDate);

        String json = "https://api.greenreceipt.net/api/TrendingReport?startDate="  + startDateString + "&endDate=" + endDateString;
        ResponseEntity responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(json,
                    HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
        } catch (Exception e) {
            return null;
        }

        return gson.fromJson((String) responseEntity.getBody(), new TypeToken<TrendingReport>() {}.getType());
    }

    /**
     * Gets a list of all of the Credit Cards the user has in the system
     * @return The list of Credit Card Objects objects
     */
    public static List<CreditCardObject> getCreditCards() {
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();

        headers.set("Authorization", "Bearer " + userInfo.getAccess_token());
        ResponseEntity responseEntity = null;
        try {
            responseEntity = restTemplate.exchange("https://api.greenreceipt.net/api/UserAccountId",
                    HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
        } catch (Exception e) {
            return null;
        }

        return gson.fromJson((String) responseEntity.getBody(), new TypeToken<List<CreditCardObject>>() {}.getType());
    }

    /**
     * Gets a list of all of the images the user has attached to this receipt
     * @return The list of Image Objects objects
     */
    public static List<ReceiptImageObject> getReceiptImages(Integer receiptId) {
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();

        headers.set("Authorization", "Bearer " + userInfo.getAccess_token());
        ResponseEntity responseEntity = null;
        try {
            responseEntity = restTemplate.exchange("https://api.greenreceipt.net/api/Image/ReceiptImages?receiptId=" + receiptId,
                    HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
        } catch (Exception e) {
            return null;
        }

        return gson.fromJson((String) responseEntity.getBody(), new TypeToken<List<ReceiptImageObject>>() {}.getType());
    }


    /**
     * adds a card to the users account
     * @param cardHash The card hash representation of the card number
     * @return Returns true if the add is successful, null if an exception occurs
     */
    public static Boolean addCard(String cardHash, String lastFour) {
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Gson gson = new Gson();
        String cardObjectJson = gson.toJson(new CreditCardObject(cardHash, lastFour));

        String apiCall = "https://api.greenreceipt.net/api/UserAccountId";
        headers.set("Authorization", "Bearer " + userInfo.getAccess_token());
        ResponseEntity responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(apiCall,
                    HttpMethod.POST, new HttpEntity<Object>(cardObjectJson, headers), String.class);
        } catch (Exception e) {
            return null;
        }

        return true;
    }

    /**
     * Removes a card
     * @param id The id of the card to remove
     * @return Returns true if the delete is successful, null if an exception occurs
     */
    public static Boolean deleteCard(String id) {
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String apiCall = "https://api.greenreceipt.net/api/UserAccountId?id=" + id;
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

    /**
     * Create the hash for the card being added
     * @param firstFour first four digits on the card
     * @param lastFour last four digits on the card
     * @param cardName The last name on the card
     * @return The newly created hash string
     * @throws NoSuchAlgorithmException
     */
    public static String hashCard(String firstFour, String lastFour, String cardName) throws NoSuchAlgorithmException {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        String toHash = firstFour + cardName.toUpperCase() + lastFour;
        byte[] passBytes = toHash.getBytes();
        byte[] passHash = sha256.digest(passBytes);

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < passHash.length; i++) {
            sb.append(Integer.toString((passHash[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    /**
     * Get the colors for the budget chart
     * @param numCategories The number of categories to get colors for
     * @return A list of the color strings
     */
    public static List<String> getChartColors(int numCategories) {
        List<String> colors;

        if(numCategories == 1) {
            return new ArrayList<String>(Arrays.asList("#9de219"));
        } else if(numCategories == 2) {
            return new ArrayList<String>(Arrays.asList("#9de219","#033939"));
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

    /**
     * Add the colors to the array
     * @param first number of steps for the first to the second color
     * @param last number of steps for the second to last color
     * @return List of colors
     */
    public static List<String> addColors(int first, int last) {
        List<String> colors = new ArrayList<String>();
        int[] mainGreen = new int[]{157, 226, 25};
        int[] lastGreen = new int[]{3, 57, 57};

        generateColors(first, colors, mainGreen, lastGreen, false);

        return colors;
    }

    /**
     * Create the colors and and them to the color list
     * @param step the number of steps in between each color
     * @param colors an empty list that will be populated with colors
     * @param firstColor the first color to start gradient from
     * @param secondColor the last color that the gradient will end with
     * @param includeLast add the last color
     */
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

    /**
     * Get the total dollar amount for your budget, a sum of all the budget item limits
     * @param budgetItems A list of all of the budget items in the users current budget
     * @return a double that is the sum of all of the budget item's limits
     */
    public static Double getBudgetTotal(List<BudgetItem> budgetItems) {
        Double total = 0.0;

        for(BudgetItem item : budgetItems) {
            total += item.getAmountAllowed();
        }

        return total;
    }

    /**
     * Build up the json for the category report
     * @param categoryReport the list of category report items
     * @param model the model object that will make the variables available in the jsp
     */
    public static void makeCategoryReportStrings(CategoryReport categoryReport, ModelAndView model, HttpSession session) {
        Double total = 0.0;
        if(categoryReport != null) {
            if(categoryReport.getCategoryReportItems() != null) {
                HashMap<String, Integer> categoryMap = new HashMap<String, Integer>();
                String categoryReportValues = "[";
                String categoryReportNames = "[";
                String prepend = "";
                for(CategoryReportItem item : categoryReport.getCategoryReportItems()) {
                    categoryMap.put(item.getCategoryName(), item.getId());
                    total += item.getTotal();
                    categoryReportValues += prepend + item.getTotal().toString();
                    categoryReportNames += prepend + '"' + item.getCategoryName() + '"';
                    prepend = ",";
                }
                categoryReportValues += "]";
                categoryReportNames += "]";

                session.setAttribute("categoryMap", categoryMap);

                model.addObject("categoryReportValues", categoryReportValues);
                model.addObject("categoryReportNames", categoryReportNames);
                if(categoryReportNames.length() < 3) {
                    model.addObject("noResults", "There are no results for this date range");
                }
                model.addObject("categoryReportTotal", total + 200);
            }
        }
    }

    /**
     * Build up the json for the trending report
     * @param trendingReport the list of category report items
     * @param model the model object that will make the variables available in the jsp
     */
    public static void makeTrendingReportStrings(TrendingReport trendingReport, ModelAndView model) {
        if(trendingReport != null) {
            if(trendingReport.getTrendingReportItems() != null) {
                String dataset = "new Array(";
                String highlighted = "new Array(";
                String months = "[";
                String prepend = "";
                for(TrendingReportItem item : trendingReport.getTrendingReportItems()) {
                    if(item.isProjected()) {
                        dataset +=  prepend + "null";
                        highlighted +=  prepend + item.getTotal().toString();
                    } else {
                        dataset += prepend + item.getTotal().toString();
                        highlighted += prepend + "null";
                    }
                    months += prepend + "'" + item.getMonth() + "'";
                    prepend = ",";
                }
                months += "]";
                dataset += ")";
                highlighted += ")";
                model.addObject("months", months);
                model.addObject("dataset", dataset);
                model.addObject("highlighted", highlighted);
                if(dataset.length() < 12) {
                    model.addObject("noResults", "There are no results for this date range");
                }
            }
        }
    }

    /**
     * Clear out all of the set values in the session
     * @param session The session object for the user
     */
    public static void clearSession(HttpSession session) {
        session.setAttribute("firstname", null);
        session.setAttribute("lastname", null);
        session.setAttribute("CategoryReportStartDate", null);
        session.setAttribute("CategoryReportEndDate", null);
        session.setAttribute("TrendingReportStartDate", null);
        session.setAttribute("TrendingReportEndDate", null);
    }

    /**
     * Take in a string formatted yyyy-MM-dd and change it to MM-dd-yyyy
     * @param date string to reformat
     */
    public static String formatDateString(String date) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = sf.parse(date);
        sf = new SimpleDateFormat("MM-dd-yyyy");
        return sf.format(d);
    }
}
