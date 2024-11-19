package stepDefinitions;

import io.cucumber.java.bs.A;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebElement;
import pageObjects.Assessment;
import utilities.BaseTest;

public class AssessmentStepDefinition extends BaseTest {
    Assessment assessment;

    @Given("that a user is on the sauce demo website")
    public void thatAUserIsOnTheSauceDemoWebsite() {
        assessment = new Assessment(driver);

    }

    @And("enters the {string} on the username field")
    public void entersTheOnTheUsernameField(String username) {
        assessment.enterUserName(username);
    }

    @And("enters the {string} on the password field")
    public void entersTheOnThePasswordField(String password) {
        assessment.enterPassWord(password);
    }

    @Given("clicks on the login button")
    public void clicks_on_the_login_button() {
        assessment.clickLoginButton();
    }

    @Given("the user sees prodcuts name after logging in")
    public void the_user_sees_prodcuts_name_after_logging_in() {
        assessment.verifyProductsName();
    }

    @Given("click the Sauce Labs Backpack add to card button")
    public void click_the_sauce_labs_backpack_add_to_card_button() {
        assessment.add_Sauce_Labs_Backpack_ToCart();
    }

    @Given("click the Sauce Labs Bike Light add to card button")
    public void click_the_sauce_labs_bike_light_add_to_card_button() {
        assessment.add_Sauce_Labs_BikeLight_ToCart();
    }

    @Given("the item should show that two items are added to the cart")
    public void the_item_should_show_that_two_items_are_added_to_the_cart() {
        assessment.verify_twoItems_Added_ToCart();
    }


    @And("verify that the two added items are displayed in the cart with the correct names and prices")
    public void verifyThatTheTwoAddedItemsAreDisplayedInTheCartWithTheCorrectNamesAndPrices() throws InterruptedException {
        assessment.verifyingTwo_Items_Details();
    }

    @And("click the checkout button")
    public void clickTheCheckoutButton() {
        assessment.clickCheckoutButton();
    }

    @And("enters details {string}, {string} and {string}")
    public void entersDetailsAnd(String fName, String lName, String pCode) {
        assessment.enterUserCredentials(fName, lName, pCode);
    }

    @And("click on the continue button")
    public void clickOnTheContinueButton() {
        assessment.clickContinueButton();
    }

    @And("proceed to the checkout overview page and verify that the correct items and their total price are listed")
    public void proceedToTheCheckoutOverviewPageAndVerifyThatTheCorrectItemsAndTheirTotalPriceAreListed() {
        assessment.verifyDetails();
    }


    @And("click the Finish button")
    public void clickTheFinishButton() {
        assessment.clickFinishButton();
    }

    @Then("the user will see s successfull messsage displayed")
    public void theUserWillSeeSSuccessfullMesssageDisplayed() {
        assessment.verifySuccessMsg();
    }

    @And("user clicks humbegur menu to log out")
    public void userClicksHumbegurMenuToLogOut() {
        assessment.logout();
    }

    @Then("check the presence of the URL")
    public void checkThePresenceOfTheURL() {
    }
}
