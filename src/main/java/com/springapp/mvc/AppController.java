package com.springapp.mvc;


import Forms.CreateAccount;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@Scope("request")
public class AppController {

    @Autowired
    private User user;

    @Autowired
    private Receipt receipt;

    @Autowired
    private Budget budget;

    @RequestMapping(value = "/protected**", method = RequestMethod.GET)
    public ModelAndView protectedPage() {

        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security 3.2.3 Hello World");
        model.addObject("message", "This is protected page - Only for Administrators !");
        model.setViewName("protected");
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

    //Spring Security see this :
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("login");

        return model;

    }

    @ModelAttribute("createAccountObject")
    public CreateAccount getCreateAccountForm() {
        return new CreateAccount();
    }

    @RequestMapping(value = "/createAccountForm", method = RequestMethod.GET)
    public ModelAndView createAccount(ModelAndView model) {
        model.addObject("message", "Please enter your information");
        model.setViewName("createAccountForm");
        return model;

    }
    
    @RequestMapping(value="/createAccountForm", method=RequestMethod.POST)
    public ModelAndView validateForm(@ModelAttribute("createAccountObject") @Valid CreateAccount createAccount, BindingResult bindingResult, ModelAndView model) {
        if (bindingResult.hasErrors()) {
            model.setViewName("createAccountForm");
            return model;
        }
        RestTemplate restTemplate = new RestTemplate();
        Gson gson = new Gson();
        String createAccountJson = gson.toJson(createAccount);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity responseEntity = restTemplate.exchange("http://greenreceipt.net/api/Account/Register", HttpMethod.POST, new HttpEntity<Object>(createAccountJson, headers), ResponseEntity.class);
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
        if(session.getAttribute("username") == null) {
            session.setAttribute("username", userInfo.getUserName());
        }
        model.addObject("dashboardActive", "active");
        model.setViewName("dashboard");
        return model;
    }

    @RequestMapping(value="/receipt", method = RequestMethod.GET)
    public ModelAndView displayReceipt(@RequestParam(defaultValue = "") String receiptId) {
        ModelAndView model = new ModelAndView();
        model.addObject("receiptsActive", "active");

        // This code will be replaced by an API call using the passed in ReceiptId
        if("receipt1".equals(receiptId)){
            List<ReceiptItem> receiptItems = new ArrayList<ReceiptItem>();

            ReceiptItem item1 = new ReceiptItem();
            item1.setName("Milk");
            item1.setPrice("$3.00");

            ReceiptItem item2 = new ReceiptItem();
            item2.setName("Eggs");
            item2.setPrice("$2.00");

            receiptItems.add(item1);
            receiptItems.add(item2);
            receipt.setItems(receiptItems);
            receipt.setStore("Smith's");
            receipt.setTotal("$5.00");
            receipt.setReturnDate(null);
            model.addObject("receipt", receipt);
        } else if("receipt2".equals(receiptId)) {
            List<ReceiptItem> receiptItems = new ArrayList<ReceiptItem>();

            ReceiptItem item1 = new ReceiptItem();
            item1.setName("Samsung TV");
            item1.setPrice("$500.00");

            receiptItems.add(item1);
            receipt.setItems(receiptItems);
            receipt.setStore("Best Buy");
            receipt.setTotal("$500.00");
            receipt.setReturnDate(null);
            model.addObject("receipt", receipt);
        } else if("receipt3".equals(receiptId)) {
            List<ReceiptItem> receiptItems = new ArrayList<ReceiptItem>();

            ReceiptItem item1 = new ReceiptItem();
            item1.setName("Dakine Backpack");
            item1.setPrice("$75.00");

            receiptItems.add(item1);
            receipt.setItems(receiptItems);
            receipt.setStore("Zumiez");
            receipt.setTotal("$75.00");
            receipt.setReturnDate("December 8, 2014");
            model.addObject("receipt", receipt);
        }

        model.setViewName("receipt");
        return model;
    }

    @RequestMapping(value="/trending", method = RequestMethod.GET)
    public ModelAndView displayTrendingReport(){
        ModelAndView model = new ModelAndView();
        model.addObject("reportActive", "active");
        model.setViewName("trending");
        return model;
    }

    @RequestMapping(value="/category", method = RequestMethod.GET)
    public ModelAndView displayCategoryReport(){
        ModelAndView model = new ModelAndView();
        model.addObject("reportActive", "active");
        model.setViewName("category");
        return model;
    }

    @RequestMapping(value="/budget", method = RequestMethod.GET)
    public ModelAndView displayBudget(){
        ModelAndView model = new ModelAndView();
        model.addObject("budgetActive", "active");
        model.addObject("budget", budget);


        model.setViewName("budget");
        return model;
    }

    @RequestMapping(value="/editBudget", method = RequestMethod.GET)
    public ModelAndView displayEditBudget(){
        ModelAndView model = new ModelAndView();
        model.addObject("budgetActive", "active");
        Budget editBudget = new Budget();
        List<BudgetItem> editBudgetItems = new ArrayList<BudgetItem>();
        for(BudgetItem item : budget.getBudgetItems()) {
            BudgetItem budgetItem = new BudgetItem(item.getAmountAllowed(), item.getCategory(), item.getAmountUsed());
            editBudgetItems.add(budgetItem);
        }
        editBudget.setBudgetItems(editBudgetItems);
        model.addObject("editBudget", editBudget);

        model.setViewName("editBudget");
        return model;
    }

    @RequestMapping(value="/editBudgetForm", method = RequestMethod.POST)
    public ModelAndView editBudgetFormSubmit(@ModelAttribute("editBudget") Budget editBudget, BindingResult result) {
        ModelAndView model = new ModelAndView();
        model.addObject("budgetActive", "active");
        List<BudgetItem> editBudgetItems = editBudget.getBudgetItems();
        int index = 0;
        for(BudgetItem item: budget.getBudgetItems()) {
            item.setAmountAllowed(editBudgetItems.get(index++).getAmountAllowed());
        }

        model.setViewName("redirect:/budget");
        return model;
    }

    @RequestMapping(value="/receipts", method = RequestMethod.GET)
    public ModelAndView displayReceipts(){
        ModelAndView model = new ModelAndView();
        model.addObject("receiptsActive", "active");

        List<Receipt> receipts = new ArrayList<Receipt>();
        Receipt receipt1 = new Receipt("Smiths", null, "$50.00", "12/12/14");
        Receipt receipt2 = new Receipt("Best Buy", null, "$50.00", "12/12/14");
        Receipt receipt3 = new Receipt("Costco", null, "$50.00", "12/12/14");
        Receipt receipt4 = new Receipt("Sephora", null, "$50.00", "12/12/14");
        receipts.add(receipt1);
        receipts.add(receipt2);
        receipts.add(receipt3);
        receipts.add(receipt4);
        model.addObject("receipts", receipts);
        model.setViewName("receipts");
        return model;
    }
}