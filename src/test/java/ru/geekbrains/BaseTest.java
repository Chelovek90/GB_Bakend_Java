package ru.geekbrains;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class BaseTest {
    protected static Properties properties = new Properties();
    protected static String token;
    protected static String userName;
    protected static Map<String, String> headers = new HashMap<>();

    public static String imageHashCode;
    public String deleteImageHashCode;

    protected ValidatableResponse response;
    protected RequestSpecification imageSpecification;
    protected RequestSpecification albumSpecification;
    protected Map<String, String> dataBody = new HashMap<>();

    @BeforeAll
    static void beforeAll() {
        loadProperties();
        token = properties.getProperty("token");
        RestAssured.baseURI = properties.getProperty("base.url");
        userName = properties.getProperty("username");
    }

    private static void loadProperties() {
        try {
            properties.load(new FileInputStream("src\\test\\resources\\application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addImage(RequestSpecification reqSpec) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        response = given()
                .header("Authorization", token)
                .spec(reqSpec)
                .expect()
                .body("success", is(true))
                .when()
                .post(APIPathes.image)
                .then()
                .statusCode(200).contentType(ContentType.JSON)
                .log()
                .status();

        deleteImageHashCode = response.extract().path(APIPathes.deleteHashCode);
        imageHashCode = response.extract().path(APIPathes.idHashCode);
        assertThat(deleteImageHashCode, notNullValue());
        assertThat(imageHashCode, notNullValue());
    }

    protected RequestSpecification getImageSpec(Map<String, String> data) {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        for (Map.Entry<String, String> pair : data.entrySet()) {
            builder.addParam(pair.getKey(), pair.getValue());
        }
        imageSpecification = builder.build();
        return imageSpecification;
    }
}
