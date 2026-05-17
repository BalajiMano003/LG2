package com.mapsted.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.mapsted.base.BaseTest;
import com.mapsted.pages.LoginPage;
import com.mapsted.pages.LocationPermissionPage;

public class LocationPermissionTest extends BaseTest {

    @Test
    public void validateLocationPermissionFlow() throws InterruptedException {

        System.out.println("Starting Location Permission Test");

        // Step 1: Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("mobile@mapsted.com", "Passw0rd1@");

        // 🔥 IMPORTANT WAIT (for navigation)
        Thread.sleep(3000);

        // Step 2: Permission Page
        LocationPermissionPage locationPage = new LocationPermissionPage(driver);

        // Step 3: Validate screen
        Assert.assertTrue(locationPage.isLocationPermissionScreenDisplayed(),
                "Location screen not displayed");

        // Step 4: Click Enable
        locationPage.clickEnable();

        // Step 5: Allow system permission
        locationPage.allowLocationPermission();

        System.out.println("Location permission flow completed");
        
        driver.getPageSource().contains("Property Listing");
    }
}