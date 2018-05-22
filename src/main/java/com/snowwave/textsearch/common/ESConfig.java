package com.snowwave.textsearch.common;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * Created by zhangfuqiang on 2018/1/22.
 */
@Configuration
public class ESConfig {

    @Bean
    public TransportClient client() throws UnknownHostException{

        InetSocketTransportAddress node = new InetSocketTransportAddress(
                InetAddress.getByName("localhost"),
                9300
        );
        //这里可以new多个node 添加到client中

        Settings settings = Settings.builder()
                .put("cluster.name","text-search")
                .build();

        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(node);
        return client;
    }

}
