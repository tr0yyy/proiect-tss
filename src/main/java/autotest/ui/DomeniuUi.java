package autotest.ui;

import org.openqa.selenium.By;

public class DomeniuUi {
    public By tabelArticole = By.xpath("//tbody//tr//td");
    public By articoleColumn1Title = By.xpath("//th[@mat-sort-header='name']");
    public By articoleColumn2Title = By.xpath("//th[@mat-sort-header='user']");
    public By articoleColumn3Title = By.xpath("//th[@mat-sort-header='date']");

}
