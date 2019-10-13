package cn.lisemd.mapper;

import cn.lisemd.pojo.UsersInfo;
import cn.lisemd.utils.MyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersInfoMapper extends MyMapper<UsersInfo> {

    /**
     * 用户受喜欢数累加
     */
    void addReceiveLikeCount(String userId);

    /**
     * 用户受喜欢数累减
     */
    void reduceReceiveLikeCount(String userId);

    /**
     *  增加粉丝数
     */
    void addFansCount(String userId);

    /**
     *  减少粉丝数
     */
    void reduceFansCount(String userId);

    /**
     *  增加关注数
     */
    void addFollowsCount(String userId);

    /**
     *  减少关注数
     */
    void reduceFollowsCount(String userId);

    /**
     *  查询关注的人
     */
    List<UsersInfo> queryFollows(String userId);

    /**
     *  查询我的粉丝
     */
    List<UsersInfo> queryFans(String userId);
}