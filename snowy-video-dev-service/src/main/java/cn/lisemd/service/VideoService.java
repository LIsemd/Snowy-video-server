package cn.lisemd.service;

import cn.lisemd.pojo.Videos;
import cn.lisemd.utils.PagedResult;

import java.util.List;


public interface VideoService {


    /**
     * 保存视频
     *
     * @return
     */
    String saveVideo(Videos video);


    /**
     * 修改视频封面
     *
     * @return
     */
    void updateVideo(String videoId, String coverPath);

    /**
     * 分页查询视频列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    PagedResult getAllVideos(Videos video, Integer isSaveRecord, Integer page, Integer pageSize);

    /**
     * 获取热搜词列表
     * @return
     */
    List<String> getHotwords();

}
