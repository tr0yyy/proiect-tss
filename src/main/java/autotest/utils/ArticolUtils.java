package autotest.utils;

import autotest.framework.InitializeTest;
import autotest.ui.ArticolUI;
import autotest.ui.CreazaArticolUI;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

public class ArticolUtils {

    public WebDriverWait wait;
    public WebDriver driver;
    public InitializeTest.Results results;

    public ArticolUtils(WebDriverWait wait, WebDriver driver, InitializeTest.Results results) {
        this.wait = wait;
        this.driver = driver;
        this.results = results;
    }

    public void creazaArticol(String titlu, String domeniu, String continut) throws InterruptedException, IOException {
        System.out.println("Started testing creaza articol");
        ArticolUI articolUI = new ArticolUI();
        CreazaArticolUI creazaArticolUI = new CreazaArticolUI();

        wait.until(ExpectedConditions.visibilityOfElementLocated(articolUI.creazaArticolButon));
        driver.findElement(articolUI.creazaArticolButon).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(creazaArticolUI.titluInput));
        driver.findElement(creazaArticolUI.titluInput).sendKeys(titlu);

        driver.findElement(creazaArticolUI.domeniuMatSelect).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(creazaArticolUI.optiuneArtaMatSelect));

        switch (domeniu) {
            case "Arta" -> driver.findElement(creazaArticolUI.optiuneArtaMatSelect).click();
            case "Geografie" -> driver.findElement(creazaArticolUI.optiuneGeografieMatSelect).click();
            case "Istorie" -> driver.findElement(creazaArticolUI.optiuneIstorieMatSelect).click();
            case "Stiinta" -> driver.findElement(creazaArticolUI.optiuneStiintaMatSelect).click();
            default -> results.assertTrue(false, "", "nu exista aceasta categorie", true);
        }

        driver.findElement(creazaArticolUI.mdInput).sendKeys(continut);

        driver.findElement(creazaArticolUI.salveazaModificarileButton).click();
        Thread.sleep(Duration.ofSeconds(3).toMillis());

        results.assertTrue(driver.getCurrentUrl().contains(titlu.replace(" ", "%20")), "Articolul a fost creat", "a esuat crearea articolului", true);
    }
}
