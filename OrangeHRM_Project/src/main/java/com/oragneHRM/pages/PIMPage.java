package com.oragneHRM.pages;

import com.oragneHRM.utils.BrowserActions;
import com.oragneHRM.utils.ElementActions;
import com.oragneHRM.utils.Validations;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.locators.RelativeLocator;

import static com.oragneHRM.utils.PropertiesUtils.getPropertyValue;

public class PIMPage {
    private final WebDriver driver;

    private final By addEmployeeButton = By.xpath("//button[normalize-space()='Add']");
    private final By employeeFirstName = By.xpath("//input[@placeholder='First Name']");
    private final By employeeMiddleName = By.xpath("//input[@placeholder='Middle Name']");
    private final By employeeLastName = By.xpath("//input[@placeholder='Last Name']");
    private final By employeeIdField = By.xpath("//label[text()='Employee Id']/following::input[1]");

    private final By saveButton = By.cssSelector("button[type='submit']");

    private final By successMessage = By.cssSelector(".oxd-text.oxd-text--p.oxd-text--toast-message.oxd-toast-content-text");
    private final By failureMessage = By.xpath("//span[normalize-space()='Required']");
    private final By userNameExists = By.cssSelector(".oxd-text.oxd-text--span.oxd-input-field-error-message.oxd-input-group__message");

    private final By employeeIdExits = By.xpath("//span[normalize-space()='Employee Id already exists']");

    private final By CreateLogin = By.cssSelector(".oxd-switch-input.oxd-switch-input--active.--label-right");
    private final RelativeLocator.RelativeBy employeeUserName = RelativeLocator.with(By.tagName("input")).below(By.xpath("//label[normalize-space()='Username']"));
    private final RelativeLocator.RelativeBy employeeUserPassword = RelativeLocator.with(By.tagName("input")).below(By.xpath("//label[normalize-space()='Password']"));
    private final RelativeLocator.RelativeBy employeeUserConfirmPassword = RelativeLocator.with(By.tagName("input")).below(By.xpath("//label[normalize-space()='Confirm Password']"));


    public PIMPage(WebDriver driver) {
        this.driver = driver;
    }

    //Methods
    @Step("Navigate to the PIM page")
    public PIMPage navigateToPIMPage() {
        BrowserActions.navigateToUrl(driver, getPropertyValue("PIMURL"));
        return this;
    }

    @Step("Click the Add Employee button")
    public PIMPage clickAddEmployeeButton() {
        ElementActions.clickElement(driver, addEmployeeButton);
        return this;
    }

    @Step("Enter employee first name: {firstName}")

    public PIMPage enterEmployeeFirstName(String firstName) {
        ElementActions.sendDataToElement(driver, employeeFirstName, firstName);
        return this;
    }

    @Step("Enter employee middle name: {middleName}")
    public PIMPage enterEmployeeMiddleName(String middleName) {
        ElementActions.sendDataToElement(driver, employeeMiddleName, middleName);
        return this;
    }

    @Step("Enter employee last name: {lastName}")

    public PIMPage enterEmployeeLastName(String lastName) {
        ElementActions.sendDataToElement(driver, employeeLastName, lastName);
        return this;
    }

    @Step("Enter employee ID: {employeeId}")
    public PIMPage enterEmployeeId(String employeeId) {
        ElementActions.sendDataToElement(driver, employeeIdField, employeeId);
        return this;
    }

    @Step("Click the Save button")
    public PIMPage clickSaveButton() {
        ElementActions.clickElement(driver, saveButton);
        return this;
    }

    @Step("Get success message")
    public String getSuccessMessage() {
        return ElementActions.getElementText(driver, successMessage);
    }

    @Step("Get failure message")
    public String getFailureMessage() {
        return ElementActions.getElementText(driver, failureMessage);
    }

    @Step("Get employee ID exists message")
    public PIMPage enableCreateUser() {
        ElementActions.clickElement(driver, CreateLogin);
        return this;
    }

    @Step("Enter employee username: {userName}")
    public PIMPage enterEmployeeUserName(String userName) {
        ElementActions.sendDataToElement(driver, employeeUserName, userName);
        return this;
    }

    @Step("Enter employee user password: {password}")
    public PIMPage enterEmployeeUserPassword(String password) {
        ElementActions.sendDataToElement(driver, employeeUserPassword, password);
        return this;
    }

    @Step("Enter employee user confirm password: {confirmPassword}")
    public PIMPage enterEmployeeUserConfirmPassword(String confirmPassword) {
        ElementActions.sendDataToElement(driver, employeeUserConfirmPassword, confirmPassword);
        return this;
    }

    @Step("Get employee ID exists message")
    public String getEmployeeIdExits() {
        return ElementActions.getElementText(driver, employeeIdExits);
    }

    // Validations or assertions for the PIM page
    @Step("Assert that the employee was added successfully")
    public void assertEmployeeAddedSuccessfully() {
        Validations.validateEquals(getSuccessMessage(), "Successfully Saved", "Employee was not added successfully, success message did not match.");
    }

    @Step("Assert that the employee addition failed")
    public void assertEmployeeAdditionFailed() {
        Validations.validateEquals(getFailureMessage(), "Required", "Employee addition should have failed with 'Required' message.");
    }

    @Step("Assert that the employee ID already exists")
    public void assertEmployeeIdExists() {
        Validations.validateEquals(getEmployeeIdExits(), "Employee Id already exists", "Employee ID should already exist.");

    }
    @Step("Assert that the username already exists")
    public void assertUserNameExists() {
        Validations.validateEquals(ElementActions.getElementText(driver, userNameExists), "Username already exists", "Username should already exist.");
    }
}

