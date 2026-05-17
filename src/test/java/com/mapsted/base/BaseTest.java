//package com.mapsted.base;
//
//import io.appium.java_client.android.AndroidDriver;
//import io.appium.java_client.android.options.UiAutomator2Options;
//
//import java.net.URI;
//
//
//
//
//public class BaseTest {
//
//    public static AndroidDriver driver;
//
//    public void setup() throws Exception {
//    	
//        UiAutomator2Options options = new UiAutomator2Options();
//
//        options.setPlatformName("Android");
//        options.setDeviceName("Android Device");
//        options.setUdid("7e7dfb26");
//
//        options.setAppPackage("com.mapsted.demo");
//        options.setAppActivity("com.mapsted.demo.activities.LoginActivity");
//        options.setCapability("appium:ignoreHiddenApiPolicyError", true);
//        options.setCapability("appium:noReset", false);
//        options.setCapability("appium:autoGrantPermissions", false);
//        options.setCapability("appium:newCommandTimeout", 300);
//        
//        driver = new AndroidDriver(
//                URI.create("http://127.0.0.1:4723").toURL(),
//                options
//                
//                
//        );
//    }
//
//    public void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
//}

package com.mapsted.base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import java.net.URL;
import java.time.Duration;

public class BaseTest {
    public static AndroidDriver driver;

    @BeforeSuite
    public void setup() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options()
            .setPlatformName("Android")
            .setDeviceName("Android Device")
            .setUdid("7e7dfb26")
            .setAppPackage("com.mapsted.demo")
            .setAppActivity("com.mapsted.demo.activities.LoginActivity")
            .setNoReset(true) ;// Keeps session alive

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
