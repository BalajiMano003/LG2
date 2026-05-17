package com.mapsted.tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mapsted.base.BaseTest;

import io.appium.java_client.AppiumBy;

public class LoginTest extends BaseTest {

    @BeforeMethod
    public void start() throws Exception {
        setup();
    }

    @Test
    public void launchAppTest() throws Exception {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        System.out.println("App Launched Successfully");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("com.mapsted.demo:id/edit_text_email")
        )).sendKeys("mobile@mapsted.com");

        driver.findElement(By.xpath("//android.widget.EditText[@resource-id='com.mapsted.demo:id/edit_text_password']"))
                .sendKeys("Passw0rd1@");

        driver.findElement(By.xpath("//android.widget.Button[@resource-id='com.mapsted.demo:id/email_sign_in_button']"))
                .click();
       
      Thread.sleep(10000);
      
    WebElement search = driver.findElement(By.xpath("//android.widget.ImageView[@content-desc=\"Search\"]"));
    search.click();
    search.sendKeys("Yorkdale Shopping Centre");
    
    driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"Yorkdale Shopping Centre\"))")).click();
      
    }

    @AfterMethod
    public void end() {
        tearDown();
    }
}