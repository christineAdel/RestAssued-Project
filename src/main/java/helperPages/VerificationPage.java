package helperPages;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class VerificationPage {
	protected helperPage helper;
	ExtentTest logger;

	public VerificationPage(ExtentTest logger) {
		this.logger = logger;
		helper = new helperPage(logger);
	}

	public boolean checkStatusCode(int statusCode, int expectedStatusCode) {
		boolean status = false;
		if (statusCode == expectedStatusCode) {
			logger.log(LogStatus.PASS, "the Status code is matched  : " + statusCode);
			status = true;
		} else {
			logger.log(LogStatus.FAIL, "Validation Failed: Expected status code is " + expectedStatusCode
					+ " but the actual is :  " + statusCode);
		}
		return status;
	}

	public boolean checkUserName(String nameRes, String userName) {
		boolean status = false;
		if (nameRes == null || nameRes.isEmpty()) {
			logger.log(LogStatus.FAIL, "Validation Failed: 'name' is null or empty.");
			// sa.assertTrue(false, "Name should not be null or empty");
		} else {
			if (nameRes.contains(userName)) {
				logger.log(LogStatus.PASS, "the expected name : " + userName + " matched the actual : " + nameRes);
				// sa.assertTrue(true, "Name should matched");
				status = true;
			} else {
				logger.log(LogStatus.FAIL, "the expected name : " + userName + " not matched the actual : " + nameRes);
				// sa.assertTrue(false, "Name should matched");
			}
		}

		return status;
	}

	public boolean checkUserAge(String ageRes, String age) {
		boolean status = false;
		if (ageRes == null || ageRes.isEmpty()) {
			logger.log(LogStatus.FAIL, "Validation Failed: 'age' is null or empty.");
			// sa.assertTrue(false, "Age should not be null or empty");
		} else {
			if (ageRes.contains(age)) {
				logger.log(LogStatus.PASS, "the expected age matched  : " + age + " the actual : " + ageRes);
				// sa.assertTrue(true, "age should matched");
				status = true;
			} else {
				logger.log(LogStatus.FAIL, "the expected age : " + age + " not matched the actual : " + ageRes);
				// sa.assertTrue(false, "age should matched");
			}
		}
		return status;

	}

	public boolean checkUserJob(String jobRes, String job) {
		boolean status = false;

		if (jobRes == null || jobRes.isEmpty()) {
			logger.log(LogStatus.FAIL, "Validation Failed: 'job' is null or empty.");
		} else {
			if (jobRes.contains(job)) {
				logger.log(LogStatus.PASS, "the expected job matched  : " + job + " the actual : " + jobRes);
				status = true;
			} else {
				logger.log(LogStatus.FAIL, "the expected job : " + job + " not matched the actual : " + jobRes);
			}
		}
		return status;
	}

	public boolean checkUserJob(String idRes) {
		boolean status = false;

		if (idRes == null || idRes.isEmpty()) {
			logger.log(LogStatus.FAIL, "Validation Failed: 'id' is null or empty.");
			// sa.assertTrue(false, "id should not be null");
		} else {
			logger.log(LogStatus.PASS, "the id is : " + idRes);
			status = true;
		}
		return status;
	}

	public boolean checkDate(String date ,String type) {
		boolean status = false;
		if (type == "createdAt") {
		// Validate 'createdAt' field
		if (date == null || date.isEmpty()) {
			logger.log(LogStatus.FAIL, "Validation Failed: 'createdAt' is null or empty.");
		} else {
			if (helper.isValidDate(date)) {
				logger.log(LogStatus.PASS, "the createdAt is in a vaild format");
				logger.log(LogStatus.PASS, "createdAt should not be null or empty");
				status = true;
			} else {
				logger.log(LogStatus.FAIL, "Validation Failed: 'createdAt' is not in valid format.");
			}
		}
	} if (type == "updatedAt")
		if (date == null || date.isEmpty()) {
			logger.log(LogStatus.FAIL, "Validation Failed: 'createdAt' is null or empty.");
		} else {
			if (helper.isValidDate(date)) {
				logger.log(LogStatus.PASS, "the updatedAt is in a vaild format : " + date);
				status = true;
				logger.log(LogStatus.PASS, "updatedAt should not be null or empty : " + date);
			} else {
				logger.log(LogStatus.FAIL, "Validation Failed: 'updatedAt' is not in valid format: " + date);
			}
		}
		return status;
	}
}
