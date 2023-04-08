package autotest.ui;

import org.openqa.selenium.By;

public class DemoUi {

    public By searchBar = By.xpath("//input[@type='search']");
    public By searchButton = By.xpath("//div[contains(@class,'input-group-btn')]//button[contains(@class,'searchbox-submit-button')]");
}
