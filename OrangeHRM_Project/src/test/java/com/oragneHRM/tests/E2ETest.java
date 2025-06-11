package com.oragneHRM.tests;

import com.oragneHRM.Listeners.TestNGListeners;
import com.oragneHRM.drivers.DriverManager;
import com.oragneHRM.pages.AdminPage;
import com.oragneHRM.pages.LoginPage;
import com.oragneHRM.pages.PIMPage;
import com.oragneHRM.utils.*;

import io.qameta.allure.testng.Tag;
import io.qameta.allure.testng.Tags;
import org.testng.ITestResult;
import org.testng.annotations.*;

import static com.oragneHRM.utils.ParseLoginData.getPassword;
import static com.oragneHRM.utils.ParseLoginData.getUserName;
import static com.oragneHRM.utils.PropertiesUtils.getPropertyValue;

@Listeners(TestNGListeners.class)
public class E2ETest {
    private JsonUtils testData;

    @BeforeClass
    public void setUp() {
        testData = new JsonUtils("test-data");
    }

    @BeforeMethod
    public void beforeTestCaseRunning() {
        String browserName = getPropertyValue("browserType");
        DriverManager.createDriverInstance(browserName);
        new LoginPage(DriverManager.getDriver()).navigateToLoginPage();

        testData.setJsonData("Login-credentials.valid.username", getUserName(DriverManager.getDriver()));
        testData.setJsonData("Login-credentials.valid.password", getPassword(DriverManager.getDriver()));
    }

    @Tag("smoke")
    @Test(groups = {"smoke", "regression", "login"} ,priority = 2)
    public void successfulLogin() {
        new LoginPage(DriverManager.getDriver())
                .enterUsername(testData.getJsonData("Login-credentials.valid.username"))
                .enterPassword(testData.getJsonData("Login-credentials.valid.password"))
                .clickLoginButton()
                .assertLoginSuccess();

    }

    @Tag("regression")
    @Test(groups = {"regression", "login"})
    public void unsuccessfulLogin() {
        new LoginPage(DriverManager.getDriver())
                .enterUsername(testData.getJsonData("Login-credentials.invalid.username"))
                .enterPassword(testData.getJsonData("Login-credentials.invalid.password"))
                .clickLoginButton()
                .assertLoginFailure("invalid");
    }

    @Tag("regression")
    @Test(groups = {"regression", "login"})
    public void emptyLogin() {
        new LoginPage(DriverManager.getDriver())
                .enterUsername("")
                .enterPassword("")
                .clickLoginButton()
                .assertEmptyLogin("empty");
    }

    @Tag("smoke")
    @Test(groups = {"smoke", "regression", "addEmployee"} ,priority = 1)
    public void successfulAddEmployee() {
        new LoginPage(DriverManager.getDriver())
                .enterUsername(testData.getJsonData("Login-credentials.valid.username"))
                .enterPassword(testData.getJsonData("Login-credentials.valid.password"))
                .clickLoginButton()
                .assertLoginSuccess();

        new PIMPage(DriverManager.getDriver())
                .navigateToPIMPage()
                .clickAddEmployeeButton()
                .enterEmployeeFirstName(testData.getJsonData("Employee-credentials.valid.firstName"))
                .enterEmployeeMiddleName(testData.getJsonData("Employee-credentials.valid.middleName"))
                .enterEmployeeLastName(testData.getJsonData("Employee-credentials.valid.lastName"))
                .clickSaveButton()
                .assertEmployeeAddedSuccessfully();
    }

