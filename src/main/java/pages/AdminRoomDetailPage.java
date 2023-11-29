package pages;

import components.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AdminRoomDetailPage extends AbstractComponent {

    private WebDriverWait wait;

    public AdminRoomDetailPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By bookingContainer = By.xpath("//div[@class='detail booking-1']");
    private By firstNameLocator = By.cssSelector(".col-sm-2 > p:nth-of-type(1)");
    private By deleteButtonLocator = By.cssSelector(".col-sm-1 > span.fa-trash");

    public WebElement waitForBookingContainer() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(bookingContainer));
    }

    public String getFirstNameFromBookingContainer(WebElement bookingContainer) {
        return bookingContainer.findElement(firstNameLocator).getText();
    }

    public List<WebElement> waitForBookingContainers() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(bookingContainer));
    }

    public void deleteBooking(WebElement bookingContainer) {
        bookingContainer.findElement(deleteButtonLocator).click();
    }


}
