package com.oragneHRM.pages;

import com.oragneHRM.utils.PropertiesUtils;
import com.oragneHRM.utils.Validations;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.locators.RelativeLocator;

import static com.oragneHRM.utils.BrowserActions.navigateToUrl;
import static com.oragneHRM.utils.ElementActions.*;

public class AdminPage {
    private final WebDriver driver;

    public AdminPage(WebDriver driver) {
        this.driver = driver;
    }

    //Locators
    private final By addUserButton = By.xpath("//button[normalize-space()='Add']");
    private final By userRoleDropdown = RelativeLocator.with(By.tagName("div")).below(By.xpath("//label[normalize-space()='User Role']"));
    private final By userStatus = RelativeLocator.with(By.tagName("div")).below(By.xpath("//label[normalize-space()='Status']"));
    private final By employeeNameField = By.xpath("//input[@placeholder='Type for hints...']");
    private final By usernameField = RelativeLocator.with(By.tagName("input")).below(By.xpath("//label[normalize-space()='Username']"));
    private final By passwordField = RelativeLocator.with(By.tagName("input")).below(By.xpath("//label[normalize-space()='Password']"));
    private final By confirmPasswordField = RelativeLocator.with(By.tagName("input")).below(By.xpath("//label[normalize-space()='Confirm Password']"));
    private final By saveButton = By.cssSelector("button[type='submit']");
    private final By options = By.xpath("//div[contains(@class,'oxd-autocomplete-dropdown')]//div[@role='option']//span");
    private final By successMessage = By.cssSelector(".oxd-text.oxd-text--p.oxd-text--toast-message.oxd-toast-content-text");


    private final By failureMessage = By.cssSelector(".oxd-text.oxd-text--span.oxd-input-field-error-message.oxd-input-group__message");
    private final By requiredField = By.xpath("//span[normalize-space()='Required']");
    private final By userNameExists = By.cssSelector(".oxd-text.oxd-text--span.oxd-input-field-error-message.oxd-input-group__message");

    // Methods
    @Step("Navigate to the Admin page")
    public AdminPage navigateToAdminPage() {
        navigateToUrl(driver, PropertiesUtils.getPropertyValue("adminPageURL"));
        return this;
    }

    @Step("Click the Add User button")
    public AdminPage clickAddUserButton() {
        clickElement(driver, addUserButton);
        return this;
    }

    @Step("Select user role: {userRole}")
    public AdminPage selectUserRole(String userRole) {
        selectOptionFromDropdown(driver, userRoleDropdown, userRole);
        return this;
    }

    @Step("Select user status: {status}")
    public AdminPage selectUserStatus(String status) {
        selectOptionFromDropdown(driver, userStatus, status);
        return this;
    }

    @Step("Enter employee name: {employeeName}")
    public AdminPage enterEmployeeName(String employeeName) {
        handleAutoComplete(driver, employeeNameField, employeeName, options);
        return this;
    }

    @Step("Enter employee name wihtout auto-complete: {employeeName}")
    public AdminPage enterEmployeeNameWithoutAutoComplete(String employeeName) {
        sendDataToElement(driver, employeeNameField, employeeName);
        return this;
    }

    @Step("Enter username: {username}")
    public AdminPage enterUsername(String username) {
        sendDataToElement(driver, usernameField, username);
        return this;
    }

    @Step("Enter password")
    public AdminPage enterPassword(String password) {
        sendDataToElement(driver, passwordField, password);
        return this;
    }

    @Step("Enter confirm password")
    public AdminPage enterConfirmPassword(String confirmPassword) {
        sendDataToElement(driver, confirmPasswordField, confirmPassword);
        return this;
    }

    @Step("Click the Save button")
    public AdminPage clickSaveButton() {
        clickElement(driver, saveButton);
        return this;
    }

    @Step("Get success message")
    public String getSuccessMessage() {
        return getElementText(driver, successMessage);
    }

    @Step("Get failure message")
    public String getFailureMessage() {
        return getElementText(driver, failureMessage);
    }

    @Step("Get Failure Message Username already exists")
    public String getFailureMessageUsernameExists() {
        return getElementText(driver, userNameExists);
    }

    @Step("Get required field message")
    public String getRequiredFieldMessage() {
        return getElementText(driver, requiredField);
    }


    //Validations or assertions for the admin page
    @Step("Assert that the user was added successfully")
    public void assertUserAddedSuccessfully() {
        Validations.validateEquals(getSuccessMessage(), "Successfully Saved", "User was not added successfully, success message did not match.");
    }

    @Step("Assert that the user addition failed")
    public void assertUserAdditionFailed() {
        Validations.validateEquals(getFailureMessage(), "Invalid", "User addition should have failed with 'Invalid' message.");
    }

    @Step("Assert that the required field message is displayed")
    public void assertRequiredFieldMessage() {
        Validations.validateEquals(getRequiredFieldMessage(), "Required", "Required field message did not match.");
    }

    @Step("Assert that the user already exists")
    public void assertUserAlreadyExists() {
        Validations.validateEquals(getFailureMessageUsernameExists(), "Username already exists", "User addition should have failed with 'Username already exists' message.");

    }
}
