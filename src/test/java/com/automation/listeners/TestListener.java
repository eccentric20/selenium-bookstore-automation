package com.automation.listeners;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.automation.factory.DriverFactory;

import io.qameta.allure.Allure;

public class TestListener implements ITestListener {
	@Override
    public void onTestFailure(ITestResult result) {

		File screenshot = ((TakesScreenshot) DriverFactory.getDriver())
		        .getScreenshotAs(OutputType.FILE);

		try (FileInputStream fis = new FileInputStream(screenshot)) {

		    Allure.addAttachment(
		            "Screenshot - " + result.getName(),
		            fis
		    );

		} catch (IOException e) {
		    e.printStackTrace();
		}
    }
}
