package autotest.ui;

import org.openqa.selenium.By;

public class AdminPanelUI {
    public By userSearchBar = By.xpath("//app-admin-panel//div//input[@type='search']");
    public By userSearchBarButton = By.xpath("//app-admin-panel//div//button[text()=' Search ']");
    public By adminRoleButton = By.xpath("//app-admin-panel//div//label[contains(@for,'btncheck1')]");
    public By modRoleButton = By.xpath("//app-admin-panel//div//label[contains(@for,'btncheck2')]");
    public By userRoleButton = By.xpath("//app-admin-panel//div//label[contains(@for,'btncheck3')]");
    public By adminRoleButtonInput = By.xpath("//app-admin-panel//div//input[contains(@id,'btncheck1')]");
    public By modRoleButtonInput = By.xpath("//app-admin-panel//div//input[contains(@id,'btncheck2')]");
    public By userRoleButtonInput = By.xpath("//app-admin-panel//div//input[contains(@id,'btncheck3')]");
    public By saveChangesButton = By.xpath("//app-admin-panel//div//button[span[span[text()=' Salveaza modificarile']]]");

    public By getUserXPATH(String user){
        return By.xpath(String.format("//app-admin-panel//div//a[text()=' %s ']", user));
    }

}
