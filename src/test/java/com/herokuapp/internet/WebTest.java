package com.herokuapp.internet;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;


public class WebTest {
    private final int WAIT_FOR_ELEMENT_TIMEOUT = 10;
    private WebDriver driver;
    private WebDriverWait webDriverWait;
    private static Logger logger;

    @BeforeAll
    public static void setUpClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_FOR_ELEMENT_TIMEOUT));
        logger = LoggerFactory.getLogger(WebTest.class);
    }

//    @Test
//    @Disabled
//    void mainTest() {
//    }

    @Test
    public void mainPage() {
        driver.get("https://the-internet.herokuapp.com/");
        System.out.println("e7c5703604daa9cc128ccf5a5d3e993513758913");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
//    public static void tearDownClass(){
//        logger.atError().log();
//    }


}