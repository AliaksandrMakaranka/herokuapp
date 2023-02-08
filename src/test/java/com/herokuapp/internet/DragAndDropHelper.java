package com.herokuapp.internet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static com.herokuapp.internet.World.*;

public class DragAndDropHelper {
    private WebDriver driver;

    public DragAndDropHelper(WebDriver driver) {
        this.driver = driver;
    }

    public void start(WebDriver driver) {
        driver = new ChromeDriver();
        //TODO
        if (driver.equals(OSLO)) {
            driver.findElement(By.xpath("//div[@id=\"box1\"]"));
        } else if (driver.equals(NORWAY)) {
            driver.findElement(By.xpath("//div[@id=\"box101\"]"));
        } else if (driver.equals(STOCKHOLM)) {
            driver.findElement(By.xpath("//div[@id=\"box2\"]"));
        } else if (driver.equals(SWEDEN)) {
            driver.findElement(By.xpath("//div[@id=\"box102\"]"));
        } else if (driver.equals(WASHINGTON)) {
            driver.findElement(By.xpath("//div[@id=\"box4\"]"));
        } else if (driver.equals(UNITEDSTATES)) {
            driver.findElement(By.xpath("//div[@id=\"box103\"]"));
        } else if (driver.equals(COPENHAGEN)) {
            driver.findElement(By.xpath("//div[@id=\"box5\"]"));
        } else if (driver.equals(DENMARK)) {
            driver.findElement(By.xpath("//div[@id=\"box104\"]"));
        } else if (driver.equals(SEOUL)) {
            driver.findElement(By.xpath("//div[@id=\"box6\"]"));
        } else if (driver.equals(SOUTHKOREA)) {
            driver.findElement(By.xpath("//div[@id=\"box105\"]"));
        } else if (driver.equals(ROME)) {
            driver.findElement(By.xpath("//div[@id=\"box7\"]"));
        } else if (driver.equals(ITALY)) {
            driver.findElement(By.xpath("//div[@id=\"box106\"]"));
        } else if (driver.equals(MADRID)) {
            driver.findElement(By.xpath("//div[@id=\"box8\"]"));
        } else if (driver.equals(SPAIN)) {
            driver.findElement(By.xpath("//div[@id=\"box107\"]"));

            throw new NullPointerException();
        } else {
            throw new NullPointerException();
        }
    }
}
