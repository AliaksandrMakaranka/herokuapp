package com.herokuapp.internet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckboxesPage extends BasePage {
    private final By firstCheckbox = By.xpath("(//input[@type='checkbox'])[1]");
    private final By secondCheckbox = By.xpath("(//input[@type='checkbox'])[2]");

    public CheckboxesPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openPage("checkboxes");
        LOG.info("Opened checkboxes page");
    }

    public boolean isFirstCheckboxSelected() {
        return waitAndFindElement(firstCheckbox).isSelected();
    }

    public boolean isSecondCheckboxSelected() {
        return waitAndFindElement(secondCheckbox).isSelected();
    }

    public void toggleFirstCheckbox() {
        LOG.info("Toggling first checkbox");
        clickElement(firstCheckbox);
    }

    public void toggleSecondCheckbox() {
        LOG.info("Toggling second checkbox");
        clickElement(secondCheckbox);
    }

    public String getFirstCheckboxAttribute(String attribute) {
        return waitAndFindElement(firstCheckbox).getAttribute(attribute);
    }
}