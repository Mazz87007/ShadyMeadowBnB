package booking_test_suite;

import application.ShadyMeadowsBooking;
import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

@Listeners(io.qameta.allure.testng.AllureTestNg.class)
public class ShadyMeadowUITestSuiteRefactored {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

//    private void dragAndDrop(WebElement source, WebElement target) {
//        // Scroll to the target element
//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", target);
//
//        // Perform the drag-and-drop action
//        new Actions(driver).moveToElement(source).clickAndHold().moveToElement(target).release().build().perform();
//    }

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

            ShadyMeadowsBooking shadyMeadowsBooking = new ShadyMeadowsBooking(driver);
            shadyMeadowsBooking.bookingPlatform.createIncompleteBooking("Richards", "Gears", "123456789998", "16", "17");

            WebElement alertModal = shadyMeadowsBooking.bookingPlatform.waitForAlertModal();

            String warningMessageReceived = shadyMeadowsBooking.bookingPlatform.getAlertMessage(alertModal);
            String warningMessageExpected = "must not be empty";

            assertEquals(warningMessageReceived, warningMessageExpected);

            shadyMeadowsBooking.bookingPlatform.closeAlertModal();

        } catch (Exception e) {
            captureScreenshot();
            throw e;
        }
    }

    @Test
    @Description("Test to create a successful booking from 14/11/2023 to 17/11/2023 and assert correct date")
    public void createSuccessfulBookingFrom14112023To17112023AssertCorrectDate1() throws Exception {
        try {

            WebDriver driver = new FirefoxDriver();
            ShadyMeadowsBooking shadyMeadowsBooking = new ShadyMeadowsBooking(driver);
            shadyMeadowsBooking.bookingPlatform.createCompleteBooking("Pams", "Backhurts","readall@emails.com","123456789998", "14", "17");

            WebElement confirmationModal = shadyMeadowsBooking.bookingPlatform.waitForConfirmationModal();
            String confirmationMessageReceived = shadyMeadowsBooking.bookingPlatform.getConfirmationMessage(confirmationModal);
            String expectedConfirmationMessage = "Booking Successful!";
            assertEquals(confirmationMessageReceived, expectedConfirmationMessage);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@tabindex='-1']")));

            String bookingDates = shadyMeadowsBooking.bookingPlatform.getConfirmationDates(confirmationModal);

            String expectedFromDate = "2023-11-14";
            String expectedToDate = "2023-11-17";

            // Assert that the actual dates match the expected dates
            assertTrue(bookingDates.contains(expectedFromDate) && bookingDates.contains(expectedToDate),
                    "From and to dates in the modal do not match the expected dates of 2023-11-14 - 2023-11-17" + ", the dates booked are: " + bookingDates);

        } catch (Exception e) {
            captureScreenshot();
            throw e;
        }
    }

    @Test
    @Description("Test to delete a created booking")
    public void deleteCreatedBooking() throws Exception {

        WebDriver driver = new FirefoxDriver();
        ShadyMeadowsBooking shadyMeadowsBooking = new ShadyMeadowsBooking(driver);
        shadyMeadowsBooking.bookingPlatform.createCompleteBooking("Booking", "ForDeletion","delete@booking.com","987654321112", "14", "17");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@tabindex='-1']")));

        driver.findElement(By.xpath("//button[text()='Close']")).click();

        shadyMeadowsBooking.bookingPlatform.openAdminPortal();
        shadyMeadowsBooking.adminLogin.login("admin","password");

        WebElement roomElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='room1']/div[2]")));

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
}
