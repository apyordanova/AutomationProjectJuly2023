Feature: As a user I would like to be able to login with existing credentials to My account dashboard

  Scenario: Log in to My Account page
    Given browser has been open
    When the My Account page reached
    Given Login section is available on page
    When Login input fields are present
    Then send input for Email address
    And send input for Password
    And Click on Login button
    Then User is on My Account page