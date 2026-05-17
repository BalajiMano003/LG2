package com.mapsted.pages;

import java.time.Duration;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;

public class LocationPermissionPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public LocationPermissionPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Locators
    private By title = By.id("com.mapsted.demo:id/tv_permission_title");
    private By enableButton = By.id("com.mapsted.demo:id/btn_action");
    private By allowWhileUsingApp = By.xpath("//android.widget.Button[@text='While using the app']");

    // Validate screen
    public boolean isLocationPermissionScreenDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(title)).isDisplayed();
    }

    // 🔥 FIXED CLICK METHOD
    public void clickEnable() {
        System.out.println("Waiting for Enable button...");

        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(enableButton));
        wait.until(ExpectedConditions.elementToBeClickable(enableButton));

        try {
            el.click();
            System.out.println("Clicked Enable normally");
        } catch (Exception e) {
            System.out.println("Normal click failed, trying tap");

            int x = el.getLocation().getX() + (el.getSize().getWidth() / 2);
            int y = el.getLocation().getY() + (el.getSize().getHeight() / 2);

            ((AndroidDriver) driver).executeScript("mobile: clickGesture", Map.of(
                    "x", x,
                    "y", y
            ));

            System.out.println("Clicked Enable using tap");
        }
    }

    // Handle system popup
    public void allowLocationPermission() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(allowWhileUsingApp)).click();
        System.out.println("Permission allowed");
    }
}