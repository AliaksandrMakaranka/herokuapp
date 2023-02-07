package com.herokuapp.internet;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.time.Duration;
import java.util.List;


public class WebTest {
    private static final String mainPage = "https://the-internet.herokuapp.com/";
    private final static int WAIT_FOR_ELEMENT_TIMEOUT = 10;
    private WebDriver driver;
    private WebDriverWait webDriverWait;
    private Logger LOG;

    @BeforeAll
    public static void setUpClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_FOR_ELEMENT_TIMEOUT));
        LOG = LoggerFactory.getLogger(WebTest.class);
        driver.manage().window().maximize();
    }


    @Test
    @DisplayName("OpenHomeAndShowNamesLinks")
    public void mainPage() {
        homePage(mainPage);
        List<WebElement> allLinks = driver.findElements(By.tagName("a"));

        for (int i = 0;i < allLinks.size();i++) {
            System.out.println(i + " : " + allLinks.get(i).getText());
        }
    }


    private void homePage(String http) {
        driver.get(http);
    }

    private void openLink(String locator) {
        WebElement link = waitAndFindElement(By.xpath(locator));
        link.click();
    }


    private WebElement waitAndFindElement(By locator){
        return webDriverWait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        LOG.atError().log("https://www.slf4j.org/api/org/slf4j/simple/SimpleLogger.html"
                + "\nATTENTION ONLY CRITICAL ERRORS ARE DISPLAYED");

    }

    @AfterAll
    public static void tearDownClass(){
        System.err.println("tearDownClass is Disabled");
    }


}