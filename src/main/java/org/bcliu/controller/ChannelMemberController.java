package org.bcliu.controller;

import org.apache.ibatis.annotations.Delete;
import org.bcliu.pojo.Result;
import org.bcliu.service.ChannelMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{channelId}/leave")
    public Result leave(@PathVariable Long channelId){
        try {
            channelMemberService.leave(channelId);
            return Result.success();
        }catch (RuntimeException e){
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/{channelId}/members/{userId}")
    public Result kick(@PathVariable Long channelId, @PathVariable Long userId){
        try {
            channelMemberService.kick(channelId, userId);
            return Result.success();
        }catch (RuntimeException e){
            return Result.error(e.getMessage());
        }
    }
}
