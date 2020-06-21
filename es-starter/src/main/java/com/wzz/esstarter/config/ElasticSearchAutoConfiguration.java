package com.wzz.esstarter.config;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * ElasticSearchAutoConfiguration
 * 对于实现了 DisposableBean 的 bean ，在spring释放该bean后调用它的destroy() 方法。
 * @author wzz
 * @date 2020/6/18
 **/
@Slf4j
@Configuration
@EnableConfigurationProperties(ElasticSearchProperties.class)
public class ElasticSearchAutoConfiguration implements DisposableBean {

    private TransportClient transportClient;

    @Autowired
    private ElasticSearchProperties properties;

    /**
     * 当Spring容器中没有TransportClient类的对象时，调用transportClient()创建对象;
     */
    @Bean
    @ConditionalOnBean(TransportClient.class)
    public TransportClient transportClient(){
        log.debug("======={}", properties.getClusterName());
        log.debug("======={}", properties.getClusterNodes());
        log.debug("======={}", properties.getUsername());
        log.debug("======={}", properties.getPassword());
        log.info("开始建立elasticsearch连接");
        transportClient = new PreBuiltTransportClient(settings());
        String[] clusterNodes = properties.getClusterNodes().split(",");
        List<TransportAddress> addressList = new ArrayList<>();
        for(String clusterNode : clusterNodes){
            String[] addressPortPairs = clusterNode.split(":");
            String address = addressPortPairs[0];
            Integer port = Integer.valueOf(addressPortPairs[1]);
            try{
                addressList.add(new TransportAddress(InetAddress.getByName(address), port));
            } catch (UnknownHostException e) {
                log.error("连接ElasticSearch失败: {}", e);
                throw new RuntimeException ("连接ElasticSearch失败: {}", e);
            }
        }
        transportClient.addTransportAddress(addressList.get(0));
        return transportClient;
    }

    private Settings settings() {
        return Settings.builder()
                .put("cluster.name", properties.getClusterName())
                .put("xpack.security.user", properties.getUsername() + ":" + properties.getPassword())
                .build();
    }

    @Override
    public void destroy() throws Exception {
        log.info("开始释放elasticsearch连接");
        if(transportClient != null){
            transportClient.close();
        }
    }
}
