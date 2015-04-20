package com.springapp.mvc;


import Forms.CreateAccount;
import Utilities.GreenReceiptUtil;
import Utilities.MailUtility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.springapp.mvc.BudgetObjects.*;
import com.springapp.mvc.CategoryReportObjects.CategoryReport;
import com.springapp.mvc.CategoryReportObjects.CategoryReportDates;
import com.springapp.mvc.CreditCardObjects.CreditCardFormObject;
import com.springapp.mvc.CreditCardObjects.CreditCardObject;
import com.springapp.mvc.ReceiptObjects.*;
import com.springapp.mvc.TrendingReportObjects.TrendingReport;
import com.springapp.mvc.UserObjects.User;
import com.springapp.mvc.UserObjects.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Scope("request")
public class AppController {

    @Autowired
    private User user;

    @Autowired
    private BudgetObject budget;

    @Autowired
    private ReceiptsContainer receiptsContainer;

    @RequestMapping(value = "/protected**", method = RequestMethod.GET)
    public ModelAndView protectedPage() {

        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security 3.2.3 Hello World");
        model.addObject("message", "This is protected page - Only for Administrators !");
        model.setViewName("protected");
        return model;

    }

    @RequestMapping(value = "/aboutUs", method = RequestMethod.GET)
    public ModelAndView aboutUs() {
        ModelAndView model = new ModelAndView();
        model.addObject("aboutActive", "active");
        model.setViewName("aboutUs");


        return model;
    }

    //used to test api calls
    @RequestMapping(value="/restStuff", method = RequestMethod.GET)
    public ModelAndView restStuff() {
        ModelAndView model = new ModelAndView();
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + userInfo.getAccess_token());
        ResponseEntity responseEntity = restTemplate.exchange("https://greenreceipt.net/api/values",
                HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
        model.addObject("message", responseEntity.getBody());
        return model;
    }

