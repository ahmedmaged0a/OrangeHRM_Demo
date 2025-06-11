package com.oragneHRM.pages;

import com.oragneHRM.utils.*;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.oragneHRM.utils.PropertiesUtils.getPropertyValue;

public class LoginPage {
    private final WebDriver driver;
    //Error message locator
    private final By errorMessage = By.cssSelector(".oxd-text.oxd-text--p.oxd-alert-content-text");
    private final By errorMessageEmpty = By.cssSelector(".oxd-text.oxd-text--span.oxd-input-field-error-message.oxd-input-group__message");
    // Locators for the login page elements
    private final By usernameField = By.cssSelector("input[placeholder='Username']");
    private final By passwordField = By.cssSelector("input[placeholder='Password']");
    private final By loginButton = By.cssSelector("button[type='submit']");
    //Constructor to initialize the WebDriver instance

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Method to navigate to the login page
    @Step("Navigate to the login page")
    public void navigateToLoginPage() {
        BrowserActions.navigateToUrl(driver, getPropertyValue("baseURL"));
    }

    // Methods to perform actions on the login page
    @Step("Enter username: {username}")
    public LoginPage enterUsername(String username) {
        ElementActions.sendDataToElement(driver, usernameField, username);
        return this;
    }

    @Step("Enter password")
    public LoginPage enterPassword(String password) {               //fluent pattern
        ElementActions.sendDataToElement(driver, passwordField, password);
        return this;
    }

    @Step("Click the login button")
    public LoginPage clickLoginButton() {
        ElementActions.clickElement(driver, loginButton);
        return this;
    }

    public String getErrorMessage(String errorType) {
        switch (errorType.toLowerCase()) {
            case "empty" -> {
                return ElementActions.getElementText(driver, errorMessageEmpty);
            }
            case "invalid" -> {
                return ElementActions.getElementText(driver, errorMessage);
            }
        }
        return errorType;
    }

    //Validations or assertions for the login page --> TestNG
    @Step("Assert that the login page is displayed")
    public void assertLoginSuccess() {
        Validations.validatePageUrl(driver, getPropertyValue("homeURL"), "Login was not successful, URL did not match expected dashboard URL.");
    }

    @Step("Assert that the login failed")
    public void assertLoginFailure(String errorType) {
        Validations.validateEquals(getErrorMessage(errorType), "Invalid credentials", "Login should have failed with 'Invalid credentials' message.");
    }

    @Step("Assert that the login failed")
    public void assertEmptyLogin(String errorType) {
        Validations.validateEquals(getErrorMessage(errorType), "Required", "Login should have failed with 'Invalid credentials' message.");
    }

}
