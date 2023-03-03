package com.kot.reactive.service;

import com.kot.reactive.dto.ProductDTO;
import com.kot.reactive.mapper.ProductDTOToEntityMapper;
import com.kot.reactive.mapper.ProductEntityToDtoMapper;
import com.kot.reactive.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductEntityToDtoMapper productEntityToDtoMapper;

    @Autowired
    private ProductDTOToEntityMapper productDTOToEntityMapper;

    public Flux<ProductDTO> getAllProducts(){
        return productRepository.findAll().map(productEntityToDtoMapper::convertEntityToDto);
    }

    public Mono<ProductDTO> getProduct(String id){
        return productRepository.findById(id).map(productEntityToDtoMapper::convertEntityToDto);
    }

    public Flux<ProductDTO> getProductInRange(double min, double max){
        return productRepository.findByPriceBetween(Range.closed(min,max));
    }

    public Mono<ProductDTO> saveProduct(Mono<ProductDTO> productDTOMono){
         return productDTOMono.map(productDTOToEntityMapper::convertDtoToEntity)
                .flatMap(productRepository::insert)
                .map(productEntityToDtoMapper::convertEntityToDto);
    }

    public Mono<ProductDTO> updateProduct(Mono<ProductDTO> productDTOMono, String id){
        return productRepository.findById(id)
                .flatMap(p-> productDTOMono.map(productDTOToEntityMapper::convertDtoToEntity))
                .doOnNext(e->e.setId(id))
                .flatMap(productRepository::save)
                .map(productEntityToDtoMapper::convertEntityToDto);
    }

    public Mono<Void> deleteById(String id){
        return productRepository.deleteById(id);
    }
}
