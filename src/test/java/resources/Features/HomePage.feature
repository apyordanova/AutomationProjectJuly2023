Feature: As a User I would like to be able to use the Home page
  Scenario: Open the Home page
    Given the browser is open
    And the home page is reached
    When there are 3 slides
    And my account button is clicked
    Then we move to the next page