@STRTREGRESSION
@ITRTREGRESSION
@Smoke
@BB
@CONTRACTCREATION
@TC29223
@JMETER
Feature:BB_29223_OrderEntry_OF_AreaCD_RID

  Scenario Outline: Validating Order Entry - BB OF Area C&D
    Given User Launch the Url "Url1" for <TestName>
    And Enter valid username "Username" and Password "Password"
    Then User click on the submit button
    Then user should click on New CRM tab in SKY home screen
    Then User focus land on the new page
    Then User click on the "IT" button
    Then User click on the Menu button and  select New Sale Option
    Then User click on Nuovo Contratto Residenziale and select Nuovo Contratto Residenziale TV
    Then User click on BB option and Enter the Address fields for Area CD
    Then User click on Packages and select Promo codes for BB Area CD "Promo"
    Then User should enter the "Nome" and "CogNome" and "Data_di_nascita" Anagrafica intestatario contratto details
    Then User should select the "Località_di_nascita" in Anagrafica intestatario contratto for BB Area CD details
    Then User should enter the "email" and "cellulare" Recapiti intestatario contratto details
    Then User should enter the "Numero_doc" and "Data Rilascio" and "Data scadenza" Documento intestatario contratto details
    Then User should enter the Indirizo details "Citofono" in BB Area CD
    Then User should enter the Pagamento details in BB
    Then User should enter the Domiciliazione bancaria IBAN details in BB Area CD
    Given Query to check the Created BB Area CD Contract Status using contract number "Cont_number"
    ###Copy Payload from Postgres and fetch Appointment date and time
    #Given Query to fetch Appointment Date and Time from Payload
    ###Run JMeter job

    Examples:
      | TestName              |
      | "ITChain_BB_29223_P1" |
    #  | "STChain_BB_29223_P1" |

  Scenario Outline: Validating Order Entry - BB OF Area C&D - After JMeter job
    Given User Launch the Url "Url1" for <TestName>
    And Enter valid username "ArcUsername" and Password "ArcPassword"
    Then User click on the submit button
    Then User click on New CRM tab in Arcadia Home Screen
    Then User focus land on the new page
    Then User click on the "IT" button
    Then I click on Crea Interazione tab and click on CREA button
    Then User enter Contract Number "CodiceContratto_AreaCD" in Codice Contratto Field
    Then User click on CERCA button
    Then User click on Contract Number hyperlink
    Then User validates the Status of BB Area CD Contract in portale page
    Given Query to check the contract status after JMeter job is run using contract number "CodiceContratto_AreaCD"

    Examples:
      | TestName              |
      | "ITChain_BB_29223_P2" |
 #| "STChain_BB_29223_P2" |