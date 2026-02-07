package dev.anirudh.ecommerce.service;

import dev.anirudh.ecommerce.documents.ElasticProduct;
import dev.anirudh.ecommerce.product.Product;
import dev.anirudh.ecommerce.repository.ElasticProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ElasticProductService {
    private final ElasticProductRepository productRepository;

    public void save(final Product product) {
        ElasticProduct elasticProduct = toElasticProduct(product);
        productRepository.save(elasticProduct);
    }

    private ElasticProduct toElasticProduct(Product product) {
        ElasticProduct.ElasticCategory elasticCategory = null;

        if (product.getCategory() != null) {
            elasticCategory = ElasticProduct.ElasticCategory.builder()
                    .id(product.getCategory().getId())
                    .name(product.getCategory().getName())
                    .description(product.getCategory().getDescription())
                    .build();
        }

        return ElasticProduct.builder()
                .id(product.getId().toString())
                .name(product.getName())
                .description(product.getDescription())
                .availableQuantity(product.getAvailableQuantity())
                .price(product.getPrice())
                .category(elasticCategory)
                .build();
    }

    public ElasticProduct findById(final String id) {
        return productRepository.findById(id).orElse(null);
    }
}
