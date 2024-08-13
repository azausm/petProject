package step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import utilities.Config;
import utilities.Driver;

public class LoginSteps {
    WebDriver driver = Driver.getDriver();
    LoginPage loginPage = new LoginPage();
    @Given("user opens with browser {string}")
    public void user_opens_with_browser(String string) {
//            driver.get(string);
        driver.get(Config.getProperty("saucedemo"));

    }

    @When("user enters the username {string}")
    public void user_enters_the_username(String string) {
//        loginPage.userNameInput.sendKeys(string);
        loginPage.userNameInput.sendKeys(Config.getProperty("username"));
    }
    @When("user enters the password {string}")
    public void user_enters_the_password(String string) {
        loginPage.passwordInput.sendKeys(Config.getProperty("password"));
//        loginPage.passwordInput.sendKeys(string);
    }
    @When("user clicks on login button")
    public void user_clicks_on_login_button() {
        loginPage.loginB8t.click();
    }
    @Then("verify the user is logged in")
    public void verify_the_user_is_logged_in() {
        String expected = "https://www.saucedemo.com/inventory.html";

        Assert.assertEquals(expected, driver.getCurrentUrl());
    }


}
