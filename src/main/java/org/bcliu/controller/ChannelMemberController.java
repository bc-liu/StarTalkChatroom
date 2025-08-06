package org.bcliu.controller;

import org.bcliu.pojo.Result;
import org.bcliu.service.ChannelMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/channels")
public class ChannelMemberController {
    @Autowired
    private ChannelMemberService channelMemberService;

    @PostMapping("/{channelId}/join")
    public Result join(@PathVariable Long channelId){
        try {
            channelMemberService.join(channelId);
            return Result.success();
        }catch (RuntimeException e){
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/{channelId}/leave")
    public Result leave(@PathVariable Long channelId){
        try {
            channelMemberService.leave(channelId);
            return Result.success();
        }catch (RuntimeException e){
            return Result.error(e.getMessage());
        }
    }
}
