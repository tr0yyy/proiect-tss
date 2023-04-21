package autotest.ui;

import org.openqa.selenium.By;

public class ArticolUI {
    public By creazaArticolButon = By.xpath("//a[@href=\"/create-articol\"]");

    public By editeazaArticolButon = By.xpath("//button[@class=\"mat-focus-indicator mat-flat-button mat-button-base mat-primary\"]");

    public By salveazaModificarileButon = By.xpath("//button[@class=\"mat-focus-indicator mat-flat-button mat-button-base mat-primary\"]");

    public By mdText = By.xpath("//markdown");
}
