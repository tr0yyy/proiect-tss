package autotest.tests;

import autotest.framework.InitializeTest;
import autotest.ui.ArticolUI;
import autotest.ui.CreazaArticolUI;
import autotest.ui.DomeniuUi;
import autotest.ui.HomeUi;
import net.bytebuddy.utility.RandomString;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticolTest extends InitializeTest {
    public void creazaArticol(String titlu, String domeniu, String continut) throws InterruptedException, IOException {
        System.out.println("Started testing creaza articol");
        ArticolUI articolUI = new ArticolUI();
        CreazaArticolUI creazaArticolUI = new CreazaArticolUI();

        wait.until(ExpectedConditions.visibilityOfElementLocated(articolUI.creazaArticolButon));
        getDriver().findElement(articolUI.creazaArticolButon).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(creazaArticolUI.titluInput));
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

        results.assertTrue(getDriver().getCurrentUrl().contains(titlu.replace(" ", "%20")), "Articolul a fost creat", "a esuat crearea articolului", true);
    }

    public void verificareArticol(String titlu, String domeniu, String continut) throws IOException, InterruptedException {
        System.out.println("Started testing verification of article in the right field");
        Thread.sleep(Duration.of(5, ChronoUnit.SECONDS));
        HomeUi homeUi = new HomeUi();
        getDriver().findElement(homeUi.homeNavButton).click();
        creazaArticol(titlu, domeniu, continut);
        getDriver().findElement(homeUi.homeNavButton).click();
        DomeniuUi domeniuUi = new DomeniuUi();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String currentDate = dtf.format(LocalDateTime.now(ZoneOffset.UTC));
        int articleCounter = 0;
        List<String> listDomenii = new ArrayList<String>() {{
            add("Arta");
            add("Geografie");
            add("Istorie");
            add("Stiinta");
        }};
        listDomenii.remove(domeniu);
        switch (domeniu) {
            case "Arta" -> getDriver().findElement(homeUi.domeniuArta).click();
            case "Geografie" -> getDriver().findElement(homeUi.domeniuGeografie).click();
            case "Istorie" -> getDriver().findElement(homeUi.domeniuIstorie).click();
            case "Stiinta" -> getDriver().findElement(homeUi.domeniuStiinta).click();
            default -> results.assertTrue(false,
                    "Nu avem eroare.",
                    "Nu exista acest domeniu in lista.",
                    true);
        }
        List<WebElement> listArticole = getDriver().findElements(domeniuUi.tabelArticole);
        for (int i = 0; i < listArticole.size(); i += 3) {
            if (listArticole.get(i).getText().equals(titlu) && listArticole.get(i + 2).getText().equals(currentDate)) {
                articleCounter++;
            }
        }
        results.verifyTrue(articleCounter == 1,
                "Articolul " + titlu + " a fost amplasat in domeniul corect.",
                "Articolul " + titlu + " nu se afla in domeniul in care ar fi trebuit.",
                true);
        for (String dom : listDomenii) {
            getDriver().findElement(homeUi.homeNavButton).click();
            switch (dom) {
                case "Arta" -> getDriver().findElement(homeUi.domeniuArta).click();
                case "Geografie" -> getDriver().findElement(homeUi.domeniuGeografie).click();
                case "Istorie" -> getDriver().findElement(homeUi.domeniuIstorie).click();
                case "Stiinta" -> getDriver().findElement(homeUi.domeniuStiinta).click();
            }
            listArticole = getDriver().findElements(domeniuUi.tabelArticole);
            for (int i = 0; i < listArticole.size(); i += 3) {
                results.verifyTrue(!(listArticole.get(i).getText().equals(titlu) && listArticole.get(i + 2).getText().equals(currentDate)),
                        "Nicio Eroare.",
                        "Articolul " + titlu + " a fost gasit si in domeniul " + dom + " desi nu face parte din acest domeniu.",
                        true);
            }
        }
    }

    public void verificareTabel50(String titlu, String domeniu, String continut) throws IOException, InterruptedException, ParseException {
        System.out.println("Started testing verification of article showing in the recent table of articles on homepage");
        Thread.sleep(Duration.of(5, ChronoUnit.SECONDS));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String currentDate = dtf.format(LocalDateTime.now(ZoneOffset.UTC));
        HomeUi homeUi = new HomeUi();
        getDriver().findElement(homeUi.homeNavButton).click();
        creazaArticol(titlu, domeniu, continut);
        getDriver().findElement(homeUi.homeNavButton).click();
        results.verifyTrue(getDriver().findElement(homeUi.articoleColumn1Title).getText().equals("Nume Articol") &&
                getDriver().findElement(homeUi.articoleColumn2Title).getText().equals("Domeniu") &&
                getDriver().findElement(homeUi.articoleColumn3Title).getText().equals("Data adăugării"),
                "Tabelul contine coloanele corect.",
                "Tabelul nu contine coloanele corect",
                true);
        List<WebElement> listArticole = getDriver().findElements(homeUi.articoleTable);
        results.verifyTrue(listArticole.size()<=150,
                "Tabelul de articole contine cele mai recente 50 de articole.",
                "Tabelul de articole nu contine cele mai recente 50 de articole.",
                true);
        results.verifyTrue(listArticole.get(0).getText().equals(titlu) &&
                listArticole.get(1).getText().equals(domeniu) &&
                listArticole.get(2).getText().equals(currentDate),
                "Articolul care doar ce a fost creat apare in lista cu cele mai recente articole.",
                "Articolul care doar ce a fost creat nu apare in lista cu cele mai recente articole",
                true);
        for (int i=5;i<listArticole.size();i+=3){
            Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(listArticole.get(i-3).getText());
            Date date2=new SimpleDateFormat("dd/MM/yyyy").parse(listArticole.get(i).getText());
            results.verifyTrue(date1.compareTo(date2)>=0,
                    "Data articolului este mai recenta.",
                    "Articolele nu au fost ordonate dupa data crearii.",
                    true);
        }

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

    @Test
    public void checkListArticles() throws InterruptedException, IOException {
        String titlu = "Troi Profesor Din Vara" + RandomString.make(4);
        String domeniu = "Istorie";
        String continut = "aaaaaaaaaaaaaaaaaaaaaaaaaaaabcdar";
        verificareArticol(titlu, domeniu, continut);
    }
    @Test
    public void checkHomeArticleTable() throws InterruptedException, IOException, ParseException {
        String titlu = "Troi Profesor Din Vara" + RandomString.make(4);
        String domeniu = "Istorie";
        String continut = "aaaaaaaaaaaaaaaaaaaaaaaaaaaabcdar";
        verificareTabel50(titlu, domeniu, continut);
    }

}
