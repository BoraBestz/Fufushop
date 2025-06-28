package best.fufushop;

import best.fufushop.dto.product.ProductCreateRequest;
import best.fufushop.dto.product.ProductResponse;
import best.fufushop.dto.product.ProductUpdateRequest;
import best.fufushop.mapper.ProductRequestMapper;
import best.fufushop.mapper.ProductResponseMapper;
import best.fufushop.model.Product;
import best.fufushop.repository.ProductRepository;
import best.fufushop.service.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductRequestMapper productRequestMapper;

    @Mock
    private ProductResponseMapper productResponseMapper;


    @InjectMocks
    ProductServiceImpl productServiceImpl;

    @Test
    void findAll_ShouldReturnAllProduct() {
        Product product1 = new Product();
        product1.setProductId(1L);
        product1.setProductName("Product 1");
        product1.setProductPrice(BigDecimal.valueOf(100.0));
        Product product2 = new Product();
        product2.setProductId(2L);
        product2.setProductName("Product 2");
        product2.setProductPrice(BigDecimal.valueOf(200.0));

        when(productRepository.findAll()).thenReturn(List.of(product1, product2));
        when(productResponseMapper.toProductResponse(any(Product.class)))
                .thenAnswer(invocation -> {
                    Product p = invocation.getArgument(0);
                    return new ProductResponse(p.getProductId(), p.getProductName(), p.getProductPrice());
                });
        List<ProductResponse> products = productServiceImpl.findAll();

        assertEquals(2, products.size());
        assertEquals("Product 1", products.get(0).getProductName());
        assertEquals("Product 2", products.get(1).getProductName());
        assertEquals(BigDecimal.valueOf(100.0), products.get(0).getProductPrice());
        assertEquals(BigDecimal.valueOf(200.0), products.get(1).getProductPrice());

    }

    @Test
    void findById_ShouldReturnProduct() {
        Product product = new Product();
        product.setProductId(1L);
        product.setProductName("Product 1");
        product.setProductPrice(BigDecimal.valueOf(100.0));

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));


        Product productResponse = productServiceImpl.findById(1L).orElseThrow();

        assertEquals("Product 1", productResponse.getProductName());
        assertEquals(BigDecimal.valueOf(100.0), productResponse.getProductPrice());
    }

    @Test
    void save_ShouldReturnSavedProduct() {
        ProductCreateRequest request = new ProductCreateRequest();
        request.setProductName("New Product");
        request.setProductPrice(BigDecimal.valueOf(150.0));

        Product product = new Product();
        product.setProductId(1L);
        product.setProductName(request.getProductName());
        product.setProductPrice(request.getProductPrice());

        when(productRequestMapper.toProductRequest(any(ProductCreateRequest.class)))
                .thenAnswer(invocation -> {
                    ProductCreateRequest req = invocation.getArgument(0);
                    Product p = new Product();
                    p.setProductName(req.getProductName());
                    p.setProductPrice(req.getProductPrice());
                    return p;
                });

        when(productResponseMapper.toProductResponse(any(Product.class)))
                .thenAnswer(invocation -> {
                    Product p = invocation.getArgument(0);
                    return new ProductResponse(p.getProductId(), p.getProductName(), p.getProductPrice());
                });
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponse savedProduct = productServiceImpl.save(request);

        assertEquals("New Product", savedProduct.getProductName());
        assertEquals(BigDecimal.valueOf(150.0), savedProduct.getProductPrice());
    }

    @Test
    void update_ShouldReturnUpdatedProduct() {
        ProductUpdateRequest request = new ProductUpdateRequest();
        request.setProductId(1L);
        request.setProductName("Updated Product");
        request.setProductPrice(BigDecimal.valueOf(200.0));

        Product product = new Product();
        product.setProductId(request.getProductId());
        product.setProductName(request.getProductName());
        product.setProductPrice(request.getProductPrice());


        when(productRepository.findById(request.getProductId())).thenReturn(java.util.Optional.of(product));
        when(productRequestMapper.updateShop(any(Product.class), any(ProductUpdateRequest.class)))
                .thenAnswer(invocation -> {
                    Product p = invocation.getArgument(0);
                    ProductUpdateRequest req = invocation.getArgument(1);
                    p.setProductName(req.getProductName());
                    p.setProductPrice(req.getProductPrice());
                    return p;
                });

        when(productResponseMapper.toProductResponse(any(Product.class)))
                .thenAnswer(invocation -> {
                    Product p = invocation.getArgument(0);
                    return new ProductResponse(p.getProductId(), p.getProductName(), p.getProductPrice());
                });
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponse updatedProduct = productServiceImpl.update(request);

        assertEquals("Updated Product", updatedProduct.getProductName());
        assertEquals(BigDecimal.valueOf(200.0), updatedProduct.getProductPrice());
    }

    @Test
    void deleteById_ShouldDeleteProduct() {
        Long productId = 1L;

        productServiceImpl.deleteById(productId);

        verify(productRepository).deleteById(productId);
    }


}
