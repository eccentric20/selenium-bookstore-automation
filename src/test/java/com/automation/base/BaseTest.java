package com.automation.base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.nio.file.Paths;
import java.sql.DriverManager;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;

import com.automation.factory.DriverFactory;
import com.automation.utils.Screenshot;
import com.google.common.io.Files;

import io.qameta.allure.Allure;
import io.qameta.allure.testng.AllureTestNg;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Listeners({
    com.automation.listeners.AnnotationTransformer.class,
    io.qameta.allure.testng.AllureTestNg.class
})

public class BaseTest {

    @BeforeMethod
    public void setup() {
        DriverFactory.initDriver();
        DriverFactory.getDriver().get("https://www.buybooksindia.com/");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {

        if (result.getStatus() == ITestResult.FAILURE) {

            File screenshotFile = Screenshot.captureScreenshot(
                    DriverFactory.getDriver(),
                    result.getName()
            );

            try (FileInputStream fis = new FileInputStream(screenshotFile)) {

                Allure.addAttachment(
                        "Screenshot - " + result.getName(),
                        fis
                );

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        DriverFactory.quitDriver();
    }
}
