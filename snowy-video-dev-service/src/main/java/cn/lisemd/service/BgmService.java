package cn.lisemd.service;

import cn.lisemd.pojo.Bgm;
import java.util.List;

public interface BgmService {

    /**
     *  查询背景音乐列表
     */
    List<Bgm> queryBgmList();

    /**
     * 根据Id查询bgm信息
     * @param bgmId
     * @return
     */
    Bgm queryBgmById(String bgmId);
}
