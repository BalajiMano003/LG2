package com.mapsted.tests;

import com.mapsted.base.BaseTest;
import com.mapsted.pages.PropertyListingPage;

import io.appium.java_client.AppiumBy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class PropertyListingTest extends BaseTest {

    private List<PropertyListingPage.PropertyInfo> properties;

    @Test(priority = 1)
    public void validateScreenAndTitle() {
        PropertyListingPage listPage = new PropertyListingPage(driver);
        new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(ExpectedConditions.visibilityOfElementLocated(
                By.id("com.mapsted.demo:id/propertyListRv")
            ));
        Assert.assertTrue(listPage.isTitleDisplayed(), "Property Listing screen did not load!");
        Assert.assertEquals(listPage.getTitleText(), "Select Property", "Title mismatch");
    }

    @Test(priority = 2)
    public void fetchAndPrintAllProperties() {
        PropertyListingPage listPage = new PropertyListingPage(driver);
        properties = listPage.getAllPropertiesWithScrollInfo();
        int propertyCount = properties.size();
        System.out.println("Total properties listed on the screen: " + propertyCount);
        Assert.assertTrue(propertyCount > 0, "No properties were found in the list!");
        System.out.println("\n--- Property List (after scroll) ---");
        for (PropertyListingPage.PropertyInfo property : properties) {
            System.out.println("Property: " + property.name + " | Address: " + property.address);
        }
        System.out.println("--- End of Property List ---\n");
    }

    @Test(priority = 3)
    public void validatePropertiesMissingAddress() {
        Assert.assertNotNull(properties, "Property list was not fetched!");
        List<String> missingAddressProperties = new java.util.ArrayList<>();
        for (PropertyListingPage.PropertyInfo property : properties) {
            Assert.assertTrue(property.name != null && !property.name.isEmpty(),
                "Property card missing name!");
            if (property.address == null || property.address.trim().isEmpty() || property.address.trim().equals(",")) {
                missingAddressProperties.add(property.name);
            }
        }
        if (!missingAddressProperties.isEmpty()) {
            System.out.println("\nProperties missing address:");
            for (String name : missingAddressProperties) {
                System.out.println("- " + name);
            }
        } else {
            System.out.println("\nAll properties have addresses.");
        }
        
        // Need to write test case for validating image, its shows some dificulty now, as default image and regular image are same, so we are not able to validate that, will work on it and update you.
    }
    @Test(priority = 4)
    public void downloadFirstPropertyAndValidateTime() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        // First property card
        By firstCard = By.xpath("(//androidx.cardview.widget.CardView[@resource-id='com.mapsted.demo:id/card'])[1]");

        // Download CTA inside first card
        By downloadCTA = By.xpath("(//androidx.cardview.widget.CardView[@resource-id='com.mapsted.demo:id/card'])[1]//android.widget.TextView[@resource-id='com.mapsted.demo:id/tvDownloadStatus']");

        // Progress bar
        By progressBar = By.id("com.mapsted.demo:id/pvPropertyDownload");

        // Up to date text
        By upToDateText = By.xpath("(//androidx.cardview.widget.CardView[@resource-id='com.mapsted.demo:id/card'])[1]//android.widget.TextView[@resource-id='com.mapsted.demo:id/tvDownloadStatus' and @text='Up To Date']");

        // Wait for first card visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstCard));

        // Tap Download
        WebElement downloadElement = wait.until(ExpectedConditions.elementToBeClickable(downloadCTA));
        downloadElement.click();

        System.out.println("Clicked on Download Property CTA");

        // Wait for progress bar to appear (download started)
        wait.until(ExpectedConditions.visibilityOfElementLocated(progressBar));
        System.out.println("Download started...");

        // Capture start time
        long startTime = System.currentTimeMillis();

        // Wait for progress bar to disappear (download completed)
        wait.until(ExpectedConditions.invisibilityOfElementLocated(progressBar));

        // Capture end time
        long endTime = System.currentTimeMillis();

        long durationSeconds = (endTime - startTime) / 1000;

        System.out.println("Download completed in: " + durationSeconds + " seconds");

        // Validate "Up To Date"
        WebElement upToDate = wait.until(ExpectedConditions.visibilityOfElementLocated(upToDateText));

        Assert.assertTrue(upToDate.isDisplayed(), "Up To Date status not shown after download!");

        System.out.println("Validation Passed: Property shows 'Up To Date'");
        
        driver.findElement(AppiumBy.accessibilityId("Navigate up")).click();
        }
}