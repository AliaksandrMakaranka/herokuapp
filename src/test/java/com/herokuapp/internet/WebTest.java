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
  private static final String MAIN_PAGE = "https://the-internet.herokuapp.com/";
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
    open(MAIN_PAGE);

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
    open(MAIN_PAGE);
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
    open(MAIN_PAGE);
    clickLocator("//a[@href=\"/basic_auth\"]");
    Thread.sleep(1000);
//        Alert alert = driver.switchTo().alert();
//        alert.dismiss();
  }


  @Test
  public void brokenImageTest() throws IOException {
    //TODO
    open(MAIN_PAGE);
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
    open(MAIN_PAGE);
    clickLocator("//a[@href=\"/challenging_dom\"]");

    clickLocator("//a[@class=\"button\"]");

    clickLocator("//a[@class=\"button alert\"]");

    clickLocator("//a[@class=\"button success\"]");
  }

  @Test
  public void checkBoxesTest() {
    open(MAIN_PAGE);
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
    open(MAIN_PAGE);
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
    open(MAIN_PAGE);
    clickLocator("//a[@href=\"/dropdown\"]");

    String selectOption = "//option[@value=\"\" and @selected=\"selected\"]";
    clickLocator(selectOption);

    String option1 = "//option[@value=\"1\"]";
    clickLocator(option1);
    //TODO assert
  }

  @Test
  public void entryAd() throws InterruptedException {
    open(MAIN_PAGE);
    clickLocator("//a[@href=\"/entry_ad\"]");

    Thread.sleep(1_000);
    clickLocator("//div[@class=\"modal-footer\"]/p");
  }

  @Test
  public void fileDownloadsListOfLinksTest() {
    open(MAIN_PAGE);
    clickLocator("//a[@href=\"/download\"]");

    String locator = "//div/div/a[contains(@href, '.')]";//103 elements

    List<WebElement> downloadLinks = driver.findElements(By.xpath(locator));
    for (WebElement links : downloadLinks) {
      System.out.println(links.getText());
    }
  }

  @Test
  public void oneFileDownloadTest() throws InterruptedException {
    ChromeOptions options = new ChromeOptions();

    String downloadFilePathLocation = System.getProperty("user.dir")+File.separator+"Downloads";

    HashMap<String, Object> chromePref = new HashMap<String, Object>();
    chromePref.put("profile.default_content_setting.popups", 0);
    chromePref.put("Downloads.default_directory", downloadFilePathLocation);

    options.setExperimentalOption("prefs", chromePref);

    open("https://the-internet.herokuapp.com/download");
    clickLocator("//a[@href=\"download/icon.png\"]");

    File downloadedFile = new File(downloadFilePathLocation+"/a4.jpg");
    Thread.sleep(5000);
//    downloadedFile.delete();
    //TODO file downloaded but assert isnt work
//    assertTrue(downloadedFile.delete());
  }

  @Test
    public void fileUploadTest() {
    String filePath = "/home/zac/Downloads/";
    String fileName = "test.zip";
    open(MAIN_PAGE);
    clickLocator("//a[@href=\"/upload\"]");

    WebElement upload = driver.findElement(
        By.xpath("//input[@type=\"file\" and @id=\"file-upload\"]"));

    upload.sendKeys(filePath, fileName);
    clickLocator("//input[@value=\"Upload\"]");

    String actualResult = driver.findElement(By.xpath("//div[contains(text(), \"test.zip\")]"))
        .getText();

    Assertions.assertEquals(fileName, actualResult);
    //TODO add if -> if file not laded + code of mistake
    }

