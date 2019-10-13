package cn.lisemd.mapper;

import cn.lisemd.pojo.Videos;
import cn.lisemd.pojo.vo.VideosVO;
import cn.lisemd.utils.MyMapper;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideosMapperCustom extends MyMapper<Videos> {
    /**
     *  根据输入查找视频
     */
    List<VideosVO> queryAllVideos(@Param("videoDesc") String videoDesc, @Param("userId") String userId);

    /**
     * 对视频喜欢的数量进行累加
     */
    void addVideoLikeCount(String videoId);

    /**
     * 对视频喜欢的数量进行累减
     */
    void reduceVideoLikeCount(String videoId);

    /**
     *  查找用户点赞视频
     */
    List<VideosVO> queryUserLike(String videoId);

    /**
     *  查找关注用户发布的视频
     */
    List<VideosVO> queryMyFollowVideos(String userId);
}