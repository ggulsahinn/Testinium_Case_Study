import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.junit.Assert;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import static org.junit.Assert.*;
import com.opencsv.CSVReader;
import userLogin.userLogin;

public class MainPage extends BaseTest{

    public String url2 = "https://www.network.com.tr/search?searchKey=ceket&page=2";
    private Label price;
    public String addPrice = price.getText();

    private Label size;
    public String addSize = price.getText();




    @Test
    public void should_true_main_page_url(){
        //when
        // when it goes to main page
        //then

        //baseUrl verdiğimiz url ile aynı mı kontrol sağlar.
        assertEquals(baseUrl,driver.getCurrentUrl());
    }

    @Test
    public void should_url_change_when_scrolling_to_second_page(){
        //given
        try {
            driver.findElement(By.cssSelector("button[id='onetrust-accept-btn-handler']")).click();
        } catch (Exception e){
            //Nothing do
        }
        WebElement elementSearch = driver.findElement(By.id("search"));
        elementSearch.sendKeys("ceket");
        elementSearch.sendKeys(Keys.ENTER);
        //when
        WebElement elementMore = driver.findElement(By.cssSelector("button[class='button -secondary -sm relative']"));
        Actions action= new Actions(driver);
        action.moveToElement(elementMore).perform();
        elementMore.click();

        //then
        String driverCurrent = driver.getCurrentUrl();
        Assert.assertNotEquals(url2, driverCurrent);

    }

    @Test
    public void should_first_reduction_product() throws InterruptedException {

        //given
        driver.get(url2);

        //when

        //İlk gelen indirimli ürün
        WebElement product = driver.findElement(By.xpath("(.//*[@class='product__discountPercent'])[1]"));

        //İndirimli üzerine hover
        Actions action = new Actions(driver);
        action.moveToElement(product).perform();

        //then

        List <WebElement> sizes = driver.findElements(By.xpath("(//*[@class='product__discountPercent'])[1]//..//..//..//div[@class='product__header']//div[@class='product__sizeItem']//div[@class='product__size -productCart radio-box']"));
        Thread.sleep(1000);

        //Ürün fiyatı
        WebElement price = driver.findElement(By.cssSelector(".product__price -actual"));

        //Ürün bedeni
        for (WebElement size:sizes) {
            if(size.getAttribute("-disabled") == null){
                String addSize = size.getText();
                Thread.sleep(100);
                action.moveToElement(size).perform();
                size.click();
                Thread.sleep(500);
            }
        }

        WebElement addToBasket = driver.findElement(By.cssSelector(".-addToBasket"));
        addToBasket.click();
        Thread.sleep(1000);
        WebElement goToCart = driver.findElement(By.cssSelector("a.header__basketModal.-checkout"));
        goToCart.click();
    }

    @Test
    public void basketCheck(){

        //Sepet bedeni ile eklenen beden aynı mı kontrol edilir
        WebElement basketSize = driver.findElement(By.cssSelector(".cartItem__attr.-size span.cartItem__attrValue"));
        Assert.assertEquals(addSize,basketSize);

        //Sepet fiyatı ile eklenen beden aynı mı kontrol edilir
        WebElement basketPrice = driver.findElement(By.cssSelector(".cartItem__price.-sale"));
        String basketPrice_str = basketPrice.getText();
        Assert.assertEquals(addPrice,basketPrice);

        //İndirimli fiyat ile eski fiyat aynı mı kontrol edilir
        WebElement oldPrice = driver.findElement(By.cssSelector("cartItem__price.-old.-labelPrice"));
        String oldPrice_str = oldPrice.getText();
        Assert.assertEquals(addPrice,oldPrice);

        //Ürünün eski fiyatı indirimli fiyatından büyük mü kontrol edilir
        int basketPrice_int = Integer.parseInt(basketPrice_str);
        oldPrice_str = oldPrice_str.replace("TL","").replaceAll(" ","").replace(".","").replace(",", "");

        int oldPrice_int = Integer.parseInt(oldPrice_str);

        if(oldPrice_int > basketPrice_int){
            logger.info("Eski ürün fiyatı indirimli fiyattan yüksek.");
        }
        else{
            logger.warning("Eski ürün fiyatı indirimli fiyattan  düşük.");
        }

        //Devam et butonuna tıklanır
        WebElement continue_btn = driver.findElement(By.cssSelector(".continueButton.n-button.large"));
        continue_btn.click();

    }

    @Test
    public userLogin IOException() throws IOException {
        String csvPath = "/user.csv";
        File file = new File(csvPath);
        CSVReader csvReader;
        String[] csvValue;

        csvReader = new CSVReader(new FileReader(csvPath));
        while ((csvValue = csvReader.readNext()) != null) {
            String email = csvValue[0];
            String password = csvValue[1];
            driver.findElement(By.id("n-input-email")).sendKeys(email);
            driver.findElement(By.id("n-input-password")).sendKeys(password);
        }

        driver.findElement(By.cssSelector("n-button large block text-center -primary")).click();
        return null;
    }

    @Test
    public void productRemove() throws InterruptedException {
        //Network logosuna tıklanır
        driver.findElement(By.cssSelector("a.header__logoImg")).click();
        //Anasayfadaki sepek iconuna tıklanır sepet açılır
        driver.findElement(By.cssSelector("button.js-basket-trigger")).click();
        Thread.sleep(1000);
        //Sepetteki ürünü silmek için icona tıklanır
        driver.findElement(By.cssSelector(".header__basketModal.-remove")).click();
        Thread.sleep(1000);
        //Çıkartmak için onay verilir
        driver.findElement(By.cssSelector("data-remove-cart-item")).click();
        Thread.sleep(1000);
        List<WebElement> basketCount = driver.findElements(By.cssSelector("span.header__basket--count"));
        if(basketCount.size() == 0) {
            logger.info("Ürün sepetten çıkarıldı.");
        }

    }

}
