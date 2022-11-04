import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.logging.Logger;


public class BaseTest {
    final static Logger logger = Logger.getLogger(String.valueOf(BaseTest.class));

    public static WebDriver driver;
    WebDriverWait wait;
    public static String baseUrl="https://www.network.com.tr/";

    @Before
    public void beforeTest(){

        System.setProperty("webdriver.chrome.driver","drivers/chromedriver");
        driver = new ChromeDriver();
        driver.get(baseUrl);
        driver.manage().window().maximize();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-extensions");
        options.addArguments("disable-popup-blocking");
    }

     @After
     public void tearDown(){
        driver.quit();
     }

}
