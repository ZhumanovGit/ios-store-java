package ru.mirea.ios.store.iosstore.adapter.rest.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.ios.store.iosstore.adapter.rest.category.mapper.CategoryMapper;
import ru.mirea.ios.store.iosstore.adapter.rest.category.model.CategoryDto;
import ru.mirea.ios.store.iosstore.app.inbound.category.GetAllCategoriesInbound;
import ru.mirea.ios.store.iosstore.app.inbound.category.GetPopularCategoriesInbound;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/categories")
public class CategoryController {
    private final GetAllCategoriesInbound getAllCategoriesInbound;
    private final GetPopularCategoriesInbound getPopularCategoriesInbound;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        System.out.println("im working");
        var categories = getAllCategoriesInbound.handleRequest();
        var dtos = categories.stream()
                .map(categoryMapper::toDto)
                .toList();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<CategoryDto>> getPopularCategories(@RequestParam String userId,
                                                                  @RequestParam LocalDate start,
                                                                  @RequestParam LocalDate end) {
        var categories = getPopularCategoriesInbound.handleRequest(userId, start, end);
        var dtos = categories.stream()
                .map(categoryMapper::toDto)
                .toList();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}
