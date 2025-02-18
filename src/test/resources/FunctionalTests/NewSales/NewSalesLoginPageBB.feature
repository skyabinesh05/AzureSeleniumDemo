@STRegressionTest
Feature:NewSalesLoginPageBB

    Scenario Outline: Verify_ST_NewSales_BB_LoginPage
        Given User Launch the Url "Url1" for <TestName>
        And Enter valid username "Username" and Password "Password"
        Then User click on the submit button
        Then user should click on New CRM tab in SKY home screen
        Then User focus land on the new page
        Then User click on the "ST" button
        Then User click on the Menu button and  select New Sale Option
        Then User click on Nuovo Contratto Residenziale and select Nuovo Contratto Residenziale TV
        Then User click on BB option and Enter the Address fields
        Then User click on Packages and select Promo codes for BB "Promo"
        Then User should enter the "Nome" and "CogNome" and "Data_di_nascita" Anagrafica intestatario contratto details
        Then User should select the "Località_di_nascita" in Anagrafica intestatario contratto for BB details
        Then User should enter the "email" and "cellulare" Recapiti intestatario contratto details
        Then User should enter the "Numero_doc" and "Data Rilascio" and "Data scadenza" Documento intestatario contratto details
        Then User should enter the Indirizo details in BB
        Then User should enter the Pagamento details in BB
        Then User should enter the Domiciliazione bancaria IBAN details in BB
        Given Query to check the Created Contract Status using contract number "Cont_number"
        Examples:
            | TestName       |
            | "STchain_Test" |