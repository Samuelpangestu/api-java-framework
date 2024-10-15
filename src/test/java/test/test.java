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


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.*;
import utils.data;

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
    public void getResource() {
        given()
                .spec(requestSpec)

                .when()
                .get("/1")

                .then()
                .spec(responseSpec)
                .statusCode(200)
                .extract()
                .response();
    }

    @Test
    public void getLists() {
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
    public void postCreateResource() {

        String requestBody = "{\n" +
                "  \"title\": \"foo\",\n" +
                "  \"body\": \"bar\",\n" +
                "  \"userId\": 1\n" +
                "}";

        given()
                .header("Content-type", "application/json; charset=UTF-8")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)  // Verifying the response status code is 201 Created
                .body("title", equalTo("foo"))
                .body("body", equalTo("bar"))
                .body("userId", equalTo(1))
                .extract().response();
    }

    @Test
    public void putCreateResource() {

        String requestBody = "{\n" +
                "  \"title\": \"foo\",\n" +
                "  \"body\": \"bar\",\n" +
                "  \"userId\": 1\n" +
                "}";

        given()
                .header("Content-type", "application/json; charset=UTF-8")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)  // Verifying the response status code is 201 Created
                .body("title", equalTo("foo"))
                .body("body", equalTo("bar"))
                .body("userId", equalTo(1))
                .extract().response();
    }

    @Test
    public void updateResource() {
        
        String requestBody = "{\n" +
                "  \"id\": 1,\n" +
                "  \"title\": \"foo\",\n" +
                "  \"body\": \"bar\",\n" +
                "  \"userId\": 1\n" +
                "}";

        // Send the PUT request
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("title", equalTo("foo"))
                .body("body", equalTo("bar"))
                .body("userId", equalTo(1))
                .extract().response();
    }

    @Test
    public void updatePost() {

        String requestBody = "{\n" +
                "  \"id\": 1,\n" +
                "  \"title\": \"foo\",\n" +
                "  \"body\": \"bar\",\n" +
                "  \"userId\": 1\n" +
                "}";

        // Send the PUT request
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("title", equalTo("foo"))
                .body("body", equalTo("bar"))
                .body("userId", equalTo(1))
                .extract().response();
    }

    @Test
    public void deletePost() {

        // Send the DELETE request
        given()
                .when()
                .delete("/posts/1")
                .then()
                .statusCode(200);
    }

    @Test
    public void getPostsByUser() {

        // Send the DELETE request
        given()
                .when()
                .get("/posts?userId=1")
                .then()
                .statusCode(200)
                .extract().response();
    }

    @Test
    public void getCommentsByPost() {
        given()
                .when()
                .get("/posts/1/comments")
                .then()
                .statusCode(200)
                .extract().response();
    }
}
