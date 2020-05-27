package com.masteringselenium.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;

import static com.masteringselenium.config.DriverType.FIREFOX;
import static com.masteringselenium.config.DriverType.valueOf;

/**
 * Created by 20191109 on 2020/4/13 21:00
 * 处理线程封装
 */
public class DriverFactory {

    private RemoteWebDriver webDriver;
    //存储正在运行测试的驱动类型
    private DriverType selectedDriverType;
    private static final DriverType DEFAULT_DRIVER_TYPE = FIREFOX;
    private final String browser = System.getProperty("browser", DEFAULT_DRIVER_TYPE.name()).toUpperCase();
    private final String operatingSystem = System.getProperty("os.name").toUpperCase();
    private final String systemArchitecture = System.getProperty("os.arch");
    private final boolean useRemoteWebDriver = Boolean.getBoolean("remoteDriver");

    public DriverFactory() {
        //浏览器选择
        DriverType driverType = DEFAULT_DRIVER_TYPE;
        String browser = System.getProperty("browser", driverType.toString()).toUpperCase();
        try {
            driverType = valueOf(browser);
        } catch (IllegalArgumentException ignored) {
            System.err.println("Unknown driver specified, defaulting to '" + driverType + "'...");
        } catch (NullPointerException ignored) {
            System.err.println("No driver specified, defaulting to '" + driverType + "'...");
        }
        selectedDriverType = driverType;
    }

    public RemoteWebDriver getDriver() {
        if (null == webDriver) {
            instantiateWebDriver(selectedDriverType);
        }
        return webDriver;
    }

    public void quitDriver() {
        if (null != webDriver) {
            webDriver.quit();
            webDriver = null;
        }
    }

    private void instantiateWebDriver(DriverType driverType) {
        System.out.println(" ");
        System.out.println("Local Operating System: " + operatingSystem);
        System.out.println("Local Architecture: " + systemArchitecture);
        System.out.println("Selected Browser: " + selectedDriverType);
        System.out.println("Current thread: "+ Thread. currentThread().getId());
        System.out.println(" ");
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        webDriver = driverType.getWebDriverObject(desiredCapabilities);

    }


}
