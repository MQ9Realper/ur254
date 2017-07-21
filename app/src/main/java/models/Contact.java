package models;

/**
 * Created by dennis on 18/06/17.
 */

public class Contact {
    private String image = null;
    private String name = null;
    private String number = null;
    private String date = null;
    private String comment = null;
    private Boolean selected = null;

    public Contact(String image, String name, String number, String date, String comment, Boolean selected) {
        super();
        this.image = image;
        this.name = name;
        this.number = number;
        this.date = date;
        this.comment = comment;
        this.selected = selected;
    }

    public String getUserImage() {
        return image;
    }

    public void setUserImage(String image) {
        this.image = image;
    }

    public String getUserName() {
        return name;
    }

    public void setUserName(String name) {
        this.name = name;
    }

    public String getUserNumber() {
        return number;
    }

    public void setUserNumber(String number) {
        this.number = number;
    }

    public String getUserDate() {
        return date;
    }

    public void setUserDate(String date) {
        this.date = date;
    }

    public String getUserComment() {
        return comment;
    }

    public void setUserComment(String comment) {
        this.comment = comment;
    }

    public Boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

}
