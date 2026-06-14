package com.vulinh.data.mapper;

import com.vulinh.data.dto.ProductDTO;
import com.vulinh.data.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

  ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

  ProductDTO toProductDTO(Product product);
}
