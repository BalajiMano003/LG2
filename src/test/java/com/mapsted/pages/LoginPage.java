//package com.mapsted.pages;
//
//import java.time.Duration;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//public class LoginPage {
//
//    private WebDriver driver;
//    private WebDriverWait wait;
//
//    // ✅ Correct Constructor (ONLY ONE)
//    public LoginPage(WebDriver driver) {
//        this.driver = driver;
//        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
//    }
//
//    // 🔹 Locators
//
//    private By emailField = By.xpath(
//        "//android.widget.LinearLayout[@resource-id='com.mapsted.demo:id/email']//android.widget.EditText"
//    );
//
//    private By passwordField = By.id("com.mapsted.demo:id/edit_text_password");
//
//    private By signInButton = By.id("com.mapsted.demo:id/email_sign_in_button");
//
//    private By welcomeText = By.id("com.mapsted.demo:id/txt_header");
//
//    private By footerText = By.id("com.mapsted.demo:id/mapstedCopyrightMessage");
//
//    private By logo = By.id("com.mapsted.demo:id/logo");
//
//    // 🔹 Actions
//
//    public void enterEmail(String email) {
//        WebElement emailInput = wait.until(
//            ExpectedConditions.visibilityOfElementLocated(emailField)
//        );
//        emailInput.clear(); // Important (pre-filled)
//        emailInput.sendKeys(email);
//    }
//
//    public void enterPassword(String password) {
//        WebElement passwordInput = wait.until(
//            ExpectedConditions.visibilityOfElementLocated(passwordField)
//        );
//        passwordInput.clear();
//        passwordInput.sendKeys(password);
//    }
//
//    public void clickSignIn() {
//        wait.until(
//            ExpectedConditions.elementToBeClickable(signInButton)
//        ).click();
//    }
//
//    // 🔹 Validations
//
//    public boolean isLoginPageDisplayed() {
//        return wait.until(
//            ExpectedConditions.visibilityOfElementLocated(welcomeText)
//        ).isDisplayed();
//    }
//
//    public boolean isLogoDisplayed() {
//        return wait.until(
//            ExpectedConditions.visibilityOfElementLocated(logo)
//        ).isDisplayed();
//    }
//
//    public String getFooterText() {
//        return wait.until(
//            ExpectedConditions.visibilityOfElementLocated(footerText)
//        ).getText();
//    }
//
//    public boolean isFooterDisplayed() {
//        return wait.until(
//            ExpectedConditions.visibilityOfElementLocated(footerText)
//        ).isDisplayed();
//    }
//
//    // 🔹 Combined Action
//
//    public void login(String email, String password) {
//        enterEmail(email);
//        enterPassword(password);
//        clickSignIn();
//    }
//}

package com.mapsted.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Locators
    private By logo = By.id("com.mapsted.demo:id/logo");
    private By welcomeText = By.id("com.mapsted.demo:id/txt_header"); // "Welcome to Mapsted"
    private By emailField = By.xpath("//android.widget.LinearLayout[@resource-id='com.mapsted.demo:id/email']//android.widget.EditText");
    private By passwordField = By.id("com.mapsted.demo:id/edit_text_password");
    private By signInButton = By.id("com.mapsted.demo:id/email_sign_in_button");
    private By errorMessage = By.id("com.mapsted.demo:id/textinput_error");
    private By copyrightFooter = By.id("com.mapsted.demo:id/mapstedCopyrightMessage");

    // Validation Methods
    public boolean isLogoVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(logo)).isDisplayed();
    }

    public String getWelcomeText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeText)).getText();
    }

    public String getCopyrightText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(copyrightFooter)).getText();
    }

    public boolean isCopyrightVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(copyrightFooter)).isDisplayed();
    }

    // Action Method
    public void login(String email, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).clear();
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(signInButton).click();
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }
}
