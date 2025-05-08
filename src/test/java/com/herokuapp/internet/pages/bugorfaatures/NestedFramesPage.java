package com.herokuapp.internet.pages.bugorfaatures;

import com.herokuapp.internet.pages.BasePage;
import org.openqa.selenium.WebDriver;

public class NestedFramesPage extends BasePage {

    public NestedFramesPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openPage("nested_frames");
        LOG.info("Opened nested frames page");
    }

    public String getFrameText(String frameName) {
        LOG.info("Getting text from frame: " + frameName);

        switch (frameName.toLowerCase()) {
            case "top":
                driver.switchTo().frame("frame-top");
                break;
            case "left":
                driver.switchTo().frame("frame-top");
                driver.switchTo().frame("frame-left");
                break;
            case "middle":
                driver.switchTo().frame("frame-top");
                driver.switchTo().frame("frame-middle");
                break;
            case "right":
                driver.switchTo().frame("frame-top");
                driver.switchTo().frame("frame-right");
                break;
            case "bottom":
                driver.switchTo().frame("frame-bottom");
                break;
            default:
                throw new IllegalArgumentException("Frame name not recognized: " + frameName);
        }

        String text = driver.findElement(org.openqa.selenium.By.xpath("//body")).getText();
        driver.switchTo().defaultContent();
        return text;
    }
}