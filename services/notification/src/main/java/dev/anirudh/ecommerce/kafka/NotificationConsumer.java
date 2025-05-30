package dev.anirudh.ecommerce.kafka;

import dev.anirudh.ecommerce.email.EmailService;
import dev.anirudh.ecommerce.kafka.order.OrderConfirmation;
import dev.anirudh.ecommerce.kafka.payment.PaymentConfirmation;
import dev.anirudh.ecommerce.notification.Notification;
import dev.anirudh.ecommerce.notification.NotificationRepository;
import dev.anirudh.ecommerce.notification.NotificationType;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static dev.anirudh.ecommerce.notification.NotificationType.ORDER_CONFIRMATION;
import static dev.anirudh.ecommerce.notification.NotificationType.PAYMENT_CONFIRMATION;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationRepository repository;

    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info(format("Consuming the message from payment-topic Topic: %s", paymentConfirmation));
        repository.save(Notification.builder()
                .type(PAYMENT_CONFIRMATION)
                .notificationDate(LocalDateTime.now())
                .paymentConfirmation(paymentConfirmation)
                .build());
        var customerName = paymentConfirmation.customerFirstName() + " " + paymentConfirmation.customerLastName();
        emailService.sendPaymentSuccessEmail(
                paymentConfirmation.customerEmail(),
                customerName,
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );
    }

    @KafkaListener(topics = "order-topic")
    public void consumerOrderConfirmationNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info(format("Consuming the message from order-topic Topic: %s", orderConfirmation));
        repository.save(
                Notification.builder()
                .type(ORDER_CONFIRMATION)
                .notificationDate(LocalDateTime.now())
                .orderConfirmation( orderConfirmation)
                .build());

        var customerName = orderConfirmation.customer().firstName() + " " + orderConfirmation.customer().lastName();
        emailService.sendOrderConfirmationEmail(
                orderConfirmation.customer().email(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );
    }


}
