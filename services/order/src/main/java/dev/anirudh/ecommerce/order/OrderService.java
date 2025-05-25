package dev.anirudh.ecommerce.order;

import dev.anirudh.ecommerce.customer.CustomerClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;

    public Integer createOrder(@Valid OrderRequest request) {
        return null;
    }
}
