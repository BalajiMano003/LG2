package com.mapsted.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PropertyListingPage {

    private AppiumDriver driver;
    private WebDriverWait wait;

    public PropertyListingPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Locators
    private By title = By.id("com.mapsted.demo:id/title_logo");
    private By propertyList = By.id("com.mapsted.demo:id/propertyListRv");
    private By card = By.id("com.mapsted.demo:id/card");

    private By propertyName = By.id("com.mapsted.demo:id/propertyName");
    private By propertyLocation = By.id("com.mapsted.demo:id/propertyAddress");
    private By downloadCTA = By.id("com.mapsted.demo:id/tvDownloadStatus");
    private By propertyImage = By.id("com.mapsted.demo:id/propertyImage");

    private String defaultImageDesc = "Default Property Image"; // Adjust if you have a specific content-desc or resource for default

    // Wait for screen to load
    public void waitForScreen() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(propertyList));
    }

    // Title validation
    public boolean isTitleDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(title)).isDisplayed();
    }

    public String getTitleText() {
        return driver.findElement(title).getText();
    }

    // Get all visible properties
    public List<WebElement> getAllVisibleProperties() {
        List<WebElement> elements = driver.findElements(card);

        // Retry after small scroll if empty (lazy loading fix)
        if (elements.size() == 0) {
            scrollDown();
            elements = driver.findElements(card);
        }

        return elements;
    }

    public List<WebElement> getPropertyList() {
        return driver.findElements(card);
    }

    // Collect all properties with scrolling
    public List<WebElement> getAllPropertiesWithScroll() {
        Set<String> seenNames = new HashSet<>();
        List<WebElement> allCards = new ArrayList<>();
        int maxScrolls = 50;
        int unchangedScrolls = 0;
        int lastCount = 0;
        for (int i = 0; i < maxScrolls && unchangedScrolls < 5; i++) {
            List<WebElement> visibleCards = getPropertyList();
            boolean foundNew = false;
            for (WebElement card : visibleCards) {
                String name = "";
                try {
                    name = card.findElement(propertyName).getText();
                } catch (Exception e) {
                    // Ignore
                }
                if (!name.isEmpty() && !seenNames.contains(name)) {
                    seenNames.add(name);
                    allCards.add(card);
                    foundNew = true;
                }
            }
            if (allCards.size() == lastCount) {
                unchangedScrolls++;
            } else {
                unchangedScrolls = 0;
                lastCount = allCards.size();
            }
            scrollToBottom();
            try { Thread.sleep(500); } catch (InterruptedException e) { }
        }
        return allCards;
    }

    // Validate property card details
    public boolean validatePropertyDetails(WebElement property) {
        try {
            WebElement name = property.findElement(propertyName);
            WebElement address = property.findElement(propertyLocation);
            WebElement download = property.findElement(downloadCTA);

            return name.isDisplayed() && address.isDisplayed() && download.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateCardContent(WebElement card) {
        try {
            boolean hasName = card.findElement(propertyName).isDisplayed();
            boolean hasLoc = card.findElement(propertyLocation).isDisplayed();
            boolean hasBtn = card.findElement(downloadCTA).isDisplayed();
            return hasName && hasLoc && hasBtn;
        } catch (Exception e) {
            return false;
        }
    }

    // Check if a property card has a default image
    public boolean hasDefaultImage(WebElement card) {
        try {
            WebElement image = card.findElement(propertyImage);
            // Check content-desc or another attribute to identify default image
            String contentDesc = image.getAttribute("content-desc");
            // If your default image has a unique content-desc or src, check here
            return contentDesc != null && contentDesc.toLowerCase().contains("default");
        } catch (Exception e) {
            return true; // If image not found, treat as default/missing
        }
    }

    // Scroll method (basic swipe)
    public void scrollDown() {
        Dimension size = driver.manage().window().getSize();

        int startX = size.width / 2;
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.3);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), startX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(List.of(swipe));
    }

    public void scrollToBottom() {
        scrollDown();
    }

    public static class PropertyInfo {
        public String name;
        public String address;
        public boolean hasDefaultImage;
        public PropertyInfo(String name, String address, boolean hasDefaultImage) {
            this.name = name;
            this.address = address;
            this.hasDefaultImage = hasDefaultImage;
        }
    }

    public List<PropertyInfo> getAllPropertiesWithScrollInfo() {
        Set<String> seenNames = new HashSet<>();
        List<PropertyInfo> allProperties = new ArrayList<>();
        int maxScrolls = 50;
        int unchangedScrolls = 0;
        int lastCount = 0;
        for (int i = 0; i < maxScrolls && unchangedScrolls < 5; i++) {
            List<WebElement> visibleCards = getPropertyList();
            boolean foundNew = false;
            for (WebElement card : visibleCards) {
                String name = "";
                String address = "";
                boolean isDefaultImage = true;
                try {
                    name = card.findElement(propertyName).getText();
                } catch (Exception e) {}
                try {
                    address = card.findElement(propertyLocation).getText();
                } catch (Exception e) {}
                try {
                    isDefaultImage = hasDefaultImage(card);
                } catch (Exception e) {}
                if (!name.isEmpty() && !seenNames.contains(name)) {
                    seenNames.add(name);
                    allProperties.add(new PropertyInfo(name, address, isDefaultImage));
                    foundNew = true;
                }
            }
            if (allProperties.size() == lastCount) {
                unchangedScrolls++;
            } else {
                unchangedScrolls = 0;
                lastCount = allProperties.size();
            }
            scrollToBottom();
            try { Thread.sleep(500); } catch (InterruptedException e) { }
        }
        return allProperties;
    }
    
}