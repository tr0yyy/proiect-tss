package autotest.tests;

import autotest.framework.InitializeTest;
import autotest.ui.DomeniuUi;
import autotest.ui.HomeUi;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SortTest extends InitializeTest {
    public void navigareSortare() throws IOException, InterruptedException, ParseException {

        results.info("Started testing the sort.",true);
        Thread.sleep(Duration.of(5, ChronoUnit.SECONDS));
        HomeUi homeUi = new HomeUi();
        DomeniuUi domeniuUi = new DomeniuUi();
        List<String> listDomenii = new ArrayList<String>() {{
            add("Arta");
            add("Geografie");
            add("Istorie");
            add("Stiinta");
        }};
        for (String dom : listDomenii) {
            getDriver().findElement(homeUi.homeNavButton).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(homeUi.domeniuArta));
            switch (dom) {
                case "Arta" -> getDriver().findElement(homeUi.domeniuArta).click();
                case "Geografie" -> getDriver().findElement(homeUi.domeniuGeografie).click();
                case "Istorie" -> getDriver().findElement(homeUi.domeniuIstorie).click();
                case "Stiinta" -> getDriver().findElement(homeUi.domeniuStiinta).click();
            }
            getDriver().findElement(domeniuUi.articoleColumn1Title).click();
            List<WebElement> listArticole = getDriver().findElements(domeniuUi.tabelArticole);
            /*
            The listArticle contains all the elements of the table so considering the table contains 3 columns to iterate
            through each row we need to skip the other 2 columns and iterate by 3 positions(+=3)
             */
            //Sortare Titlu
            for (int titleColumnIndex = 3; titleColumnIndex < listArticole.size(); titleColumnIndex += 3) {
                results.verifyTrue(listArticole.get(titleColumnIndex - 3).getText().compareTo(listArticole.get(titleColumnIndex).getText()) <= 0 ||
                        listArticole.get(titleColumnIndex).getText().equals(""),
                        "Elementul precedent se afla in ordinea buna sortat ascendent dupa titlu pentru sortarea pe domeniul: "+dom,
                        "Elementele nu sunt sortate corect daca ar fi fost ascendent dupa titlu pentru sortarea pe domeniul: "+dom,
                        true);
            }
            getDriver().findElement(domeniuUi.articoleColumn1Title).click();
            listArticole = getDriver().findElements(domeniuUi.tabelArticole);
            for (int titleColumnIndex = 3; titleColumnIndex < listArticole.size(); titleColumnIndex += 3) {
                results.verifyTrue(listArticole.get(titleColumnIndex - 3).getText().compareTo(listArticole.get(titleColumnIndex).getText()) >= 0 ||
                        listArticole.get(titleColumnIndex-3).getText().equals(""),
                        "Elementul precedent se afla in ordinea buna sortat descendent dupa titlu pentru sortarea pe domeniul: "+dom,
                        "Elementele nu sunt sortate corect daca ar fi fost descendent dupa titlu pentru sortarea pe domeniul: "+dom,
                        true);
            }
            /*
            In structura siteului a fost considerat ca empty space-ul sa fie catalogat drept ultimul element asadar
            in sortare ascendenta empty space este trecut la sfarsit iar descendenta la inceput
             */
            //Sortare autor
            getDriver().findElement(domeniuUi.articoleColumn2Title).click();
            listArticole = getDriver().findElements(domeniuUi.tabelArticole);
            for (int titleColumnIndex = 4; titleColumnIndex < listArticole.size(); titleColumnIndex += 3) {
                results.verifyTrue(listArticole.get(titleColumnIndex - 3).getText().compareTo(listArticole.get(titleColumnIndex).getText()) <= 0 ||
                        listArticole.get(titleColumnIndex).getText().equals(""),
                        "Elementul precedent se afla in ordinea buna sortat ascendent dupa autor pentru sortarea pe domeniul: "+dom,
                        "Elementele nu sunt sortate corect daca ar fi fost ascendent dupa autor pentru sortarea pe domeniul: "+dom,
                        true);
            }
            getDriver().findElement(domeniuUi.articoleColumn2Title).click();
            listArticole = getDriver().findElements(domeniuUi.tabelArticole);
            for (int titleColumnIndex = 4; titleColumnIndex < listArticole.size(); titleColumnIndex += 3) {
                results.verifyTrue(listArticole.get(titleColumnIndex - 3).getText().compareTo(listArticole.get(titleColumnIndex).getText()) >= 0 ||
                        listArticole.get(titleColumnIndex-3).getText().equals(""),
                        "Elementul precedent se afla in ordinea buna sortat descendent dupa autor pentru sortarea pe domeniul: "+dom,
                        "Elementele nu sunt sortate corect daca ar fi fost descendent dupa autor pentru sortarea pe domeniul: "+dom,
                        true);
            }
            //Sortare data
            getDriver().findElement(domeniuUi.articoleColumn3Title).click();
            listArticole = getDriver().findElements(domeniuUi.tabelArticole);
            for (int titleColumnIndex = 5; titleColumnIndex < listArticole.size(); titleColumnIndex += 3){
                Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(listArticole.get(titleColumnIndex-3).getText());
                Date date2=new SimpleDateFormat("dd/MM/yyyy").parse(listArticole.get(titleColumnIndex).getText());
                results.verifyTrue(date1.compareTo(date2)<=0,
                        "Elementul precedent se afla in ordinea buna sortat ascendent dupa data pentru sortarea pe domeniul: "+dom,
                        "Elementele nu sunt sortate corect daca ar fi fost ascendent dupa data pentru sortarea pe domeniul: "+dom,
                        true);
            }
            getDriver().findElement(domeniuUi.articoleColumn3Title).click();
            listArticole = getDriver().findElements(domeniuUi.tabelArticole);
            for (int titleColumnIndex = 5; titleColumnIndex < listArticole.size(); titleColumnIndex += 3) {
                Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(listArticole.get(titleColumnIndex-3).getText());
                Date date2=new SimpleDateFormat("dd/MM/yyyy").parse(listArticole.get(titleColumnIndex).getText());
                results.verifyTrue(date1.compareTo(date2)>=0,
                        "Elementul precedent se afla in ordinea buna sortat descendent dupa data pentru sortarea pe domeniul: "+dom,
                        "Elementele nu sunt sortate corect daca ar fi fost descendent dupa data pentru sortarea pe domeniul: "+dom,
                        true);
            }
        }
    }

    @Test
    public void checkSort() throws IOException, InterruptedException, ParseException {
        navigareSortare();
    }
}