@Test
  public void scrollDownToEndPageTest() {
  open(MAIN_PAGE);
  clickLocator("//a[@href=\"/floating_menu\"]");

  JavascriptExecutor js = (JavascriptExecutor) driver;
  js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
}
    @Test
    public void scrollDownInfinityTest() {
      open(MAIN_PAGE);
      clickLocator("//a[@href=\"/infinite_scroll\"]");

      JavascriptExecutor js = (JavascriptExecutor) driver;
      long beforeLength = (long) js.executeScript("return document.body.scrollHeight");

      while(true) {
        js.executeScript("window.scrollTo(0,document.body.scrollHeight)");

        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        long afterLength = (long) js.executeScript("return document.body.scrollHeight");
        if (beforeLength == afterLength) {
          break;
        }
        beforeLength = afterLength;
      }
    }

  @Test
  public void loginPageTestWithRightNameAndPass() {
    String username = "tomsmith";
    String password = "SuperSecretPassword!";

    open(MAIN_PAGE);
    clickLocator("//a[@href=\"/login\"]");

    WebElement usernameLocator = driver.findElement(By.id("username"));
    WebElement passwordLocator = driver.findElement(By.id("password"));
    WebElement loginButtonLocator = driver.findElement(
        By.xpath("//i[@class=\"fa fa-2x fa-sign-in\"]"));


    usernameLocator.sendKeys(username);
    passwordLocator.sendKeys(password);
    loginButtonLocator.click();

    WebElement logoutButtonLocator = driver.findElement(By.xpath("//i[@class=\"icon-2x icon-signout\"]"));

    String actualResult = driver.findElement(By.xpath("(//h4[contains(text(), \"Welcome\")])"))
        .getText().trim();
    String expectedResult = "Welcome to the Secure Area. When you are done click logout below.".trim();

    Assertions.assertEquals(actualResult,expectedResult);

    logoutButtonLocator.click();

    String loggingOutExpectedResult = " You logged out of the secure area!\n×".trim();
    String loggingOutActualResult = driver.findElement(By.xpath("//div[@class=\"flash success\"]"))
        .getText().trim();

    Assertions.assertEquals(loggingOutExpectedResult, loggingOutActualResult);
  }

  @Test
  public void loginPageTestWithRightNameAndWrongPass() {
    //TODO time to start REGEX
    String username = "tomsmith";
    String password = "WRONG_PASSWORD!";

    open(MAIN_PAGE);
    clickLocator("//a[@href=\"/login\"]");

    WebElement usernameLocator = driver.findElement(By.id("username"));
    WebElement passwordLocator = driver.findElement(By.id("password"));
    WebElement loginButtonLocator = driver.findElement(
        By.xpath("//i[@class=\"fa fa-2x fa-sign-in\"]"));


    usernameLocator.sendKeys(username);
    passwordLocator.sendKeys(password);
    loginButtonLocator.click();

    String regexFormat = "[^a-zA-Z0-9]";

    String actualResult = driver.findElement(By.xpath("//div[@class=\"flash error\"]")).getText();
    actualResult = actualResult.replaceAll(regexFormat, "");

    String expectedResult = "Your password is invalid!";
    expectedResult = expectedResult.replaceAll(regexFormat, "");

    assertEquals(actualResult, expectedResult);
  }

  @Test
  public void loginPageTestWithWrongOrEmptyNameAndRightPass() {
    String username = "TRUEHACKER1337";
//    String username = "";
    String password = "SuperSecretPassword!";

    open(MAIN_PAGE);
    clickLocator("//a[@href=\"/login\"]");

    WebElement usernameLocator = driver.findElement(By.id("username"));
    WebElement passwordLocator = driver.findElement(By.id("password"));
    WebElement loginButtonLocator = driver.findElement(
        By.xpath("//i[@class=\"fa fa-2x fa-sign-in\"]"));


    usernameLocator.sendKeys(username);
    passwordLocator.sendKeys(password);
    loginButtonLocator.click();

    String regexFormat = "[^a-zA-Z0-9]";

    String actualResult = driver.findElement(By.xpath("//div[@class=\"flash error\"]")).getText();
    actualResult = actualResult.replaceAll(regexFormat, "");

    String expectedResult = "Your username is invalid!";
    expectedResult = expectedResult.replaceAll(regexFormat, "");

    assertEquals(actualResult, expectedResult);
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