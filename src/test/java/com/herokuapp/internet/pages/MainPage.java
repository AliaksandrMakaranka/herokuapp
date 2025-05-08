package com.herokuapp.internet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MainPage extends BasePage {
    private final By allLinks = By.tagName("a");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        open(BASE_URL);
        LOG.info("Opened main page");
    }

    public List<WebElement> getAllLinks() {
        return findElements(allLinks);
    }

    public void navigateTo(String linkText) {
        LOG.info("Navigating to: " + linkText);
        clickElement(By.linkText(linkText));
    }

    public void navigateByHref(String href) {
        LOG.info("Navigating to href: " + href);
        clickElement(By.xpath("//a[@href='" + href + "']"));
    }
}