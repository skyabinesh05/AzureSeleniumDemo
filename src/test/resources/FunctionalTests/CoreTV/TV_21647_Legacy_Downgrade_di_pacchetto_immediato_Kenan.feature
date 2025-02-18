@STRTREGRESSION
@ITRTREGRESSION
@ArcadiaDowngrade
@TV_21647
@TV
@Smoke
Feature: Arcadia Downgrade Legacy - Kenan

  Scenario Outline: Validating downgrade package immediato - DTH legacy
    Given User Launch the Url "Url1" for <TestName>
    And Enter valid username "Username" and Password "Password"
    Then User click on the submit button
    Then User click on New CRM tab in Arcadia Home Screen
    Then User focus land on the new page
    Then User click on the "IT" button
    Then I click on Crea Interazione tab and click on CREA button
    When I enter Contract "Number" and click CERCA
    Then I click on the contract Code
    When I click Cambio Consistenza button from Consistenza view tab
    Then I click on a "Package" to be downgraded under Gestione Consistenza
    Then I validate selected package is displayed in the Pop up and click Si option
    Then I verify selected package is removed in Paccheto and click Avanti
    Then I verify Modifica Dati Cliente is displayed and click Prosegui button
    Then I verify Riepilogo Ordine is displayed with summary of the updated "Pack" Order
    Then I verify Esito Operazione and click conforma
    Then I verify success message in Invio Ordine page and click Fine
    Then I should verify package is downgraded in arcadia home page
    Then verify postgres Status for contract "Number"
    Then verify kenan status for contract "Number" for downgrade
    Examples:
      | TestName  |
      | "IT_Test" |


