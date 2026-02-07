package dev.anirudh.ecommerce.repository;

import dev.anirudh.ecommerce.documents.ElasticProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticProductRepository extends ElasticsearchRepository<ElasticProduct, String> {
}
