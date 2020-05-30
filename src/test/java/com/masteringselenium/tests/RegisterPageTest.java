package com.masteringselenium.tests;

import com.masteringselenium.config.DriverBase;
import com.masteringselenium.page_objects.LoginPage;
import com.masteringselenium.page_objects.RegisterPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.net.MalformedURLException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by kouguangyin on 2020/5/30 15:51
 */
public class RegisterPageTest extends DriverBase {

    private WebDriver driver;

    @Test
    public void goToTheRegisterPage() {
        driver.get("https://id.oppo.com/index.html");
        WebElement registerButton =driver.findElement(LoginPage.registerLinkLocator);
        registerButton.click();

        WebElement registerTest = getDriver().findElement(RegisterPage.registerTestHeadingLocator);
        assertThat(registerTest.getText()).isEqualTo("注册帐号");
    }
    @BeforeMethod
    public void setup() throws MalformedURLException {
        driver = getDriver();
    }
}
