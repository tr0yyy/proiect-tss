package autotest.tests;

import autotest.framework.InitializeTest;
import autotest.ui.ArticolUI;
import autotest.ui.CreazaArticolUI;
import net.bytebuddy.utility.RandomString;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class ArticolTest extends InitializeTest {
    public void creazaArticol(String titlu, String domeniu, String continut) throws InterruptedException, IOException {
        System.out.println("Started testing creaza articol");
        ArticolUI articolUI = new ArticolUI();
        CreazaArticolUI creazaArticolUI = new CreazaArticolUI();

        //wait.until(ExpectedConditions.visibilityOfElementLocated(articolUI.creazaArticolButon));
        getDriver().findElement(articolUI.creazaArticolButon).click();

        //wait.until(ExpectedConditions.visibilityOfElementLocated(creazaArticolUI.titluInput));
        getDriver().findElement(creazaArticolUI.titluInput).sendKeys(titlu);

        getDriver().findElement(creazaArticolUI.domeniuMatSelect).click();

        switch (domeniu) {
            case "Arta" -> getDriver().findElement(creazaArticolUI.optiuneArtaMatSelect).click();
            case "Geografie" -> getDriver().findElement(creazaArticolUI.optiuneGeografieMatSelect).click();
            case "Istorie" -> getDriver().findElement(creazaArticolUI.optiuneIstorieMatSelect).click();
            case "Stiinta" -> getDriver().findElement(creazaArticolUI.optiuneStiintaMatSelect).click();
            default -> results.assertTrue(false, "", "nu exista aceasta categorie", true);
        }

        getDriver().findElement(creazaArticolUI.mdInput).sendKeys(continut);

        getDriver().findElement(creazaArticolUI.salveazaModificarileButton).click();
        Thread.sleep(Duration.of(3, ChronoUnit.SECONDS));

        results.assertTrue(getDriver().getCurrentUrl().contains(titlu.replace(" ", "%20")),
                "articolul a fost creat", "a esuat crearea articolului", true);
    }

    private void modificaArticol(String continut, String continutModificat) throws IOException, InterruptedException {
        ArticolUI articolUI = new ArticolUI();
        CreazaArticolUI creazaArticolUI = new CreazaArticolUI();

        getDriver().findElement(articolUI.editeazaArticolButon).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(creazaArticolUI.mdInput));
        getDriver().findElement(creazaArticolUI.mdInput).sendKeys(continutModificat);
        Thread.sleep(Duration.of(3, ChronoUnit.SECONDS));

        getDriver().findElement(articolUI.salveazaModificarileButon).click();

        String mdText = getDriver().findElement(articolUI.mdText).getText();
        Thread.sleep(Duration.of(3, ChronoUnit.SECONDS));


        results.assertTrue(mdText.equals(continut + continutModificat), "Articolul a fost modificat cu succes",
                "Textul modificat nu corespune", true);
    }
    @Test
    public void checkCreateEditArticol() throws InterruptedException, IOException {
        String titlu = "Troi Profesor Din Vara" + RandomString.make(4);
        String domeniu = "Istorie";
        String continut = "aaaaaaaaaaaaaaaaaaaaaaaaaaaabcdar";
        creazaArticol(titlu, domeniu, continut);

        String continutModificat = "bbbbbbbbbbb";
        modificaArticol(continut, continutModificat);
    }

}
