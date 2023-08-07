Feature: As a User I would like to be able to Log Out of Dashboard page

  Scenario: Logout of the Dashboard page
    Given the browser has been opened
    When the Dashboard page is reached
    And the logout button is clicked
    Then the user is logged out