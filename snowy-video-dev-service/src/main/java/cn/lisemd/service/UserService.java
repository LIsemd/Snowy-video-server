package cn.lisemd.service;

import cn.lisemd.pojo.UsersInfo;

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

}
