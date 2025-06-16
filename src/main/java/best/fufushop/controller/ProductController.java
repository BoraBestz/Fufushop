package best.fufushop.controller;

import best.fufushop.dto.ApiResponse;
import best.fufushop.model.Product;
import best.fufushop.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody Product product) {
        Product savedProduct = productService.save(product);

        ApiResponse<Product> response = new ApiResponse<>();
        response.setStatus("success");
        response.setMessage("สร้างสินค้าสำเร็จ");
        response.setData(savedProduct);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Product>>> allProduct() {
        List<Product> products = productService.findAll();

        ApiResponse<List<Product>> response = new ApiResponse<>();
        response.setStatus("success");
        response.setMessage("Found " + products.size() + " products.");
        response.setData(products);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}