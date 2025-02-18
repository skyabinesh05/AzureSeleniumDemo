@STRTREGRESSION
@ITRTREGRESSION
@Smoke
@TV
@TC20302
Feature: Arcadia Upgrade NPP - Kenan

  Scenario Outline: Validating downgrade package immediato - DTH legacy
    Given User Launch the Url "Url1" for <TestName>
    And Enter valid username "Username" and Password "Password"
    Then User click on the submit button
    Then User click on New CRM tab in Arcadia Home Screen
    Then User focus land on the new page
    Then User click on the "IT" button
    Then I click on Crea Interazione tab and click on CREA button
    Then User enter Contract Number "CodiceContratto" in Codice Contratto Field
    Then User click on CERCA button
    Then User click on Contract Number hyperlink
    Then I Click on Cambio Consistenza for Upgrading Package
    Then I select the Cambio Consistenza option from Scelta Attivita
    Then I Verify and upgrading the package for NPP or Legacy
    Then I Verify and click on Avanti on Carrello Migrazione TV for upgrading NPP
    Then I Verify and click on Avanti on Modifica Dati Account for upgrading NPP
    Then I Verify and click on Avanti on Riepilogo Ordine for upgrading NPP
    Then I Click on Register vocal option for upgrading pachato
    Then Query to check the postgres Status for "CodiceContratto"
    Then verify kenan status for contract "CodiceContratto" for downgrade
    Examples:
      | TestName  |
      | "IT_Test" |