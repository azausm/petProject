package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import entities.CustomResponse;
import entities.RequestBody;
import io.cucumber.java.sl.In;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.manipulation.Ordering;
import utilities.CashWiseToken;
import utilities.Config;

import java.util.*;

public class PojoTest {
    Faker faker = new Faker();
    @Test
    public void CreateCategory() throws JsonProcessingException {

    String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/categories";
    String token = CashWiseToken.GetToken();

        RequestBody requestBody = new RequestBody();

        requestBody.setCategory_title("test11111");
        requestBody.setCategory_description("API TEST");
        requestBody.setFlag(true);

        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON)
                .body(requestBody).post(url);

        Assert.assertEquals(201, response.statusCode());

        ObjectMapper mapper = new ObjectMapper();

        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        System.out.println(customResponse.getCategory_id());
    }

    @Test
    public void CreateCategoryAndGetIt() throws JsonProcessingException {
        String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/categories";
        String token = CashWiseToken.GetToken();


        RequestBody requestBody = new RequestBody();
        requestBody.setCategory_title("22TASK");
        requestBody.setCategory_description("22TASKING");
        requestBody.setFlag(true);

        Response response = RestAssured.given().auth().oauth2(token)
                .contentType(ContentType.JSON).body(requestBody).post(url);

        Assert.assertEquals(201, response.statusCode());
            ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        int id = customResponse.getCategory_id();

        Response response1 = RestAssured.given().auth().oauth2(token).get(url + "/" + id);

        Assert.assertEquals(200, response1.statusCode());

        CustomResponse customResponse1 = mapper.readValue(response1.asString(), CustomResponse.class);

        Assert.assertEquals(customResponse.getCategory_id(), customResponse1.getCategory_id());



    }


    @Test
    public void CreateAndArchieve() throws JsonProcessingException {
        String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers";
        String token = CashWiseToken.GetToken();
        RequestBody requestBody = new RequestBody();
        for (int i = 0; i<15; i++){
            requestBody.setCompany_name(faker.name().title());
            requestBody.setSeller_name(faker.name().fullName());
            requestBody.setEmail(faker.internet().emailAddress());
            requestBody.setPhone_number(faker.phoneNumber().phoneNumber());
            requestBody.setAddress(faker.address().fullAddress());
            Response response = RestAssured.given().auth().oauth2(token)
                    .contentType(ContentType.JSON).body(requestBody).post(url);

            Assert.assertEquals(201, response.statusCode());
            ObjectMapper mapper = new ObjectMapper();
            CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);

            Map <String, Object> params = new HashMap<>();
            params.put("isArchived", false);
            params.put("page", 1);
            params.put("size", 1);


            Response response1 = RestAssured.given().auth().oauth2(CashWiseToken.GetToken()).params(params)
                    .get(Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers" );

            CustomResponse customResponse11 = mapper.readValue(response1.asString(), CustomResponse.class);

        }




    }

    @Test
    public void CreateSellerNoEmail(){

        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name(faker.name().title());
        requestBody.setSeller_name(faker.name().fullName());
//        requestBody.setPhone_number(faker.phoneNumber().phoneNumber());
        requestBody.setAddress(faker.address().fullAddress());
        requestBody.setEmail(faker.internet().emailAddress());

        Response response = RestAssured.given().auth().oauth2(CashWiseToken.GetToken()).contentType(ContentType.JSON)
                .body(requestBody).post(Config.getProperty("cashWiseApiUrl")+"/api/myaccount/sellers");

        Assert.assertEquals(201, response.statusCode());

    }

    @Test
    public void ArchiveSeller(){
//        4702

        Map<String, Object> params = new HashMap<>();
        params.put("sellersIdsForArchive", 5505);
        params.put("archive", true);

        Response response = RestAssured.given().auth().oauth2(CashWiseToken.GetToken())
                .params(params)
                .post(Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers/archive/unarchive");

        Assert.assertEquals(200,response.statusCode());


    }

    @Test
    public void ArchiveAll() throws JsonProcessingException {
        String token = CashWiseToken.GetToken();
        String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers";

        Map<String, Object> params = new HashMap<>();

        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 110);

        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);

        int status = response.statusCode();

        Assert.assertEquals(200, status);

        ObjectMapper mapper = new ObjectMapper();

        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);


        String urlToArchive = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers/archive/unarchive";
        int size = customResponse.getResponses().size();
        for (int i = 0; i < size; i++) {
            int id = customResponse.getResponses().get(i).getSeller_id();

            Map<String, Object> paramsToArchive = new HashMap<>();

            paramsToArchive.put("sellersIdsForArchive", id);
            paramsToArchive.put("archive", true);

            Response response1 = RestAssured.given().auth().oauth2(token).params(paramsToArchive).post(urlToArchive);

            int status1 = response1.statusCode();

            Assert.assertEquals(200, status1);
        }
    }


    @Test
    public void UnArchiveAll() throws JsonProcessingException {
        String token = CashWiseToken.GetToken();
        String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers";

        Map<String, Object> params = new HashMap<>();

        params.put("isArchived", true);
        params.put("page", 1);
        params.put("size", 110);

        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);

        int status = response.statusCode();

        Assert.assertEquals(200, status);

        ObjectMapper mapper = new ObjectMapper();

        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);


        String urlToArchive = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers/archive/unarchive";
        int size = customResponse.getResponses().size();
        for (int i = 0; i < size; i++) {
            if (customResponse.getResponses().get(i).getEmail().endsWith("@hotmail.com")){
                int id = customResponse.getResponses().get(i).getSeller_id();

                Map<String, Object> paramsToArchive = new HashMap<>();

                paramsToArchive.put("sellersIdsForArchive", id);
                paramsToArchive.put("archive", false);

                Response response1 = RestAssured.given().auth().oauth2(token).params(paramsToArchive).post(urlToArchive);

                int status1 = response1.statusCode();

                Assert.assertEquals(200, status1);
            }}
    }








    }
