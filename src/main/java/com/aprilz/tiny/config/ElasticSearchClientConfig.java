package com.aprilz.tiny.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchClientConfig {


    @Bean
    public ElasticsearchClient elasticsearchClient() {
        RestClient restClient = RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
                //设置登录验证
                .setHttpClientConfigCallback(httpAsyncClientBuilder -> {
                    //账密设置
                    CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                    //es账号密码（一般使用,用户elastic）
                    credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "123456"));
                    httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    return httpAsyncClientBuilder;
                })
                //设置超时
                .setRequestConfigCallback(builder -> builder.setConnectTimeout(5000).setSocketTimeout(5000))
                .build();
        RestClientTransport restClientTransport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        return new ElasticsearchClient(restClientTransport);
    }

}
