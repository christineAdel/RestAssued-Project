package utils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseTest {

	public static ExtentReports extent;
	public static ExtentTest logger;

	@BeforeSuite(enabled = true)
	public void beforeSuit() throws IOException, InterruptedException {
		LocalDateTime currentDateTime = LocalDateTime.now();
		extent = new ExtentReports("Reports/" + "Extent-Report-" + currentDateTime + ".html", true);
		extent.addSystemInfo("Environment", "Api Testing Using the Rest assured");
		extent.addSystemInfo("Author", "Automation Team");
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

}
