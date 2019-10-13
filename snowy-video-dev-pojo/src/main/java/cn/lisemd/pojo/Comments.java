package cn.lisemd.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Comments {
    @Id
    private String id;

    @Column(name = "to_user_id")
    private String toUserId;

    @Column(name = "father_comment_id")
    private String fatherCommentId;

    @Column(name = "video_id")
    private String videoId;

    @Column(name = "from_user_id")
    private String fromUserId;

    @Column(name = "create_date")
    private Date createDate;

    private String comment;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return to_user_id
     */
    public String getToUserId() {
        return toUserId;
    }

    /**
     * @param toUserId
     */
    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    /**
     * @return father_comment_id
     */
    public String getFatherCommentId() {
        return fatherCommentId;
    }

    /**
     * @param fatherCommentId
     */
    public void setFatherCommentId(String fatherCommentId) {
        this.fatherCommentId = fatherCommentId;
    }

    /**
     * @return video_id
     */
    public String getVideoId() {
        return videoId;
    }

    /**
     * @param videoId
     */
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    /**
     * @return from_user_id
     */
    public String getFromUserId() {
        return fromUserId;
    }

    /**
     * @param fromUserId
     */
    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    /**
     * @return create_date
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}