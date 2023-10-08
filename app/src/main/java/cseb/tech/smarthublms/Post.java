package cseb.tech.smarthublms;

import java.util.Date;

public class Post {
    private String content;
    private Date timestamp;
    private String postedBy;
    private String imageURl;
    // Default constructor is needed for Firestore
    public Post() {}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }


}