# Beware of `Utils` classes standard:

In order to use correctly these classes, you must extend from BaseUtils your new class created
```java
public class ArticolUtils extends BaseUtils {
    
    public ArticolUtils(WebDriverWait wait, WebDriver driver, InitializeTest.Results results) {
        super(wait, driver, results);
    }
    /* your implementation */
}
```

In test function, your class constructor **must** be called in a `@BeforeClass` function, for example:
```java
public class ArticolTest extends InitializeTest {

    private ArticolUtils articolUtils;

    @BeforeClass
    public void initializeUtils() {
        this.articolUtils = new ArticolUtils(wait, getDriver(), results);
    }
    /* your implementation */
}
```
Frequent error: initializing utils variable in constructor. It will not work because these three variables are initialized in `@BeforeClass` of `InitializeTests`, and annotated functions are run after constructors.
