package com.herokuapp.internet.pages.bugorfaatures;

import com.herokuapp.internet.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FramesPage extends BasePage {
    private final By nestedFramesLink = By.linkText("Nested Frames");
    private final By iFrameLink = By.linkText("iFrame");

    public FramesPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openPage("frames");
        LOG.info("Opened frames page");
    }

    public void goToNestedFrames() {
        LOG.info("Navigating to nested frames");
        clickElement(nestedFramesLink);
    }

    public void goToIFrame() {
        LOG.info("Navigating to iFrame");
        clickElement(iFrameLink);
    }
}