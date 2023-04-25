package autotest.ui;

import org.openqa.selenium.By;

public class LoginUi {
    public By loginButton = By.xpath("//span[text()=' Login ']/ancestor::button");
    public By usernameInput = By.xpath("//input[@ng-reflect-name='username']");
    public By passwordInput = By.xpath("//input[@ng-reflect-name='password']");
}
