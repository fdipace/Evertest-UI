Feature: Trendkite Login Page

  As a Trendkite user
  I want to be able to Login to the application
  In order to access its features

  @LoginOnly @Regression
  Scenario: User can login to Trendkite
    Given I navigate to Trendkite login page
    When I login with specific user and password
      | username                     | password   |
      | CISION_E2E_USER_1@cision.com | QAtest123! |
    Then I validate Dashboard page is displayed

  @BadLogin @Regression
  Scenario Outline: User cannot login to Trendkite with invalid user or password
    Given I navigate to Trendkite login page
    When I enter username <username> and password <password>
    And I click the Sign in button
    Then I validate Incorrect User or Password message is displayed
    Examples:
      | username                     | password        |
      | CISION_E2E_USER_1@cision.com | invalidPassword |
      | invalidUser                  | QAtest123!      |
