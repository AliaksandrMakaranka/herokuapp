package com.herokuapp.internet;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.File;
import java.util.HashMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class WebTest {

  private static final String USERNAME = "admin";
  private static final String PASSWORD = "admin";
  private static final String mainPage = "https://the-internet.herokuapp.com/";
  private final static int WAIT_FOR_ELEMENT_TIMEOUT = 5;
  private WebDriver driver;
  private WebDriverWait webDriverWait;
  private Logger LOG;
  private Actions actions;

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
    actions = new Actions(driver);
  }


  @Test
  @DisplayName("OpenHomeAndShowNamesLinks")
  public void mainPage() {
    open(mainPage);

    String actualTitle = driver.getTitle();
    String expectedTitle = "The Internet";

    assertEquals(actualTitle, expectedTitle);

    List<WebElement> allLinks = driver.findElements(By.tagName("a"));
//            int i = 0;
//            for(WebElement links : allLinks){
//                System.out.println(i++ +" : "+ links.getText());
//            }
    for (int i = 0; i < allLinks.size(); i++) {
      System.out.println(i + " : " + allLinks.get(i).getText());
    }
  }

  @Test
  public void addAndRemoveElementsTest() throws InterruptedException {
    open(mainPage);
    clickLocator("//a[@href=\"/add_remove_elements/\"]");
    String locatorAddElement = "//button[@onclick=\"addElement()\"]";
    String locatorDeleteElement = "//button[@class=\"added-manually\"]";
    clickLocator(locatorAddElement);
    //assert
    clickLocator(locatorDeleteElement);
    //assert
  }

  @Test
  @Disabled
  public void checkSignInWithLoginAndPassTest() {
    String URL =
        "https://" + USERNAME + ":" + PASSWORD + "@" + "the-internet.herokuapp.com/basic_auth";
    open(URL);
    String actualResult = "Congratulations! You must have the proper credentials.";
    //assert
  }

  @Test
  @Disabled
  public void checkSignInWithCancelEntryTest() throws InterruptedException {
    //TODO
    open(mainPage);
    clickLocator("//a[@href=\"/basic_auth\"]");
    Thread.sleep(1000);
//        Alert alert = driver.switchTo().alert();
//        alert.dismiss();
  }


  @Test
  public void brokenImageTest() throws IOException {
    //TODO
    open(mainPage);
    String locator = "//a[@href=\"/broken_images\"]";
    clickLocator(locator);

    URL url = new URL("https://the-internet.herokuapp.com/broken_images");
    HttpURLConnection http = (HttpURLConnection) url.openConnection();
    http.setConnectTimeout(2000);

    http.connect();

    if (http.getResponseCode() == 200) {
      System.out.println("HTTP STATUS: " + http.getResponseMessage());
    } else {
      System.err.println("HTTP STATUS: " + http.getResponseCode());
    }

    http.disconnect();
  }

  @Test
  public void bestLocatorsTest() {
    //TODO asserts
    open(mainPage);
    clickLocator("//a[@href=\"/challenging_dom\"]");

    clickLocator("//a[@class=\"button\"]");

    clickLocator("//a[@class=\"button alert\"]");

    clickLocator("//a[@class=\"button success\"]");
  }

  @Test
  public void checkBoxesTest() {
    open(mainPage);
    clickLocator("//a[@href=\"/checkboxes\"]");
    String checkbox1 = "(//input[@type=\"checkbox\"])[1]";
    String checkbox2 = "(//input[@type=\"checkbox\"])[2]";

    WebElement checkFirstBoxElement = driver.findElement(By.xpath(checkbox1));
    boolean isSelectedFirstBox = checkFirstBoxElement.isSelected();

    WebElement checkSecondBoxElement = driver.findElement(By.xpath(checkbox2));
    boolean isSelectedSecondBox = checkSecondBoxElement.isSelected();

    if (isSelectedFirstBox == false) {
      clickLocator(checkbox1);
      assertEquals("true", checkFirstBoxElement.getAttribute("checked"));
    }

    if (isSelectedSecondBox == true) {
      clickLocator(checkbox2);
      Assertions.assertFalse(checkSecondBoxElement.isSelected());
    }
  }

  @Test
  public void contextMenuTest() {
    open(mainPage);
    clickLocator("//a[@href=\"/context_menu\"]");
    String hotSpotLocator = "//div[@id=\"hot-spot\"]";
    WebElement hotSpot = driver.findElement(By.xpath(hotSpotLocator));

    actions
        .moveToElement(hotSpot)
        .contextClick(hotSpot)
        .build()
        .perform();

    driver.switchTo().alert().accept();
  }

  @Test
  public void digestAuthenticationTest() {
    ((HasAuthentication) driver).register(UsernameAndPassword.of(USERNAME, PASSWORD));
    driver.get("https://the-internet.herokuapp.com/digest_auth");

    String actualResult = String
        .valueOf(driver.findElement(By.xpath("//p[contains(text(), \"Congratulations!\")]"))
            .getText());
    String expectedResult = "Congratulations! You must have the proper credentials.";

    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void dragAndDropTest() {
    open("http://www.dhtmlgoodies.com/scripts/drag-drop-custom/demo-drag-drop-3.html");

    WebElement from = driver.findElement(By.xpath("//div[@id=\"box1\"]"));
    WebElement to = driver.findElement(By.xpath("//div[@id=\"box101\"]"));

    actions.clickAndHold(from)
        .moveToElement(to)
        .release()
        .build()
        .perform();

    String expectedResult = "rgba(0, 255, 0, 1)";//why we add a and 1???
    String actualResult = driver.findElement(
            By.xpath("(//div[@id=\"countries\"])//div[@class=\"dragableBox\" and @id=\"box1\"]"))
        .getCssValue("background-color");

    assertEquals(expectedResult, actualResult);
  }

  @Test
  public void dropDownTest() {
    open(mainPage);
    clickLocator("//a[@href=\"/dropdown\"]");

    String selectOption = "//option[@value=\"\" and @selected=\"selected\"]";
    clickLocator(selectOption);

    String option1 = "//option[@value=\"1\"]";
    clickLocator(option1);
    //TODO assert
  }

  @Test
  public void entryAd() throws InterruptedException {
    open(mainPage);
    clickLocator("//a[@href=\"/entry_ad\"]");

    Thread.sleep(1_000);
    clickLocator("//div[@class=\"modal-footer\"]/p");
  }

  @Test
  public void fileDownloadsListOfLinksTest() {
    open(mainPage);
    clickLocator("//a[@href=\"/download\"]");

    String locator = "//div/div/a[contains(@href, '.')]";//103 elements

    List<WebElement> downloadLinks = driver.findElements(By.xpath(locator));
    for (WebElement links : downloadLinks) {
      System.out.println(links.getText());
    }
  }





  private void open(String http) {
    driver.get(http);
  }

  private void clickLocator(String locator) {
    WebElement link = waitAndFindElement(By.xpath(locator));
    link.click();
  }

  private WebElement waitAndFindElement(By locator) {
    return webDriverWait.until(ExpectedConditions.presenceOfElementLocated(locator));
  }

  @AfterEach
  public void tearDown() {
    LOG.atError().log();
//        if (driver != null) {
//            driver.close();
//            driver.quit();
//        }
  }

//    @AfterAll
//    public static void tearDownClass(){
//        System.err.println("tearDownClass is Disabled");
//    }


}