package org.bcliu.controller;

import org.apache.ibatis.annotations.Delete;
import org.bcliu.dto.MuteRequestDTO;
import org.bcliu.pojo.Result;
import org.bcliu.service.ChannelMemberService;
import org.bcliu.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @PostMapping("/{channelId}/members/{userId}/mute")
    public Result mute(@PathVariable Long channelId, @PathVariable Long userId, @RequestBody @Validated MuteRequestDTO muteRequestDTO){
        try {
            Map<String, Object> map = ThreadLocalUtil.get();
            Object idObj = map.get("id");
            Long operatorId = ((Number) idObj).longValue();

            channelMemberService.mute(channelId, operatorId, userId, muteRequestDTO);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/{channelId}/members/{userId}/mute")
    public Result dismute(@PathVariable Long channelId, @PathVariable Long userId){
        try {
            Map<String, Object> map = ThreadLocalUtil.get();
            Object idObj = map.get("id");
            Long operatorId = ((Number) idObj).longValue();

            channelMemberService.dismute(channelId, operatorId, userId);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/{channelId}/members/{userId}/admin")
    public Result setAdmin(@PathVariable Long channelId, @PathVariable Long userId){
        try {
            Map<String, Object> map = ThreadLocalUtil.get();
            Object idObj = map.get("id");
            Long operatorId = ((Number) idObj).longValue();

            channelMemberService.setAdmin(channelId, userId, operatorId);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/{channelId}/members/{userId}/admin")
    public Result disAdmin(@PathVariable Long channelId, @PathVariable Long userId){
        try {
            Map<String, Object> map = ThreadLocalUtil.get();
            Object idObj = map.get("id");
            Long operatorId = ((Number) idObj).longValue();

            channelMemberService.disAdmin(channelId, userId, operatorId);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
