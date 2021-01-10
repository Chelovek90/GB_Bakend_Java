package ru.geekbrains.album;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import ru.geekbrains.APIPathes;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class AlbumTest extends BaseAlbum{

    @Test
    public void createAlbumTest() {
        albumContent.put("cover", imageHashCode);
        getAlbumeSpec(albumContent);
        createAlbum(albumSpecification);
    }

    @Test
    public void createAlbumWithTitleTest() {
        albumContent.put("title", APIPathes.title);
        albumContent.put("cover", imageHashCode);
        getAlbumeSpec(albumContent);
        createAlbum(albumSpecification);
    }

    @Test
    public void createAlbumWithTitleAndDescriptionTest() {
        albumContent.put("title", APIPathes.title);
        albumContent.put("description", APIPathes.description);
        getAlbumeSpec(albumContent);
        createAlbum(albumSpecification);
    }

    @Test
    public void getImagesFromAlbumTest() {
        createAlbumTest();
        given()
                .header("Authorization", token)
                .expect()
                .body("success", is(true))
                .when()
                .get(APIPathes.imagesAlbumURL)
                .then()
                .statusCode(200).contentType(ContentType.JSON)
                .log()
                .status();
    }

    @Test
    public void getImageFromAlbumTest() {
        createAlbumTest();
        given()
                .header("Authorization", token)
                .expect()
                .body("success", is(true))
                .when()
                .get(APIPathes.imageAlbumURL)
                .then()
                .statusCode(200).contentType(ContentType.JSON)
                .log()
                .status();
    }

    @Test
    public void doFavoriteAlbumTest() {
        createAlbumTest();
        given()
                .header("Authorization", token)
                .spec(albumSpecification)
                .expect()
                .body("success", is(true))
                .when()
                .post(APIPathes.doFavoriteAlbumURL)
                .then()
                .statusCode(200).contentType(ContentType.JSON)
                .log()
                .status();
    }

    @Test
    public void addImageAlbumTest() {
        createAlbumTest();
        dataBody.put("image", APIPathes.imageURL);
        getImageSpec(dataBody);
        addImage(imageSpecification);
        albumContent.put("ids[]", imageHashCode);
        getAlbumeSpec(albumContent);
        given()
                .header("Authorization", token)
                .spec(albumSpecification)
                .expect()
                .body("success", is(true))
                .when()
                .post(APIPathes.addImageAlbumURL)
                .then()
                .statusCode(200).contentType(ContentType.JSON)
                .log()
                .status();
    }
}
