package dev.anirudh.ecommerce.notification;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface NotificationSearchRepository extends ElasticsearchRepository<NotificationDocument, String> {
}
