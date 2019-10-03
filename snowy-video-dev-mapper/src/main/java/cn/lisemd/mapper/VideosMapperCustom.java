package cn.lisemd.mapper;

import cn.lisemd.pojo.Videos;
import cn.lisemd.pojo.vo.VideosVO;
import cn.lisemd.utils.MyMapper;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideosMapperCustom extends MyMapper<Videos> {
    List<VideosVO> queryAllVideos(@Param("videoDesc") String videoDesc);
}