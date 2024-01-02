package com.ecommerce.app.service;

import com.ecommerce.app.Exceptions.ProductNotExistsException;
import com.ecommerce.app.Repository.ProductRepo;
import com.ecommerce.app.dto.ProductDto;
import com.ecommerce.app.model.Category;
import com.ecommerce.app.model.Product;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepo productRepo;

    public void CreateProduct(ProductDto productDto, Category category) {
        Product product = new Product();
        product.setDescription(productDto.getDescription());
        product.setName((productDto.getName()));
        product.setPrice((productDto.getPrice()));
        product.setCategory(category);
        product.setImage_url(productDto.getImage_url());
        productRepo.save(product);
    }

    public ProductDto getProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setDescription(product.getDescription());
        productDto.setName((product.getName()));
        productDto.setPrice((product.getPrice()));
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setImage_url(product.getImage_url());
        productDto.setId(product.getId());
        return productDto;
    }
    public List<ProductDto> listProducts() {
        List<Product> products = productRepo.findAll();
        List <ProductDto> productDto = new ArrayList<>();
        for(Product product : products) {
            productDto.add(getProductDto(product));
        }
        return productDto;
     }


    public void updateProduct(Integer productId, ProductDto productDto) throws Exception {
        Product product = productRepo.getById(productId);
        product.setDescription(productDto.getDescription());
        product.setName((productDto.getName()));
        product.setPrice((productDto.getPrice()));
        product.setImage_url(productDto.getImage_url());
        productRepo.save(product);
    }

    public Product findById(Integer productId) throws ProductNotExistsException{
       Optional<Product> optionalProduct=  productRepo.findById(productId);
       if(optionalProduct.isEmpty()) {
           throw new ProductNotExistsException("Product Id is invalid" + productId);
       }
       return optionalProduct.get();
    }
}
