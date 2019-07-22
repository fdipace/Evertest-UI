Feature: Trendkite Dashboard Page

  As a Trendkite user
  I want to be able to Login to the application
  In order to interact with the Dashboard page features

  @DashboardOnly @Regression
  Scenario: User can create a new Dashboard
    Given I navigate to Trendkite login page
    When I login with specific user and password
      | username                     | password   |
      | CISION_E2E_USER_1@cision.com | QAtest123! |
    Then I validate Dashboard page is displayed
    When I create a new Dashboard
      | dashboardName             | searchName             | searchText           |
      | create & delete dashboard | create & delete search | "Texas" AND "Aggies" |
    Then I validate created Dashboard exists under MY DASHBOARDS section
