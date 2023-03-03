package com.kot.reactive.controller;

import com.kot.reactive.dto.ProductDTO;
import com.kot.reactive.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public Flux<ProductDTO> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Mono<ProductDTO> getProduct(@PathVariable String id){
        return productService.getProduct(id);
    }

    @GetMapping("/product-range")
    public Flux<ProductDTO> getProductBetweenRange(@RequestParam("min") Double min, @RequestParam("max") Double max){
        return productService.getProductInRange(min,max);
    }

    @PostMapping
    public Mono<ProductDTO> saveProduct(@RequestBody Mono<ProductDTO> productDTO){
        return productService.saveProduct(productDTO);
    }

    @PutMapping("/update/{id}")
    public Mono<ProductDTO> updateProduct(@RequestBody Mono<ProductDTO> productDTO, @PathVariable String id){
        return productService.updateProduct(productDTO,id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteProduct(@PathVariable String id){
        return productService.deleteById(id);
    }
}
