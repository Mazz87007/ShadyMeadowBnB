package pages;

import components.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminLogin extends AbstractComponent {

    public AdminLogin(WebDriver driver) {
        super(driver);
    }

    public By userNameInput = By.xpath("//*[@id='username']");
    public By passwordInput = By.xpath("//*[@id='password']");
    public By loginBtn = By.xpath("//*[@id='doLogin']");

    public void login(String username, String password)
    {
        sendKeys(userNameInput,username);
        sendKeys(passwordInput, password);
        click(loginBtn);
    }
}
