package com.herokuapp.internet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.xpath("//button[@type='submit']");
    private final By errorMessage = By.xpath("//div[@class='flash error']");
    private final By successMessage = By.xpath("//div[@class='flash success']");
    private final By welcomeMessage = By.xpath("//h4[contains(text(), 'Welcome')]");
    private final By logoutButton = By.xpath("//a[@class='button secondary radius']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openPage("login");
        LOG.info("Opened login page");
    }

    public void login(String username, String password) {
        LOG.info("Logging in with username: " + username);
        sendKeys(usernameField, username);
        sendKeys(passwordField, password);
        clickElement(loginButton);
    }

    public String getErrorMessage() {
        String message = waitAndFindElement(errorMessage).getText().replaceAll("[^a-zA-Z0-9]", "");
        LOG.info("Error message: " + message);
        return message;
    }

    public String getSuccessMessage() {
        String message = waitAndFindElement(successMessage).getText().replaceAll("[^a-zA-Z0-9]", "");
        LOG.info("Success message: " + message);
        return message;
    }

    public String getWelcomeMessage() {
        String message = waitAndFindElement(welcomeMessage).getText().trim();
        LOG.info("Welcome message: " + message);
        return message;
    }

    public void logout() {
        LOG.info("Logging out");
        clickElement(logoutButton);
    }
}