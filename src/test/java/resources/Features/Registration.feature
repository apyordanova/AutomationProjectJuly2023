Feature: As a User I would like to be able to register on My Account page

  Scenario: Register on My Account page
  (existing credentials are used, error will be returned on last step)
    Given browser is open
    When the My Account page is reached
    Given Register section is available on page
    When Register input fields are present
    And Register button is clickable
    Then send existing input for Email address
    And send existing input for Password
    And Click on Register button
    Then User is on Dashboard page