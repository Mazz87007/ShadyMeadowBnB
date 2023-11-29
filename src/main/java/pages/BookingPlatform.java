package pages;

import components.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BookingPlatform extends AbstractComponent {

    private WebDriver driver;
    public By bookThisRoom = By.xpath("//div[@id='root']/div[2]/div/div[4]/div/div/div[3]/button");
//    public By sourceDate = By.xpath("//button[text()='23']");
//    public By targetDate = By.xpath("//button[text()='25']");
    public By firstname = By.xpath("//input[@name='firstname']");
    public By lastname = By.xpath("//input[@name='lastname']");
    public By emailAddress = By.xpath("//input[@name='email']");
    public By phoneNumber = By.xpath("//input[@name='phone']");
    public By bookBtn = By.xpath("//div[@id='root']/div/div/div[4]/div/div[2]/div[3]/button[2]");
    public By confirmationModal = By.xpath("//div[@tabindex='-1']");
    public By bookingConfirmationMessage = By.xpath("/html/body/div[4]/div/div/div[1]/div[2]/h3");
    public By bookingConfirmationDates = By.xpath("//div[@class='form-row']/div[@class='col-sm-6 text-center']/p[2]");
    public By closeModal = By.xpath("//button[text()='Close']");
    public By alertModal = By.xpath("//div[contains(@class,'alert')]");
    public By alertMessage = By.xpath("/html/body/div/div/div/div[4]/div/div[2]/div[3]/div[5]/p");
    public By adminBtn = By.xpath("//a[@href='/#/admin']");

    public BookingPlatform(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    private void dragAndDrop(WebElement source, WebElement target) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", target);
        new Actions(driver).moveToElement(source).clickAndHold().moveToElement(target).release().build().perform();
    }


    public void createCompleteBooking(String firstName, String lastName, String email, String phone, String sourceDateText, String targetDateText) {
        navigate("https://automationintesting.online/");
        click(bookThisRoom);
        WebElement sourceDate = driver.findElement(By.xpath("//button[text()='" + sourceDateText + "']"));
        WebElement targetDate = driver.findElement(By.xpath("//button[text()='" + targetDateText + "']"));
        dragAndDrop(sourceDate, targetDate);
        sendKeys(firstname, firstName);
        sendKeys(lastname, lastName);
        sendKeys(emailAddress, email);
        sendKeys(phoneNumber, phone);
        click(bookBtn);
    }

    public void createIncompleteBooking(String firstName,String lastName,String phone, String sourceDateText, String targetDateText)
    {
        navigate("https://automationintesting.online/");
        click(bookThisRoom);
        WebElement sourceDate = driver.findElement(By.xpath("//button[text()='" + sourceDateText + "']"));
        WebElement targetDate = driver.findElement(By.xpath("//button[text()='" + targetDateText + "']"));
        dragAndDrop(sourceDate, targetDate);
        sendKeys(firstname, firstName);
        sendKeys(lastname, lastName);
        sendKeys(phoneNumber, phone);
        click(bookBtn);
    }

    public WebElement waitForAlertModal() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(alertModal));
    }

    public WebElement waitForConfirmationModal() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationModal));
    }

    public String getAlertMessage(WebElement alertModal) {
        return alertModal.findElement(alertMessage).getText();
    }

    public String getConfirmationMessage(WebElement confirmationModal) {
        return confirmationModal.findElement(bookingConfirmationMessage).getText();
    }

    public String getConfirmationDates(WebElement confirmationModal) {
        return confirmationModal.findElement(bookingConfirmationDates).getText();
    }

    public void closeAlertModal() {
        WebElement modal = waitForAlertModal();
        WebElement closeBtn = modal.findElement(closeModal);
        click((By) closeBtn);
    }

    public void openAdminPortal() {
        click(adminBtn);
    }
}
