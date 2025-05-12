package utils;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiUtils {

    public static Response sendRequestWithHandling(RequestSpecification requestSpec, String method, String endpoint) {
        Response response = null;
        try {
            switch (method.toUpperCase()) {
                case "GET":
                    response = requestSpec.get(endpoint);
                    break;
                case "POST":
                    response = requestSpec.post(endpoint);
                    break;
                case "PUT":
                    response = requestSpec.put(endpoint);
                    break;
                case "DELETE":
                    response = requestSpec.delete(endpoint);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid HTTP method: " + method);
            }
        } catch (Exception e) {
            System.err.println("Error occurred while sending request to " + endpoint);
            e.printStackTrace();
        }
        return response;
    }
}
