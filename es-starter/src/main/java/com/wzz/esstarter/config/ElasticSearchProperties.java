package com.wzz.esstarter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ElasticSearchProperties
 *
 * @author wzz
 * @date 2020/6/18
 **/
@Data
@ConfigurationProperties(prefix = "wzz.elasticsearch")
public class ElasticSearchProperties {

    private String clusterName = "elasticsearch";

    private String clusterNodes = "127.0.0.1:9300";

    private String username = "elastic";

    private String password = "123456";

}
