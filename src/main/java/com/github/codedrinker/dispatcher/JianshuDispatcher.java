package com.github.codedrinker.dispatcher;

import com.github.codedrinker.entity.DispatchMarkdown;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by codedrinker on 04/05/2018.
 */
public class JianshuDispatcher extends AbstractDispatcher {

    public JianshuDispatcher(WebDriver driver) {
        super(driver);
    }

    @Override
    void innerPost(DispatchMarkdown dispatchMarkdown) {
        directToPostPage();
        publishPost(dispatchMarkdown);
    }

    private void publishPost(DispatchMarkdown dispatchMarkdown) {
        new WebDriverWait(driver, 60)
                .until(ExpectedConditions.titleContains("写文章"));
        findElementUntil(By.className("fa-plus-circle")).click();
        findElementUntil(By.className("_24i7u")).sendKeys(dispatchMarkdown.getTitle());
        findElementUntil(By.id("arthur-editor")).sendKeys(dispatchMarkdown.getContent());
        findElementUntil(By.className("fa-mail-forward")).click();
    }

    private void directToPostPage() {
        new WebDriverWait(driver, 300)
                .until(ExpectedConditions.titleContains("简书 - 创作你的创作"));
        driver.get("https://www.jianshu.com/writer#");
    }

    @Override
    void login() {
        getDriver().get("https://www.jianshu.com/sign_in");
        getDriver().findElement(By.id("session_email_or_mobile_number")).sendKeys(getConfiguration().getJianshuUsername());
        getDriver().findElement(By.id("session_password")).sendKeys(getConfiguration().getJianshuPassword());
        getDriver().findElement(By.id("sign-in-form-submit-btn")).click();
    }
}
