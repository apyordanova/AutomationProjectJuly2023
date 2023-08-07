Feature: As a User I would like to be able to view and change the data on My Details page

  Scenario: The user would like to navigate to his "Account Details" and verify the correct email was used
    Given the browser opened
    When the My Details page is reached
    And email address field is present
    Then check the field is filled with correct address

  Scenario: (On the same page)The user would like to enter his first and last names, make sure they are
  correct in the input boxes, and save.
    Given the fields for First and Last name are present
    When fields are cleared
    Then the user enters data in the fields
    Then save button is actioned
    Then data is saved