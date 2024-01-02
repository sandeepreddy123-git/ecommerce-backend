package com.ecommerce.app.controller;

import com.ecommerce.app.Repository.CategoryRepo;
import com.ecommerce.app.Repository.ProductRepo;
import com.ecommerce.app.common.ApiResponse;
import com.ecommerce.app.dto.ProductDto;
import com.ecommerce.app.model.Category;
import com.ecommerce.app.model.Product;
import com.ecommerce.app.service.ProductService;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
@Autowired
ProductService productService;
@Autowired
CategoryRepo categoryRepo;
@Autowired
ProductRepo productRepo;

@PostMapping("/create")
public ResponseEntity<ApiResponse> CreateProduct(@RequestBody ProductDto productDto){
    Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
    if(optionalCategory.isEmpty()) {
        return new ResponseEntity<>(new ApiResponse(false,"category doesn't exist"), HttpStatus.CONFLICT);
    }
    productService.CreateProduct(productDto, optionalCategory.get());
    return new ResponseEntity<>(new ApiResponse(true,"product successfully created"),HttpStatus.CREATED);
}
@GetMapping("/list")
public ResponseEntity<List<ProductDto>> listProducts() {
    List<ProductDto> products = productService.listProducts();
    return new ResponseEntity<>(products,HttpStatus.OK);
}
@PostMapping("/update/{productId}")
public ResponseEntity<ApiResponse> UpdateProduct(@PathVariable("productId") Integer productId, @RequestBody ProductDto productDto) throws Exception {
    Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
    Optional<Product> optionalProduct = productRepo.findById(productId);
    if(optionalCategory.isEmpty() || optionalProduct.isEmpty()) {
        return new ResponseEntity<>(new ApiResponse(false,"Category/Product Doesn't Exist"),HttpStatus.CONFLICT);
    }
    productService.updateProduct(productId, productDto);
    return new ResponseEntity<>(new ApiResponse(true,"Product Updated Successfully"),HttpStatus.OK);
}

@DeleteMapping("/delete{productId}/")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable int productId) {
    Optional<Product> optionalProduct = productRepo.findById(productId);
    if(optionalProduct.isEmpty()) {
        return new ResponseEntity<>(new ApiResponse(false, "Product not Exists"),HttpStatus.CONFLICT);
    }
    productRepo.delete(optionalProduct.get());
    return new ResponseEntity<>(new ApiResponse(true, "Product Successfully deleted"),HttpStatus.OK);
}

}