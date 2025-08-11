package org.bcliu.controller;

import org.bcliu.dto.MessageDTO;
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
}
