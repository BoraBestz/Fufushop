package best.fufushop.service;

import best.fufushop.dto.product.ProductCreateRequest;
import best.fufushop.dto.product.ProductResponse;
import best.fufushop.dto.product.ProductSearchRequest;
import best.fufushop.dto.product.ProductUpdateRequest;
import best.fufushop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {
    List<ProductResponse> findAll();
    Optional<Product> findById(Long id);
    ProductResponse save(ProductCreateRequest request);

    ProductResponse update(ProductUpdateRequest request);

    void deleteById(Long id);
    Page<ProductResponse> searchProducts(ProductSearchRequest request);
} 