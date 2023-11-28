package com.example.tests;

import java.time.Duration;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.*;

public class bookingCreation {
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
    public void createBookingOmitEmailAsserCorrectErrorIsDisplayed() throws Exception {
        driver.get("https://automationintesting.online/");
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



        //locator for warning message /html/body/div/div/div/div[4]/div/div[2]/div[3]/div[5]/p = "must not be empty"

    }

    @Test
    public void createSuccessfulBookingFrom14112023To17112023AssertCorrectDate() throws Exception {
        driver.get("https://automationintesting.online/");
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
