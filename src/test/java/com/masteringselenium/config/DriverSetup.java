package com.masteringselenium.config;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Created by 20191109 on 2020/4/13 21:47
 * 获取浏览器驱动接口
 */
public interface DriverSetup {
    RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities);
}