    //used to test api calls
    @RequestMapping(value="/receiptsTest", method = RequestMethod.GET)
    public ModelAndView receiptsTest() {
        ModelAndView model = new ModelAndView();
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();


        headers.set("Authorization", "Bearer " + userInfo.getAccess_token());
        ResponseEntity responseEntity = restTemplate.exchange("https://greenreceipt.net/api/Receipt",
                HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
        List<ReceiptObject> receipts = gson.fromJson((String) responseEntity.getBody(), new TypeToken<List<ReceiptObject>>() {
        }.getType());
        receiptsContainer.setReceipts(receipts);
        model.addObject("receipts", receipts);
        model.addObject("cardTypeTest", receiptsContainer.getReceipts().get(0).getCardType());
        model.addObject("message", responseEntity.getBody());
        model.setViewName("restStuff");
        return model;
    }


    /**
     * This controllers the mapping of logging in.  Logging in and out and error all direct to the same page
     *
     * @param error     if the user enters in the wrong information the error parameter will equal "error"
     * @param logout    if the user selects to logout the logout parameter will equal "logout"
     * @return          returns the model object that points to the login jsp.
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            HttpSession session) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            session.invalidate();
            SecurityContextHolder.getContext().setAuthentication(null);
            model.addObject("timeout", "setTimeout(\"refreshPage()\", 60000)");
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("login");

        return model;

    }

    /**
     * Returns a new CreateAccount Object
     *
     * @return a new CreateAccount Object
     */
    @ModelAttribute("createAccountObject")
    public CreateAccount getCreateAccountForm() {
        return new CreateAccount();
    }

    @RequestMapping(value = "/createAccountForm", method = RequestMethod.GET)
    public ModelAndView createAccount(ModelAndView model) {
        model.setViewName("createAccountForm");
        return model;

    }
    
    @RequestMapping(value="/createAccountForm", method=RequestMethod.POST)
    public ModelAndView validateForm(@ModelAttribute("createAccountObject") @Valid CreateAccount createAccount, BindingResult bindingResult, ModelAndView model) {
        if (bindingResult.hasErrors()) {
            model.setViewName("createAccountForm");
            return model;
        }
        if(!createAccount.getPassword().equals(createAccount.getConfirmPassword())) {
            model.addObject("error", "Passwords do not match");
            model.setViewName("createAccountForm");
            return model;
        }
        RestTemplate restTemplate = new RestTemplate();
        Gson gson = new Gson();
        String createAccountJson = gson.toJson(createAccount);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity responseEntity = restTemplate.exchange("https://api.greenreceipt.net/api/Account/Register", HttpMethod.POST, new HttpEntity<Object>(createAccountJson, headers), ResponseEntity.class);
        if(responseEntity.getStatusCode().value() == 200) {
            //TODO: MAKE IT SO THE USER KNOWS THEY HAVE SUCCESSFULLY CREATED THEIR ACCOUNT
            model.setViewName("redirect:/login");
            return model;
        } else {
            model.setViewName("createAccountForm");
            return model;
        }

    }

    @RequestMapping(value={"/dashboard", "/"}, method = RequestMethod.GET)
    public ModelAndView initializeDashboard(HttpSession session) {
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ModelAndView model = new ModelAndView();

        String startDate = (String) session.getAttribute("CategoryReportStartDate");
        String endDate = (String) session.getAttribute("CategoryReportEndDate");

        CategoryReport categoryReport = null;
        try {
            categoryReport = GreenReceiptUtil.getCategoryReportItems(startDate, endDate, model, session);
        } catch (ParseException e) {
            //Do Nothing
        }
        GreenReceiptUtil.makeCategoryReportStrings(categoryReport, model, session);

        String startDateTrending = (String) session.getAttribute("TrendingReportStartDate");
        String endDateTrending = (String) session.getAttribute("TrendingReportEndDate");

        TrendingReport trendingReport = null;
        try {
            trendingReport = GreenReceiptUtil.getTrendingReportItems(startDateTrending, endDateTrending, model, session);
        } catch (ParseException e) {
            model.addObject("error", "Please check your dates");
            return model;
        }
        GreenReceiptUtil.makeTrendingReportStrings(trendingReport, model);

        if(session.getAttribute("firstname") == null) {
           session.setAttribute("firstname", userInfo.getFirstName());
        }
        if(session.getAttribute("lastname") == null) {
            session.setAttribute("lastname", userInfo.getLastName());
        }

        List<ReceiptObject> receipts = GreenReceiptUtil.getReceipts();
        if(receipts != null && !receipts.isEmpty()) {
            model.addObject("receipts", receipts);
            receiptsContainer.setReceipts(receipts);
        } else {
            model.setViewName("redirect:/login?logout");
        }

        List<ReceiptObject> returnNotifications = GreenReceiptUtil.getReturnNotifications();
        if(returnNotifications != null) {
            model.addObject("returnNotifications", returnNotifications);
        }

        List<ReceiptObject> recentReceipts = GreenReceiptUtil.getMostRecentReceipts();
        if(recentReceipts != null) {
            model.addObject("recentReceipts", recentReceipts);
        }

        budget = GreenReceiptUtil.getCurrentBudget();

        if(budget != null) {
            List<BudgetItem> budgetItems = budget.getBudgetItems();
            Double budgetTotal = GreenReceiptUtil.getBudgetTotal(budgetItems);


            List<String> colors = GreenReceiptUtil.getChartColors(budget.getBudgetItems().size());

            BudgetPieChart pieChart = new BudgetPieChart();
            List<BudgetPieChartItems> items = new ArrayList<BudgetPieChartItems>();
            int index = 0;
            for(String color : colors) {
                BudgetItem budgetItem = budgetItems.get(index++);
                Double value = budgetItem.getAmountAllowed()/budgetTotal;
                items.add(new BudgetPieChartItems(budgetItem.getCategory().getName(), Math.floor(value * 100), color));
            }

            Gson gson = new Gson();
            model.addObject("pieChartJson", gson.toJson(items));
            model.addObject("budgetItems", budget.getBudgetItems());
        }

        model.addObject("dashboardActive", "active");
        model.setViewName("dashboard");
        return model;
    }

    @RequestMapping(value="/receipt", method = RequestMethod.GET)
    public ModelAndView displayReceipt(@RequestParam(defaultValue = "") String receiptId, @RequestParam(defaultValue = "") String exchange) {
        ModelAndView model = new ModelAndView();
        model.addObject("receiptsActive", "active");
        ReceiptObject receipt = GreenReceiptUtil.getReceipt(receiptId);
        if(receipt != null) {
            model.addObject("receipt", receipt);
        } else {
            model.setViewName("redirect:/login?logout");
        }

        model.addObject("exchange", exchange);
        model.setViewName("receipt");
        return model;
    }

    @RequestMapping(value="/category", method = RequestMethod.GET)
    public ModelAndView displayCategoryReport(HttpSession session, @RequestParam(value = "error", required = false) String error){
        ModelAndView model = new ModelAndView();
        model.addObject("reportActive", "active");

        if(error != null) {
            model.addObject("error", "Please check your dates");
            model.setViewName("category");
            return model;
        }

        String startDate = (String) session.getAttribute("CategoryReportStartDate");
        String endDate = (String) session.getAttribute("CategoryReportEndDate");

        CategoryReport categoryReport = null;
        try {
            categoryReport = GreenReceiptUtil.getCategoryReportItems(startDate, endDate, model, session);
        } catch (ParseException e) {
            model.addObject("error", "Please check your dates");
            return model;
        }
        GreenReceiptUtil.makeCategoryReportStrings(categoryReport, model, session);

        model.addObject("reportsActive", "active");
        model.setViewName("category");
        return model;
    }

    @RequestMapping(value="/categoryDateForm", method = RequestMethod.POST)
    public ModelAndView categoryReportDateChange(@ModelAttribute("categoryReportDates") @Valid CategoryReportDates categoryReportDates, HttpSession session) throws ParseException {
        ModelAndView model = new ModelAndView();

        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = sf.parse(categoryReportDates.getStartDate());
            d = sf.parse(categoryReportDates.getEndDate());
        } catch (Exception e) {
            model.addObject("error", "Please check your dates");
            model.setViewName("redirect:/category");
            return model;
        }

        session.setAttribute("CategoryReportStartDate", categoryReportDates.getStartDate());
        session.setAttribute("CategoryReportEndDate", categoryReportDates.getEndDate());

        String displayStartDate = GreenReceiptUtil.formatDateString(categoryReportDates.getStartDate());
        String displayEndDate = GreenReceiptUtil.formatDateString(categoryReportDates.getEndDate());
        session.setAttribute("CategoryReportStartDateDisplay", displayStartDate);
        session.setAttribute("CategoryReportEndDateDisplay", displayEndDate);

        model.setViewName("redirect:/category");
        return model;
    }

