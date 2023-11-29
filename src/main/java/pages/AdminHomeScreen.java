package pages;

import components.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AdminHomeScreen extends AbstractComponent {

    private final WebDriverWait wait;

    public AdminHomeScreen(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public By room101 = By.xpath("//div[@class='col-sm-1'][.//*[@id='roomName101']]");

    public void selectRoom()
    {
        click(room101);
    }

    public WebElement waitForRoomElement() {
        WebElement roomElement = wait.until(ExpectedConditions.visibilityOfElementLocated(room101));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(room101));
    }

}
