package components;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class AbstractComponent {

    /*Abstract component class to host functions that will be used by tests*/
    public WebDriver driver;
    public AbstractComponent(WebDriver driver)
    {
        this.driver = driver;
    }

    //navigate
    public void navigate(String url)
    {
        driver.navigate().to(url);
    }

    //find_elements an element
    public WebElement find_element(By by)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(by));
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    //find_elements multiple elements
    public List<WebElement> find_elements(By by)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    //click on an element
    public void click(By by)
    {
        find_element(by).click();
    }

    //send keys to a field
    public void sendKeys(By by, String text)
    {
        find_element(by).sendKeys(text);
    }

    //dropdown wait
    public void dropdown_wait(By by)
    {
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    //dropdown select
    public void select_dropdown_option(By by, String option, By by_wait) {
        dropdown_wait(by_wait);
        Select dropdown = new Select(find_element(by));
        dropdown.selectByValue(option);
    }
    //get text
    public String getText(By by)
    {
        return find_element(by).getText();
    }

}