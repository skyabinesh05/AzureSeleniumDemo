#Get Json content
$workingDir=$args[0]
$extentReportFolder=$args[1]
$reportName=$args[2]
$applicationName=$args[3]
$environment=$args[4]

#########################################################
#Delete Objects  before uploading updated UI Design code 
##
echo "Getting latest json report"
$latest = Get-ChildItem "$workingDir\$extentReportFolder" *.json* | Sort-Object -Descending -Property LastWriteTime | Select-Object -first 1
$object1 = $latest.Name
echo $object1

$JSON = Get-Content -Raw -Path $workingDir\$extentReportFolder\$object1 | ConvertFrom-Json

$POSTJSON = Get-Content -Raw -Path $workingDir\"executionDetails.json" | ConvertFrom-Json

#Initialize variables
$passcount=0
$failcount=0
$totalcount
$passpercent
$failCountCheck=1
$status
$statusColour
$warningCountCheck=0
$warningCount
$passCountCheck=0
$passOrFail
$count=1

$demoarrayList = New-Object -TypeName 'System.Collections.ArrayList';

$browserNameFromJSON = New-Object -TypeName 'System.Collections.ArrayList';

$POSTJSON | Select-Object -Property browserName | ForEach-Object {
	$browserNameFromJSON.Add($_.browserName)
}
$browserName = $browserNameFromJSON[0]

#Iterate through JSON and get pass and fail count
$JSON | Select-Object -Property status | ForEach-Object {
    if($_.status -eq "PASS"){
        $passcount+=1
    }elseif($_.status -eq "FAIL"){
        $failcount+=1
    }elseif($_.status -eq "WARNING"){
        $warningCount+=1
    }
}

foreach ($js in $JSON)
{
	if($js.status -eq "FAIL"){
		$demoarrayList.Add("$count"+"."+ $js.name+"`n")
		$count+=1
	#	$demoarrayList | write-output
	}
}

#Get current location
#$curDir = Get-Location
#Calculate percentage

$totalcount=$passcount+$failcount+$warningCount
if($failcount -ge $failCountCheck -Or ($failcount -eq $passCountCheck -And $passcount -eq $passCountCheck)){
	$statusColour = "Red"
	$status = "FAIL"
}
else{
	$statusColour = "LightGreen"
	$status = "PASS"
}

if($passcount -eq $passCountCheck){
$passpercent = $passCountCheck
}else{
$passpercent = [math]::round((($passcount+$warningCount)/($passcount+$failcount+$warningCount))*100)
}

if($passpercent -ge 80){
	$statusColour = "#1dd05d"
	$passOrFail = "Pass "
}
elseif($passpercent -ge 30 -And $passpercent -lt 80) {
	$statusColour = "Orange"
	$passOrFail = "Pass "
}
else {
	$statusColour = "Red"
	$passOrFail = "Pass "
}

#$tagValue = $JSON.logs.Where{ $_.details -like "*Browser Name*"}.details

#$tagValues = $JSON | Where-Object{ $_.logs.details -like "Browser Name chrome"} | Write-Host $_.logs.details
  #  ForEach-Object { Write-Host $_.logs.details } 

#Write-Host "Browser name :: $tagValue"

$hostName = $env:COMPUTERNAME

$startTimeAL = New-Object -TypeName 'System.Collections.ArrayList';
$JSON | Select-Object -Property startTime | ForEach-Object {
    $startTimeAL.Add($_.startTime) 
}
$executionStartTime = $startTimeAL[0]

$endTimeAL = New-Object -TypeName 'System.Collections.ArrayList';
$JSON | Select-Object -Property endTime | ForEach-Object {
    $endTimeAL.Add($_.endTime) 
}
$arrLength = $endTimeAL.Count
$executionEndTime = $endTimeAL[$arrLength-1]

$executionDate = (Get-Date -Format "MM/dd/yyyy").ToString()

#Execution Duration
$executionDuration= New-TimeSpan -Start $executionStartTime -End $executionEndTime

#Write the pass fail counts and execution percentage in txt file
(Get-Content "$workingDir\mailreportTemp.html") -Replace "id=""reportName""> ", "id=""reportName"">$reportName" |

	%{$_ -replace "id=""applicationName""> ", "id=""applicationName"">$applicationName"} |
	%{$_ -replace "<td bgcolor = ""White""align = ""Center"" id=""status""><b> ", "<td bgcolor = ""$statusColour""align = ""Center"" id=""status""><b>$passOrFail $passpercent % "} |
	%{$_ -replace "id=""executionDate""> ", "id=""executionDate"">$executionStartTime"} |
	%{$_ -replace "id=""startTime""> ", "id=""startTime"">$executionStartTime"} |
	%{$_ -replace "id=""endTime""> ", "id=""endTime"">$executionEndTime"} |
	%{$_ -replace "id=""duration""> ", "id=""duration"">$executionDuration"} |
	%{$_ -replace "id=""environment""> ", "id=""environment"">$environment"} |
	%{$_ -replace "id=""browserName""> ", "id=""browserName"">$browserName"} |
    %{$_ -replace "id=""totalTestCases""> ", "id=""totalTestCases"">$totalcount"} |
    %{$_ -replace "id=""passTestCases""> ", "id=""passTestCases"">$passcount"} |
	%{$_ -replace "id=""failTestCases""> ", "id=""failTestCases"">$failcount"} |
	%{$_ -replace "id=""warningTestCases""> ", "id=""warningTestCases"">$warningCount"} |
	%{$_ -replace "passTestCases", "$passcount"} |
	%{$_ -replace "failTestCases", "$failcount"} |
	%{$_ -replace "warningTestCases", "$warningCount"} |
	%{$_ -replace "totalTestCases", "$totalcount"} |
	%{$_ -replace "passPercentage", "$passpercent"} |
	%{$_ -replace "id=""testCasesList""> ", "id=""testCasesList"">$demoarrayList"} |
    Set-Content "$workingDir\mailreport.html"