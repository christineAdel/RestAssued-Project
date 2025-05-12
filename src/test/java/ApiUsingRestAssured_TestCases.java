
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.ApiUtils;
import utils.BaseAPITest;
import utils.Logger;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.LogStatus;


public class ApiUsingRestAssured_TestCases extends BaseAPITest {

	private String nameRes;
	private String ageRes;
	private String jobRes;
	private String idRes;
	private String userName;
	private String age;
	private String job;
	private String createdAtRes;
	Response createUserResponse;
	Response getUserResponse;
	private String updatedAtRes;
	Response response;
	Response updateUserResponse;
	///////////////////////////////////
	private String updateName = "updated name";
	private String updateJob = "updated job";
	private String updateAge = "18";
	//////////////////////////////////

	@BeforeClass
	public void setup() {
		// Initialize logger
		logger = extent.startTest("API Test Cases");

		// Set the base URI of the API
		RestAssured.baseURI = baseURLVal;

		// Log setup
		logger.log(LogStatus.INFO, "Base URI set to: " + RestAssured.baseURI);
	}

	@Test(enabled = true, description = "Create a User by posting the users API ")
	public void case01_createUser() {
		logger.setDescription("Create a User by posting the users API ");

		// Generate Random user name , age and job
		userName = helper.generateRandomName();
		age = helper.generateRandomAge(20, 60); // Age between 20 and 60
		job = helper.generateRandomJob();
		logger.log(LogStatus.INFO, "The user name is: " + userName);
		logger.log(LogStatus.INFO, "The user age is: " + age);
		logger.log(LogStatus.INFO, "The user job is: " + job);

		// Send POST request to create a user
		createUserResponse = sendReqRecieveResponse("createUser");

		// Assert that the response status code is 201
		int statusCode = createUserResponse.getStatusCode();
		sa.assertTrue(verificationPage.checkStatusCode(statusCode, 201), "Expected status code is 201");

		// Extract and validate the user name
		logger.log(LogStatus.INFO, "Extract and validate the user name");
		nameRes = createUserResponse.jsonPath().getString("name");
		sa.assertTrue(verificationPage.checkUserName(nameRes, userName),
				"Name should not be null or empty and matched the expected");

		// Extract and validate the user age
		logger.log(LogStatus.INFO, "Extract and validate the user age");
		ageRes = createUserResponse.jsonPath().getString("age");
		sa.assertTrue(verificationPage.checkUserAge(ageRes, age),
				"age should not be null or empty and matched the expected");

		// Extract and validate the user job
		logger.log(LogStatus.INFO, "Extract and validate the user job");
		jobRes = createUserResponse.jsonPath().getString("job");
		sa.assertTrue(verificationPage.checkUserJob(jobRes, job),
				"job should not be null or empty and matched the expected");

		// Extract and validate the user id
		logger.log(LogStatus.INFO, "Extract and validate the user id");
		idRes = createUserResponse.jsonPath().getString("id");
		sa.assertTrue(verificationPage.checkUserJob(idRes), "id should not be null or empty ");

		// Extract and validate the created date
		logger.log(LogStatus.INFO, "Extract and validate the createdAt date");
		createdAtRes = createUserResponse.jsonPath().getString("createdAt");
		sa.assertTrue(verificationPage.checkDate(createdAtRes , "createdAt"),
				"createdAt should not be null or empty or with invaild format ");

		// Print the response info
		System.out.println("The actual name  is: " + nameRes + " The expected is : " + userName);
		System.out.println("The actual age is: " + ageRes + " The expected is : " + age);
		System.out.println("The actual job is: " + jobRes + " The expected is : " + job);
		System.out.println("The actual id is: " + idRes);
		sa.assertAll();
	}

	@Test(enabled = true, description = "Retrive a user info using Get method")
	public void case02_retriveUser() {
		// Send Get request to retrive a user
		logger.log(LogStatus.INFO, "Send Get request to retrive a user");
		retriveUser("retriveUser", userName, age, job, 200);
	}

