package cn.lisemd.controller;

import cn.lisemd.service.BgmService;
import cn.lisemd.utils.SnowyJsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "背景音乐业务的接口", tags = {"背景音乐业务的controller"})
@RequestMapping("/bgm")
public class BgmController {

    @Autowired
    private BgmService bgmService;

    @ApiOperation(value = "获取背景音乐列表", notes = "获取背景音乐列表的接口")
    @PostMapping("/list")
    public SnowyJsonResult list() {
        return SnowyJsonResult.ok(bgmService.queryBgmList());
    }

    @ApiOperation(value = "根据ID获取背景音乐信息", notes = "根据ID获取背景音乐信息的接口")
    @PostMapping("/getBgmInfo")
    public SnowyJsonResult getBgmInfo(String id) {
        return SnowyJsonResult.ok(bgmService.queryBgmById(id));
    }

}
