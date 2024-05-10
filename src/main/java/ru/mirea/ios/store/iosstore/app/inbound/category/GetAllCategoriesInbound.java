package ru.mirea.ios.store.iosstore.app.inbound.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.ios.store.iosstore.adapter.persistence.CategoryRepository;
import ru.mirea.ios.store.iosstore.domain.category.Category;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllCategoriesInbound {
    private final CategoryRepository categoryRepository;

    public List<Category> handleRequest() {
        return categoryRepository.findAll();
    }
}
