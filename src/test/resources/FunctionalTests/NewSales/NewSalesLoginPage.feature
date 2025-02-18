@STRegressionTest
Feature:NewSalesLoginPage

  Scenario Outline: Verify_ST_NewSales_LoginPage
    Given User Launch the Url "Url1" for <TestName>
    And Enter valid username "Username" and Password "Password"
    Then User click on the submit button
    Then user should click on New CRM tab in SKY home screen
    Then User focus land on the new page
    Then User click on the "ST" button
    Then User click on the Menu button and  select New Sale Option
    Then User click on Nuovo Contratto Residenziale and select Nuovo Contratto Residenziale TV
    Then User click on Packages and select Promo codes for TV "Promo"
    Then User should enter the "Nome" and "CogNome" and "Data_di_nascita" Anagrafica intestatario contratto details
    Then User should select the "Località_di_nascita" in Anagrafica intestatario contratto details
    Then User should enter the "email" and "conferma_email" and "cellulare" Recapiti intestatario contratto details
    Then User should enter the "Numero_doc" and "Data Rilascio" and "Data scadenza" Documento intestatario contratto details
    Then User should enter the Indirizo details
    Then User should enter the Pagamento details
    Then User should enter the Domiciliazione bancaria IBAN details
    Given Query to check the Created Contract Status using contract number "Cont_number"
    Examples:
      | TestName       |
      | "STchain_Test" |
#      | "STchain_Test1" |
#      | "STchain_Test2" |
#      | "STchain_Test3" |
#      | "STchain_Test4" |


#  Scenario Outline: Verify_IT_NewSales_LoginPage
#    Given User Launch the Url "Url1" for <TestName>
#    And Enter valid username "Username" and Password "Password"
#    Then User click on the submit button
#    Then user should click on New CRM tab in SKY home screen
#    Then User focus land on the new page
#    And user should see the New Salesforce Page with Login "pdv-colle2e."
#    Then User click on the "pdv-colle2e." button
#
#    Examples:
#      | TestName       |
#      | "ITchain_Test" |