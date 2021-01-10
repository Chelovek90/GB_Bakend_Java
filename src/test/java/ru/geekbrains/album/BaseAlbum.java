package ru.geekbrains.album;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.BaseTest;
import ru.geekbrains.APIPathes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class BaseAlbum extends BaseTest {

    protected List<String> images = new ArrayList<>();
    protected Map<String, String> albumContent = new HashMap<>();
    public String deleteAlbumHashCode;
    public static String albumHashCode;


    @BeforeEach
    public void setUp() {
        dataBody.put("image", APIPathes.imageURL);
        getImageSpec(dataBody);
        addImage(imageSpecification);
        albumContent.put("ids[]", imageHashCode);
    }

    @AfterEach
    public void tearDown() {
        given()
                .header("Authorization", token)
                .when()
                .delete("album/{deleteHashCode}", deleteAlbumHashCode)
                .then()
                .statusCode(200);
        given()
                .header("Authorization", token)
                .when()
                .delete("image/{deleteHashCode}", deleteImageHashCode)
                .then()
                .statusCode(200);
    }

    protected RequestSpecification getAlbumeSpec(Map<String, String> data) {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        for (Map.Entry<String, String> pair : data.entrySet()) {
            builder.addParam(pair.getKey(), pair.getValue());
        }
        albumSpecification = builder.build();
        return albumSpecification;
    }

    public void createAlbum(RequestSpecification specification) {
        getAlbumeSpec(albumContent);
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        response = given()
                .headers("Authorization", token)
                .spec(specification)
                .expect()
                .body("success", is(true))
                .when()
                .post(APIPathes.album)
                .then()
                .statusCode(200).contentType(ContentType.JSON)
                .log()
                .status();
        deleteAlbumHashCode = response.extract().path(APIPathes.deleteHashCode);
        albumHashCode = response.extract().path(APIPathes.idHashCode);
    }

}
