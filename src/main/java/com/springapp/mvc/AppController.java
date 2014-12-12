package com.springapp.mvc;


import Forms.CreateAccount;
import Utilities.MailUtility;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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
    public ModelAndView displayReceipt(@RequestParam(defaultValue = "") String receiptId, @RequestParam(defaultValue = "") String exchange) {
        ModelAndView model = new ModelAndView();
        model.addObject("receiptsActive", "active");

        // This code will be replaced by an API call using the passed in ReceiptId
        if("1".equals(receiptId)){
            List<ReceiptItem> receiptItems = new ArrayList<ReceiptItem>();

            ReceiptItem item1 = new ReceiptItem();
            item1.setName("Milk");
            item1.setPrice("$3.00");

            ReceiptItem item2 = new ReceiptItem();
            item2.setName("Eggs");
            item2.setPrice("$2.00");

            receiptItems.add(item1);
            receiptItems.add(item2);
            receipt.setReceiptId(receiptId);
            receipt.setItems(receiptItems);
            receipt.setStore("Smith's");
            receipt.setTotal("$5.00");
            receipt.setReturnDate(null);
            model.addObject("receipt", receipt);
        } else if("2".equals(receiptId)) {
            List<ReceiptItem> receiptItems = new ArrayList<ReceiptItem>();

            ReceiptItem item1 = new ReceiptItem();
            item1.setName("Chicken");
            item1.setPrice("$20.00");

            ReceiptItem item2 = new ReceiptItem();
            item2.setName("Cake");
            item2.setPrice("$20.00");

            ReceiptItem item3 = new ReceiptItem();
            item3.setName("Cereal");
            item3.setPrice("$20.00");

            receiptItems.add(item1);
            receiptItems.add(item2);
            receiptItems.add(item3);
            receipt.setReceiptId(receiptId);
            receipt.setItems(receiptItems);
            receipt.setStore("Smith's");
            receipt.setTotal("$60.00");
            receipt.setReturnDate(null);
            model.addObject("receipt", receipt);
        } else if("3".equals(receiptId)) {
            List<ReceiptItem> receiptItems = new ArrayList<ReceiptItem>();

            ReceiptItem item1 = new ReceiptItem();
            item1.setName("Bagels");
            item1.setPrice("$15.00");

            ReceiptItem item2 = new ReceiptItem();
            item2.setName("Chips");
            item2.setPrice("$20.00");

            receiptItems.add(item1);
            receiptItems.add(item2);
            receipt.setReceiptId(receiptId);
            receipt.setItems(receiptItems);
            receipt.setStore("Smith's");
            receipt.setTotal("$35.00");
            receipt.setReturnDate(null);
            model.addObject("receipt", receipt);
        } else if("4".equals(receiptId)) {
            List<ReceiptItem> receiptItems = new ArrayList<ReceiptItem>();

            ReceiptItem item1 = new ReceiptItem();
            item1.setName("Samsung TV");
            item1.setPrice("$500.00");

            receiptItems.add(item1);
            receipt.setReceiptId(receiptId);
            receipt.setItems(receiptItems);
            receipt.setStore("Best Buy");
            receipt.setTotal("$500.00");
            receipt.setReturnDate(null);
            model.addObject("receipt", receipt);
        } else if("5".equals(receiptId)) {
            List<ReceiptItem> receiptItems = new ArrayList<ReceiptItem>();

            ReceiptItem item1 = new ReceiptItem();
            item1.setName("Adult Ticket");
            item1.setPrice("$8.00");

            receiptItems.add(item1);
            receiptItems.add(item1);
            receiptItems.add(item1);
            receiptItems.add(item1);
            receiptItems.add(item1);
            receiptItems.add(item1);
            receipt.setReceiptId(receiptId);
            receipt.setItems(receiptItems);
            receipt.setStore("Megaplex");
            receipt.setTotal("$48.00");
            receipt.setReturnDate(null);
            model.addObject("receipt", receipt);
        } else if("6".equals(receiptId)) {
            List<ReceiptItem> receiptItems = new ArrayList<ReceiptItem>();

            ReceiptItem item1 = new ReceiptItem();
            item1.setName("Dinner Meal");
            item1.setPrice("$50.00");

            receiptItems.add(item1);
            receiptItems.add(item1);
            receiptItems.add(item1);

            receipt.setReceiptId(receiptId);
            receipt.setItems(receiptItems);
            receipt.setStore("Rodizio's Grill");
            receipt.setTotal("$150.00");
            receipt.setReturnDate(null);
            model.addObject("receipt", receipt);
        } else if("7".equals(receiptId)) {
            List<ReceiptItem> receiptItems = new ArrayList<ReceiptItem>();

            ReceiptItem item1 = new ReceiptItem();
            item1.setName("Dakine Backpack");
            item1.setPrice("$75.00");

            receiptItems.add(item1);

            receipt.setReceiptId(receiptId);
            receipt.setItems(receiptItems);
            receipt.setStore("Zumiez");
            receipt.setTotal("$75.00");
            receipt.setReturnDate("December 12, 2014");
            model.addObject("receipt", receipt);
        } else if("8".equals(receiptId)) {
            List<ReceiptItem> receiptItems = new ArrayList<ReceiptItem>();

            ReceiptItem item1 = new ReceiptItem();
            item1.setName("Unleaded Gas");
            item1.setPrice("$100.00");

            receiptItems.add(item1);

            receipt.setReceiptId(receiptId);
            receipt.setItems(receiptItems);
            receipt.setStore("Chevron");
            receipt.setTotal("$100.00");
            receipt.setReturnDate(null);
            model.addObject("receipt", receipt);
        } else if("8".equals(receiptId)) {
            List<ReceiptItem> receiptItems = new ArrayList<ReceiptItem>();

            ReceiptItem item1 = new ReceiptItem();
            item1.setName("Unleaded Gas");
            item1.setPrice("$100.00");

            receiptItems.add(item1);

            receipt.setReceiptId(receiptId);
            receipt.setItems(receiptItems);
            receipt.setStore("Chevron");
            receipt.setTotal("$100.00");
            receipt.setReturnDate(null);
            model.addObject("receipt", receipt);
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

        List<Receipt> receipts = new ArrayList<Receipt>();
        Receipt receipt1 = new Receipt("1", "Smiths", null, "$5.00", "");
        Receipt receipt2 = new Receipt("2", "Smiths", null, "$60.00", "");
        Receipt receipt3 = new Receipt("3", "Smiths", null, "$35.00", "");
        Receipt receipt4 = new Receipt("4", "Best Buy", null, "$500.00", "12/21/14");
        Receipt receipt5 = new Receipt("5", "Megaplex", null, "$48.00", "");
        Receipt receipt6 = new Receipt("6", "Rodizio's Grill", null, "$150.00", "");
        Receipt receipt7 = new Receipt("7", "Zumiez", null, "$75.00", "12/12/14");
        Receipt receipt8 = new Receipt("8", "Chevron", null, "$100.00", "");
        Receipt receipt9 = new Receipt("9", "Chevron", null, "$100.00", "");
        receipts.add(receipt1);
        receipts.add(receipt2);
        receipts.add(receipt3);
        receipts.add(receipt4);
        receipts.add(receipt5);
        receipts.add(receipt6);
        receipts.add(receipt7);
        receipts.add(receipt8);
        receipts.add(receipt9);
        model.addObject("receipts", receipts);
        model.setViewName("receipts");
        return model;
    }

    @RequestMapping(value="/sendEmail", method = RequestMethod.GET)
    @ResponseBody
    public String sendEmail(@RequestParam(defaultValue = "") String receiptId) {

       //TODO: ADD REST OF THE RECEIPTS
        Receipt receipt1 = new Receipt();

        if("1".equals(receiptId)){
            List<ReceiptItem> receiptItems = new ArrayList<ReceiptItem>();

            ReceiptItem item1 = new ReceiptItem();
            item1.setName("Milk");
            item1.setPrice("$3.00");

            ReceiptItem item2 = new ReceiptItem();
            item2.setName("Eggs");
            item2.setPrice("$2.00");

            receiptItems.add(item1);
            receiptItems.add(item2);
            receipt1.setItems(receiptItems);
            receipt1.setStore("Smiths");
            receipt1.setTotal("$5.00");
            receipt1.setReturnDate("");
        } else if("2".equals(receiptId)) {
            List<ReceiptItem> receiptItems = new ArrayList<ReceiptItem>();

            ReceiptItem item1 = new ReceiptItem();
            item1.setName("Chicken");
            item1.setPrice("$20.00");

            ReceiptItem item2 = new ReceiptItem();
            item2.setName("Cake");
            item2.setPrice("$20.00");

            ReceiptItem item3 = new ReceiptItem();
            item3.setName("Cereal");
            item3.setPrice("$20.00");

            receiptItems.add(item1);
            receiptItems.add(item2);
            receiptItems.add(item3);
            receipt1.setItems(receiptItems);
            receipt1.setStore("Smiths");
            receipt1.setTotal("$60.00");
            receipt1.setReturnDate("");
        } else if("3".equals(receiptId)) {
            List<ReceiptItem> receiptItems = new ArrayList<ReceiptItem>();

            ReceiptItem item1 = new ReceiptItem();
            item1.setName("Bagels");
            item1.setPrice("$15.00");

            ReceiptItem item2 = new ReceiptItem();
            item2.setName("Chips");
            item2.setPrice("$20.00");

            receiptItems.add(item1);
            receiptItems.add(item2);
            receipt1.setItems(receiptItems);
            receipt1.setStore("Smiths");
            receipt1.setTotal("$35.00");
            receipt1.setReturnDate("");
        } else if("4".equals(receiptId)) {
            List<ReceiptItem> receiptItems = new ArrayList<ReceiptItem>();

            ReceiptItem item1 = new ReceiptItem();
            item1.setName("Samsung TV");
            item1.setPrice("$500.00");

            receiptItems.add(item1);
            receipt1.setItems(receiptItems);
            receipt1.setStore("Best Buy");
            receipt1.setTotal("$500.00");
            receipt1.setReturnDate("12/21/14");
        } else if("5".equals(receiptId)) {
            List<ReceiptItem> receiptItems = new ArrayList<ReceiptItem>();

            ReceiptItem item1 = new ReceiptItem();
            item1.setName("Adult Ticket");
            item1.setPrice("$8.00");

            receiptItems.add(item1);
            receiptItems.add(item1);
            receiptItems.add(item1);
            receiptItems.add(item1);
            receiptItems.add(item1);
            receiptItems.add(item1);
            receipt1.setItems(receiptItems);
            receipt1.setStore("Megaplex");
            receipt1.setTotal("$48.00");
            receipt1.setReturnDate("");
        } else if("6".equals(receiptId)) {
            List<ReceiptItem> receiptItems = new ArrayList<ReceiptItem>();

            ReceiptItem item1 = new ReceiptItem();
            item1.setName("Dinner Meal");
            item1.setPrice("$50.00");

            receiptItems.add(item1);
            receiptItems.add(item1);
            receiptItems.add(item1);

            receipt1.setItems(receiptItems);
            receipt1.setStore("Rodizio's Grill");
            receipt1.setTotal("$150.00");
            receipt1.setReturnDate("");
        } else if("7".equals(receiptId)) {
            List<ReceiptItem> receiptItems = new ArrayList<ReceiptItem>();

            ReceiptItem item1 = new ReceiptItem();
            item1.setName("Dakine Backpack");
            item1.setPrice("$75.00");

            receiptItems.add(item1);
            receipt1.setItems(receiptItems);
            receipt1.setStore("Zumiez");
            receipt1.setTotal("$75.00");
            receipt1.setReturnDate("12/12/14");
        } else if("8".equals(receiptId)) {
            List<ReceiptItem> receiptItems = new ArrayList<ReceiptItem>();

            ReceiptItem item1 = new ReceiptItem();
            item1.setName("Unleaded Gas");
            item1.setPrice("$100.00");

            receiptItems.add(item1);
            receipt1.setItems(receiptItems);
            receipt1.setStore("Chevron");
            receipt1.setTotal("$100.00");
            receipt1.setReturnDate("");
        } else if("8".equals(receiptId)) {
            List<ReceiptItem> receiptItems = new ArrayList<ReceiptItem>();

            ReceiptItem item1 = new ReceiptItem();
            item1.setName("Unleaded Gas");
            item1.setPrice("$100.00");

            receiptItems.add(item1);
            receipt1.setItems(receiptItems);
            receipt1.setStore("Chevron");
            receipt1.setTotal("$100.00");
            receipt1.setReturnDate("");
        }

        MailUtility mailUtility = new MailUtility();
        mailUtility.sendMail("Green Receipt", receipt1);
        return "Receipt successfully sent!";

    }
}