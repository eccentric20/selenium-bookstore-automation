package com.automation.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Allure;

public class Screenshot {

	public static File captureScreenshot(WebDriver driver, String testName) {

	    String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
	            .format(new Date());

	    String screenshotDir = System.getProperty("user.dir") + "/screenshots/";
	    new File(screenshotDir).mkdirs();

	    String filePath = screenshotDir + testName + "_" + timestamp + ".png";

	    File src = ((TakesScreenshot) driver)
	            .getScreenshotAs(OutputType.FILE);

	    File destFile = new File(filePath);

	    try {
	        FileUtils.copyFile(src, destFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return destFile;
	}
}
