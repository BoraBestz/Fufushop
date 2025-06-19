package best.fufushop.controller;

import best.fufushop.dto.ApiResponse;
import best.fufushop.dto.product.ProductCreateRequest;
import best.fufushop.dto.product.ProductResponse;
import best.fufushop.dto.product.ProductSearchRequest;
import best.fufushop.dto.product.ProductUpdateRequest;
import best.fufushop.enums.Status;
import best.fufushop.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@RequestBody ProductCreateRequest request) {
        ProductResponse productResponse = productService.save(request);

        ApiResponse<ProductResponse> response = new ApiResponse<>();
        response.setStatus(Status.SUCCESS);
        response.setMessage("สร้างสินค้าสำเร็จ");
        response.setData(productResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> allProduct() {
        List<ProductResponse> productResponse = productService.findAll();

        ApiResponse<List<ProductResponse>> response = new ApiResponse<>();
        response.setStatus(Status.SUCCESS);
        response.setMessage("Found " + productResponse.size() + " products.");
        response.setData(productResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateRequest request) {
        request.setProductId(id);
        ProductResponse productResponse = productService.update(request);

        ApiResponse<ProductResponse> response = new ApiResponse<>();
        response.setStatus(Status.SUCCESS);
        response.setMessage("อัปเดตสินค้าสำเร็จ");
        response.setData(productResponse);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable Long id) {
        productService.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Product not found with id: " + id));
        productService.deleteById(id);

        ApiResponse<String> response = new ApiResponse<>();
        response.setStatus(Status.SUCCESS);
        response.setMessage("ลบสินค้าสำเร็จ");
        response.setData("Product with id " + id + " has been deleted.");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> searchProducts(@RequestBody ProductSearchRequest request) {

        Page<ProductResponse> resultSearch = productService.searchProducts(request);
        if (resultSearch.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No products found matching the keyword: " + request.getKeyword());
        }

        ApiResponse<Page<ProductResponse>> response = new ApiResponse<>();
        response.setStatus(Status.SUCCESS);
        response.setMessage("Found " + resultSearch.getTotalElements() + " products matching the keyword: " + request.getKeyword());
        response.setData(resultSearch);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}