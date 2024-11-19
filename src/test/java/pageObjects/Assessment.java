package pageObjects;

import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utilities.BaseTest;

import java.util.List;
import java.util.Set;

public class Assessment extends BaseTest {
    public static WebDriverWait wait;
    public static JavascriptExecutor js;

    public Assessment(WebDriver ldriver) {
        driver = ldriver;

    }

    public void enterUserName(String username) {
        WebElement user = driver.findElement(By.xpath(BaseTest.getLocator("username")));
        user.click();
        user.clear();
        user.sendKeys(username);
    }

    public void enterPassWord(String password) {
        WebElement pass = driver.findElement(By.xpath(BaseTest.getLocator("password")));
        pass.click();
        pass.clear();
        pass.sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(By.xpath(BaseTest.getLocator("login_button"))).click();
    }

    public void verifyProductsName() {
        try {
            WebElement text = driver.findElement(By.xpath(BaseTest.getLocator("products_name")));
            String actualText = text.getText();

            String expectedText = "Products";
            Assert.assertEquals(actualText, expectedText);
            System.out.println("The user can now see " + actualText);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public void add_Sauce_Labs_Backpack_ToCart() {
        try {
            driver.findElement(By.xpath(BaseTest.getLocator("sauce_labs_backpack"))).click();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void add_Sauce_Labs_BikeLight_ToCart() {
        try {
            driver.findElement(By.xpath(BaseTest.getLocator("sauce_labs_bikeLight"))).click();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void verify_twoItems_Added_ToCart() {
        try {
            WebElement cartIcon = driver.findElement(By.xpath(BaseTest.getLocator("cart_icon")));
            String cartNumberItems = cartIcon.getText();

            Assert.assertEquals(cartNumberItems, "2");
            System.out.println("The number of items in the cart ARE:" + cartNumberItems);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void verifyingTwo_Items_Details() throws InterruptedException {

        js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy[0,-500]");

        driver.findElement(By.xpath(BaseTest.getLocator("cart_icon"))).click();
        Thread.sleep(300);
        try {
//       Sauce Labs Backpack
            WebElement backPackName = driver.findElement(By.xpath(BaseTest.getLocator("backpack_name")));
            WebElement backPackprice = driver.findElement(By.xpath(BaseTest.getLocator("backpack_price")));

            Assert.assertEquals(backPackName.getText(), "Sauce Labs Backpack");
            Assert.assertEquals(backPackprice.getText(), "$29.99");

//    Sauce Labs Bike pack
            WebDriverWait wait = new WebDriverWait(driver, 3);
//            WebElement bikePackName = driver.findElement(By.xpath(BaseTest.getLocator("bike_name")));

//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("bike_name")));
            WebElement bikePackprice = driver.findElement(By.xpath(BaseTest.getLocator("bike_price")));

//            Assert.assertEquals(bikePackName.getText(), "Sauce Labs Bike Light");
            Assert.assertEquals(bikePackprice.getText(), "$15.99");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clickCheckoutButton() {
        js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy[0,500]");
        driver.findElement(By.xpath(BaseTest.getLocator("checkoutButton"))).click();

    }

    public void clickContinueButton() {
        driver.findElement(By.xpath(BaseTest.getLocator("continue_button"))).click();

    }

    public void enterUserCredentials(String first, String lastName, String pcode) {
        WebElement name = driver.findElement(By.xpath(BaseTest.getLocator("firstname")));
        name.click();
        name.clear();
        name.sendKeys(first);

        WebElement last = driver.findElement(By.xpath(BaseTest.getLocator("lastname")));
        last.click();
        last.clear();
        last.sendKeys(lastName);

        WebElement post = driver.findElement(By.xpath(BaseTest.getLocator("postal_code")));
        post.click();
        post.clear();
        post.sendKeys(pcode);

    }

    public void verifyDetails() {
        WebElement total = driver.findElement(By.xpath(BaseTest.getLocator("item_total")));
        String itemsTotal = total.getText();
        System.out.println("THE TOTAL OF ALL ITEMS COST IS:" + itemsTotal);
    }

    public void clickFinishButton() {
        driver.findElement(By.xpath(BaseTest.getLocator("finish_btn"))).click();
    }

    public void verifySuccessMsg() {
        try {
            WebElement msgName = driver.findElement(By.xpath(BaseTest.getLocator("success_msg")));

            Assert.assertEquals(msgName.getText(), "Thank you for your order!");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void logout() {
        js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy[0,-500]");
        driver.findElement(By.xpath(BaseTest.getLocator("menu"))).click();
        driver.findElement(By.xpath(BaseTest.getLocator("logoutBTN"))).click();
    }

    public void checkURL(){
     try {
         String actualURL= driver.getCurrentUrl();
         String expectedRL= "https://www.saucedemo.com/";
         Assert.assertEquals(actualURL,expectedRL);
     } catch (Exception e) {
         throw new RuntimeException(e);
     }
    }
}





