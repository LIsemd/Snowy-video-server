package cn.lisemd.controller;

import cn.lisemd.pojo.UsersInfo;
import cn.lisemd.pojo.vo.UsersVO;
import cn.lisemd.service.UserService;
import cn.lisemd.utils.SnowyJsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


@RestController
@Api(value = "用户相关业务的接口", tags = {"用户相关业务的controller"})
@RequestMapping("/user")
public class UserController extends BasicController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户上传头像", notes = "用户上传头像的接口")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "query")
    @PostMapping("/uploadAvatar")
    public SnowyJsonResult uploadAvatar(String userId, @RequestParam("file") MultipartFile[] files) throws Exception {

        if (StringUtils.isBlank(userId)) {
            return SnowyJsonResult.errorMsg("用户ID不能为空！");
        }
        // 保存到数据库的相对路径
        String uploadPathDB = "/" + userId + "/avatar";
        // 保存图片
        uploadPathDB = saveImage(files, uploadPathDB);

        UsersInfo user = new UsersInfo();
        user.setId(userId);
        user.setAvatar(uploadPathDB);
        userService.updateUserInfo(user);

        return SnowyJsonResult.ok(uploadPathDB);
    }

    @ApiOperation(value = "用户上传背景图片", notes = "用户上传背景图片的接口")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "query")
    @PostMapping("/uploadBackgroundImage")
    public SnowyJsonResult uploadBackgroundImage(String userId, @RequestParam("file") MultipartFile[] files) throws Exception {

        if (StringUtils.isBlank(userId)) {
            return SnowyJsonResult.errorMsg("用户ID不能为空！");
        }

        // 保存到数据库的相对路径
        String uploadPathDB = "/" + userId + "/backgroundImage";

        // 保存图片
        uploadPathDB = saveImage(files, uploadPathDB);

        UsersInfo user = new UsersInfo();
        user.setId(userId);
        user.setBackgroundImage(uploadPathDB);
        userService.updateUserInfo(user);

        return SnowyJsonResult.ok(uploadPathDB);
    }

    /**
     * 保存图片
     *
     * @param files
     * @param uploadPathDB
     * @throws Exception
     */
    public String saveImage(MultipartFile[] files, String uploadPathDB) throws Exception {
        // 文件保存的命名空间
        String fileSpace = "D:\\项目\\Web实战项目\\snowy_video_dev";

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            if (files != null && files.length > 0) {
                String fileName = files[0].getOriginalFilename();
                if (StringUtils.isNotBlank(fileName)) {
                    // 设置上传的最终保存路径
                    String finalImagePath = fileSpace + uploadPathDB + "/" + fileName;
                    // 设置数据库的保存路径
                    uploadPathDB += ("/" + fileName);

                    File outFile = new File(finalImagePath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        // 创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = files[0].getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }
        return uploadPathDB;
    }


    @ApiOperation(value = "用户修改昵称", notes = "用户修改昵称的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "nickname", value = "用户昵称", required = true, dataType = "String", paramType = "body")
    })
    @PostMapping("/updateNickName")
    public SnowyJsonResult updateNickName(String userId, @RequestBody String nickname) {

        if (StringUtils.isBlank(userId)) {
            return SnowyJsonResult.errorMsg("用户ID不能为空！");
        }

        UsersInfo user = new UsersInfo();
        user.setId(userId);
        user.setNickname(nickname);
        userService.updateUserInfo(user);

        return SnowyJsonResult.ok();
    }

    @ApiOperation(value = "用户修改个性签名", notes = "用户修改个性签名的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "signature", value = "个性签名", required = true, dataType = "String", paramType = "body")
    })
    @PostMapping("/updateSignature")
    public SnowyJsonResult updateSignature(String userId, @RequestBody String signature) {

        if (StringUtils.isBlank(userId)) {
            return SnowyJsonResult.errorMsg("用户ID不能为空！");
        }
        UsersInfo user = new UsersInfo();
        user.setId(userId);
        user.setSignature(signature);
        userService.updateUserInfo(user);

        return SnowyJsonResult.ok();
    }

    @ApiOperation(value = "用户修改性别", notes = "用户修改性别的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gender", value = "用户性别", required = true, dataType = "Integer", paramType = "body")
    })
    @PostMapping("/updateGender")
    public SnowyJsonResult updateGender(String userId, @RequestBody Integer gender) {
        // gender 0-保密 1-男 2-女
        if (StringUtils.isBlank(userId)) {
            return SnowyJsonResult.errorMsg("用户ID不能为空！");
        }
        System.out.println(gender);
        UsersInfo user = new UsersInfo();
        user.setId(userId);
        user.setGender(gender);
        userService.updateUserInfo(user);

        return SnowyJsonResult.ok();
    }

    @ApiOperation(value = "查询用户信息", notes = "查询用户信息的接口")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userId", value = "创作者ID", required = true, dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "fanId", value = "登录者ID", required = false, dataType = "String", paramType = "query")
    })
    @PostMapping("/query")
    public SnowyJsonResult query(String userId,String fanId) {

        if (StringUtils.isBlank(userId)) {
            return SnowyJsonResult.errorMsg("");
        }

        UsersInfo user = userService.queryUserInfo(userId);
        UsersVO userVO = new UsersVO();
        if (fanId != null) {
            userVO.setFollow(userService.queryIsFollow(userId, fanId));
        }
        BeanUtils.copyProperties(user, userVO);

        return SnowyJsonResult.ok(userVO);
    }


    @ApiOperation(value = "查询用户点赞信息", notes = "查询用户点赞信息的接口")
    @PostMapping("/queryUserLike")
    public SnowyJsonResult queryUserLike(String userId, String videoId) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(videoId)) {
            return SnowyJsonResult.errorMsg("");
        }
        // 查询当前登录者和视频的点赞关系
        boolean userLikeVideo = userService.isUserLikeVideo(userId,videoId);

        return SnowyJsonResult.ok(userLikeVideo);
    }

    @ApiOperation(value = "关注用户", notes = "关注用户的接口")
    @PostMapping("/follow")
    public SnowyJsonResult follow(String userId, String fanId) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(fanId)) {
            return SnowyJsonResult.errorMsg("");
        }
        userService.saveUserFanRelation(userId,fanId);
        return SnowyJsonResult.ok();
    }

    @ApiOperation(value = "取消关注用户", notes = "取消关注用户的接口")
    @PostMapping("/unFollow")
    public SnowyJsonResult unFollow(String userId, String fanId) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(fanId)) {
            return SnowyJsonResult.errorMsg("");
        }
        userService.deleteUserFanRelation(userId,fanId);
        return SnowyJsonResult.ok();
    }
}
