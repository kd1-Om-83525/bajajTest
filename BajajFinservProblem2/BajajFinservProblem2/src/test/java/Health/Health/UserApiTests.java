package Health.Health;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserApiTests {

    @Before
    public void setup() {
        RestAssured.baseURI = "https://bfhldevapigw.healthrx.co.in/automation-campus";
    }

    @Test
    public void testValidUserCreation() {
        given()
            .header("roll-number", "240340820061")
            .contentType(ContentType.JSON)
            .body("{\n" +
                  "  \"firstName\": \"Om\",\n" +
                  "  \"lastName\": \"Dhavale\",\n" +
                  "  \"phoneNumber\": 8080652895,\n" +
                  "  \"emailId\": \"omdhavale2001@gmail.com\"\n" +
                  "}")
        .when()
            .post("/create/user")
        .then()
            .statusCode(200);  // Adjust this based on actual API response
    }

    @Test
    public void testDuplicatePhoneNumber() {
        // First user creation
        given()
            .header("roll-number", "240340820061")
            .contentType(ContentType.JSON)
            .body("{\n" +
            		"  \"firstName\": \"Om\",\n" +
                    "  \"lastName\": \"Dhavale\",\n" +
                    "  \"phoneNumber\": 8080652895,\n" +
                    "  \"emailId\": \"omdhavale2001@gmail.com\"\n" +
                    "}")
        .when()
            .post("/create/user")
        .then()
            .statusCode(200);  // Adjust this based on actual API response

        // Duplicate phone number
        given()
            .header("roll-number", "240340820061")
            .contentType(ContentType.JSON)
            .body("{\n" +
            		"  \"firstName\": \"Om\",\n" +
                    "  \"lastName\": \"Dhavale\",\n" +
                    "  \"phoneNumber\": 8080652895,\n" +
                    "  \"emailId\": \"omdhavale2001@gmail.com\"\n" +
                    "}")
        .when()
            .post("/create/user")
        .then()
            .statusCode(400)  // Expecting a bad request
            .body("message", equalTo("Phone number already in use"));
    }

    @Test
    public void testDuplicateEmailId() {
        // First user creation
        given()
            .header("roll-number", "240340820061")
            .contentType(ContentType.JSON)
            .body("{\n" +
                  "  \"firstName\": \"Alice\",\n" +
                  "  \"lastName\": \"Johnson\",\n" +
                  "  \"phoneNumber\": 1111111111,\n" +
                  "  \"emailId\": \"alice.johnson@example.com\"\n" +
                  "}")
        .when()
            .post("/create/user")
        .then()
            .statusCode(200);  // Adjust this based on actual API response

        // Duplicate email
        given()
            .header("roll-number", "240340820061")
            .contentType(ContentType.JSON)
            .body("{\n" +
                  "  \"firstName\": \"Charlie\",\n" +
                  "  \"lastName\": \"Brown\",\n" +
                  "  \"phoneNumber\": 2222222222,\n" +
                  "  \"emailId\": \"alice.johnson@example.com\"\n" +
                  "}")
        .when()
            .post("/create/user")
        .then()
            .statusCode(400)  // Expecting a bad request
            .body("message", equalTo("Email already in use"));
    }

    @Test
    public void testMissingRollNumberHeader() {
        given()
            .contentType(ContentType.JSON)
            .body("{\n" +
                  "  \"firstName\": \"David\",\n" +
                  "  \"lastName\": \"White\",\n" +
                  "  \"phoneNumber\": 3333333333,\n" +
                  "  \"emailId\": \"david.white@example.com\"\n" +
                  "}")
        .when()
            .post("/create/user")
        .then()
            .statusCode(401);  // Unauthorized
    }

    @Test
    public void testInvalidPhoneNumberFormat() {
        given()
            .header("roll-number", "240340820061")
            .contentType(ContentType.JSON)
            .body("{\n" +
                  "  \"firstName\": \"Invalid\",\n" +
                  "  \"lastName\": \"Phone\",\n" +
                  "  \"phoneNumber\": \"invalidPhone\",\n" +
                  "  \"emailId\": \"invalid.phone@example.com\"\n" +
                  "}")
        .when()
            .post("/create/user")
        .then()
            .statusCode(400);  // Bad request
    }

    @Test
    public void testInvalidEmailFormat() {
        given()
            .header("roll-number", "240340820061")
            .contentType(ContentType.JSON)
            .body("{\n" +
                  "  \"firstName\": \"Invalid\",\n" +
                  "  \"lastName\": \"Email\",\n" +
                  "  \"phoneNumber\": 4444444444,\n" +
                  "  \"emailId\": \"invalidEmailFormat\"\n" +
                  "}")
        .when()
            .post("/create/user")
        .then()
            .statusCode(400);  // Bad request
    }

    @Test
    public void testMissingFirstName() {
        given()
            .header("roll-number", "240340820061")
            .contentType(ContentType.JSON)
            .body("{\n" +
                  "  \"lastName\": \"Doe\",\n" +
                  "  \"phoneNumber\": 5555555555,\n" +
                  "  \"emailId\": \"missing.firstname@example.com\"\n" +
                  "}")
        .when()
            .post("/create/user")
        .then()
            .statusCode(400);  // Bad request
    }

    @Test
    public void testMissingLastName() {
        given()
            .header("roll-number", "240340820061")
            .contentType(ContentType.JSON)
            .body("{\n" +
                  "  \"firstName\": \"Missing\",\n" +
                  "  \"phoneNumber\": 6666666666,\n" +
                  "  \"emailId\": \"missing.lastname@example.com\"\n" +
                  "}")
        .when()
            .post("/create/user")
        .then()
            .statusCode(400);  // Bad request
    }

    @Test
    public void testMissingPhoneNumber() {
        given()
            .header("roll-number", "240340820061")
            .contentType(ContentType.JSON)
            .body("{\n" +
                  "  \"firstName\": \"Missing\",\n" +
                  "  \"lastName\": \"Phone\",\n" +
                  "  \"emailId\": \"missing.phone@example.com\"\n" +
                  "}")
        .when()
            .post("/create/user")
        .then()
            .statusCode(400);  // Bad request
    }

    @Test
    public void testMissingEmailId() {
        given()
            .header("roll-number", "240340820061")
            .contentType(ContentType.JSON)
            .body("{\n" +
                  "  \"firstName\": \"Missing\",\n" +
                  "  \"lastName\": \"Email\",\n" +
                  "  \"phoneNumber\": 7777777777\n" +
                  "}")
        .when()
            .post("/create/user")
        .then()
            .statusCode(400);  // Bad request
    }

    @Test
    public void testMissingContentTypeHeader() {
        given()
            .header("roll-number", "240340820061")
            .body("{\n" +
                  "  \"firstName\": \"NoContentType\",\n" +
                  "  \"lastName\": \"Header\",\n" +
                  "  \"phoneNumber\": 8888888888,\n" +
                  "  \"emailId\": \"nocontenttype.header@example.com\"\n" +
                  "}")
        .when()
            .post("/create/user")
        .then()
            .statusCode(415);  // Unsupported Media Type
    }

    @Test
    public void testIncorrectDataType() {
        given()
            .header("roll-number", "240340820061")
            .contentType(ContentType.JSON)
            .body("{\n" +
                  "  \"firstName\": 12345,\n" +
                  "  \"lastName\": true,\n" +
                  "  \"phoneNumber\": \"abc\",\n" +
                  "  \"emailId\": 67890\n" +
                  "}")
        .when()
            .post("/create/user")
        .then()
            .statusCode(400);  // Bad request
    }
}

