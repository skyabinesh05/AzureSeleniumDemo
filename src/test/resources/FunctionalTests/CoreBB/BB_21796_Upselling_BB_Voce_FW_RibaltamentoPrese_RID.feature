@STRTREGRESSION
@ITRTREGRESSION
@Smoke
@BB
@TC21796

Feature:BB_21796_Upselling_BB_Voce_FW_RibaltamentoPrese_RID

  Scenario Outline: Validating  UpSelling BB + Voce FW + Ribaltamento Prese - RID - TV DTH NPP - Arcadia Part
    Given User Launch the Url "Url1" for <TestName>
    And Enter valid username "Username" and Password "Password"
    Then User click on the submit button
    Then User click on New CRM tab in Arcadia Home Screen
    Then User focus land on the new page
    Then User click on the "IT" button
    Then I click on Crea Interazione tab and click on CREA button
    Then User enter Contract Number "CodiceContratto_FW" in Codice Contratto Field
    Then User click on CERCA button
    Then User click on Contract Number hyperlink
    Then User click on BB under Consistenza
    Then User enters FW Address in IT Chain "Citta" "IndirizzoVia" and "NumCivico"
    Then User click Broadband image in Verfica Copertura page
    Then User chooses promo "BB_Promo" in the Composizione Offerta Page
    Then User click Fine in Delibera AGCOM page
    Then User click Avanti in Metodo di Pagamento page
    Then User click on Sky Wifi Hub option in Attivazione nuova linea o migrazione page
    Then User click on Cellulare Option in Numero di Contatto page
    Then User enter details in Verifica Documento page
    Then User click Next in Spedizione Tramite Corriere page
    Then User click checkbox and proceed in Diritto di ripensamento page
    Then User Click Avanti in Conferma Ordine page
    Then User click on Registrazione Vocal Order option in Firma Digitale page
    Then User enter details and give "email" in Canale di Comunicazione page



    Examples:
      | TestName              |
      | "ITChain_BB_21796_P1" |
