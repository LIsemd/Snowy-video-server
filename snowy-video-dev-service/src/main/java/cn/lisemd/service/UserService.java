package cn.lisemd.service;

import cn.lisemd.pojo.UsersInfo;
import cn.lisemd.pojo.UsersReport;

import java.util.List;

public interface UserService {

    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    boolean queryUsernameIsExist(String username);

    /**
     * 保存用户
     * @param users
     */
    void saveUser(UsersInfo users);

    /**
     * 判断用户密码是否匹配
     * @param username
     * @return
     */
    UsersInfo queryUserIsRight(String username, String password);

    /**
     * 用户修改信息
     * @param user
     */
    void updateUserInfo(UsersInfo user);

    /**
     * 查询用户信息
     * @param userId
     */
    UsersInfo queryUserInfo(String userId);


    /**
     * 查询用户点赞信息
     * @param userId
     */
    Boolean isUserLikeVideo(String userId, String videoId);


    /**
     * 关注
     */
    void saveUserFanRelation(String userId, String fanId);

    /**
     * 取消关注
     */
    void deleteUserFanRelation(String userId, String fanId);

    /**
     * 查询用户是否有关注
     */
    boolean queryIsFollow(String userId, String fanId);

    /**
     * 获取我关注的用户的信息
     */
    List<UsersInfo> queryFollows(String userId);

    /**
     * 获取我的粉丝的信息
     */
    List<UsersInfo> queryFans(String userId);

    void reportUser(UsersReport usersReport);
}
