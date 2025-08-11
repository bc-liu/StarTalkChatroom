package org.bcliu.controller;

import org.apache.ibatis.annotations.Param;
import org.bcliu.dto.MessageDTO;
import org.bcliu.dto.MessageDetailDTO;
import org.bcliu.pojo.Message;
import org.bcliu.pojo.PageBean;
import org.bcliu.pojo.Result;
import org.bcliu.service.MessageService;
import org.bcliu.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/channels")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/{channelId}/messages")
    public Result send(@PathVariable Long channelId, @RequestBody MessageDTO messageDTO){
        Map<String, Object> map = ThreadLocalUtil.get();
        Object idObj = map.get("id");
        Long id = ((Number) idObj).longValue();

        try {
            messageService.send(channelId, id, messageDTO);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/{channelId}/messages")
    public Result<PageBean<MessageDetailDTO>> history(
            @PathVariable Long channelId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ){
        Map<String, Object> map = ThreadLocalUtil.get();
        Object idObj = map.get("id");
        Long id = ((Number) idObj).longValue();

        try {
            return Result.success(messageService.history(channelId, id, pageNum, pageSize));
        }catch (RuntimeException e){
            return Result.error(e.getMessage());
        }
    }
}
