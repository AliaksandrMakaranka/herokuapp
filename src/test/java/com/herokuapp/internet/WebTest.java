package com.herokuapp.internet;



import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;


public class WebTest {
    private static final String mainPage = "https://the-internet.herokuapp.com/";
    private final static int WAIT_FOR_ELEMENT_TIMEOUT = 5;
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
        open(mainPage);
        List<WebElement> allLinks = driver.findElements(By.tagName("a"));
//            int i = 0;
//            for(WebElement links : allLinks){
//                System.out.println(i++ +" : "+ links.getText());
//            }
        for (int i = 0;i < allLinks.size();i++) {
            System.out.println(i + " : " + allLinks.get(i).getText());
        }
    }

    @Test
    public void addAndRemoveElementsTest() throws InterruptedException {
        open(mainPage);
        click("//a[@href=\"/add_remove_elements/\"]");
        String locatorAddElement = "//button[@onclick=\"addElement()\"]";
        String locatorDeleteElement = "//button[@class=\"added-manually\"]";
        Thread.sleep(1000);
        click(locatorAddElement);
        //assert
        Thread.sleep(500);
        click(locatorDeleteElement);
        //assert
    }

    @Test
    public void checkSignInWithLoginAndPassTest(){
        String username = "admin";
        String password = "admin";

        String URL = "https://" + username + ":" + password + "@" + "the-internet.herokuapp.com/basic_auth";
        open(URL);
        String actualResult = "Congratulations! You must have the proper credentials.";
        //assert
    }

    @Test
    public void checkSignInWithCancelEntryTest() throws InterruptedException {
        open(mainPage);
        click("//a[@href=\"/basic_auth\"]");
        Thread.sleep(1000);
        Alert alert = driver.switchTo().alert();
        alert.dismiss();
    }


    @Test
    public void brokenImageTest() throws IOException {
        open(mainPage);
        String locator = "//a[@href=\"/broken_images\"]";
        click(locator);

        URL url = new URL("https://the-internet.herokuapp.com/broken_images");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setConnectTimeout(2000);

        http.connect();


        if(http.getResponseCode() == 200) {
            System.out.println("HTTP STATUS: " + http.getResponseMessage());
        } else {
            System.err.println("HTTP STATUS: " + http.getResponseCode());
        }

        http.disconnect();
    }

    @Test
    public void bestLocatorsTest(){
        open(mainPage);
        click("//a[@href=\"/challenging_dom\"]");

        click("//a[@class=\"button\"]");

        click("//a[@class=\"button alert\"]");

        click("//a[@class=\"button success\"]");
    }

    @Test
    public void checkBoxesTest(){

    }

    private void open(String http) {
        driver.get(http);
    }

    private void click(String locator) {
        WebElement link = waitAndFindElement(By.xpath(locator));
        link.click();
    }


    private WebElement waitAndFindElement(By locator){
        return webDriverWait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
//            driver.close();
//            driver.quit();
        }
//        LOG.atError().log("\nATTENTION ONLY CRITICAL ERRORS ARE DISPLAYED");
    }

//    @AfterAll
//    public static void tearDownClass(){
//        System.err.println("tearDownClass is Disabled");
//    }


}