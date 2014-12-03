package Utilities;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Base64;

public class GreenReceiptUtil {

    static HttpHeaders createHeaders( final String username, final String password ){
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

    public static Object restWrapper() {
        Object o = null;
        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity responseEntity = restTemplate.exchange("http://155.99.160.33/GreenReceipt/api/users", HttpMethod.GET, new HttpEntity<Object>(createHeaders(username, password)), String.class);

        return o;
    }
}
