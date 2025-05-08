package com.herokuapp.internet.pages.bugorfaatures;

import com.herokuapp.internet.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IFramePage extends BasePage {
    private final By iframeElement = By.id("mce_0_ifr");
    private final By textArea = By.id("tinymce");

    public IFramePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openPage("iframe");
        LOG.info("Opened iframe page");
    }

    public void switchToIFrame() {
        LOG.info("Switching to iframe");
        driver.switchTo().frame(waitAndFindElement(iframeElement));
    }

    public void switchToDefaultContent() {
        LOG.info("Switching back to default content");
        driver.switchTo().defaultContent();
    }

    public String getEditorText() {
        switchToIFrame();
        String text = waitAndFindElement(textArea).getText();
        switchToDefaultContent();
        LOG.info("Editor text: " + text);
        return text;
    }

    public void setEditorText(String text) {
        LOG.info("Setting editor text to: " + text);
        switchToIFrame();
        WebElement editor = waitAndFindElement(textArea);
        editor.clear();
        editor.sendKeys(text);
        switchToDefaultContent();
    }
}