package autotest.ui;

import org.openqa.selenium.By;

public class DomeniuUi {
    public By tabelArticole = By.xpath("//tbody//tr");
    public By articoleColumn1Title = By.xpath("//th[@mat-sort-header='name']");
    public By articoleColumn2Title = By.xpath("//th[@mat-sort-header='user']");
    public By articoleColumn3Title = By.xpath("//th[@mat-sort-header='date']");
    public By articolNume;
    public By articolDomeniu;
    public By articolData;

    public DomeniuUi(String rand) {
        String xpathColumn1 = "//tbody/tr["+rand+"]/td[1]";
        String xpathColumn2 = "//tbody/tr["+rand+"]/td[2]";
        String xpathColumn3 = "//tbody/tr["+rand+"]/td[3]";
        this.articolNume=By.xpath(xpathColumn1);
        this.articolDomeniu=By.xpath(xpathColumn2);
        this.articolData=By.xpath(xpathColumn3);
    }
}
