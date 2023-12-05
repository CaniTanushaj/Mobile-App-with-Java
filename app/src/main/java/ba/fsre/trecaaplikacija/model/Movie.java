package ba.fsre.trecaaplikacija.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Movie {
    public String name;
    public String director;
    public String release_date;
    public String image;

    public Movie() {
    }

    public Movie(String name, String director, String realease_date, String image) {
        this.name = name;
        this.director = director;
        this.release_date = realease_date;
        this.image = image;
    }
}
