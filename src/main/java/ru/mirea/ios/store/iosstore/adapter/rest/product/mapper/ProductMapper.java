package ru.mirea.ios.store.iosstore.adapter.rest.product.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mirea.ios.store.iosstore.adapter.rest.product.model.NewProductRequest;
import ru.mirea.ios.store.iosstore.adapter.rest.product.model.ProductDto;
import ru.mirea.ios.store.iosstore.domain.category.Category;
import ru.mirea.ios.store.iosstore.domain.product.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "category.title", target = "categoryName")
    ProductDto toDto(Product product);

}
