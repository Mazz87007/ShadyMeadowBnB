package pages;

import components.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminHomeScreen extends AbstractComponent {

    public AdminHomeScreen(WebDriver driver) {
        super(driver);
    }

    //driver.findElement(By.xpath("//div[@id='room1']/div[2]")).click();

    public By room101 = By.xpath("//div[@id='room1']/div[2]");

    public void selectRoom()
    {
        click(room101);
    }
}
