Feature: Sauce Demo
@sauce
  Scenario: verify user is able to login
    Given user opens with browser "https://www.saucedemo.com/"
    When user enters the username "standard_user"
    And user enters the password "secret_sauce"
    And user clicks on login button
    Then verify the user is logged in