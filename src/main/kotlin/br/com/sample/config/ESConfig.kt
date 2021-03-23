package br.com.sample.config

import org.elasticsearch.client.RestHighLevelClient
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientProperties
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration.builder
import org.springframework.data.elasticsearch.client.RestClients.create
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration

@Configuration
class ESConfig(private val properties: ElasticsearchRestClientProperties) : AbstractElasticsearchConfiguration() {

    override fun elasticsearchClient(): RestHighLevelClient {
        val config = builder()
            .connectedTo(*this.properties.uris.toTypedArray())
            .build()
        return create(config).rest()
    }
}