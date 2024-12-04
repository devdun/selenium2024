package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import utils.CommonOperations;
import utils.ConfigFileReader;
import utils.ExcelReader;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Base {
    public WebDriver driver;
    public ExcelReader excel;
    private static final String Test_Output_Folder = "./test-output/MyReports/";
    private static final String SPECIAL_CAPTURE_FOLDER = "/SPECIAL_CAPTURE_/";
    public CommonOperations commonOperations = new CommonOperations(driver);
    public String currentSystemUrl = null;
    private Date date = Calendar.getInstance().getTime();

    @BeforeTest
    @Parameters({"browser", "URL"})
    public void setupBrowserAndURL(String browser, String URL) throws Exception {
        WebDriverManager driverManager;
        WebDriver driverInstance;
        switch (browser.toLowerCase()) {
            case "firefox":
                driverManager = WebDriverManager.firefoxdriver();
                if (browser.equalsIgnoreCase("HLFirefox")) {
                    FirefoxBinary firefoxBinary = new FirefoxBinary();
                    firefoxBinary.addCommandLineOptions("--headless");
                    FirefoxOptions options = new FirefoxOptions();
                    options.setBinary(firefoxBinary);
                    driverInstance = new FirefoxDriver(options);
                } else {
                    driverManager.setup();
                    driverInstance = new FirefoxDriver();
                }
                break;
            case "chrome":
                driverManager = WebDriverManager.chromedriver();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("window-size=1400,2100");
                if (browser.equalsIgnoreCase("HLChrome")) {
                    chromeOptions.addArguments("--headless");
                    chromeOptions.addArguments("disable-gpu");
                }
                driverManager.setup();
                driverInstance = new ChromeDriver(chromeOptions);
                break;
            default:
                throw new Exception("Invalid browser specified: " + browser);
        }

        driver = driverInstance;
        driver.manage().window().maximize();
        this.urlSetup(URL);
//        acceptCookiesPopup();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        takeSnapShot(driver, Test_Output_Folder + SPECIAL_CAPTURE_FOLDER + methodName + System.currentTimeMillis() + ".png");
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(100, TimeUnit.MILLISECONDS);
    }

    public void urlSetup(String URL) throws Exception {
        ConfigFileReader configFileReader = new ConfigFileReader();
        String targetUrl;
        switch (URL.toLowerCase()) {
            case "qa":
                targetUrl = configFileReader.applicationUrl_QA();
                break;
            case "live":
                targetUrl = configFileReader.applicationUrl_Live();
                break;
            case "demo":
                targetUrl = configFileReader.applicationUrl_Demo();
                break;
            default:
                targetUrl = URL;
                break;
        }

        if (targetUrl == null) {
            throw new Exception("URL is not correct in xml: " + URL);
        }

        driver.get(targetUrl);
        commonOperations.waitUntilPageLoaded(driver);
    }

    public void acceptCookiesPopup() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("consent_prompt_submit")));
            if (popup.isDisplayed()) {
                popup.click();
                System.out.println("Accepted cookies popup.");
            }
        } catch (Exception e) {
            System.out.println("Cookies popup not found or could not be accepted.");
        }
    }

    @AfterMethod
    public void screenShot(ITestResult result) {
        DateFormat formatter = new SimpleDateFormat("dd_MM_yyyy");
        String today = formatter.format(date);
        String outputImageFolder = Test_Output_Folder + today + "/ScreenCaptures/";
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                TakesScreenshot screenshot = (TakesScreenshot) driver;
                File src = screenshot.getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(src, new File(outputImageFolder + result.getName() + "_" + System.currentTimeMillis() + ".png"));
                System.out.println("Successfully captured a screenshot");
            } catch (Exception e) {
                System.out.println("Exception while taking screenshot " + e.getMessage());
            }
        }
    }

    @AfterClass
    public void endReport() {
        driver.quit();
    }

    public static void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception {
        TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(fileWithPath);
        FileUtils.copyFile(SrcFile, DestFile);
    }

    public String[][] getData(String sheetname, String excelname) {
        String path = System.getProperty("user.dir") + "/src/main/resources/data/" + excelname;
        excel = new ExcelReader(path);
        return excel.getDataFromSheet(sheetname, excelname);
    }
}
