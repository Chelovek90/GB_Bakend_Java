package ru.geekbrains.image;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import ru.geekbrains.APIPathes;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class ImageTest extends BaseImage{

    @Test
    public void addImageURLTest() {
        dataBody.put("image", APIPathes.imageURL);
        getImageSpec(dataBody);
        addImage(imageSpecification);
    }

    @Test
    public void addImageBase64Test() {
        dataBody.put("image", encodedImage);
        getImageSpec(dataBody);
        addImage(imageSpecification);
    }

    @Test
    public void getImageTest() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        addImageURLTest();
        given()
                .headers("Authorization", token)
                .expect()
                .body("success", is(true))
                .when()
                .get(APIPathes.imageHashCodeURL)
                .then()
                .statusCode(200).contentType(ContentType.JSON);
    }

    @Test
    public void addImageURLWithTitleTest() {
        dataBody.put("image", APIPathes.imageURL);
        dataBody.put("title", APIPathes.title);
        getImageSpec(dataBody);
        addImage(imageSpecification);
    }

    @Test
    public void addImageURLWithDescriptionTest() {
        dataBody.put("image", APIPathes.imageURL);
        dataBody.put("description", APIPathes.description);
        getImageSpec(dataBody);
        addImage(imageSpecification);
    }

    @Test
    public void addImageBase64WithTitleTest() {
        dataBody.put("image", encodedImage);
        dataBody.put("title", APIPathes.title);
        getImageSpec(dataBody);
        addImage(imageSpecification);
    }

    @Test
    public void addImageBase64WithDescriptionTest() {
        dataBody.put("image", encodedImage);
        dataBody.put("description", APIPathes.description);
        getImageSpec(dataBody);
        addImage(imageSpecification);
    }

    @Test
    public void updateImageTest() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        addImageURLWithTitleTest();
        dataBody.put("title", APIPathes.updateTitle);
        dataBody.put("description", APIPathes.updateDescription);
        getImageSpec(dataBody);
        given()
                .headers("Authorization", token)
                .spec(imageSpecification)
                .expect()
                .body("success", is(true))
                .when()
                .post(APIPathes.imageHashCodeURL)
                .then()
                .statusCode(200).contentType(ContentType.JSON)
                .log()
                .status();
    }

    @Test
    public void doFavoriteImageTest() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        addImageURLTest();
        given()
                .headers("Authorization", token)
                .expect()
                .body("success", is(true))
                .when()
                .post(APIPathes.favoriteImageURL)
                .then()
                .statusCode(200).contentType(ContentType.JSON)
                .log()
                .status();
    }

}
