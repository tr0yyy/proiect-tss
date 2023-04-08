# Testarea Sistemelor Software

- Testele vor fi create in `src/main/java/tests`
- Clasele cu elemente de UI vor fi create in `src/main/java/ui`
- Clasele cu elemente reutilizabile (algoritmi reutilizabili in mai multe teste) vor fi create in `src/main/java/utils`
- Testele create vor fi adaugate in `run_tests.xml` dupa aceasta structura:
  ```xml
  <suite ...>
     <!-- Testul pe care vrei sa-l adaugi in scope-ul suitei principale -->
    <test name="Denumire test" parallel="false">
        <parameter name="browser" value="chrome"/>
        <classes>
            <class name="autotest.tests.ClasaDeTest" />
        </classes>
    </test>
  </suite>```
---
## Pasi pornire framework
1. Porneste Selenium Grid (Citeste [aici](grid/README.md))
2. Se ruleaza `run_tests.xml` din IntelliJ pentru pornirea testelor
3. Pana la realizarea unui output formatat, rezultatele se pot vedea doar in IDE.