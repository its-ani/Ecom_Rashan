package dev.anirudh.ecommerce.payment;

import dev.anirudh.ecommerce.customer.CustomerResponse;
import dev.anirudh.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
    BigDecimal amount,
    PaymentMethod paymentMethod,
    Integer orderId,
    String orderReference,
    CustomerResponse customer
){
}
