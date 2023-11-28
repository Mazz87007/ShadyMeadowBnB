package booking_test_suite;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.interactions.Actions;
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
    public void testUntitledTestCase() throws Exception {

        driver.get("https://automationintesting.online/");
        driver.findElement(By.xpath("//div[@id='root']/div[2]/div/div[4]/div/div/div[3]/button")).click();
        WebElement sourceDate = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/div[2]/div/div[2]/div[4]/div[2]/div[1]/div[2]/button"));
        WebElement targetDate = driver.findElement(By.xpath("/html/body/div/div/div/div[4]/div/div[2]/div[2]/div/div[2]/div[4]/div[2]/div[1]/div[5]/button"));
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
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Automation in Testing'])[1]/following::div[7]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Booking Successful!'])[1]/following::button[1]")).click();
        driver.findElement(By.linkText("Admin panel")).click();
        driver.findElement(By.id("username")).click();
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("password");
        driver.findElement(By.id("doLogin")).click();
        driver.findElement(By.xpath("//div[@id='room1']/div[2]")).click();
        driver.findElement(By.xpath("//div[@id='root']/div/div/div/div/div/div[3]/div[3]/div/div/div")).click();
        driver.findElement(By.xpath("//div[@id='root']/div/div/div/div/div/div[3]/div[3]/div/div/div[7]/span[2]")).click();
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
