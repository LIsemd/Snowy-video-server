package cn.lisemd.controller;

import cn.lisemd.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {

    @Autowired
    public RedisOperator redis;

    public static final String USER_REDIS_SESSION = "user-redis-session";

    // 文件保存的命名空间
    public static final String FILE_SPACE = "D:\\项目\\Web实战项目\\snowy_video_dev";

    // ffmpeg所在目录
    public static final String FFMPEG_EXE = "D:\\FFMpeg\\ffmpeg-4.2.1-win64-static\\bin\\ffmpeg.exe";

    // 每页分页的记录数
    public static final Integer PAGE_SIZE = 6;

}
