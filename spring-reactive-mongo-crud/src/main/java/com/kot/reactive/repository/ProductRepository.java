package com.kot.reactive.repository;

import com.kot.reactive.dto.ProductDTO;
import com.kot.reactive.entity.ProductEntity;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<ProductEntity,String> {

    Flux<ProductDTO> findByPriceBetween(Range<Double> priceRange);
}
