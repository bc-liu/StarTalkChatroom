package org.bcliu.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

    // 使用 @Bean 注解，告诉 Spring：请执行这个方法，
    // 并将它返回的对象（一个配置好的WebClient实例）放入容器中，
    // 以后谁需要 WebClient，就把这个实例注入给它。
    @Bean
    public WebClient webClient(DeepSeekConfig deepSeekConfig) { // Spring会自动注入你之前创建的配置类

        // 配置底层的HttpClient，设置超时等参数
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000) // 连接超时：10秒
                .responseTimeout(Duration.ofSeconds(60)) // 响应超时：60秒
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(60, TimeUnit.SECONDS)) // 读取超时
                                .addHandlerLast(new WriteTimeoutHandler(60, TimeUnit.SECONDS)) // 写入超时
                );

        return WebClient.builder()
                // 1. 设置基础URL，所有请求都会基于这个地址
                //    这样在调用时，只需写 /chat/completions 这样的相对路径
                .baseUrl(deepSeekConfig.getBaseUrl())

                // 2. 设置默认的请求头
                //    例如，所有请求都自动带上 Content-Type: application/json
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")

                // 3. 将配置好的HttpClient应用到WebClient
                .clientConnector(new ReactorClientHttpConnector(httpClient))

                .build();
    }
}
