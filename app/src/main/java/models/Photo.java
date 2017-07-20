package models;

/**
 * Created by dennismwebia on 7/20/17.
 */

public class Photo {
    private String photo_title;
    private Integer photo;

    public Photo(String photo_title, Integer photo){
        this.photo_title = photo_title;
        this.photo = photo;
    }

    public String getPhoto_title() {
        return photo_title;
    }

    public void setPhoto_title(String photo_title) {
        this.photo_title = photo_title;
    }

    public Integer getPhoto() {
        return photo;
    }

    public void setPhoto(Integer photo) {
        this.photo = photo;
    }
}
