package microservice.productservice.com.service;

import microservice.productservice.com.dto.ProductRequest;
import microservice.productservice.com.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest productRequest);

    ProductResponse updateProduct(ProductRequest productRequest, Long id);

    void deleteProduct(Long id);

    ProductResponse getProduct(Long id);

    List<ProductResponse> getAllProducts();
}