    @RequestMapping(value="/categoryReceiptItems", method = RequestMethod.GET)
    public ModelAndView displayCategoryReceiptItems(@RequestParam(defaultValue = "") String category,
                                                    @RequestParam(defaultValue = "") String startDate,
                                                    @RequestParam(defaultValue = "") String endDate,
                                                    HttpSession session) throws ParseException {
        ModelAndView model = new ModelAndView();
        model.addObject("reportsActive", "active");
        HashMap<String, Integer> categoryMap = (HashMap<String, Integer>) session.getAttribute("categoryMap");

        List<ReceiptItem> receiptItems = GreenReceiptUtil.getCategoryReceiptItems(categoryMap.get(category), startDate, endDate);
        Double total = 0.0;
        for(ReceiptItem item: receiptItems) {
            total += Double.parseDouble(item.getPrice());
        }
        model.addObject("category", category);
        model.addObject("categoryId", categoryMap.get(category));

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        model.addObject("total", formatter.format(total));

        model.addObject("numItems", receiptItems.size());
        model.addObject("receiptItems", receiptItems);
        model.setViewName("categoryReceiptItems");
        return model;
    }

    @RequestMapping(value="/trending", method = RequestMethod.GET)
    public ModelAndView displayTrendingReport( HttpSession session){
        ModelAndView model = new ModelAndView();
        model.addObject("reportActive", "active");

        String startDate = (String) session.getAttribute("TrendingReportStartDate");
        String endDate = (String) session.getAttribute("TrendingReportEndDate");

        TrendingReport trendingReport = null;
        try {
            trendingReport = GreenReceiptUtil.getTrendingReportItems(startDate, endDate, model, session);
        } catch (ParseException e) {
            model.addObject("error", "Please check your dates");
            return model;
        }
        GreenReceiptUtil.makeTrendingReportStrings(trendingReport, model);
        model.addObject("reportsActive", "active");
        model.setViewName("trending");
        return model;
    }

