package cn.lisemd.pojo;

import javax.persistence.*;

@Table(name = "users_fans")
public class UsersFans {
    @Id
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "fan_id")
    private String fanId;

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
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return fan_id
     */
    public String getFanId() {
        return fanId;
    }

    /**
     * @param fanId
     */
    public void setFanId(String fanId) {
        this.fanId = fanId;
    }
}