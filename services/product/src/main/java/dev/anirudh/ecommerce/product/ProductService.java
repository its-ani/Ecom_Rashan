package dev.anirudh.ecommerce.product;

import dev.anirudh.ecommerce.exception.ProductPurchaseException;
import dev.anirudh.ecommerce.service.ElasticProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ElasticProductService elasticProductService;
    private final ProductMapper mapper;

    public Integer createProduct(@Valid ProductRequest request) {
        var product = mapper.toProduct(request);
        Integer productId = repository.save(product).getId();
        elasticProductService.save(product);
        return productId;
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
        var productsId = request.stream().map(ProductPurchaseRequest::productId).toList();
        var storedProducts = repository.findAllByIdInOrderById(productsId);

        if(productsId.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more product do not exists.");
        }
        var sortedRequest = request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
        for (int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = sortedRequest.get(i);
            if (product.getAvailableQuantity() < productRequest.quantity()) {
                throw new ProductPurchaseException("Insufficient stock quantity for product with ID:: " + productRequest.productId());
            }
            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            repository.save(product);
            purchasedProducts.add(mapper.toproductPurchaseResponse(product, productRequest.quantity()));
        }
        return purchasedProducts;
    }

    public ProductResponse findById(Integer productId) {
//      Redis
        return repository.findById(productId)
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID::" + productId));
    }

//    Add Pagination
    public List<ProductResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .toList();
    }
}