    @RequestMapping(value="/budget", method = RequestMethod.GET)
    public ModelAndView displayBudget(HttpSession session){
        ModelAndView model = new ModelAndView();
        model.addObject("budgetActive", "active");

        if(session.getAttribute("errorMessage") != null) {
            model.addObject("errorMessage", session.getAttribute("errorMessage"));
            session.removeAttribute("errorMessage");
        }

        budget = GreenReceiptUtil.getCurrentBudget();

        if(budget == null) {
            model.addObject("createNew", true);
        }

        if(budget != null) {
            List<BudgetItem> budgetItems = budget.getBudgetItems();
            Double budgetTotal = GreenReceiptUtil.getBudgetTotal(budgetItems);


            List<String> colors = GreenReceiptUtil.getChartColors(budget.getBudgetItems().size());

            BudgetPieChart pieChart = new BudgetPieChart();
            List<BudgetPieChartItems> items = new ArrayList<BudgetPieChartItems>();
            int index = 0;
            for(String color : colors) {
                BudgetItem budgetItem = budgetItems.get(index++);
                Double value = budgetItem.getAmountAllowed()/budgetTotal;
                items.add(new BudgetPieChartItems(budgetItem.getCategory().getName(), Math.floor(value * 100), color));
            }

            Gson gson = new Gson();
            model.addObject("pieChartJson", gson.toJson(items));
        }

        model.addObject("budget", budget);


        model.setViewName("budget");
        return model;
    }

    @RequestMapping(value="/editBudget", method = RequestMethod.GET)
    public ModelAndView displayEditBudget(@RequestParam(value = "error", required = false) String error){
        ModelAndView model = new ModelAndView();
        model.addObject("budgetActive", "active");

        if(error != null) {
            model.addObject("errorMessage", "Please double check your input");
        }
        BudgetObject editBudget = GreenReceiptUtil.getCurrentBudget();

        model.addObject("editBudget", editBudget);

        model.setViewName("editBudget");
        return model;
    }

    @RequestMapping(value="/editBudgetForm", method = RequestMethod.POST)
    public ModelAndView editBudgetFormSubmit(@ModelAttribute("editBudget") BudgetObject editBudget, BindingResult result) {
        ModelAndView model = new ModelAndView();
        model.addObject("budgetActive", "active");

        budget = GreenReceiptUtil.getCurrentBudget();

        List<BudgetItem> editBudgetItems = editBudget.getBudgetItems();
        int index = 0;
        for(BudgetItem item: budget.getBudgetItems()) {
            BudgetItem newBudgetItem = editBudgetItems.get(index++);
            if(newBudgetItem.getAmountAllowed() == null) {
                model.setViewName("redirect:/editBudget?error");
                return model;
            }
            if(item.getId().equals(newBudgetItem.getId()) && item.getAmountAllowed() != newBudgetItem.getAmountAllowed()) {
                item.setAmountAllowed(newBudgetItem.getAmountAllowed());
                GreenReceiptUtil.updateOrAddBudgetItem(item);
            }
        }

        model.setViewName("redirect:/budget");
        return model;
    }

    @RequestMapping(value="/selectCategories", method = RequestMethod.GET)
    public ModelAndView displaySelectCategories(){
        ModelAndView model = new ModelAndView();
        model.addObject("budgetActive", "active");

        List<Category> categories = GreenReceiptUtil.getCategories();

        model.addObject("categoryList", new CategoryList());
        model.addObject("categories", categories);
        model.setViewName("selectCategories");
        return model;
    }

