package utils;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;
import net.masterthought.cucumber.json.support.Status;
import net.masterthought.cucumber.presentation.PresentationMode;
import net.masterthought.cucumber.sorting.SortingMethod;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenerateReport {
    public static void GenerateJVMReport(){
        File reportOutputDirectory=new File("JVMreports");
        List<String> jsonFiles=new ArrayList<>();
        jsonFiles.add("target/cucumber-reports/Cucumber.json");
        Configuration configuration=new Configuration(reportOutputDirectory,"Resi-Customer facing");
//optional configuration
        configuration.setNotFailingStatuses(Collections.singleton(Status.SKIPPED));
        //configuration.setBuildNumber("011");
        configuration.addClassifications("Environment","PROD");
        configuration.addClassifications("Browser","Chrome");
        configuration.setSortingMethod(SortingMethod.NATURAL);
        configuration.addPresentationModes(PresentationMode.EXPAND_ALL_STEPS);
        configuration.setTrendsStatsFile(new File("target/test-classes/demo-trends.json"));
        ReportBuilder reportBuilder=new ReportBuilder(jsonFiles, configuration);
        Reportable Result=reportBuilder.generateReports();
    }




}
