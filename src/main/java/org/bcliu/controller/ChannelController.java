package org.bcliu.controller;

import org.bcliu.dto.ChannelDTO;
import org.bcliu.dto.ChannelDetailDTO;
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

    @GetMapping("/joined")
    public Result<PageBean<Channel>> getJoinedChannelsList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ){
        PageBean<Channel> pb = channelService.getJoinedChannels(pageNum, pageSize);
        return Result.success(pb);
    }

    @GetMapping("/created")
    public Result<PageBean<Channel>> getCreatedChannelList(){
        return null;
    }

    @GetMapping("/{channelId}")
    public Result<ChannelDetailDTO> details(){
        return null;
    }
}
