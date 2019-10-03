package cn.lisemd.controller;

import cn.lisemd.enums.VideoStatusEnum;
import cn.lisemd.pojo.Bgm;
import cn.lisemd.pojo.Videos;
import cn.lisemd.service.BgmService;
import cn.lisemd.service.VideoService;
import cn.lisemd.utils.FetchVideoCover;
import cn.lisemd.utils.MergeVideo;
import cn.lisemd.utils.PagedResult;
import cn.lisemd.utils.SnowyJsonResult;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@RestController
@Api(value = "视频业务的接口", tags = {"视频业务的controller"})
@RequestMapping("/video")
public class VideoController extends BasicController {

    @Autowired
    private BgmService bgmService;

    @Autowired
    private VideoService videoService;

    @ApiOperation(value = "上传视频", notes = "上传视频的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bgmId", value = "背景音乐ID", required = false, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "desc", value = "视频描述", required = false, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "duration", value = "视频时间", required = true, dataType = "double", paramType = "form"),
            @ApiImplicitParam(name = "videoWidth", value = "视频宽度", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "videoHeight", value = "视频高度", required = true, dataType = "int", paramType = "form"),
    })
    @PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
    public SnowyJsonResult upload(String userId, String bgmId, String description, double duration, Integer videoWidth,
                                  Integer videoHeight, @ApiParam(value = "短视频", required = true) MultipartFile file) throws Exception {

        if (StringUtils.isBlank(userId)) {
            return SnowyJsonResult.errorMsg("用户ID不能为空！");
        }
        // 保存到数据库的相对路径
        String uploadPathDB = "/" + userId + "/video";
        String coverPathDB = "/" + userId + "/video";

        // 设置上传的最终保存路径
        String finalVideoPath = "";
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            if (file != null) {
                String fileName = file.getOriginalFilename();
                // 获取前缀
                String fileNamePrefix = fileName.split("\\.")[0];

                if (StringUtils.isNotBlank(fileName)) {

                    // 设置数据库的保存路径
                    uploadPathDB += ("/" + fileName);
                    coverPathDB += ("/" + fileNamePrefix + ".jpg");

                    finalVideoPath = FILE_SPACE + uploadPathDB;
                    File outFile = new File(finalVideoPath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        // 创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }
            } else {
                return SnowyJsonResult.errorMsg("上传出错");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return SnowyJsonResult.errorMsg("上传出错");
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }

        // 判断bgmId是否为空，如果不为空，查询bgm信息
        if (StringUtils.isNotBlank(bgmId)) {
            Bgm bgm = bgmService.queryBgmById(bgmId);
            String mp3InputPath = FILE_SPACE + bgm.getPath();
            MergeVideo tool = new MergeVideo(FFMPEG_EXE);

            String videoInputPath = finalVideoPath;
            String videoOutputName = UUID.randomUUID().toString() + ".mp4";

            uploadPathDB = "/" + userId + "/video/" + videoOutputName;
            finalVideoPath = FILE_SPACE + uploadPathDB;

            tool.convertor(videoInputPath, mp3InputPath, duration, finalVideoPath);

        }
        // 对视频进行截图
        FetchVideoCover ffmpeg = new FetchVideoCover(FFMPEG_EXE);
        ffmpeg.convertor(finalVideoPath, FILE_SPACE + coverPathDB);

        // 保存视频信息到数据库
        Videos video = new Videos();
        video.setAudioId(bgmId);
        video.setUserId(userId);
        video.setVideoSeconds((float) duration);
        video.setVideoHeight(videoHeight);
        video.setVideoWidth(videoWidth);
        video.setVideoDesc(description);
        video.setVideoPath(uploadPathDB);
        video.setCoverPath(coverPathDB);
        video.setStatus(VideoStatusEnum.SUCCESS.value);
        video.setCreateTime(new Date());
        String videoId = videoService.saveVideo(video);

        return SnowyJsonResult.ok(videoId);
    }

    @ApiOperation(value = "上传封面", notes = "上传封面的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "videoId", value = "视频主键ID", required = true, dataType = "String", paramType = "form")
    })
    @PostMapping(value = "/uploadCover", headers = "content-type=multipart/form-data")
    public SnowyJsonResult uploadCover(String userId, String videoId, @ApiParam(value = "视频封面", required = true) MultipartFile file) throws Exception {

        if (StringUtils.isBlank(videoId) || StringUtils.isBlank(userId)) {
            return SnowyJsonResult.errorMsg("用户ID和视频主键ID不能为空！");
        }
        // 保存到数据库的相对路径
        String uploadPathDB = "/" + userId + "/video";
        // 设置上传的最终保存路径
        String finalCoverPath = "";

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;

        try {
            if (file != null) {
                String fileName = file.getOriginalFilename();
                if (StringUtils.isNotBlank(fileName)) {

                    // 设置数据库的保存路径
                    uploadPathDB += ("/" + fileName);

                    finalCoverPath = FILE_SPACE + uploadPathDB;

                    File outFile = new File(finalCoverPath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        // 创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }
            } else {
                return SnowyJsonResult.errorMsg("上传出错");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return SnowyJsonResult.errorMsg("上传出错");
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }

        videoService.updateVideo(videoId, uploadPathDB);


        return SnowyJsonResult.ok();
    }

    /**
     * 分页和搜索查询视频列表
     * isSaveRecord: 1 - 需要保存
     *               0 - 不保存
     * @param video
     * @param isSaveRecord
     * @param page
     * @return
     */
    @PostMapping(value = "/showAll")
    public SnowyJsonResult showAll(@RequestBody Videos video, Integer isSaveRecord, Integer page) {
        if (page == null) {
            page = 1;
        }
        PagedResult result = videoService.getAllVideos(video, isSaveRecord, page, PAGE_SIZE);
        return SnowyJsonResult.ok(result);
    }

    @PostMapping(value = "/hot")
    public SnowyJsonResult hot() {
        return SnowyJsonResult.ok(videoService.getHotwords());
    }
}
