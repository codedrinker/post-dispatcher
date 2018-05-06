package com.github.codedrinker.dispatcher;

import com.github.codedrinker.adapter.GithubSigninAdapter;
import com.github.codedrinker.entity.DispatchMarkdown;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by codedrinker on 04/05/2018.
 */
public class CSDNMarkdownDispatcher extends AbstractDispatcher {

    public CSDNMarkdownDispatcher(WebDriver driver) {
        super(driver);
    }

    @Override
    void innerPost(DispatchMarkdown dispatchMarkdown) {
        directToPostPage();
        publishPost(dispatchMarkdown);
    }

    private void publishPost(DispatchMarkdown dispatchMarkdown) {
        untilTitleLocated("markdown");
        findElementUntil(By.className("input-file-title")).sendKeys(dispatchMarkdown.getTitle());

        driver.findElement(By.className("editor-content")).clear();
        String[] lines = StringUtils.split(dispatchMarkdown.getContent(),"\n");
        for (String line : lines) {
            driver.findElement(By.className("editor-content")).sendKeys(line);
            driver.findElement(By.className("editor-content")).sendKeys(Keys.ENTER);
            driver.findElement(By.className("editor-content")).sendKeys(Keys.ENTER);
        }

        findElementUntil(By.className("btn-blog-publish")).click();

        findElementUntil(By.id("tags-con-blog")).findElement(By.tagName("input")).sendKeys(dispatchMarkdown.getTags());
        findElementUntil(By.id("csdn-tags-blog-button")).click();
        Select selType = new Select(findElementUntil(By.id("input-blog-type")));
        selType.selectByIndex(1);
        Select radChl = new Select(findElementUntil(By.id("input-blog-channel")));
        radChl.selectByIndex(12);
        findElementUntil(By.id("csdn-post-blog-button")).click();
    }

    private void directToPostPage() {
        untilTitleLocated("CSDN");
        driver.get("http://write.blog.csdn.net/mdeditor");
    }

    @Override
    void login() {
        getDriver().get("https://passport.csdn.net/account/login");
        WebElement githubSigninLink = findElementUntil(By.id("githubAuthorizationUrl"));
        githubSigninLink.click();
        new GithubSigninAdapter(driver).signin();
    }
}
