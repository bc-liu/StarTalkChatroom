package org.bcliu.controller;

import org.bcliu.pojo.Result;
import org.bcliu.service.BotService;
import org.bcliu.service.serviceImpl.BotServiceImpl;
import org.bcliu.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/channels")
public class BotController {
    @Autowired
    private BotService botService;

    @PostMapping("/{channelId}/bot")
    public Result create(@PathVariable Long channelId){
        //获取调用者id
        Map<String, Object> map = ThreadLocalUtil.get();
        Long creatorId = (Long) map.get("id");

        //添加机器人
        botService.create(channelId, creatorId);
        return Result.success();
    }
}
