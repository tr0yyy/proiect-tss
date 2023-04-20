package autotest.ui;

import org.openqa.selenium.By;

public class CreazaArticolUI {
    public By titluInput = By.xpath("//input[@name=\"titlu\"]");

    public By domeniuMatSelect = By.xpath("//mat-select");

    public By optiuneArtaMatSelect = By.xpath("//mat-option[@ng-reflect-value=\"Arta\"]");

    public By optiuneGeografieMatSelect = By.xpath("//mat-option[@ng-reflect-value=\"Geografie\"]");

    public By optiuneIstorieMatSelect = By.xpath("//mat-option[@ng-reflect-value=\"Istorie\"]");

    public By optiuneStiintaMatSelect = By.xpath("//mat-option[@ng-reflect-value=\"Stiinta\"]");

    public By mdInput = By.xpath("//textarea[@class=\"md-input\"]");

    public By salveazaModificarileButton = By.xpath("//button[@class=\"mat-focus-indicator mat-flat-button mat-button-base mat-primary\"]");
}
