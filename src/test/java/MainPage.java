import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.junit.Assert;
import java.util.List;
import static org.junit.Assert.*;

public class MainPage extends BaseTest{

    public String url2 = "https://www.network.com.tr/search?searchKey=ceket&page=2";
    public String cartUrl = "https://www.network.com.tr/cart";



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
        //needs to be wait

        //then
        String driverCurrent = driver.getCurrentUrl();
        Assert.assertNotEquals(url2, driverCurrent);

    }

    @Test
    public void should_first_reduction_product() throws InterruptedException {

        //given
        driver.get(url2);

        //when

        //List <WebElement> firstProduct = driver.findElements(By.cssSelector(".product__discountPercent:nth-child(1)"));
        WebElement product = driver.findElement(By.xpath("(.//*[@class='product__discountPercent'])[1]"));

        //İlk gelen indirimli ürün
        //System.out.println(products.getText());

        //İndirimli üzerine hover
        Actions action = new Actions(driver);
        action.moveToElement(product).perform();

        //then

        List <WebElement> sizes = driver.findElements(By.xpath("(//*[@class='product__discountPercent'])[1]//..//..//..//div[@class='product__header']//div[@class='product__sizeItem']//div[@class='product__size -productCart radio-box']"));
        //sizes.stream().findFirst();
        Thread.sleep(1000);



        for (WebElement size:sizes) {
            if(size.getAttribute("-disabled") == null){
                Thread.sleep(100);
                action.moveToElement(size).perform();
                size.click();
                Thread.sleep(500);
            }
        }


        WebElement goToCart = driver.findElement(By.xpath(".//href[@class='header__basket--checkout']"));
        Thread.sleep(1000);
        goToCart.click();

        assertEquals(cartUrl,driver.getCurrentUrl());

    }


}
