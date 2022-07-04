package com.demo.answers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.MutableCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static com.demo.Config.region;

public class ex1_CreateAndroidDriver {

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
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        System.out.println("*** AfterMethod hook ***");
        driver.quit();
    }


}
