package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class LoginPage {
    WebDriver driver;

    public LoginPage(){
        this.driver = Driver.getDriver();
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//input[@placeholder=\"Username\"]")
    public WebElement userNameInput;

    @FindBy(xpath = "//input[@placeholder=\"Password\"]")
    public WebElement passwordInput;

    @FindBy(xpath = "//input[@type=\"submit\"]")
    public WebElement loginB8t;
}
