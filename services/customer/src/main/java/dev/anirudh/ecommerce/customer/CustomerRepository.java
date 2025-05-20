package dev.anirudh.ecommerce.customer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {
}
