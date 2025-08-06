package org.bcliu.controller;

import org.bcliu.dto.ChannelDTO;
import org.bcliu.pojo.Channel;
import org.bcliu.pojo.PageBean;
import org.bcliu.pojo.Result;
import org.bcliu.service.ChannelMemberService;
import org.bcliu.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {
    @Autowired
    private ChannelService channelService;
    @Autowired
    private ChannelMemberService channelMemberService;

    @PostMapping("/")
    public Result create(@RequestBody @Validated ChannelDTO channelDTO){
        channelService.create(channelDTO);
        return Result.success();
    }

    @GetMapping("/public")
    public Result<PageBean<Channel>> getPublicChannelsList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ){
        PageBean<Channel> pb = channelService.getPublicChannels(pageNum, pageSize);
        return Result.success(pb);
    }

    @PostMapping("/{channelId}/join")
    public Result join(@PathVariable Long channelId){
        try {
            channelMemberService.join(channelId);
            return Result.success();
        }catch (RuntimeException e){
            return Result.error(e.getMessage());
        }
    }
}
