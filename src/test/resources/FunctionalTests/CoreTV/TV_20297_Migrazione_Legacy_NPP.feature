@STRTREGRESSION
@ITRTREGRESSION
@Smoke
@TV
@TC20297
Feature:TV_20297_Migrazione_Legacy_NPP

    Scenario Outline: Validating_Migration_of_Legacy_to_NPP_DTH_Legacy
        Given User Launch the Url "Url1" for <TestName>
        And Enter valid username "Username" and Password "Password"
        Then User click on the submit button
        Then user should click on New CRM tab in SKY home screen
        Then User focus land on the new page
        Then User click on the "ST" button
        Then I click on Crea Interazione tab and click on CREA button
        Then User enter Contract Number "CodiceContratto" in Codice Contratto Field
        Then User click on CERCA button
        Then User click on Contract Number hyperlink
        Then I Click on LISTINO button
        Then I Verify and Click Avanti on Promo di Upgrade Tecnologico
        Then I Verfiy and click on Avanti on Esito Controlli Preliminari
        Then I Verify and click on Avanti on Carrello Migrazione TV
        Then I Verify and click on Avanti on Modifica Dati Account
        Then I Verify and click on Avanti on Riepilogo Ordine
        Then I Click on Register vocal option
        Then verify postgres Status for contract "Number"

        Examples:
            | TestName |
            | "STchain_TV_20297_P1" |
#            | "ITChain_TV_20297_P1" |