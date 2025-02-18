#! /usr/bin/pwsh
$environmentName=$args[0]
$browserLocation=$args[1]
$browserName=$args[2]
$browserVersion=$args[3]
$suiteName=$args[4]
$executionType=$args[5]
$recordTimer=$args[6]
$testingType=$args[7]
$tunnelName=$args[8]
$emulatorType=$args[9]
$emulatorName=$args[10]
$platformVersion=$args[11]
$appiumVersion=$args[12]
$addressLine=$args[13]

$curDir = Get-Location
(Get-Content "$curDir\runtime.properties") -Replace "ENVIRONMENT=.*","ENVIRONMENT=$environmentName"|
		%{$_ -replace "BROWSER_LOCATION=.*","BROWSER_LOCATION=$browserLocation"} |
		%{$_ -replace "BROWSER_NAME=.*","BROWSER_NAME=$browserName"} |
		%{$_ -replace "BROWSER_VERSION=.*","BROWSER_VERSION=$browserVersion"} |
		%{$_ -replace "SUITE_NAME=.*","SUITE_NAME=$suiteName"} |
		%{$_ -replace "EXECUTION_TYPE=.*","EXECUTION_TYPE=$executionType"} |
		%{$_ -replace "RECORDTIMER=.*","RECORDTIMER=$recordTimer"} |
		%{$_ -replace "TESTING_TYPE=.*","TESTING_TYPE=$testingType"} |
		%{$_ -replace "TUNNEL_NAME=.*","TUNNEL_NAME=$tunnelName"} |
		%{$_ -replace "EMULATOR_TYPE=.*","EMULATOR_TYPE=$emulatorType"} |
		%{$_ -replace "EMULATOR_NAME=.*","EMULATOR_NAME=$emulatorName"} |
		%{$_ -replace "DEVICE_PLATFORM_VERSION=.*","DEVICE_PLATFORM_VERSION=$platformVersion"} |
		%{$_ -replace "APPIUM_VERSION=.*","APPIUM_VERSION=$appiumVersion"} |
		%{$_ -replace "ADDRESS=.*","ADDRESS=$addressLine"} |
		Set-Content "$curDir\runtime.properties"