package com.github.codedrinker.dispatcher;

import com.github.codedrinker.entity.DispatchMarkdown;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by codedrinker on 04/05/2018.
 */
public class SegmentFaultDispatcher extends AbstractDispatcher {

    public SegmentFaultDispatcher(WebDriver driver) {
        super(driver);
    }

    @Override
    void innerPost(DispatchMarkdown dispatchMarkdown) {
        directToPostPage();
        publishPost(dispatchMarkdown);
    }

    private void publishPost(DispatchMarkdown dispatchMarkdown) {
        new WebDriverWait(driver, 20).until(ExpectedConditions.titleContains("撰写文章 - SegmentFault 思否"));
        new WebDriverWait(driver, 20)
                .until(ExpectedConditions.presenceOfElementLocated(By.id("myTitle")))
                .sendKeys(dispatchMarkdown.getTitle());
        new WebDriverWait(driver, 20)
                .until(ExpectedConditions.presenceOfElementLocated(By.id("myEditor")))
                .sendKeys(dispatchMarkdown.getContent());


        WebElement tagsElement = driver.findElement(By.className("sf-typeHelper--fortags")).findElement(By.tagName("input"));
        tagsElement.sendKeys(dispatchMarkdown.getTags());
        try {
            Thread.sleep(5000L);
            tagsElement.sendKeys(Keys.ENTER);
        } catch (InterruptedException e) {
        }
        driver.findElement(By.id("publishIt")).click();
    }

    private void directToPostPage() {
        new WebDriverWait(driver, 20)
                .until(ExpectedConditions.presenceOfElementLocated(By.className("fa-file-text-o")));
        driver.get("https://segmentfault.com/write");
    }

    @Override
    void login() {
        getDriver().get("https://segmentfault.com/user/login");
        getDriver().findElement(By.name("username")).sendKeys(getConfiguration().getSfUsername());
        getDriver().findElement(By.name("password")).sendKeys(getConfiguration().getSfPassword());
        getDriver().findElement(By.tagName("form")).submit();
    }
}
