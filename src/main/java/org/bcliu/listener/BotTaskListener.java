/*
package org.bcliu.listener;

import org.bcliu.service.DeepSeekService;
import org.bcliu.service.MessageService; // 注入消息服务，用来发送机器人的回复
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BotTaskListener {

    @Autowired
    private DeepSeekService deepSeekService;

    @Autowired
    private MessageService messageService; // 假设它有发送消息的方法

    @Autowired
    private ChannelMapper channelMapper; // 用来查找机器人的用户ID

    @RabbitListener(queues = "bot_tasks") // 监听名为 "bot_tasks" 的队列
    public void handleBotTask(Map<String, Object> task) {
        try {
            Long channelId = (Long) task.get("channelId");
            String prompt = (String) task.get("prompt");

            // 1. 调用 DeepSeek 服务获取回复
            String botReply = deepSeekService.getChatCompletion(prompt);

            // 2. 获取这个频道机器人的用户ID
            Long botUserId = channelMapper.findBotUserIdByChannelId(channelId);

            // 3. 将机器人的回复作为一条新消息，发送回频道
            if (botUserId != null && botReply != null && !botReply.isEmpty()) {
                // 调用你已有的消息服务来保存并广播这条来自机器人的消息
                messageService.handleBotMessage(channelId, botUserId, botReply);
            }

        } catch (Exception e) {
            // 记录异常，防止任务失败导致无限重试
            System.err.println("处理机器人任务失败: " + e.getMessage());
        }
    }
}
*/
