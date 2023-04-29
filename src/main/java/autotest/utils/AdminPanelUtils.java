package autotest.utils;

import autotest.framework.Const;
import autotest.framework.InitializeTest;
import autotest.ui.AdminPanelUI;
import autotest.ui.HomeUi;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdminPanelUtils extends BaseUtils {

    public AdminPanelUtils(WebDriverWait wait, WebDriver driver, InitializeTest.Results results) {
        super(wait, driver, results);
    }

    /**
     * Add or Remove Role Function for Admin (implementation is the same, based on clicking on a button)
     * @param user User that needs the modification
     * @param role Role to be added or removed
     */
    public void addOrRemoveRole(String user, Const.Roles role) {
        HomeUi homeUi = new HomeUi();
        AdminPanelUI adminPanelUI = new AdminPanelUI();
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeUi.adminPanelNavButton));
        driver.findElement(homeUi.adminPanelNavButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(adminPanelUI.userSearchBar));
        driver.findElement(adminPanelUI.userSearchBar).sendKeys(user);
        driver.findElement(adminPanelUI.userSearchBarButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(adminPanelUI.getUserXPATH(user)));
        driver.findElement(adminPanelUI.getUserXPATH(user)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(adminPanelUI.saveChangesButton));
        switch (role) {
            case ADMIN -> driver.findElement(adminPanelUI.adminRoleButton).click();
            case MODERATOR -> driver.findElement(adminPanelUI.modRoleButton).click();
            case USER -> driver.findElement(adminPanelUI.userRoleButton).click();
            default -> {
            }
        }
        driver.findElement(adminPanelUI.saveChangesButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(adminPanelUI.userSearchBar));
    }
}