    @RequestMapping(value="/selectCategoriesForm", method = RequestMethod.POST)
    public ModelAndView selectCategoriesFormSubmit(@ModelAttribute("categoryList") CategoryList categoryList, BindingResult result) {
        ModelAndView model = new ModelAndView();
        model.addObject("budgetActive", "active");
        String[] selectedCategories = categoryList.getCategoriesForBudget().split(",");

        model.addObject("selectedCategories", selectedCategories);
        model.setViewName("redirect:/createBudget");
        return model;
    }

    @RequestMapping(value="/createBudget", method = RequestMethod.GET)
    public ModelAndView displayCreateBudget(@RequestParam(defaultValue = "") String[] selectedCategories){
        ModelAndView model = new ModelAndView();
         model.addObject("budgetActive", "active");

        List<BudgetItem> createBudgetItems = new ArrayList<BudgetItem>();
        BudgetObject createBudget = new BudgetObject();
        for(String categoryName : selectedCategories) {
            createBudgetItems.add(new BudgetItem(0.0, new Category(categoryName), 0.0));
        }

        createBudget.setBudgetItems(createBudgetItems);
        budget.setBudgetItems(createBudgetItems);
        model.addObject("createBudget", createBudget);
        model.setViewName("createBudget");
        return model;
    }

    @RequestMapping(value="/createBudgetForm", method = RequestMethod.POST)
    public ModelAndView createBudgetFormSubmit(@ModelAttribute("createBudget") BudgetObject createBudget, BindingResult result) {
        ModelAndView model = new ModelAndView();
        model.addObject("budgetActive", "active");
        Gson gson = new Gson();
        int index = 0;
        for(BudgetItem item : createBudget.getBudgetItems()) {
            budget.getBudgetItems().get(index).setAmountAllowed(item.getAmountAllowed());
            index++;
        }
        Boolean createSuccess = GreenReceiptUtil.createBudget(gson.toJson(budget));
        budget = null;
        model.setViewName("redirect:/budget");
        return model;
    }

    @RequestMapping(value="/addBudgetItem", method = RequestMethod.GET)
    public ModelAndView addBudgetItem(@RequestParam(defaultValue = "") String categoryName, @RequestParam(defaultValue = "") String amountAllowed, HttpSession session) {
        ModelAndView model = new ModelAndView();
        model.addObject("budgetActive", "active");

        budget = GreenReceiptUtil.getCurrentBudget();

        Double amountAllowedDouble = null;
        try {
            amountAllowedDouble = Double.parseDouble(amountAllowed);
        } catch (Exception e) {
            session.setAttribute("errorMessage", "Please input a valid limit");
            model.addObject("errorMessage", "Please input a valid limit");
            model.setViewName("redirect:/budget");
            return model;
        }

        if(categoryName.length() == 0) {
            session.setAttribute("errorMessage", "Please input a category name");
            model.addObject("errorMessage", "Please input a category name");
            model.setViewName("redirect:/budget");
            return model;
        }

        Category category = new Category(categoryName);

        GreenReceiptUtil.updateOrAddBudgetItem(new BudgetItem(amountAllowedDouble, category, budget.getId()));


        model.setViewName("redirect:/budget");
        return model;
    }

    @RequestMapping(value="/deleteBudgetItem", method = RequestMethod.GET)
    public ModelAndView deleteBudgetItem(@RequestParam(defaultValue = "") String id) {
        ModelAndView model = new ModelAndView();
        model.addObject("budgetActive", "active");

        GreenReceiptUtil.deleteBudgetItem(id);


        model.setViewName("redirect:/budget");
        return model;
    }

