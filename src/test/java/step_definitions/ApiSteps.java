package step_definitions;

import com.github.javafaker.Faker;
import entities.RequestBody;
import io.cucumber.java.en.*;
import org.junit.Assert;
import utilities.APIRunner;

import java.util.HashMap;
import java.util.Map;

public class ApiSteps {
    Faker faker = new Faker();
    String oldEmail;
    String oldName;
    String newEmail;
    String newName;
    int sellerId;
    boolean isPresent;

    @Given("user hits get single seller api with {string}")
    public void user_hits_get_single_seller_api_with(String string) {
        APIRunner.runGET(string, 5350);

        sellerId = APIRunner.getCustomResponse().getSeller_id();

    }
    @Then("verify seller email is not empty")
    public void verify_seller_email_is_not_empty() {
        Assert.assertFalse(APIRunner.getCustomResponse().getEmail().isEmpty());
    }



    @Given("user hits get all sellers api with {string}")
    public void user_hits_get_all_sellers_api_with(String string) {
        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 1);
        APIRunner.runGET(string,params);
    }
    @Then("verify sellers id is not {int}")
    public void verify_sellers_id_is_not(Integer int1) {
        int size = APIRunner.getCustomResponse().getResponses().size();

        for (int i = 0; i < size; i ++){
            int sellerId = APIRunner.getCustomResponse().getResponses().get(i).getSeller_id();
            Assert.assertNotEquals(0, sellerId);
        }
    }



    @Then("user hits put api with {string}")
    public void user_hits_put_api_with(String string) {

        oldEmail = APIRunner.getCustomResponse().getEmail();
        oldName = APIRunner.getCustomResponse().getSeller_name();
        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name(faker.name().username());
        requestBody.setSeller_name(faker.name().fullName());
        requestBody.setEmail(faker.internet().emailAddress());
        requestBody.setPhone_number(faker.phoneNumber().phoneNumber());
        requestBody.setAddress(faker.address().fullAddress());

        APIRunner.runPUT(string, 5350,requestBody);

        newEmail = APIRunner.getCustomResponse().getEmail();
        newName = APIRunner.getCustomResponse().getSeller_name();
    }
    @Then("verify user email was updated")
    public void verify_user_email_was_updated() {
        Assert.assertNotEquals(oldEmail,newEmail);

    }
    @Then("verify user first name was updated")
    public void verify_user_first_name_was_updated() {
        Assert.assertNotEquals(oldName,newName);
    }


    @Then("user hits the api with {string} to archive the seller")
    public void user_hits_the_api_with_to_archive_the_seller(String string) {
        Map <String, Object> params = new HashMap<>();
        params.put("sellersIdsForArchive", sellerId);
        params.put("archive", true);
        APIRunner.runPOST(string, params);
    }
    @Then("user hits get all archived sellers api with {string}")
    public void user_hits_get_all_archived_sellers_api_with(String string) {
        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", true);
        params.put("page", 1);
        params.put("size", 1);
        APIRunner.runGET(string,params);
        int size = APIRunner.getCustomResponse().getResponses().size();
        isPresent = false;
        for (int i = 0; i<size; i++){
            int id = APIRunner.getCustomResponse().getResponses().get(i).getSeller_id();
            if (sellerId == id){
                isPresent = true;
                break;
            }
        }
    }
    @Then("user verify seller is archived")
    public void user_verify_seller_is_archived() {
        Assert.assertTrue(isPresent);
    }




    @Given("user hits post api with {string}")
    public void user_hits_post_api_with(String string) {
        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name("whatever");
        requestBody.setSeller_name("whateverName");
        requestBody.setEmail(faker.internet().emailAddress());
        requestBody.setPhone_number(faker.phoneNumber().cellPhone());
        requestBody.setAddress(faker.address().streetAddress());

        APIRunner.runPOST(string,requestBody);

        sellerId = APIRunner.getCustomResponse().getSeller_id();
        newName = APIRunner.getCustomResponse().getSeller_name();
    }
    @Then("verify seller id was generetad")
    public void verify_seller_id_was_generetad() {
       Assert.assertTrue(sellerId != 0);
    }
    @Then("verify seller name is not empty")
    public void verify_seller_name_is_not_empty() {
        Assert.assertFalse(newName.isEmpty());
    }
    @Then("delete the seller with {string}")
    public void delete_the_seller_with(String string) {
        APIRunner.runDELETE(string,sellerId);
    }
    @Then("verify deleted seller is not in the list")
    public void verify_deleted_seller_is_not_in_the_list() {
        int size = APIRunner.getCustomResponse().getResponses().size();
        boolean isEmpty = true;
        for (int i = 0; i<size; i++){
            if (sellerId == APIRunner.getCustomResponse().getResponses().get(i).getSeller_id()){
                isEmpty = false;
            }
        }

        Assert.assertTrue(isEmpty);


    }























}
