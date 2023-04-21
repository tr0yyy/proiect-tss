package autotest.tests;

import autotest.framework.InitializeTest;
import autotest.ui.DomeniuUi;
import autotest.ui.HomeUi;
import net.bytebuddy.utility.RandomString;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ListaDomeniiTest extends InitializeTest{
    public void verificareArticol(String titlu, String domeniu, String continut) throws IOException, InterruptedException {
        System.out.println("Started testing verification of article in the right field");
        ArticolTest articolTest=new ArticolTest();
        Thread.sleep(Duration.of(5, ChronoUnit.SECONDS));
        articolTest.creazaArticol(titlu,domeniu,continut);
        DomeniuUi domeniuUiDefault = new DomeniuUi("0");
        HomeUi homeUiDefault = new HomeUi("","0");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String currentDate= dtf.format(LocalDateTime.now(ZoneOffset.UTC));
        int articleCounter=0;
        List<String> listDomenii=new ArrayList<String>(){{
          add("Arta");
          add("Geografie");
          add("Istorie");
          add("Stiinta");
        }};
        getDriver().findElement(homeUiDefault.homeNavButton).click();
        for(String dom:listDomenii) {
            HomeUi homeUi = new HomeUi(dom,"1");
            //wait.until(ExpectedConditions.visibilityOfElementLocated(homeUi.domeniu));
            getDriver().findElement(homeUi.domeniu).click();

            int rowCount=getDriver().findElements(domeniuUiDefault.tabelArticole).size();
            if(rowCount>0){
                for (int i=1;i<=rowCount;i++){
                    DomeniuUi domeniuUi = new DomeniuUi(String.valueOf(i));
                    String articleTitle= getDriver().findElement(domeniuUi.articolNume).getText();
                    String articleDate= getDriver().findElement(domeniuUi.articolData).getText();
                    if(articleTitle.equals(titlu) && !dom.equals(domeniu) && currentDate.equals(articleDate)){
                        break;
                    }
                    if(articleTitle.equals(titlu) && dom.equals(domeniu) && currentDate.equals(articleDate)){
                        articleCounter++;
                        break;
                    }
                }
            }
            getDriver().findElement(homeUi.homeNavButton).click();
        }
        if(articleCounter==1){
            results.assertTrue(true,"Articolul creat apare in lista de articole din domeniul corect.","", false);

        }
        else{
            results.assertTrue(false," ","Nu a fost gasit articolul creat.", true);
        }
    }

    @Test
    public void checkListArticles() throws InterruptedException, IOException {
        String titlu = "Troi Profesor Din Vara" + RandomString.make(4);
        String domeniu = "Istorie";
        String continut = "aaaaaaaaaaaaaaaaaaaaaaaaaaaabcdar";
        verificareArticol(titlu, domeniu, continut);
    }
}


