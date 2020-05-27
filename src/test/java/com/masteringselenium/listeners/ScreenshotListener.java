package com.masteringselenium.listeners;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.masteringselenium.DriverBase.getDriver;

/**
 * Created by kouguangyin on 2020/4/19 15:54
 * 为TestNG创建一个监听器，出错时，使用代码截图错误
 */
public class ScreenshotListener extends TestListenerAdapter {


    //创建文件
    private boolean createFile(File screenshot){
        boolean fileCreated = false;

        if (screenshot.exists()){
            fileCreated=true;
        }else {
            File parentDirectory = new File(screenshot.getParent());
            if (parentDirectory.exists()||parentDirectory.mkdirs()){
                try {
                    fileCreated = screenshot.createNewFile();
                }catch (IOException errorCreatingScreenshot){
                    errorCreatingScreenshot.printStackTrace();
                }
            }
        }
        return fileCreated;
    }
    private void writeScreenShotToFile(WebDriver driver, File screenshot){
        try {
            FileOutputStream screenshotStream = new FileOutputStream(screenshot);
            screenshotStream.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
            screenshotStream.close();
        } catch (IOException unableToWriteScreenshot) {
            System.err.println("Unable to write " + screenshot.getAbsolutePath());
            unableToWriteScreenshot.printStackTrace();
        }
    }

    @Override
    public void onTestFailure(ITestResult failingTest) {
        try {
            WebDriver driver = getDriver();
            String screenshotDirectory = System.getProperty("screenshotDirectory", "target/screenshots");
            String screenshotAbsolutePath = screenshotDirectory + File.separator + System.currentTimeMillis() + "_" + failingTest.getName() + ".png";
            File screenshot = new File(screenshotAbsolutePath);
            if (createFile(screenshot)) {
                try {
                    writeScreenShotToFile(driver, screenshot);
                } catch (ClassCastException weNeedToAugmentOurDriverObject) {
                    writeScreenShotToFile(new Augmenter().augment(driver), screenshot);
                }
                System.out.println("Written screenshot to " + screenshotAbsolutePath);
            } else {
                System.err.println("Unable to create " + screenshotAbsolutePath);
            }
        } catch (Exception ex) {
            System.err.println("Unable to capture screenshot: " + ex.getCause());
        }
    }
}
