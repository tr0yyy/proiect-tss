package autotest.ui;

import org.openqa.selenium.By;

public class HomeUi {

    public By searchHome = By.xpath("//input[@type='text']");
    public By domeniiList = By.xpath("//div[@_ngcontent-ng-cli-universal-c92 and @class='col-sm']");
    public By domeniuArta = By.xpath("//mat-list//a[text()='Arta']");
    public By domeniuGeografie = By.xpath("//mat-list//a[text()='Geografie']");
    public By domeniuIstorie = By.xpath("//mat-list//a[text()='Istorie']");
    public By domeniuStiinta = By.xpath("//mat-list//a[text()='Stiinta']");

    public By searchNavBar = By.xpath("//input[@type='search']");
    public By searchButton = By.xpath("//button[text()=' Search ']");
    public By homeNavButton = By.xpath("//a[contains(@class,'nav-link') and text()='Home']");
    public By createArticleNavButton = By.xpath("//a[contains(@class,'nav-link') and text()='Creaza articol']");
    public By loginNavButton = By.xpath("//a[contains(@class,'nav-link') and text()='Login']");
    public By logoutNavButton = By.xpath("//a[@class='nav-link text-dark' and text()='Log Out']");
    public By registerNavButton = By.xpath("//a[contains(@class,'nav-link') and text()='Register']");
    public By articoleTable = By.xpath("//tbody//tr//td");
    public By articoleColumn1Title = By.xpath("//th[text()='Nume Articol']");
    public By articoleColumn2Title = By.xpath("//th[text()='Domeniu']");
    public By articoleColumn3Title = By.xpath("//th[text()='Data adăugării']");

    public By searchResultByInput(String text) {
        return By.xpath("//nav[contains(@class,'navbar')]//a[text()='" + text + "']");
    }

}
