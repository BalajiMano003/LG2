//package com.mapsted.tests;
//
//import io.appium.java_client.android.AndroidDriver;
//import io.appium.java_client.android.options.UiAutomator2Options;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import java.net.URL;
//import java.time.Duration;
//
//public class LoginPageValidationTest {
//    protected AndroidDriver driver;
//
//    @BeforeClass
//    public void setup() throws Exception {
//        UiAutomator2Options options = new UiAutomator2Options()
//            .setPlatformName("Android")
//            .setDeviceName("Android Device")
//            .setUdid("7e7dfb26")
//            .setAppPackage("com.mapsted.demo")
//            .setAppActivity("com.mapsted.demo.activities.LoginActivity")
//            .setNoReset(true) // Keeps the app state between tests
//            .setAutomationName("UiAutomator2")
//            .setNewCommandTimeout(Duration.ofSeconds(300));
//
//        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//    }
//
//    @AfterClass(alwaysRun = true)
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
//}

package com.mapsted.tests;

import com.mapsted.base.BaseTest;
import com.mapsted.pages.LoginPage;
import com.mapsted.pages.PropertyListingPage;
import com.mapsted.config.ConfigData;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageValidationTest extends BaseTest {

    @Test(priority = 1)
    public void testUIElementsVisibility() {
        LoginPage loginPage = new LoginPage(driver);

        // Validate Logo
        Assert.assertTrue(loginPage.isLogoVisible(), "Mapsted logo is not visible!");

        // Validate Welcome Header
        String welcomeHeader = loginPage.getWelcomeText();
        Assert.assertEquals(welcomeHeader, "Welcome to Mapsted", "Header text mismatch!");

        // Validate Copyright Footer
        Assert.assertTrue(loginPage.isCopyrightVisible(), "Copyright footer is missing!");
        Assert.assertTrue(loginPage.getCopyrightText().contains("Mapsted Corp."), "Copyright text mismatch!");
    }

    @Test(priority = 2, dependsOnMethods = "testUIElementsVisibility")
    public void testInvalidLoginErrorMessage() {
        LoginPage loginPage = new LoginPage(driver);
        
        loginPage.login(ConfigData.INVALID_EMAIL, ConfigData.INVALID_PASSWORD);
        
        String actualError = loginPage.getErrorMessage();
        Assert.assertTrue(actualError.contains("Login failed"), "Error message was not displayed correctly!");
    }

    @Test(priority = 3)
    public void testSuccessfulLogin() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        
        // 1. Perform login using ConfigData
        loginPage.login(ConfigData.VALID_EMAIL, ConfigData.VALID_PASSWORD);
        
        // 2. Wait 5 seconds for the next screen to load fully
        Thread.sleep(5000);
        
        // 3. Initialize the Listing Page
        PropertyListingPage listingPage = new PropertyListingPage(driver);
        
        // 4. Robust Assertions
        Assert.assertTrue(listingPage.isTitleDisplayed(), "Property Listing screen did not load!");
        
        String actualTitle = listingPage.getTitleText();
        Assert.assertEquals(actualTitle, "Select Property", "The screen title text is incorrect!");
        
        System.out.println("Login successful and verified on the '" + actualTitle + "' screen.");
    }
}
