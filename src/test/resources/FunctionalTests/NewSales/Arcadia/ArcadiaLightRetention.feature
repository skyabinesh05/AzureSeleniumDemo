@ArcadiaLightRetention
Feature: Arcadia Light Retention

  Scenario Outline: Verify_ST_Arcadia_LightRetention
    Given User Launch the Url "Url1" for <TestName>
    And Enter valid username "Username" and Password "Password"
    Then User click on the submit button
    Then User click on New CRM tab in Arcadia Home Screen
    Then User focus land on the new page
    Then User click on the "ST" button
    Then I click on Crea Interazione tab and click on CREA button
#    When User click on Crea Interazione Manuale button
    Given Query to check the Light retention Status
#    Then User click on CREA button
    Then User enter Contract Number "CodiceContratto" in Codice Contratto Field
    Then User click on CERCA button
    Then User click on Contract Number hyperlink
    Then User click on Gestione BB and select Gestione light retention option
    Then User click on sky wifi BB and respective options for Light retention in Omniscript
    Given Query to check the Light retention Status


    Examples:
      | TestName  |
      | "ST_Test" |