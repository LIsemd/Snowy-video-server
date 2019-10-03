package cn.lisemd.service.impl;

import cn.lisemd.mapper.UsersInfoMapper;
import cn.lisemd.pojo.UsersInfo;

import cn.lisemd.service.UserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersInfoMapper usersInfoMapper;

    @Autowired
    private Sid sid;

    /**
     * 判断用户是否存在
     * @param username
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {

        UsersInfo user = new UsersInfo();
        user.setUsername(username);

        UsersInfo result = usersInfoMapper.selectOne(user);

        return result != null;
    }

    /**
     * 保存用户信息
     * @param user
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUser(UsersInfo user) {
        String userId = sid.nextShort();
        user.setId(userId);
        usersInfoMapper.insert(user);
    }

    /**
     * 验证用户
     * @param username
     * @param password
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UsersInfo queryUserIsRight(String username, String password) {
        Example userExample = new Example(UsersInfo.class);
        Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username", username);
        criteria.andEqualTo("password", password);
        UsersInfo result = usersInfoMapper.selectOneByExample(userExample);

        return result;
    }

    /**
     * 更新用户数据
     * @param user
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserInfo(UsersInfo user) {
        Example userExample = new Example(UsersInfo.class);
        Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("id", user.getId());
        usersInfoMapper.updateByExampleSelective(user, userExample);
    }

    /**
     * 查询用户数据
     * @param userId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UsersInfo queryUserInfo(String userId) {
        System.out.println(userId);
        Example userExample = new Example(UsersInfo.class);
        Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("id", userId);
        return usersInfoMapper.selectOneByExample(userExample);
    }
}
