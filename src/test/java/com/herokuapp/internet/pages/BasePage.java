package com.herokuapp.internet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final String BASE_URL = "https://the-internet.herokuapp.com/";
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open(String url) {
        driver.get(url);
    }

    public void openPage(String path) {
        driver.get(BASE_URL + path);
    }

    protected WebElement waitAndFindElement(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    protected void clickElement(By locator) {
        WebElement element = waitAndFindElement(locator);
        element.click();
    }

    protected void sendKeys(By locator, String text) {
        WebElement element = waitAndFindElement(locator);
        element.sendKeys(text);
    }

    public String getTitle() {
        return driver.getTitle();
    }
}