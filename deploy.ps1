function Automation-Testing
{ 
    Param 
    (  
        [Parameter(Mandatory=$true)] 
        [string]$WorkingDir,
        
        [Parameter(Mandatory=$true)] 
        [string]$EmailNotificationTo,
        
        [Parameter(Mandatory=$true)] 
        [string]$EmailNotificationCC,
        
        [Parameter(Mandatory=$true)] 
        [string]$ApplicationName,
        
        [Parameter(Mandatory=$true)] 
        [string]$ReleaseName,
        
        [Parameter(Mandatory=$true)] 
        [string]$Environment
        
    ) 
	

	[string[]]$To = $EmailNotificationTo.Split(',')
    [string[]]$Cc = $EmailNotificationCC.Split(',')
    
    #[string[]]$text = Get-Content -Path @($fileName)
    
    #$passPercent = Get-Item -Path  $fileName | Get-Content -Tail 1
    $hostName = $env:COMPUTERNAME
	
	$emailBody = "<pre>         *** This is an auto generated automation report email. ***</pre>"
    
    $emailBody += "<pre><br><br>Hi All,<br><br>Please find below the Automation Execution details.</pre>"
    
    $emailBody += Get-Content -Path $WorkingDir\mailreport.html
    
    
    $html = New-Object -ComObject "HTMLFile"
    $content = Get-Content -Path $WorkingDir\mailreport.html -Raw
   # $html.IHTMLDocument2_write($content)
    #$text= $html.getElementsByTagName('td')

	#foreach($tag in $text)
    #{
    #if ($tag.innerText.Contains('PASS')){
        
    #    break;
    #} else {
    #    $EmailSubject = "$ApplicationName :: $ReleaseName :: $Environment :: Automation Execution Status - Error :: " + (Get-Date).ToString()
    #}
    #}
	#$ApplicationName = $ApplicationName.ToUpper()

	$ApplicationName = (Get-Culture).TextInfo.ToTitleCase($ApplicationName)
	
	echo "aApplicationName $ApplicationName"
	
    $EmailSubject = "$ApplicationName :: $Environment :: $ReleaseName :: Automation Execution :: " + (Get-Date).ToString()
	
	$today_date = Get-Date -Format g
	#$today_date = $today_date.Substring(0,$today_date.Length-6)
	$today_date = $today_date -replace "/","_"
	$today_date = $today_date -replace ":","_"
	$today_date = $today_date -replace " ","_"
	$Source = $WorkingDir+"\ExtentReport"
	#New-Item -Path $WorkingDir+"\logs\Report" -ItemType Directory
	$Destination = $WorkingDir+"\logs\Report\"+$ApplicationName+"_"+$Environment+"_AutomationReport_$today_date.zip"

	Add-Type -AssemblyName System.IO.Compression.Filesystem
	#Copy-Item -Path "$Source\*" -Destination $Staging
	[System.IO.Compression.ZipFile]::CreateFromDirectory($Source, $Destination)
	
	$Attachments = $Destination
	
	$POSTJSON = Get-Content -Raw -Path $workingDir\"executionDetails.json" | ConvertFrom-Json
	
	$jsonAppliToolsURL = New-Object -TypeName 'System.Collections.ArrayList';
	
	$POSTJSON | Select-Object -Property appliToolsURL | ForEach-Object {
	$jsonAppliToolsURL.Add($_.appliToolsURL)
	}
	$appliToolsURL = $jsonAppliToolsURL[0]

	$emailBody += "<pre>         Applitools Result Endpoint URL: <font color=blue><a href=$appliToolsURL>eyes.applitools.com/</a> !</font></pre>"
	
	$emailBody += "<pre>         <br/><font color=Red>Note : </font> Please see the attached file for more detailed report.</i></pre>"
	
    Send-MailMessage -From 'c-surender.komire@charter.com' -to $To -cc $Cc -Subject $EmailSubject  -Attachments $Attachments -Body $emailBody  -SmtpServer "mailrelay.chartercom.com" -BodyAsHtml
    
	Remove-Item -Path "$WorkingDir\logs\Report\*" -Force
}