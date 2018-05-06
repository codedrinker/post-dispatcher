package com.github.codedrinker.dispatcher;

import com.github.codedrinker.config.Configuration;
import com.github.codedrinker.entity.DispatchMarkdown;
import com.github.codedrinker.util.Loader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by codedrinker on 04/05/2018.
 */
public abstract class AbstractDispatcher {
    protected WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    public AbstractDispatcher(WebDriver driver) {
        this.driver = driver;
    }

    public void post(DispatchMarkdown dispatchMarkdown) {
        login();
        innerPost(dispatchMarkdown);
    }

    abstract void innerPost(DispatchMarkdown dispatchMarkdown);

    abstract void login();

    public Configuration getConfiguration() {
        return Loader.load();
    }

    protected WebElement findElementUntil(By by) {
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
        }
        return new WebDriverWait(driver, 60).until(ExpectedConditions.presenceOfElementLocated(by));
    }
}
