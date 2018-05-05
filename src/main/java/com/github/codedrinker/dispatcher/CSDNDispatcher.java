package com.github.codedrinker.dispatcher;

import com.github.codedrinker.adapter.GithubSigninAdapter;
import com.github.codedrinker.entity.DispatchMarkdown;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by codedrinker on 04/05/2018.
 */
public class CSDNDispatcher extends AbstractDispatcher {

    public CSDNDispatcher(WebDriver driver) {
        super(driver);
    }

    @Override
    void innerPost(DispatchMarkdown dispatchMarkdown) {
        directToPostPage();
        publishPost(dispatchMarkdown);
    }

    private void publishPost(DispatchMarkdown dispatchMarkdown) {
        new WebDriverWait(driver, 20).until(ExpectedConditions.titleContains("写文章"));


        try {
            new WebDriverWait(driver, 30)
                    .until(ExpectedConditions.presenceOfElementLocated(By.id("btnStart")))
                    .click();
        } catch (Exception e) {
        }

        new WebDriverWait(driver, 20)
                .until(ExpectedConditions.presenceOfElementLocated(By.id("txtTitle")))
                .sendKeys(dispatchMarkdown.getTitle());


        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("var win = document.getElementById('xhe0_iframe').contentWindow || document.getElementById('xhe0_iframe').contentDocument;" +
                "win.document.body.innerHTML= '" + dispatchMarkdown.getContent() + "'");

        Select selType = new Select(driver.findElement(By.id("selType")));
        selType.selectByIndex(1);

        Select radChl = new Select(driver.findElement(By.id("radChl")));
        radChl.selectByIndex(12);

        driver.findElement(By.id("btnPublish")).click();
    }

    private void directToPostPage() {
        driver.get("https://mp.csdn.net/postedit");
    }

    @Override
    void login() {
        getDriver().get("https://passport.csdn.net/account/login");
        WebElement githubSigninLink = new WebDriverWait(driver, 20)
                .until(ExpectedConditions.presenceOfElementLocated(By.id("githubAuthorizationUrl")));
        githubSigninLink.click();
        new GithubSigninAdapter(driver).signin();
    }
}
