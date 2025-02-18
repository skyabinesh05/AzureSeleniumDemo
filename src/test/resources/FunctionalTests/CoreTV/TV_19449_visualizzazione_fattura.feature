@STRTREGRESSION
@ITRTREGRESSION
@Smoke
@TV
@TC19449
Feature: Fattura
  Scenario Outline: Verify_ST_Fattura
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
    Then I click on Balance Fatture Pagamento
    Then User focusing on fattura Pdf new Window

#    Contract= 501846532 [IT]
#    Contract = 22614444 [ST]


    Examples:
      | TestName  |
      | "IT_Test" |