    @Tag("regression")
    @Test(groups = {"regression", "addEmployee"})
    public void successfulAddUserFromPIM() {
        new LoginPage(DriverManager.getDriver())
                .enterUsername(testData.getJsonData("Login-credentials.valid.username"))
                .enterPassword(testData.getJsonData("Login-credentials.valid.password"))
                .clickLoginButton()
                .assertLoginSuccess();

        new PIMPage(DriverManager.getDriver())
                .navigateToPIMPage()
                .clickAddEmployeeButton()
                .enterEmployeeFirstName(testData.getJsonData("Employee-credentials.valid.firstName"))
                .enterEmployeeMiddleName(testData.getJsonData("Employee-credentials.valid.middleName"))
                .enterEmployeeLastName(testData.getJsonData("Employee-credentials.valid.lastName"))
                .enableCreateUser()
                .enterEmployeeUserName(testData.getJsonData("Employee-credentials.valid.userName"))
                .enterEmployeeUserPassword(testData.getJsonData("User-credentials.valid.password"))
                .enterEmployeeUserConfirmPassword(testData.getJsonData("User-credentials.valid.password"))
                .enterEmployeeId(testData.getJsonData("Employee-credentials.valid.employeeId"))
                .clickSaveButton()
                .assertEmployeeAddedSuccessfully();
    }

    @Tag("regression")
    @Test(groups = {"regression", "addEmployee"})
    public void unsuccessfulAddUserFromPIM() {
        new LoginPage(DriverManager.getDriver())
                .enterUsername(testData.getJsonData("Login-credentials.valid.username"))
                .enterPassword(testData.getJsonData("Login-credentials.valid.password"))
                .clickLoginButton()
                .assertLoginSuccess();

        new PIMPage(DriverManager.getDriver())
                .navigateToPIMPage()
                .clickAddEmployeeButton()
                .enterEmployeeFirstName(testData.getJsonData("Employee-credentials.valid.firstName"))
                .enterEmployeeMiddleName(testData.getJsonData("Employee-credentials.valid.middleName"))
                .enterEmployeeLastName(testData.getJsonData("Employee-credentials.valid.lastName"))
                .enableCreateUser()
                .enterEmployeeUserName(testData.getJsonData("Employee-credentials.invalid.userName"))
                .enterEmployeeUserPassword(testData.getJsonData("User-credentials.valid.password"))
                .enterEmployeeUserConfirmPassword(testData.getJsonData("User-credentials.valid.password"))
                .enterEmployeeId(testData.getJsonData("Employee-credentials.valid2.employeeId"))
                .clickSaveButton()
                .assertUserNameExists();
    }

    @Tag("regression")
    @Test(groups = {"regression", "addEmployee"})
    public void unsuccessfullyAddWithEmptyField() {
        new LoginPage(DriverManager.getDriver())
                .enterUsername(testData.getJsonData("Login-credentials.valid.username"))
                .enterPassword(testData.getJsonData("Login-credentials.valid.password"))
                .clickLoginButton()
                .assertLoginSuccess();

        new PIMPage(DriverManager.getDriver())
                .navigateToPIMPage()
                .clickAddEmployeeButton()
                .enterEmployeeFirstName(testData.getJsonData("Employee-credentials.invalid.firstName"))
                .enterEmployeeLastName(testData.getJsonData("Employee-credentials.invalid.lastName"))
                .clickSaveButton()
                .assertEmployeeAdditionFailed();
    }

    @Tag("regression")
    @Test(groups = {"regression", "addEmployee"})
    public void unsuccessfullyAddWithExistingEmployeeId() {
        new LoginPage(DriverManager.getDriver())
                .enterUsername(testData.getJsonData("Login-credentials.valid.username"))
                .enterPassword(testData.getJsonData("Login-credentials.valid.password"))
                .clickLoginButton()
                .assertLoginSuccess();

        new PIMPage(DriverManager.getDriver())
                .navigateToPIMPage()
                .clickAddEmployeeButton()
                .enterEmployeeFirstName(testData.getJsonData("Employee-credentials.valid.firstName"))
                .enterEmployeeLastName(testData.getJsonData("Employee-credentials.valid.lastName"))
                .enterEmployeeId(testData.getJsonData("Employee-credentials.invalid.employeeId"))
                .clickSaveButton()
                .assertEmployeeIdExists();
    }

