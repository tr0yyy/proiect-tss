package autotest.utils;

import autotest.framework.InitializeTest;
import autotest.ui.HomeUi;
import autotest.ui.LoginUi;
import autotest.ui.RegisterUi;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

public class AccountUtils extends BaseUtils{

    public AccountUtils(WebDriverWait wait, WebDriver driver, InitializeTest.Results results) {
        super(wait, driver, results);
    }

    public void register(String username, String email, String password) throws IOException {
        results.info("Started testing verification of register action",true);
        HomeUi homeUi = new HomeUi();
        RegisterUi registerUi = new RegisterUi();
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeUi.registerNavButton));
        driver.findElement(homeUi.registerNavButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(registerUi.usernameInput));
        driver.findElement(registerUi.usernameInput).sendKeys(username);
        driver.findElement(registerUi.emailInput).sendKeys(email);
        driver.findElement(registerUi.passwordInput).sendKeys(password);
        driver.findElement(registerUi.registerButton).click();
        checkLoggedInAccount();
    }

    public void login(String username, String password) throws IOException {
        results.info("Started testing verification of login action",true);
        HomeUi homeUi = new HomeUi();
        LoginUi loginUi = new LoginUi();
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeUi.loginNavButton));
        driver.findElement(homeUi.loginNavButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginUi.loginButton));
        driver.findElement(homeUi.loginNavButton).click();
        driver.findElement(loginUi.usernameInput).sendKeys(username);
        driver.findElement(loginUi.passwordInput).sendKeys(password);
        driver.findElement(loginUi.loginButton).click();
        checkLoggedInAccount();
    }

    public void logout() throws IOException {
        results.info("Started testing verification of logout action",true);
        HomeUi homeUi = new HomeUi();
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeUi.logoutNavButton));
        results.assertTrue(!driver.findElements(homeUi.logoutNavButton).isEmpty(),
                "Logout button is displayed", "Logout button is not displayed", true);
        driver.findElement(homeUi.logoutNavButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeUi.domeniiList));
        results.assertTrue(driver.findElements(homeUi.logoutNavButton).isEmpty(),
                "Logout button disappeared", "Logout button is still displayed", true);
    }

    public void checkLoggedInAccount() throws IOException {
        HomeUi homeUi = new HomeUi();
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeUi.logoutNavButton));
        results.verifyTrue(driver.findElements(homeUi.registerNavButton).isEmpty(),
                "Register button disappeared", "Register is still there", true);
        results.verifyTrue(driver.findElements(homeUi.loginNavButton).isEmpty(),
                "Login button disappeared", "Login is still there", true);
        results.verifyTrue(!driver.findElements(homeUi.logoutNavButton).isEmpty(),
                "Logout button appeared", "Logout button did not appear", true);
    }
}
