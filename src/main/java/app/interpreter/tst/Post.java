package app.interpreter.tst;

public class Post {
    private String post_id;
    private String actor_id;
    private String picOfPersonWhoPosted;
    private String nameOfPersonWhoPosted;
    private String message;
    private int likesCount;
    private String[] comments;
    private int timeOfPost;

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getActor_id() {
        return actor_id;
    }

    public void setActor_id(String actor_id) {
        this.actor_id = actor_id;
    }

    public String getPicOfPersonWhoPosted() {
        return picOfPersonWhoPosted;
    }

    public void setPicOfPersonWhoPosted(String picOfPersonWhoPosted) {
        this.picOfPersonWhoPosted = picOfPersonWhoPosted;
    }

    public String getNameOfPersonWhoPosted() {
        return nameOfPersonWhoPosted;
    }

    public void setNameOfPersonWhoPosted(String nameOfPersonWhoPosted) {
        this.nameOfPersonWhoPosted = nameOfPersonWhoPosted;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public String[] getComments() {
        return comments;
    }

    public void setComments(String[] comments) {
        this.comments = comments;
    }

    public int getTimeOfPost() {
        return timeOfPost;
    }

    public void setTimeOfPost(int timeOfPost) {
        this.timeOfPost = timeOfPost;
    }
}