    @RequestMapping(value="/receipts", method = RequestMethod.GET)
    public ModelAndView displayReceipts(HttpSession session){
        ModelAndView model = new ModelAndView();
        model.addObject("receiptsActive", "active");

        List<ReceiptObject> receipts = null;
        String amount = (String) session.getAttribute("numReceipts");
        if(amount != null && !amount.equals("All")) {
            Integer amountNum = Integer.parseInt(amount);
            receipts = GreenReceiptUtil.getReceipts(amountNum);
        } else {
            receipts = GreenReceiptUtil.getReceipts();
        }
        //TODO: Get all receipts call

        if(receipts != null && !receipts.isEmpty()) {
            model.addObject("receipt", receipts);
            receiptsContainer.setReceipts(receipts);
        } else {
            model.setViewName("redirect:/login?logout");
        }

        model.addObject("receipts", receipts);
        model.setViewName("receipts");
        return model;
    }

    @RequestMapping(value="/receiptsMap", method = RequestMethod.GET)
    public ModelAndView displayReceiptsMap(HttpSession session){
        ModelAndView model = new ModelAndView();
        model.addObject("receiptsActive", "active");

        List<ReceiptObject> receipts = null;
        String amount = (String) session.getAttribute("numReceipts");
        if(amount != null && !amount.equals("All")) {
            Integer amountNum = Integer.parseInt(amount);
            receipts = GreenReceiptUtil.getReceipts(amountNum);
        } else {
            receipts = GreenReceiptUtil.getReceipts();
        }

        if(receipts != null && !receipts.isEmpty()) {
            model.addObject("receipt", receipts);
            receiptsContainer.setReceipts(receipts);
        } else {
            model.setViewName("redirect:/login?logout");
        }

        model.addObject("receiptsCount", receipts.size());
        model.addObject("receipts", receipts);
        model.setViewName("receiptsMap");
        return model;
    }

    @RequestMapping(value="/numReceiptsForm", method = RequestMethod.POST)
    public ModelAndView numReceiptsForm(@ModelAttribute("receiptViewAmount") @Valid ReceiptsViewAmount receiptsViewAmount, HttpSession session) {
        ModelAndView model = new ModelAndView();

        session.setAttribute("numReceipts", receiptsViewAmount.getNumReceipts());

        model.setViewName("redirect:" + receiptsViewAmount.getView());
        return model;
    }

    @RequestMapping(value="/sendEmail", method = RequestMethod.GET)
    @ResponseBody
    public String sendEmail(@RequestParam(defaultValue = "") String receiptId) {
        ModelAndView model = new ModelAndView();
        ReceiptObject receipt = GreenReceiptUtil.getReceipt(receiptId);
        if(receipt != null) {
            model.addObject("receipt", receipt);
        } else {
            return "Receipt could not be sent, please try again later";
        }

        MailUtility mailUtility = new MailUtility();
        try {
            mailUtility.sendMail("Green Receipt", receipt);
        } catch (Exception e) {
            return "Receipt could not be sent, please try again later";
        }

        return "Receipt successfully sent!";
    }

    @RequestMapping(value="/deleteReceipt", method = RequestMethod.GET)
    public ModelAndView deleteReceipt(@RequestParam(defaultValue = "") String id) {
        ModelAndView model = new ModelAndView();
        model.addObject("receiptsActive", "active");

        GreenReceiptUtil.deleteReceipt(id);


        model.setViewName("redirect:/receipts");
        return model;
    }

    @RequestMapping(value = "/downloadPDF", method = RequestMethod.GET)
    public ModelAndView downloadExcel(@RequestParam(defaultValue = "") String categoryId,
                                      @RequestParam(defaultValue = "") String startDate,
                                      @RequestParam(defaultValue = "") String endDate,
                                      HttpSession session) throws ParseException {



        List<ReceiptObject> receipts = GreenReceiptUtil.getCategoryReceipts(categoryId, startDate, endDate, session);

        return new ModelAndView("pdfView", "receipts", receipts);
    }

    @RequestMapping(value = "/downloadReceiptPDF", method = RequestMethod.GET)
    public ModelAndView downloadReceiptPDF(@RequestParam(defaultValue = "") String receiptId,
                                      @RequestParam(defaultValue = "") String startDate,
                                      @RequestParam(defaultValue = "") String endDate,
                                      HttpSession session) throws ParseException {



//        List<ReceiptObject> receipts = GreenReceiptUtil.getCategoryReceipts(categoryId, startDate, endDate, session);
        ReceiptObject receipt = GreenReceiptUtil.getReceipt(receiptId);
        return new ModelAndView("receiptPdfView", "receipt", receipt);
    }

