package com.herokuapp.internet.pages.bugorfaatures;

import com.herokuapp.internet.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class DragAndDropPage extends BasePage {
    private final By columnA = By.id("column-a");
    private final By columnB = By.id("column-b");

    public DragAndDropPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openPage("drag_and_drop");
        LOG.info("Opened drag and drop page");
    }

    public WebElement getColumnA() {
        return waitAndFindElement(columnA);
    }

    public WebElement getColumnB() {
        return waitAndFindElement(columnB);
    }

    public String getColumnAHeader() {
        return getColumnA().getText();
    }

    public String getColumnBHeader() {
        return getColumnB().getText();
    }

    public void dragAToB() {
        LOG.info("Dragging column A to column B");
        WebElement elementA = getColumnA();
        WebElement elementB = getColumnB();
        Actions DragAndDropHelper = null;//todo
        //DragAndDropHelper.dragAndDrop(driver, elementA, elementB);
    }

    public void dragBToA() {
        LOG.info("Dragging column B to column A");
        WebElement elementA = getColumnA();
        WebElement elementB = getColumnB();
        Actions DragAndDropHelper = null;//todo
        //DragAndDropHelper.dragAndDrop(driver, elementB, elementA);
    }
}