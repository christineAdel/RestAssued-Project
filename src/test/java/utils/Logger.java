package utils;

import io.restassured.response.Response;
import org.json.JSONObject;

public class Logger {

    public static void logRequest(String method, String endpoint, JSONObject payload) {
        System.out.println("\n--- Request Details ---");
        System.out.println("Method: " + method);
        System.out.println("Endpoint: " + endpoint);
        if (payload != null) {
            System.out.println("Payload: " + payload.toString(2)); // Pretty print JSON
        }
    }

    public static void logResponse(Response response) {
        System.out.println("\n--- Response Details ---");
        if (response != null) {
            System.out.println("Status Code: " + response.getStatusCode());
            System.out.println("Response Body: ");
            response.prettyPrint();
        } else {
            System.out.println("Response is null (possibly due to an error)");
        }
    }

    public static void logError(String message, Exception e) {
        System.err.println("\n--- Error ---");
        System.err.println(message);
        e.printStackTrace();
    }
}
