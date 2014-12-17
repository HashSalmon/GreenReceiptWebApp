package Utilities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.springapp.mvc.Receipt;
import com.springapp.mvc.UserInfo;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class GreenReceiptUtil {

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

        List<Receipt> receipts = gson.fromJson((String) responseEntity.getBody(), new TypeToken<List<Receipt>>() {
        }.getType());
        return receipts;
    }

    public static Receipt getReceipt(String recieptId) {
        RestTemplate restTemplate = new RestTemplate();
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();
        String apiCall = "https://greenreceipt.net/api/Receipt?id=" + recieptId;

        headers.set("Authorization", "Bearer " + userInfo.getAccess_token());
        ResponseEntity responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(apiCall,
                    HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
        } catch (Exception e) {
            return null;
        }

        Receipt receipt = gson.fromJson((String) responseEntity.getBody(), new TypeToken<Receipt>() {
        }.getType());
        return receipt;
    }
}
