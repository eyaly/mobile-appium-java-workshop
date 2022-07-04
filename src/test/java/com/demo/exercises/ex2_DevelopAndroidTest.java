package com.demo.exercises;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static com.demo.Config.region;

public class ex2_DevelopAndroidTest {

    private String SAUCE_EU_URL = "https://ondemand.eu-central-1.saucelabs.com/wd/hub";
    private String SAUCE_US_URL = "https://ondemand.us-west-1.saucelabs.com/wd/hub";

    private AndroidDriver driver;

    @BeforeMethod
    public void setup(Method method) throws MalformedURLException {

        String methodName = method.getName();
        System.out.println("*** BeforeMethod hook. Running method " + methodName + " ***");
        URL url;

        switch (region) {
            case "us":
                System.out.println("region is us");
                url = new URL(SAUCE_US_URL);
                break;
            case "eu":
            default:
                System.out.println("region is eu");
                url = new URL(SAUCE_EU_URL);
                break;
        }

        MutableCapabilities capabilities = new MutableCapabilities();
        MutableCapabilities sauceOptions = new MutableCapabilities();

        //find a device in the cloud
        capabilities.setCapability("platformName", "android");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("appium:deviceName", "Samsung.*");
        capabilities.setCapability("appium:platformVersion", "12");

         capabilities.setCapability("appium:app",
               "https://github.com/saucelabs/sample-app-mobile/releases/download/2.7.1/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk");
        capabilities.setCapability("appium:appWaitActivity","com.swaglabsmobileapp.MainActivity");

        // Sauce capabilities
        sauceOptions.setCapability("name", methodName);
        sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
        capabilities.setCapability("sauce:options", sauceOptions);

        driver = new AndroidDriver(url, capabilities);

    }

    @Test
    public void demoTest() {
        System.out.println("*** Start demoTest test ***");

        // (1) Add code to set username and password, click on the login button
        // Login
        // Accessibility-id test-Username  sendKeys standard_user
        // Accessibility-id test-Password  sendKeys secret_sauce
        // Accessibility-id test-LOGIN click
        driver.findElement(AppiumBy.accessibilityId("test-Username")).sendKeys("standard_user");

        // (2) add code to verify we are on the next page
        // Verify using Assert.assertTrue
        // xpath -> //android.widget.ScrollView[@content-desc="test-PRODUCTS"]
        //Assert.assertTrue(isOnProductsPage());

    }

    @AfterMethod
    public void teardown(ITestResult result) {
        System.out.println("*** AfterMethod hook ***");
        // (3) Add code to check if test passed or failed
        try {
        //     ((JavascriptExecutor) driver).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        } finally {
            driver.quit();
        }
    }

//    public boolean isOnProductsPage() {
//
//        //Create an instance of an explicit wait so that we can dynamically wait for an element
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//
//        //wait for the product field to be visible and store that element into a variable
//        try {
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.ScrollView[@content-desc=\"test-PRODUCTS\"]")));
//        } catch (TimeoutException e){
//            return false;
//        }
//        return true;
//    }

    // (4) in resources -> config -> myDemoTests.xml point to this class
    // <class name="com.demo.exercises.ex2_DevelopAndroidTest"></class>

    // (5) In the terminal run the cmd : "mvn clean test"
}