    @Tag("regression")
    @Test(groups = {"smoke", "regression", "UsersManagement"})
    public void successfullyAddUserFromAdmin() {
        new LoginPage(DriverManager.getDriver())
                .enterUsername(testData.getJsonData("Login-credentials.valid.username"))
                .enterPassword(testData.getJsonData("Login-credentials.valid.password"))
                .clickLoginButton()
                .assertLoginSuccess();

        new AdminPage(DriverManager.getDriver()).
                navigateToAdminPage()
                .clickAddUserButton()
                .selectUserRole(testData.getJsonData("User-credentials.valid.userRole"))
                .selectUserStatus(testData.getJsonData("User-credentials.valid.status"))
                .enterUsername(testData.getJsonData("User-credentials.valid.username"))
                .enterPassword(testData.getJsonData("User-credentials.valid.password"))
                .enterConfirmPassword(testData.getJsonData("User-credentials.valid.password"))
                .enterEmployeeName(testData.getJsonData("User-credentials.valid.employeeName"))
                .clickSaveButton()
                .assertUserAddedSuccessfully();
    }

    @Tag("regression")
    @Test(groups = {"regression", "UsersManagement"})
    public void unsuccessfullyAddUserFromAdmin() {
        new LoginPage(DriverManager.getDriver())
                .enterUsername(testData.getJsonData("Login-credentials.valid.username"))
                .enterPassword(testData.getJsonData("Login-credentials.valid.password"))
                .clickLoginButton()
                .assertLoginSuccess();

        new AdminPage(DriverManager.getDriver()).
                navigateToAdminPage()
                .clickAddUserButton()
                .selectUserRole(testData.getJsonData("User-credentials.valid.userRole"))
                .selectUserStatus(testData.getJsonData("User-credentials.valid.status"))
                .enterUsername(testData.getJsonData("User-credentials.valid.username"))
                .enterPassword(testData.getJsonData("User-credentials.valid.password"))
                .enterConfirmPassword(testData.getJsonData("User-credentials.valid.password"))
                .enterEmployeeNameWithoutAutoComplete(testData.getJsonData("User-credentials.invalid.employeeName"))
                .clickSaveButton()
                .assertUserAdditionFailed();
    }

    @Tag("regression")
    @Test(groups = {"regression", "UsersManagement"})
    public void unsuccessfullyAddUserEmptyEmp() {
        new LoginPage(DriverManager.getDriver())
                .enterUsername(testData.getJsonData("Login-credentials.valid.username"))
                .enterPassword(testData.getJsonData("Login-credentials.valid.password"))
                .clickLoginButton()
                .assertLoginSuccess();

        new AdminPage(DriverManager.getDriver()).
                navigateToAdminPage()
                .clickAddUserButton()
                .selectUserRole(testData.getJsonData("User-credentials.valid.userRole"))
                .selectUserStatus(testData.getJsonData("User-credentials.valid.status"))
                .enterUsername(testData.getJsonData("User-credentials.valid.username"))
                .enterPassword(testData.getJsonData("User-credentials.valid.password"))
                .enterConfirmPassword(testData.getJsonData("User-credentials.valid.password"))
                .clickSaveButton()
                .assertRequiredFieldMessage();
    }

    @Tag("regression")
    @Test(groups = {"regression", "UsersManagement"})
    public void unsuccessfullyAddUserWithExistsUserName() {
        new LoginPage(DriverManager.getDriver())
                .enterUsername(testData.getJsonData("Login-credentials.valid.username"))
                .enterPassword(testData.getJsonData("Login-credentials.valid.password"))
                .clickLoginButton()
                .assertLoginSuccess();

        new AdminPage(DriverManager.getDriver()).
                navigateToAdminPage()
                .clickAddUserButton()
                .selectUserRole(testData.getJsonData("User-credentials.valid.userRole"))
                .selectUserStatus(testData.getJsonData("User-credentials.valid.status"))
                .enterUsername(testData.getJsonData("User-credentials.valid.username"))
                .enterPassword(testData.getJsonData("User-credentials.valid.password"))
                .enterConfirmPassword(testData.getJsonData("User-credentials.valid.password"))
                .enterEmployeeName(testData.getJsonData("User-credentials.valid.employeeName"))
                .clickSaveButton()
                .assertUserAlreadyExists();
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (DriverManager.getDriver() != null) {
            BrowserActions.closeBrowser(DriverManager.getDriver());
        }
    }

}
