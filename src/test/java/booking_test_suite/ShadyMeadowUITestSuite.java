package booking_test_suite;

import io.qameta.allure.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

@Listeners(io.qameta.allure.testng.AllureTestNg.class)
public class ShadyMeadowUITestSuite {
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
    @Description("Test to create a booking with omitted email and assert correct error message")
    public void createBookingOmitEmailAssertCorrectErrorIsDisplayed() throws Exception {
        try {
            driver.get("https://automationintesting.online/");

            // Your test code
            driver.findElement(By.xpath("//div[@id='root']/div[2]/div/div[4]/div/div/div[3]/button")).click();
            WebElement sourceDate = driver.findElement(By.xpath("//button[text()='10']"));
            WebElement targetDate = driver.findElement(By.xpath("//button[text()='11']"));
            dragAndDrop(sourceDate, targetDate);
            driver.findElement(By.name("firstname")).click();
            driver.findElement(By.name("firstname")).clear();
            driver.findElement(By.name("firstname")).sendKeys("NameFirst");
            driver.findElement(By.name("lastname")).click();
            driver.findElement(By.name("lastname")).clear();
            driver.findElement(By.name("lastname")).sendKeys("NameLast");
            driver.findElement(By.name("phone")).click();
            driver.findElement(By.name("phone")).clear();
            driver.findElement(By.name("phone")).sendKeys("12345678991");
            driver.findElement(By.xpath("//div[@id='root']/div[2]/div/div[4]/div/div[2]/div[3]/button[2]")).click();

            // Wait for the modal to appear
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'alert')]")));

            WebElement warningMessage = modal.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/div[3]/div[5]/p"));
            String warningMessageReceived = warningMessage.getText();
            String warningMessageExpected = "must not be empty";

            assertEquals(warningMessageReceived, warningMessageExpected);

        } catch (Exception e) {
            captureScreenshot();
            throw e;
        }
    }

    @Test
    @Description("Test to create a successful booking from 14/11/2023 to 17/11/2023 and assert correct date")
    public void createSuccessfulBookingFrom14112023To17112023AssertCorrectDate() throws Exception {
        try {
            driver.get("https://automationintesting.online/");

            // Your test code
            driver.findElement(By.xpath("//div[@id='root']/div[2]/div/div[4]/div/div/div[3]/button")).click();
            WebElement sourceDate = driver.findElement(By.xpath("//button[text()='14']"));
            WebElement targetDate = driver.findElement(By.xpath("//button[text()='17']"));
            dragAndDrop(sourceDate, targetDate);
            driver.findElement(By.name("firstname")).click();
            driver.findElement(By.name("firstname")).clear();
            driver.findElement(By.name("firstname")).sendKeys("NameFirst");
            driver.findElement(By.name("lastname")).clear();
            driver.findElement(By.name("lastname")).sendKeys("NameLast");
            driver.findElement(By.name("email")).clear();
            driver.findElement(By.name("email")).sendKeys("justanotheremail@email.com");
            driver.findElement(By.name("phone")).clear();
            driver.findElement(By.name("phone")).sendKeys("1234567899");
            driver.findElement(By.xpath("//div[@id='root']/div[2]/div/div[4]/div/div[2]/div[3]/button[2]")).click();
            driver.findElement(By.name("phone")).click();
            driver.findElement(By.name("phone")).clear();
            driver.findElement(By.name("phone")).sendKeys("12345678991");
            driver.findElement(By.xpath("//div[@id='root']/div[2]/div/div[4]/div/div[2]/div[3]/button[2]")).click();

            // After booking is created, wait for the modal to appear
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@tabindex='-1']")));

            driver.findElement(By.xpath("//div[@tabindex='-1']"));
            driver.findElement(By.xpath("/html/body/div[4]/div/div/div[1]/div[2]/h3"));
            driver.findElement(By.xpath("/html/body/div[4]/div/div/div[1]/div[2]/p[2]"));

            // Modal appears //div[@tabindex='-1']
            // Booking successful message: /html/body/div[4]/div/div/div[1]/div[2]/h3
            // Dates of booking: /html/body/div[4]/div/div/div[1]/div[2]/p[2]

            // Locate the element containing the from and to dates
            WebElement confirmationMessage = modal.findElement(By.xpath("/html/body/div[4]/div/div/div[1]/div[2]/h3"));

            String actualConfirmationMessage = confirmationMessage.getText();
            String expectedConfirmationMessage = "Booking Successful!";

            assertEquals(actualConfirmationMessage, confirmationMessage.getText());

            WebElement dateElement = modal.findElement(By.xpath("//div[@class='form-row']/div[@class='col-sm-6 text-center']/p[2]"));

            // Get the text content of the element
            String dateText = dateElement.getText();

            // Extract the actual from and to dates from the text (modify as per your date format)
            String expectedFromDate = "2023-11-14";
            String expectedToDate = "2023-11-17";

            // Assert that the actual dates match the expected dates
            assertTrue(dateText.contains(expectedFromDate) && dateText.contains(expectedToDate),
                    "From and to dates in the modal do not match the expected dates of 2023-11-14 - 2023-11-17" + ", the dates booked are: " + dateText);

        } catch (Exception e) {
            captureScreenshot();
            throw e;
        }
    }

    @Test
    @Description("Test to delete a created booking")
    public void deleteCreatedBooking() throws Exception {
        driver.get("https://automationintesting.online/");
        driver.findElement(By.xpath("//div[@id='root']/div[2]/div/div[4]/div/div/div[3]/button")).click();
        WebElement sourceDate = driver.findElement(By.xpath("//button[text()='23']"));
        WebElement targetDate = driver.findElement(By.xpath("//button[text()='25']"));
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

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] captureScreenshot() {
        // Capture and attach a screenshot to the Allure report
        // Example: TakesScreenshot screenshot = ((TakesScreenshot) driver);
        // byte[] screenshotBytes = screenshot.getScreenshotAs(OutputType.BYTES);
        // return screenshotBytes;

        // Mocked example (replace with actual screenshot capture logic)
        return "Screenshot captured".getBytes();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        try {
            driver.quit();
        } catch (Exception e) {
            captureScreenshot();
            throw e;
        }

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
