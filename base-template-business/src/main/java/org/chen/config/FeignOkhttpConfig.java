package org.chen.config;

import org.chen.util.http.OkhttpUtils;
import feign.Feign;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feign配置okhttp作为客户端
 *  
 * @author YuChen
 * @date 2020/7/27 15:22
 **/

@Configuration
@ConditionalOnClass(Feign.class)
@AutoConfigureBefore(FeignAutoConfiguration.class)
public class FeignOkhttpConfig{


    @Bean
    public okhttp3.OkHttpClient okHttpClient(){
        return OkhttpUtils.getHttpClient();
    }
}
