package autotest.tests;

import autotest.framework.InitializeTest;
import autotest.ui.ArticolUI;
import autotest.ui.CreazaArticolUI;
import autotest.ui.DomeniuUi;
import autotest.ui.HomeUi;
import autotest.utils.ArticolUtils;
import net.bytebuddy.utility.RandomString;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ArticolTest extends InitializeTest {

    private ArticolUtils articolUtils;

    @BeforeClass
    public void initializeUtils() {
        this.articolUtils = new ArticolUtils(wait, getDriver(), results);
    }

    @Test
    public void checkCreateEditArticol() throws InterruptedException, IOException {
        String titlu = "Troi Profesor Din Vara" + RandomString.make(4);
        String domeniu = "Istorie";
        String continut = "aaaaaaaaaaaaaaaaaaaaaaaaaaaabcdar";
        articolUtils.creazaArticol(titlu, domeniu, continut);

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

    public void verificareArticol(String titlu, String domeniu, String continut) throws IOException, InterruptedException {
        results.info("Started testing verification of article in the right field",true);
        Thread.sleep(Duration.ofSeconds(5).toMillis());
        HomeUi homeUi = new HomeUi();
        getDriver().findElement(homeUi.homeNavButton).click();
        articolUtils.creazaArticol(titlu, domeniu, continut);
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
        //listDomenii contains only the domains different than the one from the article creation input
        listDomenii.remove(domeniu);
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeUi.domeniuArta));
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
        /*
        The listArticle contains all the elements of the table so considering the table contains 3 columns to iterate
        through each row we need to skip the other 2 columns and iterate by 3 positions(+=3)
        Also in order to access the date column which is the third one in the table, considering the title column is the first one
        we need to move 2 positions.
         */
        for (int titleColumnIndex = 0; titleColumnIndex < listArticole.size(); titleColumnIndex += 3) {
            if (listArticole.get(titleColumnIndex).getText().equals(titlu) && listArticole.get(titleColumnIndex + 2).getText().equals(currentDate)) {
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
        results.info("Started testing verification of article showing in the recent table of articles on homepage",true);
        Thread.sleep(Duration.ofSeconds(5).toMillis());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String currentDate = dtf.format(LocalDateTime.now(ZoneOffset.UTC));
        HomeUi homeUi = new HomeUi();
        getDriver().findElement(homeUi.homeNavButton).click();
        articolUtils.creazaArticol(titlu, domeniu, continut);
        getDriver().findElement(homeUi.homeNavButton).click();
        results.verifyTrue(getDriver().findElement(homeUi.articoleColumn1Title).getText().equals("Nume Articol") &&
                getDriver().findElement(homeUi.articoleColumn2Title).getText().equals("Domeniu") &&
                getDriver().findElement(homeUi.articoleColumn3Title).getText().equals("Data adăugării"),
                "Tabelul contine coloanele corect.",
                "Tabelul nu contine coloanele corect",
                true);
        List<WebElement> listArticole = getDriver().findElements(homeUi.articoleTable);
        /*
        The list that contains the table with articles has a size of rows*columns elements. So if the limit is 50 articles
        we need 50 articles * the 3 columns we have per article.
         */
        results.verifyTrue(listArticole.size()<=50*3,
                "Tabelul de articole contine cele mai recente 50 de articole.",
                "Tabelul de articole nu contine cele mai recente 50 de articole.",
                true);
        results.verifyTrue(listArticole.get(0).getText().equals(titlu) &&
                listArticole.get(1).getText().equals(domeniu) &&
                listArticole.get(2).getText().equals(currentDate),
                "Articolul care doar ce a fost creat apare in lista cu cele mai recente articole.",
                "Articolul care doar ce a fost creat nu apare in lista cu cele mai recente articole",
                true);
    }

    private void modificaArticol(String continut, String continutModificat) throws IOException, InterruptedException {
        ArticolUI articolUI = new ArticolUI();
        CreazaArticolUI creazaArticolUI = new CreazaArticolUI();

        getDriver().findElement(articolUI.editeazaArticolButon).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(creazaArticolUI.mdInput));
        getDriver().findElement(creazaArticolUI.mdInput).sendKeys(continutModificat);
        Thread.sleep(Duration.ofSeconds(3).toMillis());

        getDriver().findElement(articolUI.salveazaModificarileButon).click();

        String mdText = getDriver().findElement(articolUI.mdText).getText();
        Thread.sleep(Duration.ofSeconds(3).toMillis());


        results.assertTrue(mdText.equals(continut + continutModificat), "Articolul a fost modificat cu succes",
                "Textul modificat nu corespune", true);
    }

}
