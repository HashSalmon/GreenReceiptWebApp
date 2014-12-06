package com.springapp.mvc;


import Forms.CreateAccount;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
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

    @RequestMapping(value="/restStuff", method = RequestMethod.GET)
    public ModelAndView restStuff() {
        ModelAndView model = new ModelAndView();
        RestTemplate restTemplate = new RestTemplate();
        String username = "jordan";
        String password = "test";
//        ResponseEntity responseEntity = restTemplate.exchange("http://192.168.1.114/MarketingRest/api/authorize", HttpMethod.GET, new HttpEntity<Object>(createHeaders(username, password)), String.class);
//        Gson gson = new Gson();
//        User user = gson.fromJson((String) responseEntity.getBody(), new TypeToken<User>(){}.getType());
//        model.addObject("message", responseEntity.getBody());
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
//        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
//        restTemplate.setRequestFactory(requestFactory);
        ResponseEntity responseEntity = restTemplate.exchange("http://10.0.0.22/api/Account/Register", HttpMethod.POST, new HttpEntity<Object>(createAccountJson, headers), ResponseEntity.class);
//        ResponseEntity responseEntity = restTemplate.postForObject("http://10.0.0.22/api/Account/Register", createAccountJson, ResponseEntity.class);
        if(responseEntity.getStatusCode().value() == 200) {
            model.addObject("creationSuccessful", "You have successfully created your account! Log In now to continue.");
            model.setViewName("redirect:/login");
            return model;
        } else {
            model.setViewName("createAccountForm");
            return model;
        }

    }

    @RequestMapping(value="/dashboard", method = RequestMethod.GET)
    public ModelAndView initializeDashboard() {
        //TODO
        // Add in "Hello (Username)"
        ModelAndView model = new ModelAndView();
        model.addObject("dashboardActive", "active");
        model.setViewName("dashboard");
        return model;
    }

    @RequestMapping(value="/receipt", method = RequestMethod.GET)
    public ModelAndView displayReceipt(@RequestParam(defaultValue = "") String receiptId) {
        ModelAndView model = new ModelAndView();
        model.addObject("receiptActive", "active");

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


        model.setViewName("editBudget");
        return model;
    }
}