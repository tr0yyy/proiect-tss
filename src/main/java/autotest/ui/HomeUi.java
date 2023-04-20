package autotest.ui;

import org.openqa.selenium.By;

public class HomeUi {

    public By searchHome = By.xpath("//input[@type='text']");
    public By domeniiList = By.xpath("//div[@_ngcontent-ng-cli-universal-c92 and @class='col-sm']");
    public By domeniu;
    public By searchNavBar = By.xpath("//input[@type='search']");
    public By homeNavButton = By.xpath("//a[contains(@class,'nav-link') and text()='Home']");
    public By createArticleNavButton = By.xpath("//a[contains(@class,'nav-link') and text()='Creaza articol']");
    public By loginNavButton = By.xpath("//a[contains(@class,'nav-link') and text()='Login']");
    public By registerNavButton = By.xpath("//a[contains(@class,'nav-link') and text()='Register']");
    public By articoleTable = By.xpath("//tbody");
    public By articoleColumn1Title = By.xpath("//th[text()='Nume Articol']");
    public By articoleColumn2Title = By.xpath("//th[text()='Domeniu']");
    public By articoleColumn3Title = By.xpath("//th[text()='Data adăugării']");
    public By articolNume;
    public By articolDomeniu;
    public By articolData;

    public HomeUi(String domeniu, String rand) {
        String xpathDomeniu = "//mat-list//a[text()='" + domeniu + "']";
        this.domeniu = By.xpath(xpathDomeniu);
        String xpathColumn1 = "//tbody/tr["+rand+"]/td[1]";
        String xpathColumn2 = "//tbody/tr["+rand+"]/td[2]";
        String xpathColumn3 = "//tbody/tr["+rand+"]/td[3]";
        this.articolNume=By.xpath(xpathColumn1);
        this.articolDomeniu=By.xpath(xpathColumn2);
        this.articolData=By.xpath(xpathColumn3);
    }
}
