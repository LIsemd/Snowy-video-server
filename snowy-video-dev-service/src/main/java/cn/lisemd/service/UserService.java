package cn.lisemd.service;

import cn.lisemd.pojo.Users;

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
    void saveUser(Users users);

    /**
     * 判断用户密码是否匹配
     * @param username
     * @return
     */
    Users queryUserIsRight(String username, String password);
}
