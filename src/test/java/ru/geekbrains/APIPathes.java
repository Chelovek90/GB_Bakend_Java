package ru.geekbrains;

import ru.geekbrains.BaseTest;
import ru.geekbrains.album.BaseAlbum;

public interface APIPathes {
    String image = "/image";
    String album = "/album";
    String title = "titleTest";
    String updateTitle = "updateTitleTest";
    String description = "descriptionTest";
    String updateDescription = "updateDescriptionTest";
    String imageURL = "http://placeimg.com/640/480/animals";
    String deleteHashCode = "data.deletehash";
    String idHashCode = "data.id";

    String favoriteImageURL = image + "/" + BaseTest.imageHashCode + "/favorite";
    String imageHashCodeURL = image + "/" + BaseTest.imageHashCode;
    String imagesAlbumURL = album + "/" + BaseAlbum.albumHashCode + "/images" ;
    String doFavoriteAlbumURL = album + "/" + BaseAlbum.albumHashCode + "/favorite" ;
    String addImageAlbumURL = album + "/" + BaseAlbum.albumHashCode + "/add";
    String imageAlbumURL = album + "/" + BaseAlbum.albumHashCode + "/image" + BaseTest.imageHashCode;
}
