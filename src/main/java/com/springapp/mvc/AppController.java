package com.springapp.mvc;


import Forms.CreateAccount;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
import java.util.Base64;

@Controller
@Scope("request")
public class AppController {

    @Autowired
    private User user;

    @RequestMapping(value = { "/", "/helloworld**" }, method = RequestMethod.GET)
    public ModelAndView welcomePage() {

        ModelAndView model = new ModelAndView();
        model.addObject("title", "Green Receipt");
        model.addObject("message", "Welcome Page !!!!!");
        model.setViewName("helloworld");
        return model;

    }

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
        ResponseEntity responseEntity = restTemplate.exchange("http://192.168.1.114/MarketingRest/api/authorize", HttpMethod.GET, new HttpEntity<Object>(createHeaders(username, password)), String.class);
        Gson gson = new Gson();
        User user = gson.fromJson((String) responseEntity.getBody(), new TypeToken<User>(){}.getType());
        model.addObject("authToken", user.getAuthToken());
        model.addObject("refreshToken", user.getRefreshToken());
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
        model.setViewName("createAccountForm");
        return model;

    }
    
    @RequestMapping(value="/createAccountForm", method=RequestMethod.POST)
    public ModelAndView validateForm(@ModelAttribute("createAccountObject") @Valid CreateAccount createAccount, BindingResult bindingResult, ModelAndView model) {
        if (bindingResult.hasErrors()) {
            model.setViewName("createAccountForm");
            return model;
        }
        user.setUsername(createAccount.getUsername());
        model.setViewName("redirect:/login");
        return model;
    }

    @RequestMapping(value="/dashboard", method = RequestMethod.GET)
    public ModelAndView initializeDashboard() {
        ModelAndView model = new ModelAndView();
        model.setViewName("dashboard");
        return model;
    }

    HttpHeaders createHeaders( final String username, final String password ){
        return new HttpHeaders(){
            {
                String auth = username + password;
                byte[] encodedAuth = Base64.getEncoder().encode(
                        auth.getBytes(Charset.forName("US-ASCII")));
                String authHeader = new String( encodedAuth );
                set( "Authorization", authHeader );
            }
        };
    }

}