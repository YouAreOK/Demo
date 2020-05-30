# 三、异常处理
## 3.1 NoSuchElementException异常
### 3.1.1 查找元素的定位器不正确。
- 谷歌控制台直接输入 $('img') 或 $x('//img') 。来查找元素是否存在，以高亮显示

### 3.1.2 页面出错导致元素未渲染出来。
- 问题产生的原因是元素还没有渲染出来，则元素有可能在错误发生时没有出现，但在截屏时出现了。在这种情况下，截屏将展示元素，而实际上Selenium在查找它时，它是缺失的。这些通常很难诊断，但如果你直接在浏览器中观察测试的运行，通常最终都能找出原因。

### 3.1.3 在元素渲染之前尝试查找它。
- 通常js的加载速度远低于seleium的运行速度，所以可能在js没有初始化前，seleium就已经开始工作，但是又找不到这个元素，造成报错，这个时候需要给程序设置等待时间
（注意的是，不能使用Thread.sleep()），因为在比较差的机器上面有可能会报错
## 3.2　NoSuchFrameException异常
- driver.switchTo().defaultContent();
## 3.3　NoSuchWindowException异常
 -导致NoSuchWindowException异常的原因是当前的浏览器窗口列表不是最新的。之前存在的一个窗口已经不在了，因此无法切换到该窗口。首先要检查代码，确认是不是在关闭窗口后没有更新可用的窗口列表。
  driver.getWindowHandles();
  获得该异常的另一个原因是在调用 driver.getWindowHandles()之前，就尝试切换到某个窗口。哪个窗口句柄对应于哪个窗口，是无法直接看出来的。要进行跟踪，最好的办法是使用以下代码在打开新窗口之前，获得当前窗口的句柄。
  String currentWindowHandle = driver.getWindowHandle();
当打开新窗口并获得窗口句柄列表后，可以遍历列表并忽略当前已打开窗口的句柄。通过这样的筛除过程，可以计算出哪个句柄与刚刚打开的新窗口相关联。

## 3.4　ElementNotVisibleException异常
- 检查是不是存在看起来加载缓慢的内容。常规的修复方式是添加一个显式等待

## 3.5　StaleElementReferenceException异常
    
    from selenium import webdriver
    
    driver = webdriver.Firefox()
    driver.get('http://www.baidu.com')
    
    kw = driver.find_element_by_id('kw')  # 先定位并获得kw元素
    kw.click()
    
    driver.refresh()  # refresh
    
    kw.click()  # 刷新后，仍用原来的kw元素操作，这时会报错
    
    driver.quit()
- 刷新后需要重新查找元素，当然这可能只是一种情况

## 3.6　InvalidElementStateException异常
- 如果操作某个时，需要上个元素的验证时，而你又非要操作当前这个禁止的元素，就会报这个异常
## 3.7　UnsupportedCommandException异常
- 可能当前WebDriver实现不支持某个核心WebDriver API命令时，将会发现抛出的 UnsupportedCommandException 异常。
## 3.8　UnreachableBrowserException异常
webdriver API-->RemoteWebDriver(服务端模式)-->浏览器
- 使用WebDriver API编写的代码通过连接协议，经由 RemoteWebDriver实例发送到浏览器。正如你所想象的那样，如果没有可用于通信的浏览器，则此过程将出现问题。
  若命令已发出，却迟迟收不到响应，就会抛出 UnreachableBrowserException 异常
- 原因是可能浏览器崩溃了，浏览器没有启动，当前使用的 RemoteWebDriver 实例版本与当前使用的浏览器版本不兼容等
## 3.9　SessionNotFoundException异常
-原因无意中退出了驱动实例。浏览器崩溃了。
## 3.10　WebDriverException异常——元素此时不可单击
解决方案是显式等待元素变为可单击状态。
``` 
By locator = By.id("someElement");  
WebDriverWait wait = new WebDriverWait(driver, 10);   
wait.until(ExpectedConditions.elementToBeClickable(locator));
```
## 3.11　NoAlertPresentException异常
```driver.switchTo().alert().accept();```
- 由于提示框未曾出现，因此将抛出异常

# 四、selenium等待
- 如果页面在15s内没有完成加载，则将抛出 WebDriverException 异常。
```driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);```
## 4.1 显示等待
```
WebDriverWait wait = new WebDriverWait(getDriver(), 15, 100);
WebElement myElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("foo")));
```
- 元素变为可见状态时再进行查找

- 判决ajax请求是否完毕
```java
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

    //判断angularjs请求Ajax调用是否完毕
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

}
```
- 调用如下
```
WebDriverWait wait = new WebDriverWait(getDriver(), 15, 100);
wait.until(AdditionalConditions.angularHasFinishedProcessing());

```
## 4.6　显式等待的核心——流式等待

- 创建了一个有15s超时的 wait对象，该对象每500ms轮询一次，以查看是否满足条件。由于我们决定在等待条件变为true时，忽略任何 NoSuchElementException实例，因此使用 .ignore() 命令进行指定
```

Wait<WebDriver> wait = new FluentWait<>(driver)
         .withTimeout(Duration.ofSeconds(15))
         .pollingEvery(Duration.ofMillis(500))
         .ignoreAll(Arrays.asList(
                NoSuchElementException.class,
                StaleElementReferenceException.class
         ))
         .withMessage("The message you will see in if a TimeoutException is thrown");
```
# 五、高效的页面对象
<保持代码简洁和便于维护>

- DRY （ Don’t Repeat Yourself ， 不做重复的事情 ） 原则 ，以及将此原则应用到页面对象上的方法。应将断言与页面对象分离开的原因。
- Selenium 支持包中可用的Java PageFactory类。
- 构建合理的、可扩展的页面对象以完成驱动测试的重要工作的方法。
- 使用页面对象来创建易读的领域特定语言（Domain-Specific Language，DSL）的方法。我们并不需要用Cucumber来编写易读的测试。
## 5.1 为何不断做重复的事情
- 同一区域内对场景实施自动化的人员不止一个，就如所有事情一样，不同的人有不同的做事方式
- 先看以下代码
```
public void goToTheAboutPage() {
    driver.get("http://id.oppo.com/index.html");
    driver.findElement(By.linkText("注册帐号")).click();
    WebElement element = driver.findElement(By.cssSelector("h3"));

    assertThat(element.getText()).isEqualTo("注册帐号");
}

```
