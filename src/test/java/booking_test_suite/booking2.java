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

public class booking2 {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "https://www.google.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testUntitledTestCase() throws Exception {
        driver.get("https://automationintesting.online/");
        driver.findElement(By.xpath("//button[@type='button']")).click();
        driver.findElement(By.xpath("//div[@id='root']/div/div/div[4]/div/div[2]/div[2]/div/div[2]/div[4]/div")).click();
        driver.findElement(By.xpath("//div[@id='root']/div/div/div[4]/div/div[2]/div[3]")).click();
        driver.findElement(By.name("firstname")).click();
        driver.findElement(By.name("firstname")).clear();
        driver.findElement(By.name("firstname")).sendKeys("Marnitz");
        driver.findElement(By.name("lastname")).clear();
        driver.findElement(By.name("lastname")).sendKeys("de Lange");
        driver.findElement(By.name("email")).clear();
        driver.findElement(By.name("email")).sendKeys("marnitz@delange.com");
        driver.findElement(By.name("phone")).clear();
        driver.findElement(By.name("phone")).sendKeys("12345678999");
        driver.findElement(By.xpath("//div[@id='root']/div/div/div[4]/div/div[2]/div[3]/button[2]")).click();

        // Wait for the modal to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));// Adjust the timeout as needed
        By modalLocator = By.xpath(".//*[normalize-space(text()) and normalize-space(.)='Automation in Testing'])[1]/following::h3[1]"); // Replace with the actual locator of your modal
        wait.until(ExpectedConditions.visibilityOfElementLocated(modalLocator));

        // Assert that the booking was successful
        WebElement successMessage = driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Booking Successful!'])[1]/following::p[2]"));
        assertTrue(successMessage.isDisplayed());
        assertEquals(successMessage.getText(), "Your success message"); // Replace with the expected success message


        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Automation in Testing'])[1]/following::h3[1]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Booking Successful!'])[1]/following::p[2]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Booking Successful!'])[1]/following::button[1]")).click();
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
