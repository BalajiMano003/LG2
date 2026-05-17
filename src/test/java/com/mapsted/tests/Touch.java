package com.mapsted.tests;

import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.io.File;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput.Origin;

import com.mapsted.base.BaseTest;

public class Touch extends BaseTest {

	
	
	PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
	Sequence tap = new Sequence(finger, 1);

	public void tap() {
		tap.addAction(finger.createPointerMove(Duration.ofMillis(0), Origin.viewport(), 500, 800)); // Move to the tap location
		tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg())); // Touch down	
		tap.addAction(new Pause(finger, Duration.ofMillis(200))); // Hold for 200ms
			tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg())); // Touch up
	}
	
	public void takeScreenshot() throws Exception {

	    TakesScreenshot shot = (TakesScreenshot) driver;

	    File src = shot.getScreenshotAs(OutputType.FILE);
	    File dest = new File("screenshot.png");

	    FileUtils.copyFile(src, dest);
	}
		
	public void performTap() {
		driver.perform(java.util.List.of(tap));
	
}
}
