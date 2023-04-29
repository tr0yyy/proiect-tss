package autotest.tests;

import autotest.framework.Const;
import autotest.framework.InitializeTest;
import autotest.ui.ArticolUI;
import autotest.utils.AccountUtils;
import autotest.utils.AdminPanelUtils;
import autotest.utils.ArticolUtils;
import net.bytebuddy.utility.RandomString;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class UserTest extends InitializeTest {
    private AccountUtils accountUtils;
    private ArticolUtils articolUtils;
    private AdminPanelUtils adminPanelUtils;
    private String user;
    private final String pass = "!@#4QWEr";
    private String articol;

    @BeforeClass
    public void initializeUtils() {
        this.accountUtils = new AccountUtils(wait, getDriver(), results);
        this.articolUtils = new ArticolUtils(wait, getDriver(), results);
        this.adminPanelUtils = new AdminPanelUtils(wait, getDriver(), results);
    }

    @Test
    public void preconditions() throws IOException, InterruptedException {
        this.user = "autotest_user_" + RandomString.make(4);
        accountUtils.register(user, user + "@gmail.com", pass);
        articol = "My random article_" + RandomString.make(4);
        articolUtils.creazaArticol(articol, "Arta", "Un continut");
        articolUtils.modificaArticol("Un continut", "Un continut modificat");
    }

    @Test(dependsOnMethods = {"preconditions"})
    public void checkRevertByUser() throws IOException {
        ArticolUI articolUI = new ArticolUI();
        wait.until(ExpectedConditions.visibilityOfElementLocated(articolUI.editeazaArticolButon));
        getDriver().findElement(articolUI.editeazaArticolButon).click();
        results.verifyTrue(getDriver().findElement(articolUI.revertButton).getAttribute("disabled").equals("true"),
                "Article cannot be reverted by user",
                "Article can be reverted by user",
                true);
    }

    @Test(dependsOnMethods = {"checkRevertByUser"})
    public void checkRevertByModerator() throws IOException {
        ArticolUI articolUI = new ArticolUI();
        accountUtils.logout();
        accountUtils.login("admin", pass);
        adminPanelUtils.addOrRemoveRole(user, Const.Roles.MODERATOR);
        accountUtils.logout();
        accountUtils.login(user, pass);
        articolUtils.searchAndSelectArticle(articol);
        wait.until(ExpectedConditions.visibilityOfElementLocated(articolUI.editeazaArticolButon));
        getDriver().findElement(articolUI.editeazaArticolButon).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(articolUI.revertButton));
        results.verifyTrue(getDriver().findElement(articolUI.revertButton).getAttribute("ng-reflect-disabled").equals("false"),
                "Article can be reverted by moderator",
                "Article cannot be reverted by moderator",
                true);
        getDriver().findElement(articolUI.revertButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(articolUI.mdText));
        results.verifyTrue(getDriver().findElement(articolUI.mdText).getText().equals("Un continut"),
                "Article successfully reverted",
                "Article revert failed",
                true);

    }

}
