package autotest.tests;

import autotest.framework.InitializeTest;
import autotest.ui.*;
import net.bytebuddy.utility.RandomString;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest extends InitializeTest {

    String username = "dandi08" + RandomString.make(4);
    String email = "test" + RandomString.make(4) + "@mail.com";
    String password = "Proba123!";

    @Test
    public void checkRegister() throws IOException {
        register(username, email, password);
        logout();
    }

    @Test(dependsOnMethods = {"checkRegister"})
    public void checkLogin() throws InterruptedException, IOException {
        login(username, password);
        logout();
    }

    public void register(String username, String email, String password) throws IOException {
        results.info("Started testing verification of register action",true);
        HomeUi homeUi = new HomeUi();
        RegisterUi registerUi = new RegisterUi();
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeUi.registerNavButton));
        getDriver().findElement(homeUi.registerNavButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(registerUi.usernameInput));
        getDriver().findElement(registerUi.usernameInput).sendKeys(username);
        getDriver().findElement(registerUi.emailInput).sendKeys(email);
        getDriver().findElement(registerUi.passwordInput).sendKeys(password);
        getDriver().findElement(registerUi.registerButton).click();
        checkLoggedInAccount();
    }

    public void login(String username, String password) throws IOException {
        results.info("Started testing verification of login action",true);
        HomeUi homeUi = new HomeUi();
        LoginUi loginUi = new LoginUi();
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeUi.loginNavButton));
        getDriver().findElement(homeUi.loginNavButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginUi.loginButton));
        getDriver().findElement(homeUi.loginNavButton).click();
        getDriver().findElement(loginUi.usernameInput).sendKeys(username);
        getDriver().findElement(loginUi.passwordInput).sendKeys(password);
        getDriver().findElement(loginUi.loginButton).click();
        checkLoggedInAccount();
    }

    public void logout() throws IOException {
        results.info("Started testing verification of logout action",true);
        HomeUi homeUi = new HomeUi();
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeUi.logoutNavButton));
        results.assertTrue(!getDriver().findElements(homeUi.logoutNavButton).isEmpty(),
                "Logout button is displayed", "Logout button is not displayed", true);
        getDriver().findElement(homeUi.logoutNavButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeUi.domeniiList));
        results.assertTrue(getDriver().findElements(homeUi.logoutNavButton).isEmpty(),
                "Logout button disappeared", "Logout button is still displayed", true);
    }

    public void checkLoggedInAccount() throws IOException {
        HomeUi homeUi = new HomeUi();
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeUi.logoutNavButton));
        results.verifyTrue(getDriver().findElements(homeUi.registerNavButton).isEmpty(),
                "Register button disappeared", "Register is still there", true);
        results.verifyTrue(getDriver().findElements(homeUi.loginNavButton).isEmpty(),
                "Login button disappeared", "Login is still there", true);
        results.verifyTrue(!getDriver().findElements(homeUi.logoutNavButton).isEmpty(),
                "Logout button appeared", "Logout button did not appear", true);
    }
}
