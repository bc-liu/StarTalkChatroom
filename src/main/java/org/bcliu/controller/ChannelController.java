package org.bcliu.controller;

import org.bcliu.dto.ChannelDTO;
import org.bcliu.pojo.Result;
import org.bcliu.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
