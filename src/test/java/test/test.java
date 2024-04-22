package test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.*;

public class test {

    private static RequestSpecification requestSpec;
    public static ResponseSpecification responseSpec;

    @BeforeClass
    public static void createRequestSpecification() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://jsonplaceholder.typicode.com/posts")
                .build()
                .contentType(ContentType.JSON);

        responseSpec = new ResponseSpecBuilder()
                .build();
    }

    @Test
    public void getAndValidateDataType() {
        Response response = given()
                .spec(requestSpec)

                .when()
                .get()

                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract()
                .response();

        JsonPath responseBody = response.body().jsonPath();

        utils.assertDataType(responseBody.getList("userId"), "integer");
        utils.assertDataType(responseBody.getList("id"), "integer");
        utils.assertDataType(responseBody.getList("title"), "string");
        utils.assertDataType(responseBody.getList("body"), "string");
    }

    @Test
    public void getAndValidateSchema() {
        given()
                .spec(requestSpec)

                .when()
                .get()

                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsonschema/usersResponseSchema.json"));
    }

    @Test
    public void postAndValidateData() throws JsonProcessingException {

        data postRequest = new data("recommendation", "motorcycle", 14);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(postRequest);

        given()
                .spec(requestSpec)
                .body(requestBody)

                .when()
                .post()

                .then()
                .spec(responseSpec)
                .log().body()
                .body("title", equalTo("recommendation"),
                        "body", equalTo("motorcycle"),
                        "userId", equalTo(14),
                        "id", equalTo(101));
    }
}
