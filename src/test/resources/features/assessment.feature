@Assessment

Feature:E-Commerce

  Scenario Outline:adding items to cart
    Given that a user is on the sauce demo website
    And enters the "<username>" on the username field
    And enters the "<password>" on the password field
    And clicks on the login button
    And the user sees prodcuts name after logging in
    And click the Sauce Labs Backpack add to card button
    And click the Sauce Labs Bike Light add to card button
    And the item should show that two items are added to the cart
    And verify that the two added items are displayed in the cart with the correct names and prices
    And click the checkout button
    And enters details "<firstname>", "<lastname>" and "<postal code>"
    And click on the continue button
    And proceed to the checkout overview page and verify that the correct items and their total price are listed
    And click the Finish button
    Then the user will see s successfull messsage displayed
    And user clicks humbegur menu to log out
    Then check the presence of the URL

    Examples:
      | username      | password     | firstname | lastname | postal code |
      | standard_user | secret_sauce | John      | Doe      | 12345       |

