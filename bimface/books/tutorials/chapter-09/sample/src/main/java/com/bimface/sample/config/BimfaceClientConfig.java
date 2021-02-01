package com.bimface.sample.config;

import com.bimface.sdk.BimfaceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 初始化BimfaceClient
 * 在BIMFACE官网注册并创建一个App，即可获取AppKey和AppSecret
 * 在配置文件application.properties中配置AppKey和AppSecret，用于创建BimfaceClient
 */
@Configuration
public class BimfaceClientConfig {
    /**
     * 从配置文件中读取AppKey和AppSecret
     */
    @Value("${app.key}")
    private String appKey;
    @Value("${app.secret}")
    private String appSecret;

    /**
     * 初始化BimfaceClient，并注册为一个Bean
     */
    @Bean
    public BimfaceClient bimfaceClient(){
        return new BimfaceClient(appKey, appSecret);
    }
}
