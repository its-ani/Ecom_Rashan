package dev.anirudh.ecommerce.kafka;

import dev.anirudh.ecommerce.customer.CustomerResponse;
import dev.anirudh.ecommerce.order.PaymentMethod;
import dev.anirudh.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
