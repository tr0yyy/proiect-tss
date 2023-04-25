package autotest.ui;

import org.openqa.selenium.By;

public class RegisterUi {

    public By usernameInput = By.xpath("//input[@formcontrolname='username']");
    public By emailInput = By.xpath("//input[@formcontrolname='email']");
    public By passwordInput = By.xpath("//input[@formcontrolname='password']");
    public By registerButton = By.xpath("//span[@class='mat-button-wrapper']");
}
