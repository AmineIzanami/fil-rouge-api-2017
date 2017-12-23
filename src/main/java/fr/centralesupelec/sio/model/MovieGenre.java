package fr.centralesupelec.sio.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;
/**
 * An enum for movie genres.
 */
public enum MovieGenre {

    ACTION,
    ADVENTURE,
    ANIMATION,
    CHILDRENS,
    COMEDY,
    CRIME,
    DOCUMENTARY,
    DRAMA,
    FANTASY,
    FILM_NOIR,
    HORROR,
    MUSICAL,
    MYSTERY,
    ROMANCE,
    SCIENCE_FICTION,
    THRILLER,
    WAR,
    WESTERN;




    public static <T extends Enum<T>> StringBuilder getGenres(Class<T> enumClass) {
        StringBuilder genres = new StringBuilder();

        if (enumClass == null) {
            throw new IllegalArgumentException("EnumClass value can't be null.");
        }

        genres.append("{");
        for (Enum<?> enumValue : enumClass.getEnumConstants()) {
            genres.append(enumValue.ordinal() + ":").append(enumValue+",");
        }
        genres.append("}");
        return genres;

    }
}

