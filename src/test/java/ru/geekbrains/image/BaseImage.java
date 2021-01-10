package ru.geekbrains.image;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.geekbrains.BaseTest;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public abstract class BaseImage extends BaseTest {

    protected static String encodedImage;

    @BeforeEach
    public void setUp() {
        byte[] fileContent = getFileContent();
        encodedImage = Base64.getEncoder().encodeToString(fileContent);
    }

    @AfterEach
    public void tearDown() {
        given()
                .header("Authorization", token)
                .when()
                .delete("image/{deleteHashCode}", deleteImageHashCode)
                .then()
                .statusCode(200);
    }

    public byte[] getFileContent() {
        ClassLoader classLoader = getClass().getClassLoader();
        File inputeFile = new File(classLoader.getResource("name.png").getFile());
        byte[] fileContent = new byte[0];
        try {
            fileContent = FileUtils.readFileToByteArray(inputeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }
}
