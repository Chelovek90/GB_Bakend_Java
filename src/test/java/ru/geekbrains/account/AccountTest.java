package ru.geekbrains.account;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import ru.geekbrains.BaseTest;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class AccountTest extends BaseTest {
//    Map<String, String> headers = new HashMap<>();



    @Test
    public void accountTest() {
//        headers.put("Authorization", "Bearer e770e7533ba97a5f7d73e28b47b5afa23b363350");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        String url = given()
                .headers(headers)
                .log()
                .headers()
                .when()
                .get("https://api.imgur.com/3/account/Chelovek90")
                .prettyPeek()
                .then()
                .statusCode(200)
                .contentType("application/json")
                .log()
                .status()
                .extract()
                .response()
                .jsonPath()
                .getString("data.url");
        assertThat(url,equalTo("Chelovek90"));
    }

    @Test
    public void accountTest2() {
//        headers.put("Authorization", "Bearer e770e7533ba97a5f7d73e28b47b5afa23b363350");
//        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        given()
                .headers(headers)
                .expect()
                .body("success", is(true))
                .body("data.url",is("Chelovek90"))
                .when()
                .get("account/{userName}", userName)
                .prettyPeek()
                .then()
                .statusCode(200)
                .contentType("application/json")
                .log()
                .status();
    }

    @Test
    public void accountTest1() {
        headers.put("Authorization", "Bearer e770e7533ba97a5f7d73e28b47b5afa23b363350");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        given()
                .headers(headers)
                .when()
                .get("https://api.imgur.com/3/account/Chelovek90")
                .then()
                .statusCode(200)
                .log()
                .status();

    }
}
