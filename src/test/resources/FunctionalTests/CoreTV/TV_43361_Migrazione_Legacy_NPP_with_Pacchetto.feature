@STRTREGRESSION
@ITRTREGRESSION
@Smoke
@TV
@TC43361
Feature:TV_43361_Migrazione_Legacy_NPP_with_Pacchetto

  Scenario Outline: Validating_Migration_of_Legacy_to_NPP_DTH_Legacy_with_Pacchetto
    Given User Launch the Url "Url1" for <TestName>
    And Enter valid username "Username" and Password "Password"
    Then User click on the submit button
    Then user should click on New CRM tab in SKY home screen
    Then User focus land on the new page
    Then User click on the "IT" button
    Then I click on Crea Interazione tab and click on CREA button
    Then User enter Contract Number "CodiceContratto" in Codice Contratto Field
    Then User click on CERCA button
    Then User click on Contract Number hyperlink
    Then I Click on LISTINO button for Upgrading Pachato
    Then I Verify and Click Avanti on Promo di Upgrade Tecnologico for upgrading pachato
    Then I Verfiy and click on Avanti on Esito Controlli Preliminari for upgrading pachato
    Then I Verify and upgrading the package
    Then I Verify and click on Avanti on Carrello Migrazione TV for upgrading pachato
    Then I Verify and click on Avanti on Modifica Dati Account for upgrading pachato
    Then I Verify and click on Avanti on Riepilogo Ordine for upgrading pachato
    Then I Click on Register vocal option for upgrading pachato
    Then Query to check the postgres Status for "CodiceContratto"
    When Completed the Query after check the Listino in Arcadia Home Page

    Examples:
      | TestName |
      | "ITchain_TV_43361_P1" |
#     | "STchain_TV_43361_P1" |