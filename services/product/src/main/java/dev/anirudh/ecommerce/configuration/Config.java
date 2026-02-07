package dev.anirudh.ecommerce.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "dev.anirudh.ecommerce.repository")
@ComponentScan(basePackages = {"dev.anirudh.ecommerce"})
public class Config extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.uris}")
    public String elasticsearchUrl;

    @Bean
    @Override
    public ClientConfiguration clientConfiguration() {
        String hostAndPort = elasticsearchUrl.replace("http://", "").replace("https://", "");

        return ClientConfiguration.builder()
                .connectedTo(hostAndPort)
                .withConnectTimeout(10000)
                .withSocketTimeout(30000)
                .build();
    }
}
