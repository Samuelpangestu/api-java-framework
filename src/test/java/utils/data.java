package utils;

public class data {
    private String title;
    private String body;
    private int userId;

    //Constructor
    public data(String title, String body, int userId) {
        this.title = title;
        this.body = body;
        this.userId = userId;
    }

    //Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
