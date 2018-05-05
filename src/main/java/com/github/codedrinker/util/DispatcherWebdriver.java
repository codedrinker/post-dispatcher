package com.github.codedrinker.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by codedrinker on 01/05/2018.
 */
public class DispatcherWebdriver {

    public static WebDriver getDriver() {
        System.setProperty("webdriver.gecko.driver", "/Users/codedrinker/App/driver/geckodriver"); // 需要配置
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        firefoxProfile.setPreference("dom.webnotifications.enabled", false);
        firefoxProfile.setPreference("browser.startup.homepage", "about:blank");
        desiredCapabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
        return new FirefoxDriver(desiredCapabilities);
    }
}
