package best.fufushop.service;

import best.fufushop.dto.product.ProductCreateRequest;
import best.fufushop.dto.product.ProductResponse;
import best.fufushop.dto.product.ProductSearchRequest;
import best.fufushop.dto.product.ProductUpdateRequest;
import best.fufushop.mapper.ProductRequestMapper;
import best.fufushop.mapper.ProductResponseMapper;
import best.fufushop.model.Product;
import best.fufushop.repository.ProductRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@Service
@NoArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductRequestMapper productRequestMapper;

    @Autowired
    private ProductResponseMapper productResponseMapper;


    @Override
    public List<ProductResponse> findAll() {
        List<Product> productInDb = productRepository.findAll();
        return productInDb.stream()
                .map(productResponseMapper::toProductResponse)
                .toList();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public ProductResponse save(ProductCreateRequest request) {
        Product product = productRequestMapper.toProductRequest(request);
        productRepository.save(product);
        return productResponseMapper.toProductResponse(product);
    }

    @Override
    public ProductResponse update(ProductUpdateRequest request) {
        Product productInDB = productRepository.findById(request.getProductId()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Product not found with id: " + request.getProductId()));

        Product productUpdate = productRequestMapper.updateShop(productInDB, request);
        productUpdate = productRepository.save(productUpdate);
        return productResponseMapper.toProductResponse(productUpdate);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<ProductResponse> searchProducts(ProductSearchRequest request) {
        int pageNumber = Math.max(request.getPageNumber()-1, 0);
        String sortBy = Optional.ofNullable(request.getSortBy()).orElse("id");
        Sort.Direction direction;
        try {
            direction = Sort.Direction.fromString(request.getSortDirection());
        } catch (IllegalArgumentException | NullPointerException e) {
            direction = Sort.Direction.ASC;
        }
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(pageNumber, request.getPageSize(), sort);
        String keyword = request.getKeyword();

        if (keyword == null || keyword.isEmpty()) {
            return productRepository.findAll(pageable).map(productResponseMapper::toProductResponse);
        }
        return productRepository.findByProductNameContainingIgnoreCase(keyword, pageable)
                .map(productResponseMapper::toProductResponse);
    }
}