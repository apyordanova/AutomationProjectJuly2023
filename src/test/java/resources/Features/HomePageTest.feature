Feature: As a User I would like to be able to use the Home page

  Scenario: Open the Home page and find 3 slides on it
    Given the browser is open
    And the home page is reached
    When there are 3 slides

  Scenario: As a User I would like to be able to go on My Account page when using My Acc button
    When my account button is clicked
    Then we move to the next page