import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.apache.log4j.Logger;


public class BaseTest {

    public static WebDriver driver;
    public static String baseUrl="https://www.network.com.tr/";
    public String url2 = "https://www.network.com.tr/search?searchKey=ceket&page=2";

    static Logger log = Logger.getLogger(BaseTest.class.getName());


    @Before
    public void beforeTest(){

        System.setProperty("webdriver.chrome.driver","drivers/chromedriver");
        driver = new ChromeDriver();
        driver.get(baseUrl);
        driver.manage().window().maximize();
        log.info("driver başlatıldı");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-extensions");
        options.addArguments("disable-popup-blocking");
    }

     @After
     public void quit(){
        driver.quit();
     }

}
