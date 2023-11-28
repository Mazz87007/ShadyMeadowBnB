package booking_test_suite;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class bookingDeletion {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    private void dragAndDrop(WebElement source, WebElement target) {
        // Scroll to the target element
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", target);

        // Perform the drag-and-drop action
        new Actions(driver).moveToElement(source).clickAndHold().moveToElement(target).release().build().perform();
    }

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "https://www.google.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void deleteCreatedBooking() throws Exception {

        driver.get("https://automationintesting.online/");
        driver.findElement(By.xpath("//div[@id='root']/div[2]/div/div[4]/div/div/div[3]/button")).click();
        WebElement sourceDate = driver.findElement(By.xpath("//button[text()='14']"));
        WebElement targetDate = driver.findElement(By.xpath("//button[text()='17']"));
        dragAndDrop(sourceDate, targetDate);
        driver.findElement(By.name("firstname")).click();
        driver.findElement(By.name("firstname")).clear();
        driver.findElement(By.name("firstname")).sendKeys("Booking");
        driver.findElement(By.name("lastname")).clear();
        driver.findElement(By.name("lastname")).sendKeys("ForDeletion");
        driver.findElement(By.name("email")).clear();
        driver.findElement(By.name("email")).sendKeys("delete@booking.com");
        driver.findElement(By.name("phone")).clear();
        driver.findElement(By.name("phone")).sendKeys("987654321112");
        driver.findElement(By.xpath("//div[@id='root']/div/div/div[4]/div/div[2]/div[3]/button[2]")).click();


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@tabindex='-1']")));

        driver.findElement(By.xpath("//button[text()='Close']")).click();

        driver.findElement(By.linkText("Admin panel")).click();
        driver.findElement(By.id("username")).click();
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("doLogin")).click();

        driver.findElement(By.xpath("//div[@id='room1']/div[2]")).click();

        // XPath for the container of booking details
        String bookingContainerXPath = "//div[@class='detail booking-1']";

        // Get all booking containers
        List<WebElement> bookingContainers = driver.findElements(By.xpath(bookingContainerXPath));

        boolean bookingFound = false;

        // Loop through booking containers
        for (WebElement bookingContainer : bookingContainers) {
            // Extract first name from the booking container
            String firstName = bookingContainer.findElement(By.cssSelector(".col-sm-2 > p:nth-of-type(1)")).getText();

            System.out.println("Found Booking: " + firstName);

            // Check if the first name matches the one you want to delete
            if (firstName.equals("Booking")) {
                // Found the booking, perform deletion
                System.out.println("Deleting Booking: " + firstName);
                bookingContainer.findElement(By.cssSelector(".col-sm-1 > span.fa-trash")).click();
                // Add additional steps if needed (e.g., confirm deletion)
                bookingFound = true;
                break; // Break the loop after deleting the booking
            }
        }

        // Assert that the booking was found and deleted
        assertTrue(bookingFound, "Booking was not found for deletion");


    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
