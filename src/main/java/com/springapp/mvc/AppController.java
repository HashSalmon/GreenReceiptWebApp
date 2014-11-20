package com.springapp.mvc;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.nio.charset.Charset;
import java.util.Base64;

@Controller
public class AppController {

    @RequestMapping(value = { "/", "/helloworld**" }, method = RequestMethod.GET)
    public ModelAndView welcomePage() {

        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security 3.2.3 Hello World Application");
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

    @RequestMapping(value = "/confidential**", method = RequestMethod.GET)
    public ModelAndView superAdminPage() {

        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security 3.2.3 Hello World");
        model.addObject("message", "This is confidential page - Need Super Admin Role !");
        model.setViewName("protected");

        return model;

    }

    @RequestMapping(value = "/protectedTester", method = RequestMethod.GET)
    public ModelAndView tester() {
        ModelAndView model = new ModelAndView();
        model.addObject("message", "ollo");
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
        Page page = gson.fromJson((String) responseEntity.getBody(), new TypeToken<Page>(){}.getType());
        model.addObject("authToken", page.AuthToken);
        model.addObject("refreshToken", page.RefreshToken);
        model.addObject("message", responseEntity.getBody());
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