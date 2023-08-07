Feature: As a User I would like to be able to register on My Account page

  Scenario: Register on My Account page
    Given browser is open
    When the My Account page is reached
    Given Register section is available on page
    When Register input fields are present
    Then send existing input for Email address
    And send existing input for Password
    And Click on Register button
    Then User is on My Account page