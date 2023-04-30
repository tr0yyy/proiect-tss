package autotest.tests;

import autotest.framework.Const;
import autotest.framework.InitializeTest;
import autotest.ui.AdminPanelUI;
import autotest.ui.HomeUi;
import autotest.utils.AccountUtils;
import autotest.utils.AdminPanelUtils;
import net.bytebuddy.utility.RandomString;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class AdminPanelTest extends InitializeTest {
    private AccountUtils accountUtils;
    private AdminPanelUtils adminPanelUtils;
    private final String userName = "user-"+RandomString.make(4);
    private final String userEmail = userName+"@gmail.com";
    private final String userPass = "!@#4QWEr";
    private AdminPanelUI adminPanelUI;
    private HomeUi homeUI;

    @BeforeClass
    private void initializeUtils(){
        this.accountUtils = new AccountUtils(wait, getDriver(), results);
        this.adminPanelUtils = new AdminPanelUtils(wait, getDriver(), results);
        this.adminPanelUI = new AdminPanelUI();
        this.homeUI = new HomeUi();
    }

    @Test
    private void preconditions() throws IOException {
        accountUtils.register(userName, userEmail, userPass);
        accountUtils.logout();
        accountUtils.login("admin", "!@#4QWEr");
    }

    @Test(dependsOnMethods = {"preconditions"})
    private void checkUserInList() throws IOException {

        results.assertTrue(
                adminPanelUtils.isUserInList(userName),
                String.format("Urmatorul utilizator a fost gasit in lista de utilizatori: %s", userName),
                String.format("Urmatorul utilizator nu a fost gasit in lista de utilizatori: %s", userName),
                true
                );
    }

    @Test(dependsOnMethods = {"checkUserInList"})
    private void checkRolesArePresent() throws IOException {
        getDriver().findElement(adminPanelUI.getUserXPATH(userName)).click();
        results.verifyTrue(
                getDriver().findElement(adminPanelUI.adminRoleButton).isDisplayed(),
                "Butonul pentru rolul de ADMIN este prezent",
                "Butonul pentru rolul de ADMIN nu este prezent",
                false
        );
        results.verifyTrue(
                getDriver().findElement(adminPanelUI.modRoleButton).isDisplayed(),
                "Butonul pentru rolul de MODERATOR este prezent",
                "Butonul pentru rolul de MODERATOR nu este prezent",
                false
        );
        results.verifyTrue(
                getDriver().findElement(adminPanelUI.userRoleButton).isDisplayed(),
                "Butonul pentru rolul de UTILIZATOR este prezent",
                "Butonul pentru rolul de UTILIZATOR nu este prezent",
                false
        );
    }

    @Test(dependsOnMethods = {"checkRolesArePresent"})
    private void checkSaveUserWithoutRoles() throws IOException {
        adminPanelUtils.disableRoleInAdminPanel(Const.Roles.USER);
        adminPanelUtils.disableRoleInAdminPanel(Const.Roles.ADMIN);
        adminPanelUtils.disableRoleInAdminPanel(Const.Roles.MODERATOR);

        results.verifyTrue(
                !getDriver().findElement(adminPanelUI.saveChangesButton).isEnabled(),
                "Utilizatorul nu poate fi salvat fara roluri",
                "Utilizatorul a putut fi salvat fara roluri",
                true
        );
    }

    @Test(dependsOnMethods = {"checkSaveUserWithoutRoles"})
    private void checkAdminPanelOnAdminRole() throws IOException {
        adminPanelUtils.enableRoleInAdminPanel(Const.Roles.ADMIN);
        getDriver().findElement(adminPanelUI.saveChangesButton).click();

        accountUtils.logout();
        accountUtils.login(userName, userPass);

        results.verifyTrue(
          getDriver().findElement(homeUI.adminPanelNavButton).isDisplayed(),
          "Butonul de Admin Panel este afisat pentru userul cu rolul de ADMIN",
          "Butonul de Admin Panel nu este afisat pentru userul cu rolul de ADMIN",
          true
        );
    }

    @Test(dependsOnMethods = {"checkAdminPanelOnAdminRole"})
    private void checkUserManageSelfRoles() throws IOException {
        getDriver().findElement(homeUI.adminPanelNavButton).click();
        adminPanelUtils.isUserInList(userName);
        getDriver().findElement(adminPanelUI.getUserXPATH(userName)).click();

        results.verifyTrue(
                !getDriver().findElement(adminPanelUI.saveChangesButton).isEnabled(),
                "Userul nu isi poate modifica propriile roluri",
                "Userul isi poate modifica propriile roluri",
                true
        );
    }

}
