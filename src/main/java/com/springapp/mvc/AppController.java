package com.springapp.mvc;


import Forms.CreateAccount;
import Utilities.GreenReceiptUtil;
import Utilities.MailUtility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@Scope("request")
public class AppController {

    @Autowired
    private User user;

    @Autowired
    private Budget budget;

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
        List<Receipt> receipts = gson.fromJson((String) responseEntity.getBody(), new TypeToken<List<Receipt>>() {
        }.getType());
        receiptsContainer.setReceipts(receipts);
        model.addObject("receipts", receipts);
        model.addObject("cardTypeTest", receiptsContainer.getReceipts().get(0).getCardType());
        model.addObject("message", responseEntity.getBody());
        model.setViewName("restStuff");
        return model;
    }

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
        ResponseEntity responseEntity = restTemplate.exchange("https://greenreceipt.net/api/Account/Register", HttpMethod.POST, new HttpEntity<Object>(createAccountJson, headers), ResponseEntity.class);
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

        List<Receipt> receipts = GreenReceiptUtil.getReceipts();
        if(receipts != null && !receipts.isEmpty()) {
            model.addObject("receipt", receipts);
            receiptsContainer.setReceipts(receipts);
        } else {
            model.setViewName("redirect:/login?logout");
        }

        model.addObject("receipts", receipts);
        model.addObject("dashboardActive", "active");
        model.setViewName("dashboard");
        return model;
    }

    @RequestMapping(value="/receipt", method = RequestMethod.GET)
    public ModelAndView displayReceipt(@RequestParam(defaultValue = "") String receiptId, @RequestParam(defaultValue = "") String exchange) {
        ModelAndView model = new ModelAndView();
        model.addObject("receiptsActive", "active");
        Receipt receipt = GreenReceiptUtil.getReceipt(receiptId);
        if(receipt != null) {
            model.addObject("receipt", receipt);
        } else {
            model.setViewName("redirect:/login?logout");
        }

        model.addObject("exchange", exchange);
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
        List<Receipt> receipts = GreenReceiptUtil.getReceipts();
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

    @RequestMapping(value="/sendEmail", method = RequestMethod.GET)
    @ResponseBody
    public String sendEmail(@RequestParam(defaultValue = "") String receiptId) {
        ModelAndView model = new ModelAndView();
        Receipt receipt = GreenReceiptUtil.getReceipt(receiptId);
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
}