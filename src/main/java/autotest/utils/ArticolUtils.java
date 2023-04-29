package autotest.utils;

import autotest.framework.InitializeTest;
import autotest.ui.ArticolUI;
import autotest.ui.CreazaArticolUI;
import autotest.ui.DomeniuUi;
import autotest.ui.HomeUi;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ArticolUtils extends BaseUtils {

    public ArticolUtils(WebDriverWait wait, WebDriver driver, InitializeTest.Results results) {
        super(wait, driver, results);
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

    public void modificaArticol(String continut, String continutModificat) throws IOException, InterruptedException {
        ArticolUI articolUI = new ArticolUI();
        CreazaArticolUI creazaArticolUI = new CreazaArticolUI();

        driver.findElement(articolUI.editeazaArticolButon).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(creazaArticolUI.mdInput));
        driver.findElement(creazaArticolUI.mdInput).sendKeys(continutModificat);
        Thread.sleep(Duration.ofSeconds(3).toMillis());

        driver.findElement(articolUI.salveazaModificarileButon).click();

        String mdText = driver.findElement(articolUI.mdText).getText();
        Thread.sleep(Duration.ofSeconds(3).toMillis());


        results.assertTrue(mdText.equals(continut + continutModificat), "Articolul a fost modificat cu succes",
                "Textul modificat nu corespune", true);
    }

    public void searchAndSelectArticle(String title) {
        HomeUi homeUi = new HomeUi();
        ArticolUI articolUI = new ArticolUI();
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeUi.searchNavBar));
        driver.findElement(homeUi.searchNavBar).sendKeys(title);
        driver.findElement(homeUi.searchButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeUi.searchResultByInput(title)));
        driver.findElement(homeUi.searchResultByInput(title)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(articolUI.articleTitle));
    }

}
