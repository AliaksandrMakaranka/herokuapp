package com.herokuapp.internet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static com.herokuapp.internet.World.*;

public class DragAndDropHelper {
    private WebDriver driver;

    public void start(WebDriver driver) {
        driver = new ChromeDriver();
        switch (driver) {
            case OSLO:
                driver.findElement(By.xpath("//div[@id=\"box1\"]"));
                break;
            case NORWAY:
                driver.findElement(By.xpath("//div[@id=\"box101\"]"));
                break;
            case STOCKHOLM:
                driver.findElement(By.xpath("//div[@id=\"box2\"]"));
                break;
            case SWEDEN:
                driver.findElement(By.xpath("//div[@id=\"box102\"]"));
                break;
            case WASHINGTON:
                driver.findElement(By.xpath("//div[@id=\"box4\"]"));
                break;
            case UNITEDSTATES:
                driver.findElement(By.xpath("//div[@id=\"box103\"]"));
                break;
            case COPENHAGEN:
                driver.findElement(By.xpath("//div[@id=\"box5\"]"));
                break;
            case DENMARK:
                driver.findElement(By.xpath("//div[@id=\"box104\"]"));
                break;
            case SEOUL:
                driver.findElement(By.xpath("//div[@id=\"box6\"]"));
                break;
            case SOUTHKOREA:
                driver.findElement(By.xpath("//div[@id=\"box105\"]"));
                break;
            case ROME:
                driver.findElement(By.xpath("//div[@id=\"box7\"]"));
                break;
            case ITALY:
                driver.findElement(By.xpath("//div[@id=\"box106\"]"));
                break;
            case MADRID:
                driver.findElement(By.xpath("//div[@id=\"box8\"]"));
                break;
            case SPAIN:
                driver.findElement(By.xpath("//div[@id=\"box107\"]"));
            default:
                throw new NullPointerException();
        }
    }
}
