package ru.mirea.ios.store.iosstore.adapter.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.ios.store.iosstore.domain.category.Category;

public interface CategoryRepository extends MongoRepository<Category, String> {
}
