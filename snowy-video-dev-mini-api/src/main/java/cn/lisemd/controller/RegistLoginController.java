package cn.lisemd.controller;

import cn.lisemd.pojo.UsersInfo;
import cn.lisemd.pojo.vo.UsersVO;
import cn.lisemd.service.UserService;

import cn.lisemd.utils.MD5Utils;
import cn.lisemd.utils.SnowyJsonResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Api(value = "用户注册登录的接口", tags = {"注册和登录的controller"})
public class RegistLoginController extends BasicController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户注册", notes = "用户注册的接口")
    @PostMapping("/regist")
    public SnowyJsonResult regist(@RequestBody UsersInfo user) throws Exception {
        String username = user.getUsername();
        String password = user.getPassword();
        // 1. 判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return SnowyJsonResult.errorMsg(" 用户名和密码不能为空 ");
        }
        // 2. 判断用户名是否存在
        boolean usernameIsExist = userService.queryUsernameIsExist(username);

        // 3. 保存用户，注册信息
        if (!usernameIsExist) {
            user.setNickname(username);
            user.setPassword(MD5Utils.getMD5Str(password));
            user.setFansCounts(0);
            user.setFollowCounts(0);
            user.setGender(0);
            user.setSignature("");
            user.setReceiveLikeCounts(0);
            userService.saveUser(user);
        } else {
            return SnowyJsonResult.errorMsg(" 用户名已经存在 ");
        }
        user.setPassword("");

        UsersVO userVO = setUserRedisSessionToken(user);
        return SnowyJsonResult.ok(userVO);
    }

    @ApiOperation(value = "用户登录", notes = "用户登录的接口")
    @PostMapping("/login")
    public SnowyJsonResult login(@RequestBody UsersInfo user) throws Exception {
        String username = user.getUsername();
        String password = user.getPassword();
        // 1. 判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return SnowyJsonResult.errorMsg(" 用户名和密码不能为空 ");
        }
        // 2-1. 判断用户名是否存在
        boolean usernameIsExist = userService.queryUsernameIsExist(user.getUsername());
        if (!usernameIsExist) {
            return SnowyJsonResult.errorMsg(" 用户名不存在 ");
        }
        // 2-2. 判断密码是否正确
        UsersInfo userResult = userService.queryUserIsRight(username, MD5Utils.getMD5Str(password));
        if (userResult == null) {
            return SnowyJsonResult.errorMsg(" 密码有误，请重试 ");
        }

        // 3. 实现登录
        userResult.setPassword("");
        UsersVO userVO = setUserRedisSessionToken(userResult);
        return SnowyJsonResult.ok(userVO);
    }

    @ApiOperation(value = "用户注销", notes = "用户注销的接口")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "query")
    @PostMapping("/logout")
    public SnowyJsonResult logout(String userId) {
        redis.del(USER_REDIS_SESSION + ":" + userId);
        return SnowyJsonResult.ok();
    }


    public UsersVO setUserRedisSessionToken(UsersInfo userModel) {
        String uniqueToken = UUID.randomUUID().toString();
        redis.set(USER_REDIS_SESSION + ":" + userModel.getId(), uniqueToken, 1000 * 60 * 30);

        UsersVO userVO = new UsersVO();
        BeanUtils.copyProperties(userModel, userVO);
        userVO.setUserToken(uniqueToken);
        return userVO;
    }

}
