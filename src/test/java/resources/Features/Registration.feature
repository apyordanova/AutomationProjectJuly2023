Feature: As a User I would like to be able to register on My Account page

  Scenario: Register on My Account page
  (Scenario amended: User attempts to register with already existing user, an error message should be displayed on page)
    Given browser is open
    When the My Account page is reached
    Given Register section is available on page
    When Register input fields are present
    And Register button is clickable
    Then send existing input for Email address
    And send existing input for Password
    And Click on Register button
    Then error message is displayed