package com.github.codedrinker.adapter;

import com.github.codedrinker.util.Loader;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by codedrinker on 05/05/2018.
 */
public class GithubSigninAdapter {
    private WebDriver driver;

    public GithubSigninAdapter(WebDriver driver) {
        this.driver = driver;
    }

    public void signin() {
        String currentWindow = driver.getWindowHandle();
        for (String s : driver.getWindowHandles()) {
            if (!StringUtils.equals(currentWindow, s)) {
                driver.switchTo().window(s);
            }
        }
        WebElement usernameInput = new WebDriverWait(driver, 20)
                .until(ExpectedConditions.presenceOfElementLocated(By.id("login_field")));
        usernameInput.sendKeys(Loader.load().getGithubUsername());
        driver.findElement(By.id("password")).sendKeys(Loader.load().getGithubPassword());
        driver.findElement(By.name("commit")).click();
    }
}
