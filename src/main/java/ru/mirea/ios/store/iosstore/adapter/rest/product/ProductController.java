package ru.mirea.ios.store.iosstore.adapter.rest.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.ios.store.iosstore.adapter.rest.product.mapper.ProductMapper;
import ru.mirea.ios.store.iosstore.adapter.rest.product.model.NewProductRequest;
import ru.mirea.ios.store.iosstore.adapter.rest.product.model.ProductDto;
import ru.mirea.ios.store.iosstore.app.inbound.product.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/products")
public class ProductController {
    private final GetAllProductsInCategoryInbound getAllProductsInCategoryInbound;
    private final GetProductsByTitleInbound getProductsByTitleInbound;
    private final PostProductToOrderInbound postProductToOrderInbound;
    private final PostProductInbound postProductInbound;
    private final GetUnpopularProductsByDateInbound getUnPopularProductsByDateInbound;
    private final SearchProductsByFilterInbound searchProductsByFilterInbound;
    private final ProductMapper productMapper;

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDto>> getAllProductsInCategory(@PathVariable String categoryId) {
        var products = getAllProductsInCategoryInbound.handleRequest(categoryId);
        var dto = products.stream()
                .map(productMapper::toDto)
                .toList();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ProductDto>> getProductsByTitle(@RequestParam String title) {
        var products = getProductsByTitleInbound.handleRequest(title);
        var dto = products.stream()
                .map(productMapper::toDto)
                .toList();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/bucket/{orderId}")
    public ResponseEntity<String> postProductToOrder(@PathVariable String orderId,
                                                     @RequestParam String productId,
                                                     @RequestParam String userId) {
        postProductToOrderInbound.handleRequest(orderId, productId, userId);
        return new ResponseEntity<>("Product with id = " + productId + " added to order with id = " + orderId, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> postProduct(@RequestBody NewProductRequest body,
                                              @RequestParam String employeeId) {
        var product = postProductInbound.handleRequest(body, employeeId);
        return new ResponseEntity<>("Created new product with id = " + product.getId(), HttpStatus.OK);
    }

    @GetMapping("/unpopular")
    public ResponseEntity<List<ProductDto>> getUnpopularProductsForLastMonth() {
        var products = getUnPopularProductsByDateInbound.handleRequest();
        var dto = products.stream()
                .map(productMapper::toDto)
                .toList();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProductsByName(@RequestParam String searchString) {
        var products = searchProductsByFilterInbound.handleRequest(searchString);
        var dto = products.stream()
                .map(productMapper::toDto)
                .toList();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