	@Test(enabled = true, description = "Update a user info using put method")
	public void case03_updateUserInfo() {
		logger.setDescription("Update a user info using put method");

		// Send PUT request to update a user info
		updateUserResponse = sendReqRecieveResponse("update");
		
		// Assert that the response status code is 200
		int statusCode = getUserResponse.getStatusCode();
		sa.assertTrue(verificationPage.checkStatusCode(statusCode, 200), "Expected status code is 200");


		// Extract and validate the user name
		logger.log(LogStatus.INFO, "Extract and validate the user name");
		nameRes = getUserResponse.jsonPath().getString("name");
		sa.assertTrue(verificationPage.checkUserName(nameRes, updateName),
				"Name should not be null or empty and matched the expected");

		// Extract and validate the user age
		logger.log(LogStatus.INFO, "Extract and validate the user age");
		ageRes = getUserResponse.jsonPath().getString("age");
		sa.assertTrue(verificationPage.checkUserAge(ageRes, updateAge),
				"age should not be null or empty and matched the expected");

		// Extract and validate the user job
		logger.log(LogStatus.INFO, "Extract and validate the user job");
		jobRes = getUserResponse.jsonPath().getString("job");
		// sa.assertNotNull(jobRes, "job should not be null");
		sa.assertTrue(verificationPage.checkUserJob(jobRes, updateJob),
				"job should not be null or empty and matched the expected");

		// Extract and validate the updated date
		logger.log(LogStatus.INFO, "Extract and validate the updatedAt date");
		updatedAtRes = getUserResponse.jsonPath().getString("updatedAt");
		// Validate 'updatedAt' field
		sa.assertTrue(verificationPage.checkDate(updatedAtRes ,"updatedAt") ,"createdAt should not be null or empty or with invaild format");
		
		//make sure that the user info is updated correctly by retrieving the user info
		retriveUser("retriveUser", updateName, updateAge, updateJob, 200);

		// Print the response info
		System.out.println("The actual name  is: " + nameRes + " The expected is : " + updateName);
		System.out.println("The actual age is: " + ageRes + " The expected is : " + updateAge);
		System.out.println("The actual job is: " + jobRes + " The expected is : " + updateJob);
		System.out.println("The actual id is: " + idRes);
		sa.assertAll();

	}

	public void retriveUser(String type, String userName, String age, String job, int statusCodeExpected) {
		// Send Get request to retrieve a user data and receive response
		getUserResponse = sendReqRecieveResponse("retriveUser");
		
		// Assert that the response status code
		int statusCode = getUserResponse.getStatusCode();
		sa.assertTrue(verificationPage.checkStatusCode(statusCode, statusCodeExpected),
				"Expected status code is " + statusCodeExpected);

		// Extract and validate the user name
		logger.log(LogStatus.INFO, "Extract and validate the user name");
		nameRes = createUserResponse.jsonPath().getString("name");
		sa.assertTrue(verificationPage.checkUserName(nameRes, userName),
				"Name should not be null or empty and matched the expected");

		// Extract and validate the user age
		logger.log(LogStatus.INFO, "Extract and validate the user age");
		ageRes = getUserResponse.jsonPath().getString("age");
		sa.assertTrue(verificationPage.checkUserAge(ageRes, age),
				"age should not be null or empty and matched the expected");
		
		// Extract and validate the user job
		logger.log(LogStatus.INFO, "Extract and validate the user job");
		jobRes = getUserResponse.jsonPath().getString("job");
		// sa.assertNotNull(jobRes, "job should not be null");
		sa.assertTrue(verificationPage.checkUserJob(jobRes, job),
				"job should not be null or empty and matched the expected");
		
		// Extract and validate the user id
		logger.log(LogStatus.INFO, "Extract and validate the user id");
		idRes = getUserResponse.jsonPath().getString("id");
		sa.assertTrue(verificationPage.checkUserJob(idRes), "id should not be null or empty ");

		// Print the response info
		System.out.println("The actual name  is: " + nameRes + " The expected is : " + userName);
		System.out.println("The actual age is: " + ageRes + " The expected is : " + age);
		System.out.println("The actual job is: " + jobRes + " The expected is : " + job);
		System.out.println("The actual id is: " + idRes);
		sa.assertAll();

	}

	public Response sendReqRecieveResponse(String type) {

		if (type == "retriveUser") {
			logger.log(LogStatus.INFO, "Retrive a user info using Get method then getting the response");
			response = ApiUtils.sendRequestWithHandling(RestAssured.given().header("x-api-key", xApiKeyVal), "GET",
					createUserVal + "/" + idRes);

			if (getUserResponse == null) {
				Assert.fail("Failed to receive the user data");
			}
			Logger.logResponse(response);
		}
		if (type == "createUser") {
			// Create JSON payload
			logger.log(LogStatus.INFO, "Create JSON payload to create a user");
			JSONObject requestBody = new JSONObject();
			requestBody.put("name", userName);
			requestBody.put("job", job);
			requestBody.put("age", age);

			// Send POST request to create a user
			logger.log(LogStatus.INFO, "Send POST request to create a user");
			response = ApiUtils.sendRequestWithHandling(RestAssured.given().header("x-api-key", xApiKeyVal)
					.contentType("application/json").body(requestBody.toString()), "POST", createUserVal);
			if (response == null) {
				Assert.fail("Failed to create a user");
			}
			Logger.logResponse(createUserResponse);
		} if (type == "update") {
			// Create JSON payload
			logger.log(LogStatus.INFO, "Create JSON payload to update a user info");
			JSONObject requestBody = new JSONObject();
			requestBody.put("name", updateName);
			requestBody.put("job", updateJob);
			requestBody.put("age", updateAge);

			// Send Put request to update a user info
			logger.log(LogStatus.INFO, "Send put request to update a user info");
			getUserResponse = ApiUtils.sendRequestWithHandling(RestAssured.given().header("x-api-key", xApiKeyVal)
					.contentType("application/json").body(requestBody.toString()), "PUT", createUserVal + "/" + idRes);

			if (getUserResponse == null) {
				Assert.fail("Failed to receive the user data");
			}
			Logger.logResponse(getUserResponse);

		}

		return response;
	}

}
