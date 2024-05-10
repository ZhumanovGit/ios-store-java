package ru.mirea.ios.store.iosstore.domain.product;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.mirea.ios.store.iosstore.domain.category.Category;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String title;
    private String description;
    private double price;
    private String publisher;
    @DBRef
    private Category category;
}
