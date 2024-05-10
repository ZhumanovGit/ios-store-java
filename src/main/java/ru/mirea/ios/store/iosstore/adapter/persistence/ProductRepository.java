package ru.mirea.ios.store.iosstore.adapter.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.mirea.ios.store.iosstore.domain.product.Product;

import java.util.List;
import java.util.Set;

public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findAllByCategoryId(String categoryId);

    List<Product> findAllByTitle(String title);

    List<Product> findAllByIdIsNotIn(Set<String> ids);

    List<Product> findAllByTitleStartingWithIgnoreCase(String searchString);

    List<Product> findAllByIdIn(List<String> ids);
}
