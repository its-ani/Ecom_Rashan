package dev.anirudh.ecommerce.kafka.order;

import org.springframework.data.mongodb.core.aggregation.ArrayOperators;

import java.math.BigDecimal;

public record Product(
        Integer productId,
        String name,
        String description,
        BigDecimal price,
        double quantity
) {
}
