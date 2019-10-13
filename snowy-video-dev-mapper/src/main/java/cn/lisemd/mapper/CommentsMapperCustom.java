package cn.lisemd.mapper;

import cn.lisemd.pojo.Comments;
import cn.lisemd.pojo.vo.CommentsVO;
import cn.lisemd.utils.MyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsMapperCustom extends MyMapper<Comments> {
    List<CommentsVO> getVideoComments(String videoId);

    List<CommentsVO> getAllComments(String userId);
}