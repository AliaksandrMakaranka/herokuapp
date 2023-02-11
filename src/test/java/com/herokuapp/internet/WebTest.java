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
import org.openqa.selenium.Keys;
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
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


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
  public void mainPageTest() {
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
    //TODO for all image on page
    open(MAIN_PAGE);
    String locator = "//a[@href=\"/broken_images\"]";
    clickLocator(locator);
    statusCode("https://the-internet.herokuapp.com/broken_images");

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
  @Disabled("need actual name fail for download")
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
    @Disabled("long test")
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

  @Test
  public void geolocationTest() throws InterruptedException {
    open(MAIN_PAGE);

    //TODO make after accept  to request for get coordinate

    clickLocator("//a[@href=\"/geolocation\"]");
    clickLocator("//button[@onclick=\"getLocation()\"]");
    Thread.sleep(1000);

    String latitude = driver.findElement(By.xpath("//div[@id=\"lat-value\"]")).getText();
    String longitude = driver.findElement(By.id("long-value")).getText();

    System.out.println("your coordinate is: \n" + "latitude: " + latitude + "\nlongitude: " + longitude);
  }

  @Test
  public void horizontalSlideTest() throws InterruptedException {
    open(MAIN_PAGE);
    clickLocator("//a[@href=\"/horizontal_slider\"]");
    WebElement slider = driver.findElement(By.xpath("//input[@type=\"range\"]"));

    //TODO autodetect length of slider! for each?
    for (int i = 0; i < 9; i++) {
      slider.sendKeys(Keys.ARROW_RIGHT);
      Thread.sleep(500);
    }
  }

  @Test
  public void horizontalSlideWithActionTest() {
    open(MAIN_PAGE);
    clickLocator("//a[@href=\"/horizontal_slider\"]");
    WebElement slider = driver.findElement(By.xpath("//input[@type=\"range\"]"));

    actions.clickAndHold(slider);
    actions.moveByOffset(50, 0);
    actions.build().perform();
  }

  @Test
  public void hoversTest() throws IOException {
    open(MAIN_PAGE);
    clickLocator("//a[@href=\"/hovers\"]");

    WebElement user1 = driver.findElement(By.xpath("(//img[@alt=\"User Avatar\"])[1]"));
    actions.moveToElement(user1).perform();

    clickLocator("//a[@href=\"/users/1\"]");
    //todo define working page and  get status code
    statusCode("https://the-internet.herokuapp.com/users/1");
  }

  @Test
  public void inputsUpToFlatValueTest() {
    open(MAIN_PAGE);
    clickLocator("//a[@href=\"/inputs\"]");

    WebElement input = driver.findElement(By.xpath("//input[@type=\"number\"]"));
    input.click();

    for (int i = 0; i < 100; i++) {
      input.sendKeys(Keys.ARROW_UP);
    }
  }

  @Test
  public void javascriptAlertsTest() throws InterruptedException {
    open(MAIN_PAGE);
    clickLocator("//a[@href=\"/javascript_alerts\"]");

    //TODO three methods

    WebElement jsAlertButton = driver.findElement(By.xpath("//button[@onclick=\"jsAlert()\"]"));
    WebElement jsConfirmButton = driver.findElement(By.xpath("//button[@onclick=\"jsConfirm()\"]"));
    WebElement jsPromptButton = driver.findElement(By.xpath("//button[@onclick=\"jsPrompt()\"]"));

    //alert method
    jsAlertButton.click();
    driver.switchTo().alert().accept();

    String expectedResultAlertButton = "You successfully clicked an alert";
    String actualResultAlertButton = driver.findElement(By.xpath("//p[@id=\"result\"]")).getText();
    assertEquals(expectedResultAlertButton, actualResultAlertButton);


    //confirm method
    jsConfirmButton.click();
    driver.switchTo().alert().accept();

    String actualResultConfirmButton = driver.findElement(By.xpath("//p[@id=\"result\"]")).getText();
    String expectedResultConfirmButtonOk = "You clicked: Ok";
    assertEquals(expectedResultConfirmButtonOk, actualResultConfirmButton);


    //prompt just enter
    jsPromptButton.click();
    driver.switchTo().alert().accept();

    String actualResultPromptButton = driver.findElement(By.xpath("//p[@id=\"result\"]")).getText();
    String expectedResultPromptButton = "You entered:";
    assertEquals(expectedResultPromptButton,actualResultPromptButton);


    //prompt cancel
    jsPromptButton.click();
    driver.switchTo().alert().dismiss();

    String actualResultPromptButtonCancel = driver.findElement(By.xpath("//p[@id=\"result\"]")).getText();
    String expectedResultPromptButtonCancel = "You entered: null";
    assertEquals(expectedResultPromptButtonCancel,actualResultPromptButtonCancel);

    //prompt some text
    jsPromptButton.click();
    driver.switchTo().alert().sendKeys("Access granted");
    driver.switchTo().alert().accept();

    String actualResultPromptButtonWithSomeText = driver.findElement(By.xpath("//p[@id=\"result\"]")).getText();
    String expectedResultPromptButtonWithSomeText = "You entered: Access granted";
    assertEquals(expectedResultPromptButtonWithSomeText,actualResultPromptButtonWithSomeText);
  }


  @Test
  public void keyPressTest() {
    open(MAIN_PAGE);
    clickLocator("//a[@href=\"/key_presses\"]");

//    clickLocator("//input[@id=\"target\"]");
    actions.sendKeys(Keys.SPACE).build().perform();// working in any place page
    String text = driver.findElement(By.xpath("//p[@id=\"result\"]")).getText();
    assertEquals(text, "You entered: SPACE");
  }


  @Test
  public void largeDomTest() {
    open(MAIN_PAGE);
    clickLocator("//a[@href=\"/large\"]");

    WebElement lastLocator = driver.findElement(
        By.xpath("(//td[@class=\"column-50\" and //tr[@class=\"row-50\"]])[50]"));
    String text = lastLocator.getText();
    lastLocator.click();

    assertEquals(text, "50.50");
  }
  @Test
  public void statusCodeTest() throws IOException {
    open(MAIN_PAGE);
    clickLocator("//a[@href=\"/status_codes\"]");

    List<WebElement> links = driver.findElements(By.tagName("a"));

    for (int i = 0; i < links.size(); i++) {
      WebElement E1= links.get(i);
      String url = E1.getAttribute("href");
      statusCode(url);
    }
  }

  @Test
  public void newWindowTest() {
    open(MAIN_PAGE);
    clickLocator("//a[@href=\"/windows\"]");
    clickLocator("(//a[@target=\"_blank\"])[1]");

    String originalWindow = driver.getWindowHandle();
    for (String windowHandle : driver.getWindowHandles()) {
      if(!originalWindow.contentEquals(windowHandle)) {
        driver.switchTo().window(windowHandle);
        break;
      }
    }

    String expectedResult = "New Window";
    String actualResult = driver.getTitle();
    assertEquals(expectedResult, actualResult);
  }











  private void statusCode(String httpUrlLink) throws IOException {
    URL url = new URL(httpUrlLink);
    HttpURLConnection http = (HttpURLConnection) url.openConnection();
    http.setConnectTimeout(2000);

    int responseCode = http.getResponseCode();
    String responseMessage = http.getResponseMessage();
    String statusCodeResult = "Response code for " + url + " is: " + responseCode + " " + responseMessage;

    http.connect();
    System.out.println(statusCodeResult);
    http.disconnect();
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
          if (driver != null) {
//              driver.close();
//              driver.quit();
          }
    }

//    @AfterAll
//    public static void tearDownClass(){
//        System.err.println("tearDownClass is Disabled");
//    }


}