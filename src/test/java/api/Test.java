package api;

import com.fasterxml.jackson.databind.util.JSONPObject;
import entities.RequestBody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.codehaus.groovy.reflection.stdclasses.CachedSAMClass;
import org.junit.Assert;
import utilities.CashWiseToken;
import utilities.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Test {

    CashWiseToken cashWiseToken = new CashWiseToken();

    @org.junit.Test
    public void testToken(){
        String endPoint = "https://backend.cashwise.us/api/myaccount/auth/login";
        RequestBody requestBody = new RequestBody();

        requestBody.setEmail("azizusmanuulu@gmail.com");
        requestBody.setPassword("Kaspersky00");

        Response response =  RestAssured.given().contentType(ContentType.JSON)
                .body(requestBody).post(endPoint);
        int statusCode = response.statusCode();

        Assert.assertEquals(200, statusCode);
//        response.prettyPrint();
        String token = response.jsonPath().getString("jwt_token");
        System.out.println(token);
    }


    @org.junit.Test
    public void GetSingleSeller(){
        Response response = RestAssured.given().auth().oauth2(CashWiseToken.GetToken())
                .get(Config.getProperty("cashWiseApiUrl")+ "/api/myaccount/sellers/4702");

//        System.out.println(response.getStatusCode());
//       System.out.println(response.prettyPrint());

        String expectedEmail =response.jsonPath().getString("email");

        Assert.assertFalse(expectedEmail.isEmpty());
        Assert.assertTrue(expectedEmail.endsWith(".COM"));
    }

    @org.junit.Test
    public void GetAllSellers(){
        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 1);
        Response response = RestAssured.given().auth().oauth2(CashWiseToken.GetToken())
                .params(params).get( Config.getProperty("cashWiseApiUrl")+ "/api/myaccount/sellers");
        Assert.assertEquals(200, response.statusCode());

        System.out.println(response.prettyPrint());
        String email = response.jsonPath().getString("responses[0].email");
        Assert.assertNotNull(email);

//        Assert.assertNotNull(response.jsonPath().getString("responses[2].email"));
//        Assert.assertNotNull(response.jsonPath().getString("responses[4].email"));

    }



    @org.junit.Test
    public void GetAllSellersLoop(){
        Map <String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 1);


        Response response = RestAssured.given().auth().oauth2(CashWiseToken.GetToken()).params(params)
                .get(Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers" );

        List<String > listOfEmails = response.jsonPath().getList("responses.email");
//        response.prettyPrint();

        for (int a = 0; a <4; a++){
            System.out.println(response.jsonPath().getString("responses" + " [" + a + "]" + ".email"));
//            Assert.assertNotNull(response.jsonPath().getString("responses" + " [" + a + "]" + "email"));
        }
    }


    @org.junit.Test
    public void CreateSeller(){
        String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers";
        String token = CashWiseToken.GetToken();

        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name("J1P MORGAN12");
        requestBody.setSeller_name("1CHASE12");
        requestBody.setEmail("1chase1@gmail.com");
        requestBody.setPhone_number("312123321");
        requestBody.setAddress("DOWNTOWN");

        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON)
                .body(requestBody).post(url);

        Assert.assertEquals(201, response.getStatusCode());

        String id = response.jsonPath().getString("seller_id");
        Response response1 = RestAssured.given().auth().oauth2(CashWiseToken.GetToken()).get(Config.
                getProperty("cashWiseApiUrl") + "/api/myaccount/sellers/" + id);
        Assert.assertEquals(200, response1.getStatusCode());

    }



}
