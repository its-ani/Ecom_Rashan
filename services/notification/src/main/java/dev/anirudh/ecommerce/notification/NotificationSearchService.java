package dev.anirudh.ecommerce.notification;

import dev.anirudh.ecommerce.kafka.payment.PaymentMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationSearchService {

    private final ElasticsearchOperations elasticsearchOperations;

    public List<NotificationDocument> findAll() {
        Query query = new CriteriaQuery(new Criteria())
                .addSort(Sort.by(Sort.Direction.DESC, "notificationDate"));
        SearchHits<NotificationDocument> hits = elasticsearchOperations.search(query, NotificationDocument.class);
        return hits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .toList();
    }

    public List<NotificationDocument> findByType(NotificationType type) {
        Criteria criteria = new Criteria("type").is(type.name());
        Query query = new CriteriaQuery(criteria)
                .addSort(Sort.by(Sort.Direction.DESC, "notificationDate"));
        SearchHits<NotificationDocument> hits = elasticsearchOperations.search(query, NotificationDocument.class);
        return hits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .toList();
    }

    public List<NotificationDocument> searchByKeyword(String keyword) {
        Criteria criteria = new Criteria("customerName").matches(keyword)
                .or(new Criteria("orderReference").is(keyword));
        Query query = new CriteriaQuery(criteria)
                .addSort(Sort.by(Sort.Direction.DESC, "notificationDate"));
        SearchHits<NotificationDocument> hits = elasticsearchOperations.search(query, NotificationDocument.class);
        return hits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .toList();
    }

    public List<NotificationDocument> findByPaymentMethod(PaymentMethod paymentMethod) {
        Criteria criteria = new Criteria("paymentMethod").is(paymentMethod.name());
        Query query = new CriteriaQuery(criteria)
                .addSort(Sort.by(Sort.Direction.DESC, "notificationDate"));
        SearchHits<NotificationDocument> hits = elasticsearchOperations.search(query, NotificationDocument.class);
        return hits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .toList();
    }
}
