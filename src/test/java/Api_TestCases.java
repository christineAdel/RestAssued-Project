
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.BaseTest;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Api_TestCases extends BaseTest {

	private String accessToken;
	private String orderId; 
	private int bookId=1;
	private String customerName="John";
	private int quantity=1;
	private String newCustomerName="Updated John";
	
	@BeforeClass
	public void setup() {
		 // Initialize logger
        logger = extent.startTest("API Test Cases");
        
		// Set the base URI of the API
		RestAssured.baseURI = "https://simple-books-api.glitch.me";
		
		// Log setup
        logger.log(LogStatus.INFO, "Base URI set to: " + RestAssured.baseURI);
	}
	

	@Test(enabled = true, description = "Getting the access token by posting the API client")
	public void case01_apiAuthentication() {
		logger.setDescription("Getting the access token using post the API client");
		
		// Generate a unique email address using a UUID
		String uniqueEmail = "test" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
		logger.log(LogStatus.INFO,"The Generated unique email address using a UUID is: " + uniqueEmail);
		
		// Create JSON payload
		logger.log(LogStatus.INFO,"Create JSON payload to get the access token");
		JSONObject requestBody = new JSONObject();
		requestBody.put("clientName", "Test");
		requestBody.put("clientEmail", uniqueEmail);
		
		// Send POST request to get the token
		logger.log(LogStatus.INFO,"Send POST request to get the access token");
		Response response = RestAssured.given().header("Content-Type", "application/json").body(requestBody.toString())
				.post("/api-clients/");
		
		// Assert that the response status code is 201
		logger.log(LogStatus.INFO,"Assert that the response status code is 201");
		Assert.assertEquals(response.getStatusCode(), 201, "Expected status code is 201");
		
		// Extract and validate the token
		logger.log(LogStatus.INFO,"Extract and validate the access token");
		accessToken = response.jsonPath().getString("accessToken");
		Assert.assertNotNull(accessToken, "Token should not be null");
		
		// Print the unique email and token for verification
		System.out.println("The Generated Email is: " + uniqueEmail);
		System.out.println("The Generated Token is: " + accessToken);
		logger.log(LogStatus.INFO,"The unique Generated Token is: "+accessToken);
	}
	

	@Test(enabled = true, description = "Submit an order using post the orders")
	public void case02_testSubmitOrder() {
		logger.setDescription("Submit an order by posting the orders");
		
		  // Payload for creating an order
		logger.log(LogStatus.INFO,"Create JSON Payload for creating an order");
        JSONObject orderPayload = new JSONObject();
        orderPayload.put("bookId", bookId);
        orderPayload.put("customerName", customerName);
        
        // Send POST request to create an order
        logger.log(LogStatus.INFO," Send POST request to create an order");
        Response createOrderResponse = RestAssured
                .given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .body(orderPayload.toString())
                .post("/orders/");
       
        // Assert status code is 201
        logger.log(LogStatus.INFO," Assert that the status code is 201");
        Assert.assertEquals(createOrderResponse.getStatusCode(), 201, "Expected status code is 201");
       
        // Extract the response body and validate its contents
        logger.log(LogStatus.INFO," Extract the response body and validate its contents");
        boolean created = createOrderResponse.jsonPath().getBoolean("created");
        orderId = createOrderResponse.jsonPath().getString("orderId");
        
        // Assert that the Order creation should return 'created' as true
        logger.log(LogStatus.INFO," Assert that the Order creation should return 'created' as true");
        Assert.assertTrue(created, "Order creation should return 'created' as true");
     
        // Assert that the Order id should not be null
        logger.log(LogStatus.INFO," Assert that the Order id should not be null");
        Assert.assertNotNull(orderId, "Order ID should not be null");
       
        //Print The order Details
        System.out.println("Order Created: " + orderId);
        String ResponseBody = createOrderResponse.getBody().asString();
        System.out.println("Response Body: " + ResponseBody);
        logger.log(LogStatus.INFO," The order Details is:  " + ResponseBody);
	}

	@Test(enabled = true, description = "Checking the submited orders using get the orders")
	public void case03_testRetrieveOrders() {
		logger.setDescription("Checking the submited orders using get the orders");
		
		  // Send GET request to retrieve orders
		logger.log(LogStatus.INFO," Send GET request to retrieve orders");
        Response retrieveOrdersResponse = RestAssured
                .given()
                .header("Authorization", "Bearer " + accessToken)
                .get("/orders");
        
        // Assert that the status code is 200
        logger.log(LogStatus.INFO,"Assert that the response status code is 200");
        Assert.assertEquals(retrieveOrdersResponse.getStatusCode(), 200, "Expected status code is 200");
        
        // Extract response body as JSON
        logger.log(LogStatus.INFO," Extract response body as JSON");
        List<Map<String, Object>> orders = retrieveOrdersResponse.jsonPath().getList("");
        
        // Assert that the orders list is not empty
        logger.log(LogStatus.INFO," Assert that the orders list is not empty");
        Assert.assertTrue(orders.size() > 0, "Orders list should not be empty");
        
     // Assert that the newly created order is present in the list
        logger.log(LogStatus.INFO," Assert that the newly created order is present in the list");
        boolean isOrderFound = false;
        for (Map<String, Object> order : orders) {
            if (order.get("id").equals(orderId)) {
                isOrderFound = true;
                Assert.assertEquals(order.get("bookId"), bookId, "Book ID should match");
                Assert.assertEquals(order.get("customerName"), customerName , "Customer Name should match");
                Assert.assertEquals(order.get("quantity"), quantity , "quantity should match");
                break;
            }
        }
        
        //Assert that The created order should be found in the retrieved orders list
        logger.log(LogStatus.INFO," Assert that The created order should be found in the retrieved orders list");
        Assert.assertTrue(isOrderFound, "The created order should be found in the retrieved orders list");
        
      //Print The order Details
        String ResponseBody = retrieveOrdersResponse.getBody().asString();
        System.out.println("Orders: " + ResponseBody);
        logger.log(LogStatus.INFO," The order Details is: " + ResponseBody);
    }
	
	@Test(enabled = true, description = "Update an existing order using patch orders")
	public void case04_testUpdateOrder() {
		logger.setDescription("Update an existing order using patch orders");
		
		   // Create the payload to update customerName
		logger.log(LogStatus.INFO," Create the payload to update the customer name ");
        JSONObject updatePayload = new JSONObject();
        updatePayload.put("customerName",newCustomerName);
        
        // Send PATCH request to update the order
        logger.log(LogStatus.INFO," Send PATCH request to update the order ");
        Response updateResponse = RestAssured
                .given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .body(updatePayload.toString())
                .patch("/orders/" + orderId);
        
        // Assert the response status code is 204
        logger.log(LogStatus.INFO,"Assert that the response status code is 204");
        Assert.assertEquals(updateResponse.getStatusCode(), 204, "Expected status code is 204 for order update");
	}
	
	
	@Test(enabled = true, description = "Making sure that the existing order is updating correctly using get the orders")
	public void case05_testRetrieveUpdatingOrders() {
		logger.setDescription("Making sure that the existing order is updating correctly using get the orders");
		
		  // Send GET request to retrieve orders
		logger.log(LogStatus.INFO," Send GET request to retrieve orders ");
        Response retrieveOrdersResponse = RestAssured
                .given()
                .header("Authorization", "Bearer " + accessToken)
                .get("/orders");

        // Assert status code is 200
        logger.log(LogStatus.INFO,"Assert that the response status code is 200 ");
        Assert.assertEquals(retrieveOrdersResponse.getStatusCode(), 200, "Expected status code is 200");

        // Extract response body as JSON
        logger.log(LogStatus.INFO," Extract response body as JSON ");
        List<Map<String, Object>> orders = retrieveOrdersResponse.jsonPath().getList("");

        // Assert that the orders list is not empty
        logger.log(LogStatus.INFO," Assert that the orders list is not empty ");
        Assert.assertTrue(orders.size() > 0, "Orders list should not be empty");

        // Assert that the newly created order is present in the list
        logger.log(LogStatus.INFO," Assert that the newly created order is present in the list ");
        boolean isOrderFound = false;
        for (Map<String, Object> order : orders) {
            if (order.get("id").equals(orderId)) {
                isOrderFound = true;
                Assert.assertEquals(order.get("bookId"), bookId, "Book ID should match");
                Assert.assertEquals(order.get("customerName"), newCustomerName , "Customer Name should match");
                Assert.assertEquals(order.get("quantity"), quantity , "quantity should match");
                break;
            }
        }
        
        // Assert that the created order should be found in the retrieved orders list
        logger.log(LogStatus.INFO,"  Assert that the created order should be found in the retrieved orders list ");
        Assert.assertTrue(isOrderFound, "The created order should be found in the retrieved orders list");
        
        //print the order details
        String orderDetails = retrieveOrdersResponse.getBody().asString();
        System.out.println("Orders: " + orderDetails);
        logger.log(LogStatus.INFO," The order details is: "+ orderDetails);

    }
	
	
	@Test(enabled = true, description = "Delete an existing order using delete the orders")
	public void case06_testDeleteOrder() {
		logger.setDescription("Delete an existing order using delete the orders");
		
		  // Send DELETE request to delete the order
		logger.log(LogStatus.INFO," Send DELETE request to delete the order ");
        Response deleteResponse = RestAssured
                .given()
                .header("Authorization", "Bearer " + accessToken)
                .delete("/orders/" + orderId);

        // Assert the response status code
        logger.log(LogStatus.INFO,"Assert that the response status code is 204 ");
        Assert.assertEquals(deleteResponse.getStatusCode(), 204, "Expected status code is 204 for successful deletion");

        // Validate that the response body is empty
        logger.log(LogStatus.INFO,"Assert that the response body is empty ");
        Assert.assertTrue(deleteResponse.getBody().asString().isEmpty(), "Response body should be empty");

        // Verify the order no longer exists
        logger.log(LogStatus.INFO,"Assert that the order no longer exists ");
        Response getResponse = RestAssured
                .given()
                .header("Authorization", "Bearer " + accessToken)
                .get("/orders");
      
        // Assert the response status code
        logger.log(LogStatus.INFO,"Assert that the response status code is 200 ");
        Assert.assertEquals(getResponse.getStatusCode(), 200, "Expected status code is 200 for retrieving orders");

        // Assert that the deleted order ID is no longer present in the orders list
        logger.log(LogStatus.INFO,"Assert that the deleted order ID is no longer present in the orders list ");
        String ordersList = getResponse.getBody().asString();
        System.out.println("Orders List: " + ordersList);
        Assert.assertFalse(ordersList.contains(orderId), "Deleted order should not be present in the orders list");
    }
	
}
