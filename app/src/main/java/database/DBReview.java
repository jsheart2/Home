package database;

public class DBReview {
    private String commentar;

    public DBReview (){}

    public DBReview(String commentar) {
        this.commentar = commentar;
    }

    public String getCommentar() {
        return commentar;
    }

    public void setCommentar(String commentar) {
        this.commentar = commentar;
    }

}
