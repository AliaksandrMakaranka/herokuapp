package com.herokuapp.internet;

import com.herokuapp.internet.pages.CheckboxesPage;
import com.herokuapp.internet.pages.LoginPage;
import com.herokuapp.internet.pages.MainPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WebTest {
    private WebDriver driver;
    private final Logger LOG = LoggerFactory.getLogger(WebTest.class);

    @BeforeAll
    public static void setUpClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        LOG.info("WebDriver initialized");
    }

    @Test
    @DisplayName("OpenHomeAndShowNamesLinks")
    public void mainPageTest() {
        LOG.info("Executing main page test");

        MainPage mainPage = new MainPage(driver);
        mainPage.open();

        String expectedTitle = "The Internet";
        assertEquals(expectedTitle, mainPage.getTitle());

        mainPage.getAllLinks().forEach(link ->
                System.out.println(link.getText())
        );
    }

    @Test
    public void loginPageTestWithRightNameAndPass() {
        LOG.info("Executing login test with correct credentials");

        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.navigateByHref("/login");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("tomsmith", "SuperSecretPassword!");

        String expectedWelcomeMessage = "Welcome to the Secure Area. When you are done click logout below.";
        assertEquals(expectedWelcomeMessage, loginPage.getWelcomeMessage());

        loginPage.logout();

        String expectedLogoutMessage = "You logged out of the secure area";
        assertTrue(loginPage.getSuccessMessage().contains(expectedLogoutMessage.replaceAll("[^a-zA-Z0-9]", "")));
    }

    @Test
    public void loginPageTestWithRightNameAndWrongPass() {
        LOG.info("Executing login test with incorrect password");

        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.navigateByHref("/login");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("tomsmith", "WRONG_PASSWORD!");

        String expectedMessage = "Your password is invalid";
        assertTrue(loginPage.getErrorMessage().contains(expectedMessage.replaceAll("[^a-zA-Z0-9]", "")));
    }

    @Test
    public void checkBoxesTest() {
        LOG.info("Executing checkboxes test");

        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.navigateByHref("/checkboxes");

        CheckboxesPage checkboxesPage = new CheckboxesPage(driver);

        boolean isFirstCheckboxSelected = checkboxesPage.isFirstCheckboxSelected();
        boolean isSecondCheckboxSelected = checkboxesPage.isSecondCheckboxSelected();

        if (!isFirstCheckboxSelected) {
            checkboxesPage.toggleFirstCheckbox();
            assertTrue(checkboxesPage.isFirstCheckboxSelected());
        }

        if (isSecondCheckboxSelected) {
            checkboxesPage.toggleSecondCheckbox();
            assertFalse(checkboxesPage.isSecondCheckboxSelected());
        }
    }

    @AfterEach
    public void tearDown() {
        LOG.info("Closing WebDriver");
        if (driver != null) {
            driver.quit();
        }
    }
}