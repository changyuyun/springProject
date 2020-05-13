package com.ityun.web.controller.site;

import com.ityun.base.lang.Result;
import com.ityun.base.lang.ResultConst;
import com.ityun.modules.entity.Channel;
import com.ityun.modules.service.ChannelService;
import com.ityun.web.annotation.DisableAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/channel")
@Validated
public class ChannelController {
    @Autowired
    private ChannelService channelService;

    @GetMapping("/list")
    @DisableAuth
    public Result list() {
        List<Channel> list = channelService.list();
        return Result.success(ResultConst.commonMessage.COMMON_SUCCESS, list);
    }
}
