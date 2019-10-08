package cn.lisemd.mapper;

import cn.lisemd.pojo.UsersInfo;
import cn.lisemd.utils.MyMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersInfoMapper extends MyMapper<UsersInfo> {

    /**
     * 用户受喜欢数累加
     */
    public void addReceiveLikeCount(String userId);

    /**
     * 用户受喜欢数累减
     */
    public void reduceReceiveLikeCount(String userId);

    /**
     *  增加粉丝数
     */
    public void addFansCount(String userId);

    /**
     *  减少粉丝数
     */
    public void reduceFansCount(String userId);

    /**
     *  增加关注数
     */
    public void addFollowsCount(String userId);

    /**
     *  减少关注数
     */
    public void reduceFollowsCount(String userId);

}