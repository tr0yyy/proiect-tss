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
    public void creazaArticol(String titlu, String continut, String domeniu) throws InterruptedException, IOException {
        System.out.println("Started testing creaza articol");
        ArticolUI articolUI = new ArticolUI();
        CreazaArticolUI creazaArticolUI = new CreazaArticolUI();

        wait.until(ExpectedConditions.visibilityOfElementLocated(articolUI.creazaArticolButon));
        getDriver().findElement(articolUI.creazaArticolButon).click();

        getDriver().findElement(creazaArticolUI.titluInput).sendKeys(titlu);

        getDriver().findElement(creazaArticolUI.domeniuMatSelect).click();

        switch (continut) {
            case "Arta" -> getDriver().findElement(creazaArticolUI.optiuneArtaMatSelect).click();
            case "Geografie" -> getDriver().findElement(creazaArticolUI.optiuneGeografieMatSelect).click();
            case "Istorie" -> getDriver().findElement(creazaArticolUI.optiuneIstorieMatSelect).click();
            case "Stiinta" -> getDriver().findElement(creazaArticolUI.optiuneStiintaMatSelect).click();
            default -> results.assertTrue(false, "", "nu exista aceasta categorie", true);
        }

        getDriver().findElement(creazaArticolUI.mdInput).sendKeys(domeniu);

        getDriver().findElement(creazaArticolUI.salveazaModificarileButton).click();
        Thread.sleep(Duration.of(3, ChronoUnit.SECONDS));

        results.assertTrue(getDriver().getCurrentUrl().contains(titlu.replace(" ", "%20")),
                "articolul a fost creat", "a esuat crearea articolului", true);
    }

    @Test
    public void checkCreateEditArticol() throws InterruptedException, IOException {
        creazaArticol("Troi Profesor Din Vara" + RandomString.make(4), "Istorie", "aaaaaaaaaaaaaaaaaaaaaaaaaaaabcdar");
    }
}