    @RequestMapping(value="/manageCards", method = RequestMethod.GET)
    public ModelAndView manageCards(HttpSession session) {
        ModelAndView model = new ModelAndView();
        model.addObject("settingsActive", "active");

        List<CreditCardObject> allIds = GreenReceiptUtil.getCreditCards();

        List<CreditCardObject> creditCards = new LinkedList<CreditCardObject>();
        for(CreditCardObject card: allIds) {
            if(card.getAccountId().length() != 9) {
                creditCards.add(card);
            }
        }
        if(session.getAttribute("manageCardError") != null) {
            model.addObject("errorMessage", session.getAttribute("manageCardError"));
            session.removeAttribute("manageCardError");
        }
        model.addObject("cards", creditCards);
        model.setViewName("manageCards");
        return model;
    }

    @RequestMapping(value="/addCard", method = RequestMethod.POST)
    public ModelAndView addCard(@ModelAttribute("cardFormObject") @Valid CreditCardFormObject cardFormObject, BindingResult result, HttpSession session) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        ModelAndView model = new ModelAndView();

        if (result.hasErrors()) {
            String errorMessage = "";
            for(int i = 0; i<result.getAllErrors().size(); i++) {
                if(result.getAllErrors().get(i).getDefaultMessage().equals("must match \"[\\d]{4}\"")) {
                    errorMessage += "Number must be a four digit number<br>";
                } else {
                    errorMessage += result.getAllErrors().get(i).getDefaultMessage() + "<br>";
                }
            }
            session.setAttribute("manageCardError", errorMessage);
            model.setViewName("redirect:/manageCards");
            return model;
        }

        String hash = GreenReceiptUtil.hashCard(cardFormObject.getFirstFour(), cardFormObject.getLastFour(), cardFormObject.getCardName());
        if(GreenReceiptUtil.addCard(hash, cardFormObject.getLastFour()) == null) {
            session.setAttribute("manageCardError", "An Error occurred adding your card");
            model.setViewName("redirect:/manageCards");
            return model;
        }

        model.setViewName("redirect:/manageCards");
        return model;
    }

    @RequestMapping(value="/editCard", method = RequestMethod.POST)
    public ModelAndView editCard(@ModelAttribute("cardFormObject") @Valid CreditCardFormObject cardFormObject, BindingResult result, HttpSession session) throws NoSuchAlgorithmException {
        ModelAndView model = new ModelAndView();

        if (result.hasErrors()) {
            String errorMessage = "";
            for(int i = 0; i<result.getAllErrors().size(); i++) {
                if(result.getAllErrors().get(i).getDefaultMessage().equals("must match \"[\\d]{4}\"")) {
                    errorMessage += "Number must be a four digit number<br>";
                } else {
                    errorMessage += result.getAllErrors().get(i).getDefaultMessage() + "<br>";
                }
            }
            session.setAttribute("manageCardError", errorMessage);
            model.setViewName("redirect:/manageCards");
            return model;
        }

        if(GreenReceiptUtil.deleteCard(cardFormObject.getCardId())) {
            String hash = GreenReceiptUtil.hashCard(cardFormObject.getFirstFour(), cardFormObject.getLastFour(), cardFormObject.getCardHash());
            GreenReceiptUtil.addCard(hash, cardFormObject.getLastFour());
        } else {
            session.setAttribute("manageCardError", "An Error occurred editing your card");
            model.setViewName("redirect:/manageCards");
            return model;//error handling
        }
        model.setViewName("redirect:/manageCards");
        return model;
    }

    @RequestMapping(value="/deleteCard", method = RequestMethod.GET)
    public ModelAndView deleteCard(@RequestParam(defaultValue = "") String id) {
        ModelAndView model = new ModelAndView();

        GreenReceiptUtil.deleteCard(id);


        model.setViewName("redirect:/manageCards");
        return model;
    }
}