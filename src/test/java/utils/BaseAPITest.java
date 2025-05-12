package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Properties;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import helperPages.VerificationPage;
import helperPages.helperPage;

public class BaseAPITest {

	public static ExtentReports extent;
	public static ExtentTest logger;
	protected FileInputStream inputStream;
	protected Properties prop;
	protected String baseURLVal;
	protected String createUserVal;
	protected String xApiKeyVal;
	protected SoftAssert sa;
	protected VerificationPage verificationPage;
	protected helperPage helper;

	@BeforeSuite(enabled = true)
	public void beforeSuit() throws IOException, InterruptedException {
		LocalDateTime currentDateTime = LocalDateTime.now();
		extent = new ExtentReports("Reports/" + "Extent-Report-" + currentDateTime + ".html", true);
		extent.addSystemInfo("Environment", "Api Testing Using the Rest assured");
		extent.addSystemInfo("Author", "Automation Team");
		configData();
	}
	
	@AfterSuite
	public void afterSuite() {
		 if (extent != null) {
	            extent.flush();
	        }
	}
	
	@BeforeMethod
	public void beforeMethod(Method method) throws IOException, InterruptedException {
		logger = extent.startTest(method.getName());
	    verificationPage = new VerificationPage(logger);
		helper = new helperPage(logger);
		sa = new SoftAssert();
	}
	
	@AfterMethod
	public void afterMethod(Method method, ITestResult result) {
		if (result.getStatus() == ITestResult.SUCCESS) {
			logger.log(LogStatus.PASS, "Test is Passed");

		} else if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(LogStatus.FAIL, "Test is failed");
			logger.log(LogStatus.FAIL, result.getThrowable());
		} else {
			logger.log(LogStatus.SKIP, "Test is Skipped");
		}
	}
	
	public void configData() throws IOException {
		File propFile = new File("src/main/resources/config/configFile.properties");
		inputStream = new FileInputStream(propFile);
		prop = new Properties();
		prop.load(inputStream);
		baseURLVal = prop.getProperty("baseURL");
		createUserVal = prop.getProperty("createUser");
		xApiKeyVal = prop.getProperty("xApiKey");
	}

}
