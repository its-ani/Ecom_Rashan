package dev.anirudh.ecommerce.notification;

import dev.anirudh.ecommerce.kafka.order.OrderConfirmation;
import dev.anirudh.ecommerce.kafka.order.Product;
import dev.anirudh.ecommerce.kafka.payment.PaymentConfirmation;
import dev.anirudh.ecommerce.kafka.payment.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "notification")
@Setting(settingPath = "static/es-settings.json")
public class NotificationDocument {

    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type = FieldType.Keyword)
    private NotificationType type;

    @Field(type = FieldType.Date)
    private LocalDateTime notificationDate;

    // Flattened searchable fields
    @Field(type = FieldType.Text)
    private String customerName;

    @Field(type = FieldType.Keyword)
    private String customerEmail;

    @Field(type = FieldType.Keyword)
    private String orderReference;

    @Field(type = FieldType.Keyword)
    private PaymentMethod paymentMethod;

    @Field(type = FieldType.Double)
    private BigDecimal amount;

    // Nested objects for full detail
    @Field(type = FieldType.Object)
    private OrderConfirmation orderConfirmation;

    @Field(type = FieldType.Object)
    private PaymentConfirmation paymentConfirmation;
}
