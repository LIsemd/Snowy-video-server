package cn.lisemd.service.impl;

import cn.lisemd.mapper.*;
import cn.lisemd.pojo.Comments;
import cn.lisemd.pojo.SearchRecords;
import cn.lisemd.pojo.UsersLikeVideos;
import cn.lisemd.pojo.Videos;
import cn.lisemd.pojo.vo.CommentsVO;
import cn.lisemd.pojo.vo.VideosVO;
import cn.lisemd.utils.PagedResult;
import cn.lisemd.utils.TimeAgo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.n3r.idworker.Sid;
import cn.lisemd.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;


@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideosMapper videosMapper;

    @Autowired
    private Sid sid;

    @Autowired
    private VideosMapperCustom videosMapperCustom;

    @Autowired
    private SearchRecordsMapper searchRecordsMapper;

    @Autowired
    private UsersLikeVideosMapper usersLikeVideosMapper;

    @Autowired
    private UsersInfoMapper usersInfoMapper;

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private CommentsMapperCustom commentsMapperCustom;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public PagedResult getVideos(Videos video, Integer isSaveRecord, Integer page, Integer pageSize) {
        // 保存热搜词
        String desc = video.getVideoDesc();
        String userId = video.getUserId();
        if (isSaveRecord != null && isSaveRecord == 1) {
            SearchRecords record = new SearchRecords();
            String recordId = sid.nextShort();
            record.setId(recordId);
            record.setContent(desc);
            searchRecordsMapper.insert(record);
        }

        PageHelper.startPage(page, pageSize);
        List<VideosVO> list = videosMapperCustom.queryAllVideos(desc, userId);

        PageInfo<VideosVO> pageList = new PageInfo<>(list);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(list);
        pagedResult.setRecords(pageList.getTotal());

        return pagedResult;
    }

    /**
     * 获取全部视频
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<VideosVO> getAllVideos() {
        List<VideosVO> videosList = videosMapperCustom.queryAllVideos("","");

        return videosList;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<String> getHotwords() {
        return searchRecordsMapper.getHotwords();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void userLikeVideo(String userId, String videoId, String videoCreaterId) {

        // 1.保存用户和视频的喜欢点赞关联关系表
        String likeId = sid.nextShort();
        UsersLikeVideos usersLikeVideos = new UsersLikeVideos();
        usersLikeVideos.setId(likeId);
        usersLikeVideos.setUserId(userId);
        usersLikeVideos.setVideoId(videoId);

        usersLikeVideosMapper.insert(usersLikeVideos);

        // 2.视频喜欢数量累加

        videosMapperCustom.addVideoLikeCount(videoId);

        // 3.用户受喜欢数量累加

        usersInfoMapper.addReceiveLikeCount(videoCreaterId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void userUnlikeVideo(String userId, String videoId, String videoCreaterId) {
        // 1.删除用户和视频的喜欢点赞关联关系表

        Example example = new Example(UsersLikeVideos.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("videoId", videoId);

        usersLikeVideosMapper.deleteByExample(example);
        // 2.视频喜欢数量累减

        videosMapperCustom.reduceVideoLikeCount(videoId);

        // 3.用户受喜欢数量累减

        usersInfoMapper.reduceReceiveLikeCount(videoCreaterId);
    }

    @Override
    public List<VideosVO> queryUserLike(String userId) {
        List<VideosVO> list = videosMapperCustom.queryUserLike(userId);
        return list;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String saveVideo(Videos video) {
        String id = sid.nextShort();
        video.setId(id);
        videosMapper.insertSelective(video);
        return id;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateVideo(String videoId, String coverPath) {
        Videos video = new Videos();
        video.setId(videoId);
        video.setCoverPath(coverPath);
        videosMapper.updateByPrimaryKeySelective(video);
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<VideosVO> queryMyFollowVideos(String userId) {


        List<VideosVO> list = videosMapperCustom.queryMyFollowVideos(userId);


        return list;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComment(Comments comment) {
        String id = sid.nextShort();
        comment.setId(id);
        comment.setCreateDate(new Date());
        commentsMapper.insert(comment);
    }
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<CommentsVO> getVideoComments(String videoId) {

        List<CommentsVO> list = commentsMapperCustom.getVideoComments(videoId);

        for(CommentsVO c: list) {
            String timeAgo = TimeAgo.format(c.getCreateDate());
            c.setTimeAgoStr(timeAgo);
        }

        return list;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<CommentsVO> getAllComments(String userId) {
        List<CommentsVO> list = commentsMapperCustom.getAllComments(userId);

        for(CommentsVO c: list) {
            String timeAgo = TimeAgo.format(c.getCreateDate());
            c.setTimeAgoStr(timeAgo);
        }
        return list;
    }


}
