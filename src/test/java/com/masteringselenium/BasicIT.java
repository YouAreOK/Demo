package com.masteringselenium;

import com.masteringselenium.config.DriverBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import java.net.MalformedURLException;
import java.util.ResourceBundle;

import static org.assertj.core.api.Assertions.assertThat;

public class BasicIT extends DriverBase {


    private ExpectedCondition<Boolean> pageTitleStartsWith(final String searchString) {
        return driver -> driver.getTitle().toLowerCase().startsWith(searchString.toLowerCase());
    }

    private void googleExampleThatSearchesFor(final String searchString) {
//        ResourceBundle resource = ResourceBundle.getBundle("config");
//        String geckodriver = resource.getString("geckodriver");
//        System.setProperty("webdriver.gecko.driver", geckodriver);
//        WebDriver driver = new FirefoxDriver();
        WebDriver driver = DriverBase.getDriver();

        driver.get("http://www.baidu.com");

        WebElement searchField = driver.findElement(By.name("wd"));

        searchField.clear();
        searchField.sendKeys(searchString);

        System.out.println("Page title is: " + driver.getTitle());

        searchField.submit();

        WebDriverWait wait = new WebDriverWait(getDriver(), 15, 100);
        //判断jq的aiax请求是否加载完毕
        wait.until(AdditionalConditions.jQueryAJAXCallsHaveCompleted());

//        wait.until(AdditionalConditions.weFindElementWd);

//        WebDriverWait wait = new WebDriverWait(driver, 10, 100);
        wait.until(pageTitleStartsWith(searchString));

        System.out.println("Page title is: " + driver.getTitle());

    }

    @Test
    public void googleCheeseExample() {
        googleExampleThatSearchesFor("Cheese!");
    }

    @Test
    public void googleMilkExample() {
        googleExampleThatSearchesFor("Milk!");
    }


}
