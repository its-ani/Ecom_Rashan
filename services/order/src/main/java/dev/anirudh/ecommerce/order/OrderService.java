package dev.anirudh.ecommerce.order;

import dev.anirudh.ecommerce.customer.CustomerClient;
import dev.anirudh.ecommerce.exception.BusinessException;
import dev.anirudh.ecommerce.orderline.OrderLine;
import dev.anirudh.ecommerce.orderline.OrderLineRequest;
import dev.anirudh.ecommerce.orderline.OrderLineService;
import dev.anirudh.ecommerce.product.ProductClient;
import dev.anirudh.ecommerce.product.PurchaseRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;

    public Integer createOrder(@Valid OrderRequest request) {
        var customer = this.customerClient.findCustomerById(request.customerId())
                            .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with the provided id."));

        this.productClient.purchaseProducts(request.products());
        var order = this.repository.save(mapper.toOrder(request));

        for(PurchaseRequest purchaseRequest: request.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        return null;
    }
}