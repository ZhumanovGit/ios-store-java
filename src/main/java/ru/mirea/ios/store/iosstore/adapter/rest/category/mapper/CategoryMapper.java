package ru.mirea.ios.store.iosstore.adapter.rest.category.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mirea.ios.store.iosstore.adapter.rest.category.model.CategoryDto;
import ru.mirea.ios.store.iosstore.domain.category.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toDto(Category category);
}
