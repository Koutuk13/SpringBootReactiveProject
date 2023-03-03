package com.kot.reactive.mapper;

import com.kot.reactive.dto.ProductDTO;
import com.kot.reactive.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductEntityToDtoMapper {

    ProductDTO convertEntityToDto(ProductEntity productEntity);
}

