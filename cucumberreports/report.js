$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("file:src/test/resources/FunctionalTests/CableBuyflow/ApplitoolProdToProdComparision.feature");
formatter.feature({
  "name": "ApplitoolsComparison",
  "description": "",
  "keyword": "Feature"
});
formatter.scenarioOutline({
  "name": "ApplitoolProdToProdComparision",
  "description": "",
  "keyword": "Scenario Outline"
});
formatter.step({
  "name": "User launching the url \"Url\" for \u003cTestName\u003e",
  "keyword": "Given "
});
formatter.step({
  "name": "Capturing applitool full \u003cTestName\u003e page footprint",
  "keyword": "Then "
});
formatter.examples({
  "name": "",
  "description": "",
  "keyword": "Examples",
  "rows": [
    {
      "cells": [
        "TestName"
      ]
    },
    {
      "cells": [
        "\"SB_home\""
      ]
    }
  ]
});
formatter.scenario({
  "name": "ApplitoolProdToProdComparision",
  "description": "",
  "keyword": "Scenario Outline"
});
formatter.before({
  "status": "passed"
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "User launching the url \"Url\" for \"SB_home\"",
  "keyword": "Given "
});
formatter.match({
  "location": "ProdToProd.userLaunchingUrl(String,String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Capturing applitool full \"SB_home\" page footprint",
  "keyword": "Then "
});
formatter.match({
  "location": "ProdToProd.applitoolCapture(String)"
});
formatter.result({
  "status": "passed"
});
formatter.uri("file:src/test/resources/FunctionalTests/CableBuyflow/CableBuyflow_EndToEnd.feature");
formatter.feature({
  "name": "CableBuyflow_EndToEnd",
  "description": "",
  "keyword": "Feature"
});
formatter.scenarioOutline({
  "name": "VerifyingCableBuyflowEndToEnd",
  "description": "",
  "keyword": "Scenario Outline"
});
formatter.step({
  "name": "User navigates to address localization Page \"Url1\" for \u003cTestName\u003e",
  "keyword": "Given "
});
formatter.step({
  "name": "Entering Valid credential \"PID\" and \"PWD\"",
  "keyword": "When "
});
formatter.step({
  "name": "User Localizing with valid \"Address\" \"Apt\" and \"Zipcode\"",
  "keyword": "And "
});
formatter.step({
  "name": "User navigates to Store Front page successfully",
  "keyword": "Then "
});
formatter.examples({
  "name": "",
  "description": "",
  "keyword": "Examples",
  "rows": [
    {
      "cells": [
        "TestName"
      ]
    },
    {
      "cells": [
        "\"ContentValidation_Internet\""
      ]
    }
  ]
});
formatter.scenario({
  "name": "VerifyingCableBuyflowEndToEnd",
  "description": "",
  "keyword": "Scenario Outline"
});
formatter.before({
  "status": "passed"
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "User navigates to address localization Page \"Url1\" for \"ContentValidation_Internet\"",
  "keyword": "Given "
});
formatter.match({
  "location": "CableTVResidentialE2E.user_navigates_to_address_localization_Page_for(String,String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Entering Valid credential \"PID\" and \"PWD\"",
  "keyword": "When "
});
formatter.match({
  "location": "CableTVResidentialE2E.entering_Valid_credential_and(String,String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "User Localizing with valid \"Address\" \"Apt\" and \"Zipcode\"",
  "keyword": "And "
});
formatter.match({
  "location": "CableTVResidentialE2E.user_Localizing_with_valid_and(String,String,String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "User navigates to Store Front page successfully",
  "keyword": "Then "
});
formatter.match({
  "location": "CableTVResidentialE2E.user_is_navigated_to_Store_Front_page_successfully()"
});
formatter.result({
  "status": "passed"
});
});