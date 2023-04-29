package autotest.tests;

import autotest.framework.InitializeTest;
import autotest.ui.*;
import autotest.utils.AccountUtils;
import autotest.utils.ArticolUtils;
import net.bytebuddy.utility.RandomString;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest extends InitializeTest {

    String username = "dandi08" + RandomString.make(4);
    String email = "test" + RandomString.make(4) + "@mail.com";
    String password = "Proba123!";

    private AccountUtils accountUtils;

    @BeforeClass
    public void initializeUtils() {
        this.accountUtils = new AccountUtils(wait, getDriver(), results);
    }
    @Test
    public void checkRegister() throws IOException {
        accountUtils.register(username, email, password);
        accountUtils.logout();
    }

    @Test(dependsOnMethods = {"checkRegister"})
    public void checkLogin() throws InterruptedException, IOException {
        accountUtils.login(username, password);
        accountUtils.logout();
    }

}
