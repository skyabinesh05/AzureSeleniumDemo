package coeqa.managers;

import coeqa.pageobjects.*;
import coeqa.testbase.TestBase;
import org.openqa.selenium.WebDriver;
import utils.HelperClass;

public class PageObjectManager {

    private final WebDriver driver;
    private TestBase testBase;
    private HelperClass helperClass;
    private CommonMethods commonMethods;
    private LoginPage loginPage;
    private ArcadiaHomePage Arcadia_HomePage;
    private NewSalesPage_Offerta NewSales_Page_Off;
    private Arcadia_BB_Upselling_FW Arcadia_BB_UpsellingFW;

    public PageObjectManager(WebDriver driver) {

        this.driver = driver;
        getHelperClass();
    }


    public TestBase getTestBase() {
        if (testBase == null) {
            testBase = new TestBase(driver);
        }
        return testBase;
    }

    public void getHelperClass() {
        if (helperClass == null) helperClass = new HelperClass(driver);
        // return applitools
    }


    //coe
    public CommonMethods getCommonMethods() {
        if (commonMethods == null) commonMethods = new CommonMethods(driver);
        return commonMethods;
    }
    public LoginPage getLoginPage() {
        if (loginPage == null) loginPage = new LoginPage(driver);
        return loginPage;
    }

    public ArcadiaHomePage getArcadia_HomePage() {
        if (Arcadia_HomePage == null) Arcadia_HomePage = new ArcadiaHomePage(driver);
        return Arcadia_HomePage;
    }

    public NewSalesPage_Offerta getNewSalesPage() {
        if (NewSales_Page_Off == null) NewSales_Page_Off = new NewSalesPage_Offerta(driver);
        return NewSales_Page_Off;
    }

    public Arcadia_BB_Upselling_FW getArcadia_BB_UpsellingFW() {
        if (Arcadia_BB_UpsellingFW == null) Arcadia_BB_UpsellingFW = new Arcadia_BB_Upselling_FW(driver);
        return Arcadia_BB_UpsellingFW;
    }


}
