package com.masteringselenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.function.Function;

import static jdk.nashorn.internal.objects.Global.undefined;

/**
 * Created by kouguangyin on 2020/5/30 0:50
 *
 */
public class AdditionalConditions {

    //判断页面JQ的ajax请求是否加载完毕
    public static ExpectedCondition<Boolean> jQueryAJAXCallsHaveCompleted() {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return (Boolean) ((JavascriptExecutor) driver).executeScript("return" +
                        "(window.jQuery != null) && (jQuery.active === 0);");
            }
        };
    }

    //判断AngularJs请求Ajax调用是否完毕
    public static ExpectedCondition<Boolean> angularHasFinishedProcessing() {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return Boolean.valueOf(((JavascriptExecutor)
                        driver).executeScript("return " +
                        "(window.angular !== undefined) && (angular.element(document).injector() !== undefined) " +
                        "&& (angular.element(document).injector() .get('$http').pendingRequests.length === 0)").toString());
            }
        };
    }

    //元素foo的布尔类型对象
    public static Function<WebDriver, Boolean> weFindElementWd = new Function<WebDriver, Boolean>() {
        public Boolean apply(WebDriver driver) {
            return driver.findElements(By.id("wd")).size() > 0;
        }
    };

    //元素foo的布尔类型对象的Lambda写法
    public static Function<WebDriver, Boolean> didWeFindElementWd =
            driver -> driver.findElements(By.id("wd")).size() > 0;
}
