package com.github.codedrinker.dispatcher;

import com.github.codedrinker.config.Configuration;
import com.github.codedrinker.entity.DispatchMarkdown;
import com.github.codedrinker.util.Loader;
import org.openqa.selenium.WebDriver;

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
}
